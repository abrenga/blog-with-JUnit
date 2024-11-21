package com.social.myblog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.json.JsonMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.social.myblog.DTOs.PostRequestDTO;
import com.social.myblog.DTOs.PostResponseDTO;
import com.social.myblog.model.Post;
import com.social.myblog.model.User;
import com.social.myblog.service.MyBlogService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

;

@WebMvcTest(controllers = MyBlogRestController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class MyBlogRestControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private MyBlogService myBlogService;


    @MockBean
    private MyBlogRestController myBlogRestController;

    @Autowired
    private ObjectMapper objectMapper;

    @Spy
    private PostRequestDTO postRequestDTO;

    @Spy
    private PostResponseDTO postResponseDTO;

    @Spy
    private Post post;


    @Test
    public void MyBlogController_addMyBlogPost_ok() throws Exception {

        PostRequestDTO postProva = new PostRequestDTO(1, "titolo", "contenuto", 1, LocalDate.now(), "/uploads");
        ObjectMapper mapper = JsonMapper.builder()
                .addModule(new JavaTimeModule())
                .build();
        String json = mapper.writeValueAsString(postProva);
        Post post = new Post(1, "titolo", "contenuto", new User(), "/uploads");

        when(myBlogService.uploadURl(any())).thenReturn(post.getUrl());

        when(myBlogService.createPost(postProva, post.getUrl()))
                .thenReturn(post);

        ResultActions boh = mockMvc.perform(
                multipart("/")
                        .file(new MockMultipartFile("dto", "filedt", "application/json", json.getBytes()))
                        .file(new MockMultipartFile("json", "yuygyyg", "multipart/form-data", "{\"json\": \"someValue\"}".getBytes())));

        boh.andExpect(status().is(200));


    }



    @Test
    public void MyBlogRestController_GetAllPosts_ok() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(myBlogRestController).build();

        List<Post> listaPostFittizzi = Arrays.asList(
                new Post(1, "titolo", "contenuto", new User(), "/uploads"),
                new Post(2, "titoloDue", "contenutoDue", new User(), "/uploads")
        );

        when(myBlogService.getAllPosts()).thenReturn(listaPostFittizzi);

        mockMvc.perform(get("/")
            .contentType(MediaType.APPLICATION_JSON)

        ).andExpect(status().isOk());
    }

    @Test
    public void MyBlogRestController_GetPostById_ok() throws Exception {

        //mockMvc = MockMvcBuilders.standaloneSetup(myBlogRestController).build();

        Post post = new Post(1, "titolo", "contenuto", new User(), "/uploads");

        when(myBlogService.getPostById(1))
                .thenReturn(post);

        mockMvc.perform(
                        get("/{id}", 1))
                .andExpect(status().isOk());


    }


    @Test
    public void MyBlogPost_updateThePost_ok() throws Exception {
        PostResponseDTO responseDTO = new PostResponseDTO(1, "titolo", "contenuto", "pino", "/uploads");
        Post post = new Post(1, "titolo", "contenuto", new User(), "/uploads");
        PostRequestDTO postDto = new PostRequestDTO(1, "titolo", "contenuto", 1, LocalDate.now(), "/uploads");
        when(myBlogService.updatePost(postDto, 1)).thenReturn(post);

        mockMvc.perform(post("/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(responseDTO))
        );
    }

    @Test
    public void MyBlogRestController_deletePost_ok() throws Exception {
        Post post = new Post(1, "titolo", "contenuto", new User(), "/uploads");
        mockMvc.perform(post("/delete/{id}", 1))
                .andExpect(status().is(200));

    }

}
