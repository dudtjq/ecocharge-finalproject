package com.eco.chap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.SecureRandom;
import java.util.Base64;

@SpringBootTest
@SpringBootApplication
class FinalApplicationTests {

	@Test
	void contextLoads() {
	}

	@Test
	@DisplayName("토큰 서명 해시값 생성하기")
	void makeSecretKey() {
		SecureRandom random = new SecureRandom();
		byte[] key = new byte[8]; // 64byte -> 512bit
		random.nextBytes(key);
		String encodedKey = Base64.getEncoder().encodeToString(key);
		System.out.println("\n\n\n");
		System.out.println("encodedKey = " + encodedKey);
		System.out.println("\n\n\n");
	}

}
