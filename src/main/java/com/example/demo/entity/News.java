package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter @Setter
@EqualsAndHashCode
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_news")
public class News {
    
    @Id
    private int newsNo;
    
    @Column(scale = 50)
    private String title;
    
    @Column(scale = 300)
    private String content;
    
    @Column(scale = 100)
    private String imgUrl;
    
    @Column(scale = 50)
    private String source;
    
    private String regDate;
    
}
