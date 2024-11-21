package com.social.myblog.service;

import com.social.myblog.DTOs.PostRequestDTO;
import com.social.myblog.model.Post;
import com.social.myblog.model.User;
import com.social.myblog.repository.PostRepo;
import com.social.myblog.repository.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MyBlogService implements IMyBlogService {

    @Autowired
    private PostRepo postRepo;
    @Autowired
    private UserRepo userRepo;

    private static final String UPLOAD_DIR = "uploads/";

    public Post createPost(PostRequestDTO post, String url) {
        Optional<User> user = userRepo.findById(post.getAuthorId());
        Post postEntity = new Post();
        postEntity.setTitle(post.getTitle());
        postEntity.setContent(post.getContent());
        postEntity.setAuthor(user.get());
        postEntity.setDate(LocalDate.now());
        postEntity.setUrl(url);

        return postRepo.save(postEntity);
    }


    public List<Post> getAllPosts() {

        return postRepo.findAll();
    }


    public List<Post> getPostsByUser(User user) {
        List<Post> postsOfUser = postRepo.findByUserId(user);
        return postsOfUser;
    }

    public Post getPostById(Integer id) throws Exception {
        if (id < 0) {
            throw new Exception("id non valido");
        }
        try {
            Post post = postRepo.findById(id).get();
            return post;
        } catch (Exception e) {
            throw new Exception("post non presente");
        }

    }

    public Post updatePost(PostRequestDTO post, Integer id) throws Exception {
        if (post.getId() < 0 || post.getAuthorId()==0|| post.getContent() == null || post.getContent().isEmpty()) {
            throw new Exception();
        }

        Optional<User> user = userRepo.findById(post.getAuthorId());

        Post postDaConfermare = getPostById(post.getId());

        postDaConfermare.setTitle(post.getTitle());
        postDaConfermare.setContent(post.getContent());
        postDaConfermare.setAuthor(user.get());
        postDaConfermare.setId(id);
        postDaConfermare.getDate();

        return postRepo.save(postDaConfermare);
    }


    public void deletePost(Integer id) {
        postRepo.deleteById(id);

    }


    public String uploadURl(MultipartFile file) throws IOException {
        File uploadPercorso = new File(UPLOAD_DIR);
        if (!uploadPercorso.exists()) {
            uploadPercorso.mkdirs();
        }
        //Recupera il nome del file e prepara il percorso per il salvataggio
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Path percorso = Paths.get(UPLOAD_DIR + fileName);

        //salva il file
        Files.write(percorso, file.getBytes());
        return percorso.toString();
    }





}