package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.ParticipantGroupRequest;
import dgrowth.com.one_server.data.dto.response.*;
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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/participant")
public class ParticipantGroupController {

    private final ParticipantGroupService participantGroupService;

    @GetMapping("/{page}")
    public ResponseEntity<MyGroupParticipantListResponse> getGroupListByUser(@PathVariable("page") Integer page, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(participantGroupService.findByUser(page, httpServletRequest));

    }

    @PostMapping("/new")
    public ResponseEntity<ParticipantGroupResponse> groupParticipate(@RequestBody ParticipantGroupRequest request, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(participantGroupService.participant(request, httpServletRequest));
    }
}
