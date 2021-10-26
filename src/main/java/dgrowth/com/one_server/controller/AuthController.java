package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.KakaoRequest;
import dgrowth.com.one_server.data.dto.request.SignUpRequest;
import dgrowth.com.one_server.data.dto.request.TokenRequest;
import dgrowth.com.one_server.data.dto.response.AuthResponse;
import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.dto.response.SignUpResponse;
import dgrowth.com.one_server.data.dto.response.TokenResponse;
import dgrowth.com.one_server.data.property.ResponseMessage;
import dgrowth.com.one_server.exception.DuplicatedUserException;
import dgrowth.com.one_server.exception.ExpiredTokenException;
import dgrowth.com.one_server.exception.InvalidTokenException;
import dgrowth.com.one_server.exception.InvalidUserException;
import dgrowth.com.one_server.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/auth")
public class AuthController {

    private final AuthService authService;

    /**
     * 카카오 로그인 버튼 클릭 시 유저 정보를 가져와 회원여부를 판단
     * @param kakaoRequest KakaoRequest
     * @return Response<AuthResponse>
     */
    @PostMapping("/kakao")
    public Response<AuthResponse> loginWithKakao(@RequestBody KakaoRequest kakaoRequest){
        Response<AuthResponse> response;
        try{
            AuthResponse authResponse = authService.getUserInfoByKakao(kakaoRequest.getCode());
            response = new Response<>(authResponse);
        } catch (HttpClientErrorException he){
            response = new Response<>(HttpStatus.BAD_REQUEST, ResponseMessage.FAILED_TO_AUTH_KAKAO);
        } catch(Exception e){
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_LOGIN_KAKAO);
        }

        return response;
    }

    /**
     * 회원 가입
     * @param signUpRequest SignUpRequest
     * @return Response<SignUpResponse>
     */
    @PostMapping("/signup")
    public Response<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        Response<SignUpResponse> response;
        try{
            SignUpResponse signUpResponse = authService.signUp(signUpRequest);
            response = new Response<>(signUpResponse);
        } catch (DuplicatedUserException e){
            response = new Response<>(e.getHttpStatus(), e.getMessage());
        } catch (Exception e){
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_SIGN_UP);
        }

        return response;
    }

    @PostMapping("/token")
    public Response<TokenResponse> reIssueToken(@RequestBody TokenRequest tokenRequest){

        Response<TokenResponse> response;
        try {
            TokenResponse tokenResponse = authService.generateTokenByRefreshToken(tokenRequest.getRefreshToken());
            response = new Response<>(tokenResponse);
        } catch (ExpiredTokenException expiredTokenException){ // 토큰 만료
            response = new Response<>(expiredTokenException.getHttpStatus(), expiredTokenException.getMessage());
        } catch (InvalidUserException invalidUserException){ // 회원 정보 없음
            response = new Response<>(invalidUserException.getHttpStatus(), invalidUserException.getMessage());
        } catch (Exception exception){
            exception.printStackTrace();
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_RE_ISSUE_TOKEN);
        }

        return response;
    }
}
