package com.ctu.bookstore.mapper;

import com.ctu.bookstore.dto.request.RoleRequest;
import com.ctu.bookstore.dto.respone.RoleRespone;
import com.ctu.bookstore.entity.Role;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RoleMapper {
    @Mapping(target = "permissions", ignore = true)
    Role toRole(RoleRequest roleRequest);

    RoleRespone toRoleRespone(Role role);
}
