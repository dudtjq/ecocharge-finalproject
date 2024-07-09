package com.example.demo.dto.request;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Charger;
import lombok.*;

import java.util.Collections;
import java.util.List;

@Getter @ToString @Setter
@EqualsAndHashCode @AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChargeSpotRequestDTO {


    private String limitYn; // 이용제한

    private String chgerType;

    private String powerType;

    private String lat;

    private String lng;

    private String zoom;
}
