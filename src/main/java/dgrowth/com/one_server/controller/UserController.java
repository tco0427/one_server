package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final AuthService authService;

    @GetMapping("/me")
    public ResponseEntity<UserResponse> getUserInfo(HttpServletRequest httpServletRequest) {
        Response<UserResponse> response = null;

        // 헤더의 토큰 체크 & 토큰 유효성 체크 및 유저 정보 가져옴
        String token = authService.getTokenByHeader(httpServletRequest);

        return ResponseEntity.ok(authService.getUserInfoByToken(token));
    }
}
