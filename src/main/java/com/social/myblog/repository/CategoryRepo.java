package com.social.myblog.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.social.myblog.model.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{
}