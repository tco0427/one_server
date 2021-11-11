package dgrowth.com.one_server.group.controller;

import dgrowth.com.one_server.group.dto.request.GroupRequest;
import dgrowth.com.one_server.group.dto.response.*;
import dgrowth.com.one_server.group.domain.enumeration.Category;
import dgrowth.com.one_server.group.service.GroupService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/group")
public class GroupController {

    private final GroupService groupService;

    @ApiOperation(value = "", notes = "내가 만든 그룹 삭제")
    @DeleteMapping("/delete/{groupId}")
    public ResponseEntity<DeleteGroupResponse> deleteGroupByUser(HttpServletRequest request,
        @PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok().body(groupService.deleteById(request, groupId));
    }

    @PostMapping("/create")
    public ResponseEntity<GroupResponse> create(@ModelAttribute GroupRequest groupRequest,
        HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(groupService.save(groupRequest, httpServletRequest));
    }

    @GetMapping("/all")
    public ResponseEntity<List<GroupWithNoticeResponse>> all(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(groupService.findAll(null, httpServletRequest));
    }

    @GetMapping("/all/{category}")
    public ResponseEntity<List<GroupWithNoticeResponse>> allByCategory(
        @PathVariable("category") Category category, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(groupService.findAll(category, httpServletRequest));
    }

    @GetMapping("/info/{groupId}")
    public ResponseEntity<GroupResponse> info(HttpServletRequest httpServletRequest,
        @PathVariable("groupId") Long groupId) {
        return ResponseEntity.ok().body(groupService.groupInfoById(httpServletRequest, groupId));
    }

    @GetMapping("/hot")
    public ResponseEntity<HotGroupListResponse> getRealtimeHot() {
        return ResponseEntity.ok().body(groupService.findHotGroup());
    }

    @GetMapping("/categories")
    public ResponseEntity<List<CategoryResponse>> categories() {
        return ResponseEntity.ok().body(groupService.categories());
    }
}
