package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.CreateNoticeGroupRequest;
import dgrowth.com.one_server.data.dto.request.CreateNoticeMajorRequest;
import dgrowth.com.one_server.data.dto.request.GroupRequest;
import dgrowth.com.one_server.data.dto.request.NoticeUpdateRequest;
import dgrowth.com.one_server.data.dto.response.*;
import dgrowth.com.one_server.domain.enumeration.Category;
import dgrowth.com.one_server.service.NoticeService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notice")
public class NoticeController {

    private final NoticeService noticeService;

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<NoticeDeleteResponse> deleteGroupByUser(HttpServletRequest request,
                                                                  @PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok().body(noticeService.deleteNoticeById(request, noticeId));
    }

    @PostMapping("/group/create")
    public ResponseEntity<CreateNoticeGroupResponse> createWithGroup(HttpServletRequest httpServletRequest, CreateNoticeGroupRequest request) {
        return ResponseEntity.ok().body(noticeService.saveGroupNotice(httpServletRequest, request));
    }

    @PostMapping("/major/create")
    public ResponseEntity<CreateNoticeMajorResponse> createWithMajor(HttpServletRequest httpServletRequest, CreateNoticeMajorRequest request) {
        return ResponseEntity.ok().body(noticeService.saveMajorNotice(httpServletRequest, request));
    }

    @GetMapping("/group")
    public ResponseEntity<NoticeGroupResponse> findWithGroup(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(noticeService.findGroupNoticeByUser(httpServletRequest));
    }

    @GetMapping("/major")
    public ResponseEntity<NoticeMajorResponse> findWithMajor(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(noticeService.findMajorNoticeByUser(httpServletRequest));
    }

    @PutMapping("/{noticeId}")
    public ResponseEntity<NoticeResponse> updateNotice(HttpServletRequest httpServletRequest, NoticeUpdateRequest noticeUpdateRequest, @PathVariable("noticeId") Long noticeId) {
        return ResponseEntity.ok().body(noticeService.updateNoticeByUser(httpServletRequest, noticeUpdateRequest, noticeId));
    }
}
