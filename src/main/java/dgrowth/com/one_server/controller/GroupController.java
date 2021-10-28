package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.CreateGroupRequest;
import dgrowth.com.one_server.data.dto.response.GroupResponse;
import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.property.ResponseMessage;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.User;
import dgrowth.com.one_server.exception.ExpiredTokenException;
import dgrowth.com.one_server.exception.InvalidTokenException;
import dgrowth.com.one_server.exception.InvalidUserException;
import dgrowth.com.one_server.service.AuthService;
import dgrowth.com.one_server.service.GroupService;
import dgrowth.com.one_server.service.UserService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final AuthService authService;
    private final GroupService groupService;
    private final UserService userService;

    /**
     * 그룹 생성
     * @param
     * @return
     */
    @PostMapping("/create")
    public Response<GroupResponse> create(@RequestBody CreateGroupRequest createGroupRequest, HttpServletRequest httpServletRequest) {
        Response<GroupResponse> response = null;

        try {
            // 1. 헤더에서 토큰 체크
            String token = authService.getTokenByHeader(httpServletRequest);

            // 2. 토큰 유효성 체크 및 유저 불러오기
            Long hostId = authService.getUserByToken(token).getId();

            // 3. 그룹 생성
            Group newGroup = new Group(createGroupRequest.getTitle(), hostId);
            Long savedId = groupService.save(newGroup);

            // 4. Response 생성
            response = new Response<>(newGroup.toResponse());

        } catch (InvalidTokenException invalidTokenException){ // 토큰 전달 이상
            response = new Response<>(invalidTokenException.getHttpStatus(), invalidTokenException.getMessage());
        } catch (ExpiredTokenException expiredTokenException){ // 토큰 만료
            response = new Response<>(expiredTokenException.getHttpStatus(), expiredTokenException.getMessage());
        } catch (InvalidUserException invalidUserException) { // 회원 정보 없음
            response = new Response<>(invalidUserException.getHttpStatus(), invalidUserException.getMessage());
        } catch (Exception exception){
            response = new Response<>(HttpStatus.INTERNAL_SERVER_ERROR, ResponseMessage.FAILED_TO_FIND_USER);
        }

        return response;
    }
}
