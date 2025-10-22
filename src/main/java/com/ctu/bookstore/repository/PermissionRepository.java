package com.ctu.bookstore.repository;

import com.ctu.bookstore.entity.Permission;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface PermissionRepository extends JpaRepository<Permission, String> {

}
