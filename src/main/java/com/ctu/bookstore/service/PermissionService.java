package com.ctu.bookstore.service;

import com.ctu.bookstore.dto.request.PermissionRequest;
import com.ctu.bookstore.dto.respone.PermissionRespone;
import com.ctu.bookstore.entity.Permission;
import com.ctu.bookstore.mapper.PermissionMapper;
import com.ctu.bookstore.repository.PermissionRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PermissionService {
    PermissionRepository permissionRepository;
    PermissionMapper permissionMapper;

    public PermissionRespone create(PermissionRequest request){
        Permission permission = permissionMapper.toPermission(request);
        permission = permissionRepository.save(permission);
        return permissionMapper.toPermissionRespone(permission);
    }

    public List<PermissionRespone> getAll(){
        var permissions = permissionRepository.findAll();
        return permissions.stream().map(permissionMapper::toPermissionRespone).toList();
    }

    public void delete(String permission){
        permissionRepository.deleteById(permission);
    }
}