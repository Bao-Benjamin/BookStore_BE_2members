package com.ctu.bookstore.repository;

import com.ctu.bookstore.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, String> {
     boolean existsByUsername(String username);
}
