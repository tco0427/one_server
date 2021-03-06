package dgrowth.com.one_server.auth.controller;

import dgrowth.com.one_server.auth.dto.request.KakaoRequest;
import dgrowth.com.one_server.auth.dto.request.SignUpRequest;
import dgrowth.com.one_server.auth.dto.request.TokenRequest;
import dgrowth.com.one_server.auth.dto.response.AuthResponse;
import dgrowth.com.one_server.auth.dto.response.SignUpResponse;
import dgrowth.com.one_server.auth.dto.response.TokenResponse;
import dgrowth.com.one_server.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 카카오 로그인 버튼 클릭 시 유저 정보를 가져와 회원여부를 판단
     *
     * @param kakaoRequest KakaoRequest
     * @return Response<AuthResponse>
     */
    @PostMapping("/kakao")
    public ResponseEntity<AuthResponse> loginWithKakao(@RequestBody KakaoRequest kakaoRequest) {
        return ResponseEntity.ok(authService.getUserInfoByKakao(kakaoRequest.getCode()));
    }

    /**
     * 회원 가입
     *
     * @param signUpRequest SignUpRequest
     * @return Response<SignUpResponse>
     */
    @PostMapping("/signup")
    public ResponseEntity<SignUpResponse> signUp(@ModelAttribute SignUpRequest signUpRequest) {
        return ResponseEntity.ok(authService.signUp(signUpRequest));
    }

    @PostMapping("/token")
    public ResponseEntity<TokenResponse> reIssueToken(@RequestBody TokenRequest tokenRequest) {
        return ResponseEntity.ok(
            authService.generateTokenByRefreshToken(tokenRequest.getRefreshToken()));
    }
}
