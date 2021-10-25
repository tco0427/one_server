package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.dto.response.AuthResponse;
import dgrowth.com.one_server.data.dto.response.OAuthResponse;
import dgrowth.com.one_server.data.dto.response.TokenResponse;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    public AuthResponse getUserInfoByKakao(String code){
        AuthResponse authResponse = new AuthResponse();

        // 1. 코드 값으로 토큰 구하기
        String token = "";

        // 2. 토큰값으로 카카오 정보 구하기
        OAuthResponse oAuthResponse = new OAuthResponse();
        authResponse.setOAuthResponse(oAuthResponse);

        // 3. platformId로 회원여부 판단
        authResponse.setUser(true);

        // 4-1. 회원 O > 유저 객체 저장
        UserResponse userResponse = new UserResponse();
        authResponse.setUserResponse(userResponse);

        // 4-2. 회원 O > 토큰 정보 가쟈와서 유효기간 판단 후 재발급여부 결정
        TokenResponse tokenResponse = new TokenResponse();
        authResponse.setTokenResponse(tokenResponse);

        // 5-1. 회원 X > 회원정보 및 토큰 정보에 null set

        return authResponse;
    }

}
