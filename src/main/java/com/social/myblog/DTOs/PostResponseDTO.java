package com.social.myblog.DTOs;

import com.social.myblog.model.User;

import java.time.LocalDate;




public class PostResponseDTO {
    public boolean inserito;
    private Integer id;
    private String title;
    private String content;
    private User author;
    private LocalDate date;
    private String image;
    private String url;

    
    
    public PostResponseDTO() {

    }
    
    
    public PostResponseDTO(int id, String title, String content, User author,String url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = LocalDate.now();
        this.url=url;
        
    }

    public String getImage() {
        return image;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }



    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }


}
