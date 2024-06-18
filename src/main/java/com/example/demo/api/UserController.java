package com.example.demo.api;

import com.example.demo.auth.TokenUserInfo;
import com.example.demo.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.*;


import java.io.File;
import java.io.IOException;
import java.util.Map;


@RestController
@Slf4j
@RequiredArgsConstructor
@RequestMapping("/api")
public class UserController {

    private final UserService userService;

    // 프로필 사진 이미지 데이터를 클라이언트에게 응답 처리
    @GetMapping("/load-profile")
    public ResponseEntity<?> loadFile(
            @AuthenticationPrincipal TokenUserInfo userInfo
    ) {
        try {
            // 1. 프로필 사진의 경로부터 얻어야 한다.
            String filePath = userService.findProfilePath(userInfo.getUserId());
            log.info("filePath: {}", filePath);

            // 2. 얻어낸 파일 경로를 통해 실제 파일 데이터를 로드하기.
            File profileFile = new File(filePath);

            // 모든 사용자가 프로필 사진을 가지는 것은 아니다. -> 프사를 등록하지 않은 사람은 해당 경로가 존재하지 않을 것.
            // 만약 존재하지 않는 경로라면 클라이언트로 404 status를 리턴.
            if (!profileFile.exists()) {
                // 만약 조회한 파일 경로가 http://~~~로 시작한다면 -> 카카오 로그인 한 사람이다!
                // 카카오 로그인 프로필은 변환 과정 없이 바로 이미지 url을 리턴해 주시면 됩니다.
                if (filePath.startsWith("http://")) {
                    return ResponseEntity.ok().body(filePath);
                }
                return ResponseEntity.notFound().build();
            }

            // 해당 경로에 저장된 파일을 바이트 배열로 직렬화 해서 리턴
            byte[] fileData = FileCopyUtils.copyToByteArray(profileFile);

            // 3. 응답 헤더에 컨텐츠 타입을 설정
            HttpHeaders headers = new HttpHeaders();
            MediaType contentType = findExtensionAndGetMediaType(filePath);
            if (contentType == null) {
                return ResponseEntity.internalServerError()
                        .body("발견된 파일은 이미지 파일이 아닙니다.");
            }
            headers.setContentType(contentType);
            return  ResponseEntity.ok()
                    .headers(headers)
                    .body(fileData);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }


    // 로그아웃 처리
    @GetMapping("/logout")
    public ResponseEntity<?> logout(
            @AuthenticationPrincipal TokenUserInfo userInfo
    ) {
        log.info("/api/auth/logout - GET! - user: {}", userInfo.getEmail());

        String result = userService.logout(userInfo);

        return ResponseEntity.ok().body(result);
    }

    // 리프레쉬 토큰을 활용한 엑세스 토큰 재발급 요청
    @PostMapping("/refresh")
    public ResponseEntity<?> refresh(@RequestBody Map<String, String> tokenRequest) {
        log.info("/api/auth/refresh: POST! - tokenRequest: {}", tokenRequest);
        String renewalAccessToken = userService.renewalAccessToken(tokenRequest);
        if (renewalAccessToken != null) {
            return ResponseEntity.ok().body(Map.of("access_token", renewalAccessToken));
        }
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid refresh token");
    }


    private MediaType findExtensionAndGetMediaType(String filePath) {

        // 파일 경로에서 확장자 추출
        // C:/todo_upload/fgjdgkskgkhs_abc.jpg
        String ext = filePath.substring(filePath.lastIndexOf(".") + 1);

        // 추출한 확장자를 바탕으로 MediaType을 설정 -> Header에 들어갈 Content-type이 됨.
        switch (ext.toUpperCase()) {
            case "JPG": case "JPEG":
                return MediaType.IMAGE_JPEG;
            case "PNG":
                return MediaType.IMAGE_PNG;
            case "GIF":
                return MediaType.IMAGE_GIF;
            default:
                return null;

        }

    }


}
