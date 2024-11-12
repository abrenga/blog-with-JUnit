package com.social.myblog.model;


import jakarta.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;


import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Entity
@Table
public class Post {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Integer id;
    private String title;
    private String content;
    private String url;

    @Column( nullable = false)
    @DateTimeFormat(pattern = "dd-MM-yyyy")
    private LocalDate date;


    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;



    @ManyToOne
    @JoinColumn(nullable=false)
    private Category category;

    public Post(){

    }

    public Post(Integer id, String title, String content, User author,String url) {
        this.id= id;
        this.title = title;
        this.content = content;
        this.user = author;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }




    public Integer getId() {
        return id;
    }

    public User getAuthor() {
        return user;
    }


    public void setAuthor(User author) {
        this.user = author;
    }


    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDate getDate() {
        return date;
    }


    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }
}