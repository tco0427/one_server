package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.GroupRequest;
import dgrowth.com.one_server.data.dto.response.GroupResponse;
import dgrowth.com.one_server.data.dto.response.Response;
import dgrowth.com.one_server.data.property.ResponseMessage;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.User;
import dgrowth.com.one_server.exception.InvalidTokenException;
import dgrowth.com.one_server.service.AuthService;
import dgrowth.com.one_server.service.GroupService;
import dgrowth.com.one_server.service.UserService;
import dgrowth.com.one_server.util.JwtUtil;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;
    private final AuthService authService;
    private final UserService userService;
    private final JwtUtil jwtUtil;

    @ApiOperation(value = "", notes = "내가 만든 그룹 삭제")
    @DeleteMapping("/delete/{groupId}")
    public Response<DeleteGroupResponse> deleteGroupByUser(HttpServletRequest request, @PathVariable("groupId") Long groupId) {
        Response<DeleteGroupResponse> response = null;
        DeleteGroupResponse deleteGroupResponse;


        try{
            String token = authService.getTokenByHeader(request);

            boolean tokenExpired = jwtUtil.isTokenExpired(token);

            if(tokenExpired == true) {
                return new Response<>(HttpStatus.UNAUTHORIZED, ResponseMessage.EXPIRED_TOKEN);
            }

            Long userId = jwtUtil.getUserIdByToken(token);

            List<Group> groupList = groupService.findByHostId(userId);

            for (Group group : groupList) {
                if (group.getId() == groupId) {
                    groupService.deleteById(groupId);
                    deleteGroupResponse = new DeleteGroupResponse(group);
                    return new Response<>(deleteGroupResponse);
                }
            }

            return new Response<>(HttpStatus.BAD_REQUEST, ResponseMessage.FAILED_TO_DELETE_GROUP);

        } catch (InvalidTokenException e) {
            return new Response<>(HttpStatus.UNAUTHORIZED, ResponseMessage.INVALID_TOKEN);
        }
    }

    @Data
    @AllArgsConstructor
    static class DeleteGroupResponse {
        private Long id;
        private String title;

        public DeleteGroupResponse(Group group) {
            this.id = group.getId();
            this.title = group.getTitle();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> create(@RequestBody GroupRequest groupRequest,
        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(groupService.save(groupRequest, httpServletRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupResponse>> all(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok(groupService.findAll(httpServletRequest));
    }

    @GetMapping("/info/{groupId}")
    public ResponseEntity<GroupResponse> info(HttpServletRequest httpServletRequest, @PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok(groupService.groupInfoById(httpServletRequest, groupId));
    }
}
