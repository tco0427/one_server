package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.ParticipantGroupRequest;
import dgrowth.com.one_server.data.dto.response.*;
import dgrowth.com.one_server.service.ParticipantGroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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
