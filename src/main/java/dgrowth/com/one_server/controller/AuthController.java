package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.KakaoRequest;
import dgrowth.com.one_server.data.dto.request.SignUpRequest;
import dgrowth.com.one_server.data.dto.response.AuthResponse;
import dgrowth.com.one_server.data.dto.response.OAuthResponse;
import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.dto.response.SignUpResponse;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.data.property.ResponseMessage;
import dgrowth.com.one_server.service.AuthService;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (Exception e){
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_LOGIN_KAKAO);
        }

        return response;
    }

    @PostMapping("/signup")
    public Response<SignUpResponse> signUp(@RequestBody SignUpRequest signUpRequest){
        Response<SignUpResponse> response;
        try{
            SignUpResponse signUpResponse = authService.signUp(signUpRequest);
            response = new Response<>(signUpResponse);
        } catch (Exception e){
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_SIGN_UP);
        }

        return response;
    }
}
