package com.example.demo.repository;

import com.example.demo.entity.ChargeSpot;
import com.example.demo.entity.Charger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class ChargeSpotRepositoryTest {

        @Autowired
        private ChargeSpotRepository chargeSpotRepository;

        @Autowired
        private ChargerRepository chargerRepository;

        @Test
        @DisplayName("예약 여부 y n 체크")
        void testSave() {
            // given
            Random random = new Random();
            final List<ChargeSpot> spotList = chargeSpotRepository.findAll();
            //when

            String ynValue = random.nextBoolean() ? "y" : "n";

            for (ChargeSpot chargeSpot : spotList) {

                final List<Charger> chargerList = chargeSpot.getChargerList();
                for (Charger charger : chargerList) {
                    charger.setYnCheck(ynValue);
                    chargerRepository.save(charger);
                }

            }

            // then

        }

}