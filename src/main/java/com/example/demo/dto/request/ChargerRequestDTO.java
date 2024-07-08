package com.example.demo.dto.request;

import com.example.demo.entity.Charger;
import lombok.*;

@Getter @Setter @ToString
@EqualsAndHashCode @AllArgsConstructor @NoArgsConstructor
@Builder
public class ChargerRequestDTO {

    private String powerType;

    private String chgerType;



    public Charger toEntity(){
        return  Charger.builder()
                .powerType(powerType)
                .chgerType(chgerType)
                .build();
    }

}
