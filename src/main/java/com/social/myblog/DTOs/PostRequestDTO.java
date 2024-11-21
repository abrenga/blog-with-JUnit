package com.social.myblog.DTOs;

import com.social.myblog.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Setter
@Getter
public class PostRequestDTO {

private Integer id;

    private String title;

    private String content;

    private Integer authorId;
    private LocalDate date;

    private String url;

    public  PostRequestDTO() {

    }

    public PostRequestDTO(Integer id, String title, String content, Integer authorId, LocalDate date, String url) {
        this.title = title;
        this.content = content;
        this.authorId = authorId;
        this.url = url;
        this.date = date;
        this.id = id;
    }


}
