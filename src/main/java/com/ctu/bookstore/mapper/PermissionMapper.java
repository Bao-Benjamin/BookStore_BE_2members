package com.ctu.bookstore.mapper;

import com.ctu.bookstore.dto.request.PermissionRequest;
import com.ctu.bookstore.dto.respone.PermissionRespone;
import com.ctu.bookstore.entity.Permission;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface PermissionMapper {
    Permission toPermission(PermissionRequest permissionRequest);
    PermissionRespone toPermissionRespone(Permission permission);
}
