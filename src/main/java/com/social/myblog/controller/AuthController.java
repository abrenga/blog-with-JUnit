package com.social.myblog.controller;


import com.social.myblog.DTOs.PostResponseDTO;
import com.social.myblog.model.LoginRequest;
import com.social.myblog.model.LoginResponse;
import com.social.myblog.model.Post;
import com.social.myblog.model.User;
import com.social.myblog.repository.PostRepo;
import com.social.myblog.repository.UserRepo;
import com.social.myblog.security.JwtIssuer;
import com.social.myblog.security.UserPrincipal;
import com.social.myblog.service.IMyBlogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class AuthController {

    private final JwtIssuer jwtiuser;
    private final UserRepo userrepository;
    private final IMyBlogService postService;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody @Validated LoginRequest request) {
        if (request.getUsername().isEmpty() || request.getPassword().isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        Optional<User> user = userrepository.findByUsername(request.getUsername());
        if (user.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }
        if (!user.get().getPassword().equals(request.getPassword())) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
        }

        var token = jwtiuser.issue(1, request.getUsername(), List.of("USER"));
        return new ResponseEntity<>(LoginResponse.builder()
                .accessToken(token)
                .build(), HttpStatus.OK);

    }

    @GetMapping("/my-posts")
    public List<PostResponseDTO> logged(@RequestHeader("Authorization")String token) {
        Integer userId=getUserIdFromToken(token);
        List<Post> post=  postService.getPostsByUserId(userId);
        List<PostResponseDTO> postResponseDTOList=new ArrayList<>();
        for (Post post1 : post) {
            PostResponseDTO postResponseDTO=new PostResponseDTO();
            postResponseDTO.setUrl(post1.getUrl());
            postResponseDTO.setTitle(post1.getTitle());
            postResponseDTO.setContent(post1.getContent());
            postResponseDTO.setAuthor(post1.getAuthor().getUsername());
            postResponseDTOList.add(postResponseDTO);
        }

         return postResponseDTOList;

    }


    private Integer getUserIdFromToken(String token){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserPrincipal currentPrincipalName = (UserPrincipal)authentication.getPrincipal();
        return currentPrincipalName.getUserId();

    }
}
