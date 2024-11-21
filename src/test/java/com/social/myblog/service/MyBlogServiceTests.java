package com.social.myblog.service;

import com.social.myblog.DTOs.PostRequestDTO;
import com.social.myblog.model.Post;
import com.social.myblog.model.User;
import com.social.myblog.repository.PostRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.stubbing.Answer;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.doAnswer;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
public class MyBlogServiceTests {
    //qui mi prendo il repository dove lavorare
    @Spy
    private PostRepo postRepo;

    @Spy
    private Post post;

    @Spy
    private PostRequestDTO postRequest;

    //qui mi prendo il service
    @InjectMocks
    private MyBlogService myBlogService;


    @Test
    public void myBlogService_createPost_returnPostOk() {
        post.setTitle("My Blog");
        post.setContent("My Blog Content");
        post.setAuthor(new User());
        post.setDate(LocalDate.now());
        post.setUrl("url/finto");

        postRequest.setTitle("My Blog");
        postRequest.setContent("My Blog Content");
        postRequest.setAuthorId(1);
        postRequest.setDate(LocalDate.now());
        postRequest.setUrl("url/finto");

        Assertions.assertNotEquals(0, post.getId());

        /*Post postInserito = myBlogService.createPost(post);

        Assertions.assertTrue(postInserito!=null);
        Assertions.assertTrue(postInserito.getId() !=0);

       Optional<Post> postCreato= postRepo.findById(postInserito.getId());
        Assertions.assertTrue(postCreato.isPresent());*/

        when(postRepo.save(post)).thenReturn(post);
        Post returnedPost = myBlogService.createPost(postRequest, post.getUrl());
        Assertions.assertNotNull(returnedPost);
        Assertions.assertEquals(post.getTitle(), returnedPost.getTitle());
        Assertions.assertEquals(post.getContent(), returnedPost.getContent());
        Assertions.assertEquals(post.getAuthor(), returnedPost.getUser());
        Assertions.assertEquals(post.getDate(), returnedPost.getDate());
        Assertions.assertEquals(post.getId(), returnedPost.getId());
    }

    @Test
    public void myBlogService_createPost_fail() {
        Post post = new Post(1, "My Blog", "My bog content", new User(), "rappresenta/url");
        PostRequestDTO postRequest = new PostRequestDTO(1, "My Blog", "My bog content", 1, LocalDate.now(), "rappresenta/url");

        Assertions.assertNotEquals(0, post.getId());
        when(postRepo.save(post)).thenReturn(post);
        Post returnedPost = myBlogService.createPost(postRequest, "finto url");
        Assertions.assertNull(returnedPost);
        Assertions.fail();
    }


    @Test
    public void myBlogService_getPosts_success() {
        Post post = new Post(1, "My Blog", "My bog content", new User(), "url/finto");
        Post postUno = new Post(2, "My BlogUno", "My bogUno content", new User(), "url/finto");
        Post postDue = new Post(3, "My BlogDue", "My bog due content", new User(), "url/finto");
        List<Post> postsDaVerificare = new ArrayList<Post>();
        postsDaVerificare.add(post);
        postsDaVerificare.add(postUno);
        postsDaVerificare.add(postDue);

        when(postRepo.findAll()).thenReturn(postsDaVerificare);

        List<Post> postsdaTornare = myBlogService.getAllPosts();
        Assertions.assertNotNull(postsdaTornare);
        Assertions.assertEquals(postsdaTornare.size(), postsDaVerificare.size());

        for (int i = 0; i < postsdaTornare.size(); i++) {
            Post expected = postsDaVerificare.get(i);
            Post actual = postsdaTornare.get(i);
            Assertions.assertEquals(expected.getTitle(), actual.getTitle());
            Assertions.assertEquals(expected.getContent(), actual.getContent());
            Assertions.assertEquals(expected.getAuthor(), actual.getAuthor());

        }
    }

