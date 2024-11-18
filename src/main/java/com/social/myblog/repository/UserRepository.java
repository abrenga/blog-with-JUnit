package com.social.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.social.myblog.model.User;

public interface UserRepository  extends JpaRepository<User, Long> {
    User findByUsername(String username);

}
