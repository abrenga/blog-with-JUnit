package com.social.myblog.service;

import com.social.myblog.DTOs.PostRequestDTO;
import com.social.myblog.model.Post;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IMyBlogService {
    public Post createPost(PostRequestDTO post, String url);
    public Post updatePost(PostRequestDTO post, Integer id) throws Exception;
    public Post getPostById(Integer id) throws Exception;
    public List<Post> getAllPosts();

    public void deletePost(Integer id);
    public String uploadURl(MultipartFile file) throws IOException;

    List<Post> getPostsByUserId(Integer userId);
}
