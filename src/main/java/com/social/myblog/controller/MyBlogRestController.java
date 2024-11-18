package com.social.myblog.controller;

import com.social.myblog.DTOs.PostRequestDTO;
import com.social.myblog.DTOs.PostResponseDTO;
import com.social.myblog.model.Post;
import com.social.myblog.repository.UserRepo;
import com.social.myblog.service.IMyBlogService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController("index")
public class MyBlogRestController {

    @Autowired
    private IMyBlogService myBlogService;
    @Autowired
    private UserRepo userRepo;


    @GetMapping
    public ResponseEntity<List<PostResponseDTO>> getMyBlogPosts() {
        List<Post> posts = myBlogService.getAllPosts();
        List<PostResponseDTO> postResponseDTOs = new ArrayList<>();
        /*for (Post post : posts) {
            PostResponseDTO postDTO = new PostResponseDTO();
            postDTO.setAuthor(post.getAuthor());
            postDTO.setTitle(post.getTitle());
            postDTO.setContent(post.getContent());
            postDTO.setDate(post.getDate());
            postResponseDTOs.add(postDTO);
        }*/

        postResponseDTOs = posts.stream().map((post) -> {
            PostResponseDTO postDTO = new PostResponseDTO();
            postDTO.setAuthor(post.getAuthor());
            postDTO.setTitle(post.getTitle());
            postDTO.setContent(post.getContent());
            postDTO.setDate(post.getDate());
            postDTO.setImage(post.getUrl());
            return postDTO;
        }).toList();
        return ResponseEntity.ok(postResponseDTOs);
    }


    @GetMapping("/{id}")
    public ResponseEntity<PostRequestDTO> getMyBlogPostById(@PathVariable Integer id) throws Exception {
        Post post = myBlogService.getPostById(id);
        PostRequestDTO response = new PostRequestDTO();
        response.setAuthor(post.getAuthor());
        response.setTitle(post.getTitle());
        response.setContent(post.getContent());


        if (post != null) {
            return new ResponseEntity<>(response, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

    }

    @PostMapping//(consumes = { MediaType.MULTIPART_FORM_DATA_VALUE })
    public ResponseEntity<PostResponseDTO> addMyBlogPost(@RequestPart(value = "dto") PostRequestDTO request, @RequestPart(value = "json") MultipartFile file) throws Exception {
        if (file.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            String percorso = myBlogService.uploadURl(file);
            myBlogService.createPost(request, percorso);

            PostResponseDTO response = new PostResponseDTO();
            response.setAuthor(request.getAuthor());
            response.setContent(request.getContent());
            response.setTitle(request.getTitle());
            response.setImage(percorso);
            response.inserito = true;

            return new ResponseEntity<>(response, HttpStatus.OK);
            //return new ResponseEntity<>(percorso.toString(),HttpStatus.OK);


        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);

        }
    }


    @PostMapping("/{id}")
    public ResponseEntity<PostResponseDTO> updateThePost(@PathVariable Integer id, @RequestBody PostRequestDTO request) throws Exception {

        Post postModificato = myBlogService.updatePost(request, id);

        PostResponseDTO response = new PostResponseDTO();
        response.setAuthor(postModificato.getAuthor());
        response.setContent(postModificato.getContent());
        response.setTitle(postModificato.getTitle());

        return new ResponseEntity<>(response, HttpStatus.OK);

    }


    @PostMapping("/delete/{id}")
    public void deleteMyBlogPost(@PathVariable Integer id) throws Exception {
        myBlogService.deletePost(id);
    }


}




