package com.social.myblog.DTOs;

import com.social.myblog.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
public class PostRequestDTO {
@Getter
private Integer id;
    @Getter
    private String title;
    @Getter
    private String content;
    @Getter
    private User author;
    private LocalDate date;
    @Getter
    private String url;

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

    public LocalDate getLocation() {
        return date;
    }


}
