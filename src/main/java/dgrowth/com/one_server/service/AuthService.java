package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.dto.request.SignUpRequest;
import dgrowth.com.one_server.data.dto.response.AuthResponse;
import dgrowth.com.one_server.data.dto.response.OAuthResponse;
import dgrowth.com.one_server.data.dto.response.SignUpResponse;
import dgrowth.com.one_server.data.dto.response.TokenResponse;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.domain.entity.User;
import dgrowth.com.one_server.domain.entity.UserToken;
import dgrowth.com.one_server.exception.DuplicatedUserException;
import dgrowth.com.one_server.util.JwtUtil;
import java.io.IOException;
import javax.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthService {
    private final KakaoService kakaoService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    /**
     * 회원 가입
     * @param request SignUpRequest
     * @return SignUpResponse
     * @throws DuplicatedUserException
     */
    @Transactional
    public SignUpResponse signUp(SignUpRequest request) throws DuplicatedUserException {
        // 1. 플랫폼 타입(카카오, 네이버)와 고유 아이디로 회원 여부 조회
        if (userService.isSavedUser(request.getPlatformType(), request.getPlatformId())) {
            throw new DuplicatedUserException();
        }

        // 2. 회원 정보 저장
        User user = request.toUser();
        User savedUser = userService.save(user);

        // 3. 토큰 발급 및 정보 저장
        TokenResponse tokenResponse = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getAuthority());
        UserToken savedUserToken = userService.saveToken(tokenResponse.toUserToken());
        savedUser.setUserToken(savedUserToken);
        savedUser = userService.save(user);

        return new SignUpResponse(savedUser.toResponse(), tokenResponse);
    }

    /**
     * 카카오 인증을 통해 회원정보를 가져옴
     * @param code String
     * @return AuthResponse
     * @throws IOException
     */
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
        User savedUser = userService.findByPlatformTypeAndPlatformId(
            oAuthResponse.getPlatformType(), oAuthResponse.getPlatformId());
        if (savedUser == null) { // 5-1. 회원이 아닐경우 회원정보 및 토큰 정보에 null set
            userResponse = null;
            tokenResponse = null;
        } else { // 4. 회원일경우
            isUser = true;
            // 4-1. 유저 객체 저장
            userResponse = savedUser.toResponse();
            // 4-2. 토큰 정보 가쟈와서 유효기간 판단 후 재발급여부 결정
            UserToken userToken = savedUser.getUserToken();
            if(jwtUtil.isTokenExpired(userToken.getAccessToken())){ // 유효기간이 만료된 토큰일 경우 재발급
                tokenResponse = jwtUtil.generateToken(savedUser.getId(), savedUser.getEmail(), savedUser.getAuthority());
                userToken.setToken(tokenResponse.getAccessToken(), tokenResponse.getRefreshToken());
                userToken = userService.saveToken(userToken);
            }
            tokenResponse = userToken.toResponse();
        }

        authResponse.setUser(isUser);
        authResponse.setUserResponse(userResponse);
        authResponse.setTokenResponse(tokenResponse);

        return authResponse;
    }

}
