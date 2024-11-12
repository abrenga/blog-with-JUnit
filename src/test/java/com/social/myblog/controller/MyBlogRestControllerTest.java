package com.social.myblog.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.social.myblog.DTOs.PostRequestDTO;
import com.social.myblog.DTOs.PostResponseDTO;
import com.social.myblog.model.Post;
import com.social.myblog.model.User;
import com.social.myblog.repository.PostRepo;
import com.social.myblog.repository.UserRepo;
import com.social.myblog.service.MyBlogService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.servlet.HandlerExceptionResolver;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
    public void MyBlogRestController_GetAllPosts_ok() throws Exception {

        mockMvc = MockMvcBuilders.standaloneSetup(myBlogRestController).build();

        List<Post> listaPostFittizzi = Arrays.asList(
                new Post(1, "titolo", "contenuto", new User(), "/uploads"),
                new Post(2, "titoloDue", "contenutoDue", new User(), "/uploads")
        );

        when(myBlogService.getAllPosts()).thenReturn(listaPostFittizzi);

        mockMvc.perform(get("/index")
            .contentType(MediaType.APPLICATION_JSON)
                //controlla perche non accetta andExpect
        );
    }

    @Test
    public void MyBlogRestController_GetPostById_ok() throws Exception {

        //mockMvc = MockMvcBuilders.standaloneSetup(myBlogRestController).build();

        Post post = new Post(1, "titolo", "contenuto", new User(), "/uploads");

        when(myBlogService.getPostById(1))
                .thenReturn(post);

        mockMvc.perform(
            get("/index/{id}", 1))
            .andExpect(status().isOk()
        );


    }
}
