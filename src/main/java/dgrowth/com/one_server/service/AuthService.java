package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.dto.request.SignUpRequest;
import dgrowth.com.one_server.data.dto.response.AuthResponse;
import dgrowth.com.one_server.data.dto.response.OAuthResponse;
import dgrowth.com.one_server.data.dto.response.SignUpResponse;
import dgrowth.com.one_server.data.dto.response.TokenResponse;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.domain.entity.User;
import java.io.IOException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final KakaoService kakaoService;
    private final UserService userService;

    @Transactional
    public SignUpResponse signUp(SignUpRequest request) {
        // 플랫폼 타입(카카오, 네이버)와 고유 아이디로 회원 여부 조회
        if (userService.isSavedUser(request.getPlatformType(), request.getPlatformId())) {
            throw new RuntimeException("이미 가입되어 있는 유저입니다");
        }

        // 회원 정보 저장
        User user = request.toUser();
        User savedUser = userService.save(user);

        TokenResponse tokenResponse = new TokenResponse();

        SignUpResponse response = new SignUpResponse(savedUser.toResponse(), tokenResponse);

        return response;
    }

    public AuthResponse getUserInfoByKakao(String code) throws IOException {
        AuthResponse authResponse = new AuthResponse();

        // 1. 코드 값으로 토큰 구하기
        String token = kakaoService.getTokenByCode(code);

        // 2. 토큰값으로 카카오 정보 구하기
        OAuthResponse oAuthResponse = kakaoService.getKakaoUserInfoByToken(token);
        authResponse.setOAuthResponse(oAuthResponse);

        UserResponse userResponse;
        TokenResponse tokenResponse;
        boolean isUser = false;

        // 3. platformId로 회원여부 판단
        User savedUser = userService.findByPlatformTypeAndPlatformId(oAuthResponse.getPlatformType(), oAuthResponse.getPlatformId());
        if (savedUser == null) { // 5-1. 회원이 아닐경우 회원정보 및 토큰 정보에 null set
            userResponse = null;
            tokenResponse = null;
        } else { // 4. 회원일경우
            isUser = true;
            // 4-1. 유저 객체 저장
            userResponse = savedUser.toResponse();

            // 4-2. 토큰 정보 가쟈와서 유효기간 판단 후 재발급여부 결정
            tokenResponse = new TokenResponse();

        }

        authResponse.setUser(isUser);
        authResponse.setUserResponse(userResponse);
        authResponse.setTokenResponse(tokenResponse);

        return authResponse;
    }

}
