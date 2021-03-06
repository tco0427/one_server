package dgrowth.com.one_server.major.controller;

import dgrowth.com.one_server.major.dto.request.MajorRequest;
import dgrowth.com.one_server.major.dto.response.MajorResponse;
import dgrowth.com.one_server.major.dto.response.UserMajorResponse;
import dgrowth.com.one_server.user.dto.response.UserResponse;
import dgrowth.com.one_server.major.service.MajorService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/major")
public class MajorController {

    private final MajorService majorService;

    @PostMapping("/add")
    public ResponseEntity<MajorResponse> newMajor(@RequestBody MajorRequest majorRequest, HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(majorService.save(majorRequest, httpServletRequest));
    }

    @GetMapping("/{majorId}")
    public ResponseEntity<MajorResponse> info(HttpServletRequest httpServletRequest, @PathVariable("majorId") Long majorId) {
        return ResponseEntity.ok().body(majorService.majorInfoById(httpServletRequest, majorId));
    }

    @GetMapping("/user")
    public ResponseEntity<MajorResponse> infoByUser(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(majorService.majorInfoByUserId(httpServletRequest));
    }

    @GetMapping("/{majorId]/{studentId}")
    public ResponseEntity<List<UserMajorResponse>> getinfoByStudnetId(HttpServletRequest httpServletRequest,
                                                                      @PathVariable("studentId") Long studentId) {
        return ResponseEntity.ok().body(majorService.findByStudentId(httpServletRequest, studentId));
    }
}
