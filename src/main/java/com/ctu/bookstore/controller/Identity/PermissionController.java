package com.ctu.bookstore.controller;


import com.ctu.bookstore.dto.request.PermissionRequest;
import com.ctu.bookstore.dto.respone.ApiRespone;
import com.ctu.bookstore.dto.respone.PermissionRespone;
import com.ctu.bookstore.service.PermissionService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/permissions")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class PermissionController {
    PermissionService permissionService;

    @PostMapping
    ApiRespone<PermissionRespone> create(@RequestBody PermissionRequest request){
        return ApiRespone.<PermissionRespone>builder()
                .result(permissionService.create(request))
                .build();
    }

    @GetMapping
    ApiRespone<List<PermissionRespone>> getAll(){
        return ApiRespone.<List<PermissionRespone>>builder()
                .result(permissionService.getAll())
                .build();
    }

    @DeleteMapping("/{permission}")
    ApiRespone<Void> delete(@PathVariable String permission){
        permissionService.delete(permission);
        return ApiRespone.<Void>builder().build();
    }
}