package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.dto.request.LoginRequest;
import dgrowth.com.one_server.dto.request.SignUpRequest;
import dgrowth.com.one_server.dto.request.TokenRequest;
import dgrowth.com.one_server.dto.response.TokenDto;
import dgrowth.com.one_server.dto.response.UserResponse;
import dgrowth.com.one_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/signup")
    public ResponseEntity<UserResponse> signup(@RequestBody SignUpRequest request) {
        return ResponseEntity.ok(authService.signup(request));
    }

    @PostMapping("/login")
    public ResponseEntity<TokenDto> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("/reissue")
    public ResponseEntity<TokenDto> reissue(@RequestBody TokenRequest request) {
        return ResponseEntity.ok(authService.reissue(request));
    }
}
