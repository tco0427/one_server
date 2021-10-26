package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.data.property.ResponseMessage;
import dgrowth.com.one_server.exception.ExpiredTokenException;
import dgrowth.com.one_server.exception.InvalidTokenException;
import dgrowth.com.one_server.exception.InvalidUserException;
import dgrowth.com.one_server.service.AuthService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/user")
public class UserController {

    private final AuthService authService;

    @GetMapping("/me")
    public Response<UserResponse> getUserInfo(HttpServletRequest httpServletRequest){
        Response<UserResponse> response = null;

        try{
            // 1. 헤더의 토큰 체크
            String token = authService.getTokenByHeader(httpServletRequest);

            // 2. 토큰 유효성 체크 및 유저 정보 가져옴
            UserResponse userResponse = authService.getUserInfoByToken(token);
            response = new Response<>(userResponse);

        } catch (InvalidTokenException invalidTokenException){ // 토큰 전달 이상
            response = new Response<>(invalidTokenException.getHttpStatus(), invalidTokenException.getMessage());
        } catch (ExpiredTokenException expiredTokenException){ // 토큰 만료
            response = new Response<>(expiredTokenException.getHttpStatus(), expiredTokenException.getMessage());
        } catch (InvalidUserException invalidUserException){ // 회원 정보 없음
            response = new Response<>(invalidUserException.getHttpStatus(), invalidUserException.getMessage());
        } catch (Exception exception){
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_FIND_USER);
        }

        return response;
    }
}
