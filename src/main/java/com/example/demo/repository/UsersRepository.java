package com.example.demo.repository;

import com.example.demo.model.Users;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UsersRepository extends JpaRepository<Users, Long> {
    // Custom query methods can be added here
    Optional<Users> findByEmail(String email);

    boolean existsByEmail(String email);

    Optional<Users> findByUsername(String username);

    @Query("SELECT ur.roleId FROM users_roles ur WHERE ur.user_Id = :userId")
    Optional<Long> findRoleIdByUserId(@Param("userId") Long userId);
}
