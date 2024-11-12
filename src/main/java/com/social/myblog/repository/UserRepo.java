package com.social.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.social.myblog.model.User;

import java.util.Optional;


public interface UserRepo extends JpaRepository<User,Integer> {
    Optional<User> findByUsername(String username);
}