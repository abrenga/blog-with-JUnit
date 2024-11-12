package com.social.myblog.DTOs;

import com.social.myblog.model.User;

import javax.xml.stream.Location;
import java.time.LocalDate;

public class PostRequestDTO {
private Integer id;
    private String title;
    private String content;
    private User author;
    private LocalDate date;
    private String url;

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId() {
        return id;
    }

    public  PostRequestDTO() {

    }

    public PostRequestDTO(Integer id, String title, String content, User author, LocalDate date, String url) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.url = url;
        this.date = date;
        this.id = id;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public LocalDate getLocation() {
        return date;
    }

    public String getUrl() {
        return url;
    }

    public String getTitle() {
        return this.title;
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


}
