package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.request.MajorRequest;
import dgrowth.com.one_server.data.dto.response.MajorResponse;
import dgrowth.com.one_server.data.dto.response.UserResponse;
import dgrowth.com.one_server.service.MajorService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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

    // user test를 위한 임시 controller
    @PostMapping("/usertest/{majorId}")
    public ResponseEntity<UserResponse> addMajor(HttpServletRequest httpServletRequest,
        @PathVariable("majorId") Long majorId) {
        return ResponseEntity.ok().body(majorService.userAddMajor(httpServletRequest, majorId));
    }
}
