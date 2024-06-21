package com.example.demo.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode(of = "id")
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "tbl_crawling_subsidycar")
public class SubsidyCar {

    @Id
    @Column(name = "subsidycar_id")
    private int id;
    
    @Column(scale = 100)
    private String carName;
    
    @Column(scale = 300)
    private String imgUrl;
    
    @Column(scale = 20)
    private String ridingCapacity;
    
    @Column(scale = 20)
    private String topSpeed;
    
    @Column(scale = 20)
    private String fullChargeRange;
    
    @Column(scale = 20)
    private String battery;
    
    @Column(scale = 20)
    private String subsidy;
    
    @Column(scale = 20)
    private String callNumber;
    
    @Column(scale = 20)
    private String company;
    
    @Column(scale = 20)
    private String country;
    
    @Column(scale = 100)
    private String locationUrl;

}