    @Test
    public void myBlogService_findPostByUser_returnPostOk() {
        User serena = new User(1);
        User fernando = new User(2);

        Post post = new Post(1, "My Blog", "My bog content r", serena, "url/finto");
        Post postUno = new Post(2, "My BlogUno", "My bogUno contentd d", fernando, "url/finto");
        Post postDue = new Post(3, "My BlogDue", "My bog due content dddd", serena, "url/finto");
        List<Post> postProva = new ArrayList<>();
        postProva.add(post);
        postProva.add(postUno);
        postProva.add(postDue);


        when(postRepo.findByUserId(serena)).thenReturn(postProva.stream().filter((p) -> {
            return p.getUser().getId() == post.getUser().getId();

        }).toList());

        List<Post> postsFiltratiDaTestare = myBlogService.getPostsByUser(serena);
        Assertions.assertNotNull(postsFiltratiDaTestare);
        Assertions.assertTrue(postsFiltratiDaTestare.size() == 0);
        for (int i = 0; i < postsFiltratiDaTestare.size(); i++) {
            Assertions.assertEquals(postsFiltratiDaTestare.get(i).getUser().getId(), postProva.get(i).getUser().getId());

        }
    }

    @Test
    public void getPostId_Test_ok() throws Exception {
        Post post = new Post(1, "title", "content", new User(), "url");
        Post postUno = new Post(2, "My BlogUno", "My bogUno content", new User(), "urlfinto2");
        Post postDue = new Post(3, "My BlogDue", "My bog due content", new User(), "url Fintotre");
        List<Post> postsDaVerificare = new ArrayList<Post>();
        postsDaVerificare.add(post);
        postsDaVerificare.add(postUno);
        postsDaVerificare.add(postDue);

        when(postRepo.findById(2)).thenReturn(Optional.of(postUno));
        Post postDaControllare = myBlogService.getPostById(2);
        for (int i = 0; i < postsDaVerificare.size(); i++) {
            Post p = postsDaVerificare.get(i);
            Assertions.assertEquals(2, postDaControllare.getId());
            Assertions.assertTrue(p != null);
        }


    }

    @Test
    public void updatePostTest_ok() throws Exception {
        Post post = new Post(1, "My Blog", "My bog content", new User(), "urlFintoQuattro");
        PostRequestDTO postDTO = new PostRequestDTO(1, "My Blog", "My bog content", 1, LocalDate.now(), "urlFintoQuattro");

        post.setTitle("My Blog modificato");
        post.setContent("My content modificato");
        post.setUser(new User());

        postDTO.setTitle("My Blog modificato");
        postDTO.setContent("My content modificato");
        postDTO.setAuthorId(1);


        when(postRepo.findById(1)).thenReturn(Optional.of(post));
        when(postRepo.save(post)).thenReturn(post);
        Post nuovoPostSalvato = myBlogService.updatePost(postDTO, 1);
        Assertions.assertEquals("My Blog modificato", nuovoPostSalvato.getTitle());
        Assertions.assertEquals("My content modificato", nuovoPostSalvato.getContent());
    }

    @Test
    public void deletePost_test_ok() throws Exception {
        Post post = new Post(1, "My Blog", "My bog content", new User(), "urlfintoebasta");
        Post postP = new Post(2, "My Blog d", "My bog content c", new User(), "e vedise e finto questo url");

        List<Post> posts = new ArrayList<>();
        posts.add(post);
        posts.add(postP);


        doAnswer(new Answer<Void>() {
            @Override
            public Void answer(InvocationOnMock invocation) {
                Object[] args = invocation.getArguments();
                int id = (Integer) args[0];
                for (int i = 0; i < posts.size(); i++) {
                    if (posts.get(i).getId() == id) {
                        posts.remove(i);
                        break;
                    }
                }


                return null;
            }
        }).when(postRepo).deleteById(anyInt());

        myBlogService.deletePost(1);

        Assertions.assertEquals(1, posts.size());
        Assertions.assertNotEquals(1, posts.get(0).getId());
    }
}