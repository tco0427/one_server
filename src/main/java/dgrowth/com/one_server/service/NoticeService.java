package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.dto.response.*;
import dgrowth.com.one_server.domain.entity.*;
import dgrowth.com.one_server.repository.MajorRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class NoticeService {

    private final MajorRepository majorRepository;
    private final AuthService authService;
    private final ParticipantGroupService participantGroupService;
    private final UserService userService;
    private final GroupService groupService;

    /**
     * 그룹 공지 조회
     */
    public NoticeGroupResponse findNoticeByUser(HttpServletRequest httpServletRequest) {
        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(userId);

        MyGroupParticipantResponse myGroupParticipantResponse = participantGroupService.findByUser(0, httpServletRequest)
                .getMyGroupParticipantResponses().get(0);

        GroupResponse groupResponse = myGroupParticipantResponse.getGroupResponse();

        Group group = groupService.findById(groupResponse.getId());
        List<Notice> notices = group.getNotices();

        Notice notice = null;

        String groupImageUrl = groupResponse.getGroupImageUrl();


        NoticeGroupResponse noticeGroupResponse = new NoticeGroupResponse(notice.getId(), notice.getTitle(), groupImageUrl);

        return noticeGroupResponse;
    }

    /**
     * 학과 공지 조회
     */
}
