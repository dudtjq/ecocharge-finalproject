package com.example.demo.dto.response;

import com.example.demo.common.PageMaker;
import com.example.demo.entity.SubsidyCar;
import jakarta.persistence.Column;
import jakarta.persistence.Id;
import lombok.*;

import java.util.List;

@Getter @ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SubsidyCarResponseDTO {
    
    private List<SubsidyCar> subsidyCarList;
    private PageMaker pageMaker;
    
}
