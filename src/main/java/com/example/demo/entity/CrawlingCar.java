package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter
@EqualsAndHashCode(of = "dataId")
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "tbl_crawling")
public class CrawlingCar {

    @Id
    private int dataId;
    
    @Column(scale = 100)
    private String imgUrl;
    
    @Column(scale = 300)
    private String infoUrl;

}
