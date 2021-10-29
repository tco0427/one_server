package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.response.MyGroupParticipantListResponse;
import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.property.ResponseMessage;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.domain.entity.User;
import dgrowth.com.one_server.exception.InvalidTokenException;
import dgrowth.com.one_server.exception.InvalidUserException;
import dgrowth.com.one_server.service.AuthService;
import dgrowth.com.one_server.service.GroupService;
import dgrowth.com.one_server.service.ParticipantGroupService;
import dgrowth.com.one_server.service.UserService;
import dgrowth.com.one_server.util.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participant")
public class ParticipantGroupController {

    private final ParticipantGroupService participantGroupService;
    private final GroupService groupService;
    private final AuthService authService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @GetMapping("{page}")
    public Response<MyGroupParticipantListResponse> getGroupListByUser(@PathVariable("page") Integer page, HttpServletRequest httpServletRequest) {
        Response<MyGroupParticipantListResponse> response = null;
        MyGroupParticipantListResponse myGroupParticipantResponse;

        try{
            String token = authService.getTokenByHeader(httpServletRequest);

            boolean tokenExpired = jwtUtil.isTokenExpired(token);

            if(tokenExpired == true) {
                return new Response<>(HttpStatus.UNAUTHORIZED, ResponseMessage.EXPIRED_TOKEN);
            }

            Long userId = jwtUtil.getUserIdByToken(token);

            User user = userService.findById(userId);

            PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDate"));

            List<ParticipantGroup> content = participantGroupService.findByUserId(user.getId(), pageRequest);

            List<MyGroupParticipantResponse> myGroupParticipantResponseList = new ArrayList<>();

            for (ParticipantGroup participantGroup : content) {
                Long id = participantGroup.getGroup().getId();

            }




        } catch (InvalidUserException e) {
            return new Response<>(HttpStatus.NOT_FOUND, ResponseMessage.INVALID_USER);
        } catch (InvalidTokenException e) {
            return new Response<>(HttpStatus.UNAUTHORIZED, ResponseMessage.INVALID_TOKEN);
        }
    }

    @Data
    @AllArgsConstructor
    static class MyGroupParticipantResponse {
        private Long groupId;
    }

}
