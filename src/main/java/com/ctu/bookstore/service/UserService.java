package com.ctu.bookstore.service;

import com.ctu.bookstore.dto.request.UserRequest;
import com.ctu.bookstore.dto.request.UserUpdateRequest;
import com.ctu.bookstore.dto.respone.UserRespone;
import com.ctu.bookstore.entity.User;
//import com.ctu.bookstore.entity.Role;
import com.ctu.bookstore.enums.Role;
import com.ctu.bookstore.exception.AppException;
import com.ctu.bookstore.exception.ErrorCode;
import com.ctu.bookstore.mapper.RoleMapper;
import com.ctu.bookstore.mapper.UserMapper;
import com.ctu.bookstore.repository.RoleRepository;
import com.ctu.bookstore.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserMapper userMapper;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    RoleMapper roleMapper;

    @Autowired
    private RoleRepository roleRepository;
    public UserRespone createUser(UserRequest userRequest){
        if (userRepository.existsByUsername(userRequest.getUsername()))
            throw new AppException(ErrorCode.USER_EXISTED);

        User user = userMapper.toUser(userRequest);
        System.out.println("User trong CreateUser, userService: "+ user);
        System.out.println("Password TRƯỚC ENCODE: " + user.getPassword());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        System.out.println("Password SAU ENCODE: " + user.getPassword());
        HashSet<String> roles = new HashSet<>();
        roles.add(Role.USER.name());

//        user.setRoles(roles);

       return  userMapper.toUserRespone(userRepository.save(user));

    }
//    @PreAuthorize("hasRole('ADMIN8')"
    @PreAuthorize("hasAuthority('APPROVE_POST')")
    public List<UserRespone> getUsers(){
        return userRepository.findAll().stream()
                .map(userMapper::toUserRespone).toList();
    }
    public UserRespone getUser(String id){
        log.info("In method get user by Id");
        return userMapper.toUserRespone(userRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED)));
    }
    @PostAuthorize("returnObject.username == authentication.name")
    public UserRespone getMyInfor(){
        SecurityContextHolder securityContextHolder = new SecurityContextHolder();
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        User user = userRepository.findByUsername(name).orElseThrow(()-> new AppException(ErrorCode.USER_NOT_EXISTED));
        return userMapper.toUserRespone(user);
    }
    public UserRespone updateUser(String id, UserUpdateRequest userUpdateRequest){
         User user = userRepository.findById(id)
                 .orElseThrow(() -> new AppException(ErrorCode.USER_NOT_EXISTED));
         userMapper.updateUser(user,userUpdateRequest);

         user.setPassword(passwordEncoder.encode(userUpdateRequest.getPassword()));

         var roles = roleRepository.findAllById(userUpdateRequest.getRoles());
         user.setRoles(new HashSet<>(roles));
        System.out.println("User trong updateUser UserService: "+ user);
         return userMapper.toUserRespone(userRepository.save(user));


    }
}
