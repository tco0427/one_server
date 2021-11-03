package dgrowth.com.one_server.controller;

import dgrowth.com.one_server.data.dto.response.MajorResponse;
import dgrowth.com.one_server.service.MajorService;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/major")
public class MajorController {

    private final MajorService majorService;

    @GetMapping("/{majorId}")
    public ResponseEntity<MajorResponse> info(HttpServletRequest httpServletRequest, @PathVariable("majorId") Long majorId) {
        return ResponseEntity.ok().body(majorService.majorInfoById(httpServletRequest, majorId));
    }

    @GetMapping("/user")
    public ResponseEntity<MajorResponse> info(HttpServletRequest httpServletRequest) {
        return ResponseEntity.ok().body(majorService.majorInfoByUserId(httpServletRequest));
    }
}
