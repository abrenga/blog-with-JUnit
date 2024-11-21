package com.social.myblog.DTOs;

import com.social.myblog.model.User;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;




public class PostResponseDTO {
    public boolean inserito;
    @Setter
    @Getter
    private Integer id;
    @Setter
    @Getter
    private String title;
    @Setter
    @Getter
    private String content;
    @Setter
    @Getter
    private String author;
    @Setter
    @Getter
    private LocalDate date;
    @Setter
    @Getter
    private String image;
    @Setter
    @Getter
    private String url;

    
    
    public PostResponseDTO() {

    }
    
    
    public PostResponseDTO(int id, String title, String content, String author,String url) {
        this.id = id;
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = LocalDate.now();
        this.url=url;
        
    }


}
