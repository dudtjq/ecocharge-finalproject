package com.example.demo.repository;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Charger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class ChargerRepositoryTest {

    @Autowired
    private ChargerRepository chargerRepository;

    @Test
    @DisplayName("예약 여부 y n 체크")
    void testSave() {
        // given
        Random random = new Random();
        final List<Charger> chargerList = chargerRepository.findAll();
        //when


            for (Charger charger : chargerList) {
                String ynValue = random.nextBoolean() ? "y" : "n";
                charger.setYnCheck(ynValue);
                chargerRepository.save(charger);
            }

        // then

    }


}