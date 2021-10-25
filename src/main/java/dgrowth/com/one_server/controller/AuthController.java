package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.KakaoRequest;
import dgrowth.com.one_server.data.dto.response.AuthResponse;
import dgrowth.com.one_server.data.dto.response.OAuthResponse;
import dgrowth.com.one_server.data.dto.response.Response;
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
}
