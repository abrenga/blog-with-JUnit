package com.social.myblog.repository;

import com.social.myblog.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import com.social.myblog.model.Post;

import java.util.List;

public interface PostRepo extends JpaRepository<Post, Integer>{
    List<Post> findByUserId(Integer userId);
}