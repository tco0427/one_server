package dgrowth.com.one_server.notice.service;

import dgrowth.com.one_server.auth.service.AuthService;
import dgrowth.com.one_server.group.dto.request.CreateNoticeGroupRequest;
import dgrowth.com.one_server.major.dto.request.CreateNoticeMajorRequest;
import dgrowth.com.one_server.major.dto.response.CreateNoticeMajorResponse;
import dgrowth.com.one_server.major.dto.response.NoticeMajorResponse;
import dgrowth.com.one_server.notice.dto.request.NoticeUpdateRequest;
import dgrowth.com.one_server.notice.domain.entity.*;
import dgrowth.com.one_server.notice.dto.response.NoticeDeleteResponse;
import dgrowth.com.one_server.notice.dto.response.NoticeResponse;
import dgrowth.com.one_server.participantGroup.service.ParticipantGroupService;
import dgrowth.com.one_server.user.domain.enumeration.Authority;
import dgrowth.com.one_server.group.domain.entity.Group;
import dgrowth.com.one_server.group.dto.response.CreateNoticeGroupResponse;
import dgrowth.com.one_server.group.dto.response.GroupResponse;
import dgrowth.com.one_server.group.dto.response.NoticeGroupResponse;
import dgrowth.com.one_server.group.service.GroupService;
import dgrowth.com.one_server.major.domain.entity.Major;
import dgrowth.com.one_server.major.dto.response.MajorResponse;
import dgrowth.com.one_server.major.service.MajorService;
import dgrowth.com.one_server.notice.domain.repository.NoticeRepository;
import dgrowth.com.one_server.user.domain.entity.User;
import dgrowth.com.one_server.user.service.UserService;
import java.util.ArrayList;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.NoSuchElementException;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class NoticeService {

    private final MajorService majorService;
    private final AuthService authService;
    private final ParticipantGroupService participantGroupService;
    private final UserService userService;
    private final GroupService groupService;
    private final NoticeRepository noticeRepository;

    /**
     * 그룹 공지 생성
     */
    @Transactional
    public CreateNoticeGroupResponse saveGroupNotice(HttpServletRequest httpServletRequest, CreateNoticeGroupRequest request) {
        CreateNoticeGroupResponse createNoticeGroupResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        Group group = groupService.findById(request.getGroupId());

        System.out.println(group.getId());

        if(group.getHostId() == userId) {

            Notice notice = new Notice(request.getTitle(), request.getContent(), group);

            //실행?

            Notice savedNotice = noticeRepository.save(notice);

            createNoticeGroupResponse = new CreateNoticeGroupResponse(savedNotice.getId(), group.getId(), savedNotice.getTitle(), savedNotice.getContent());
        }

        return createNoticeGroupResponse;
    }

    /**
     * 학과 공지 생성
     */
    @Transactional
    public CreateNoticeMajorResponse saveMajorNotice(HttpServletRequest httpServletRequest, CreateNoticeMajorRequest request) {
        CreateNoticeMajorResponse createNoticeMajorResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(userId);

        Major major = majorService.findById(request.getMajorId());

        if(user.getAuthority() == Authority.ASSOCIATION) {
            Notice notice = new Notice(request.getTitle(), request.getContent(), major);

            Notice savedNotice = noticeRepository.save(notice);

            createNoticeMajorResponse = new CreateNoticeMajorResponse(savedNotice.getId(), major.getId(), savedNotice.getTitle(), savedNotice.getContent());
        }

        return createNoticeMajorResponse;
    }

    /**
     * 그룹 공지 조회
     */
    public List<NoticeGroupResponse> findGroupNoticeByUser(HttpServletRequest httpServletRequest) {

        List<NoticeGroupResponse> noticeGroupResponses = new ArrayList<>();

        GroupResponse groupResponse = participantGroupService.findByUser(0, httpServletRequest)
                .getMyGroupParticipantResponses().get(0);

        Group group = groupService.findById(groupResponse.getId());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        List<Notice> notices = noticeRepository.findByGroup(group, sort);

        int length = Math.min(notices.size(), 2);

        for (int i = 0; i < length; i++) {

            Notice notice = notices.get(i);

            NoticeGroupResponse noticeGroupResponse = new NoticeGroupResponse(notice.getId(), notice.getTitle(), group.getGroupImageUrl());

            noticeGroupResponses.add(noticeGroupResponse);
        }

        return noticeGroupResponses;
    }

    /**
     * 학과 공지 조회
     */
    public List<NoticeMajorResponse> findMajorNoticeByUser(HttpServletRequest httpServletRequest) {

        List<NoticeMajorResponse> noticeMajorResponses = new ArrayList<>();

        MajorResponse majorResponse = majorService.majorInfoByUserId(httpServletRequest);

        Major major = majorService.findById(majorResponse.getId());

        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");

        List<Notice> notices = noticeRepository.findByMajor(major, sort);

        int length = Math.min(notices.size(), 2);

        for (int i = 0; i < length; i++) {

            Notice notice = notices.get(i);

            NoticeMajorResponse noticeMajorResponse = new NoticeMajorResponse(notice.getId(), notice.getTitle(), major.getProfileUrl());

            noticeMajorResponses.add(noticeMajorResponse);
        }

        return noticeMajorResponses;
    }

    /**
     * 공지 수정
     */
    @Transactional
    public NoticeResponse updateNoticeByUser(HttpServletRequest httpServletRequest, NoticeUpdateRequest noticeUpdateRequest, Long id) {
        NoticeResponse noticeResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(userId);

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        String title = noticeUpdateRequest.getTitle();
        String content = noticeUpdateRequest.getContent();

        if(notice.getMajor() != null) {
            if(user.getAuthority() == Authority.ASSOCIATION) {
                notice.update(title, content);
                noticeResponse = new NoticeResponse(notice.getId(), notice.getTitle(), notice.getContent());
            }
        }
        if(notice.getGroup() != null) {
            Long hostId = notice.getGroup().getHostId();
            if(hostId == userId ) {
                notice.update(title, content);
                noticeResponse = new NoticeResponse(notice.getId(), notice.getTitle(), notice.getContent());
            }
        }

        return noticeResponse;
    }

    /**
     * 공지 삭제
     */
    @Transactional
    public NoticeDeleteResponse deleteNoticeById(HttpServletRequest httpServletRequest, Long id) {
        NoticeDeleteResponse noticeDeleteResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(userId);

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);


        if(notice.getMajor() != null) {
            if(user.getAuthority() == Authority.ASSOCIATION) {
                noticeRepository.deleteById(id);
                noticeDeleteResponse = new NoticeDeleteResponse(id);
            }
        }
        if(notice.getGroup() != null) {
            Long hostId = notice.getGroup().getHostId();
            if(hostId == userId ) {
                noticeRepository.deleteById(id);
                noticeDeleteResponse = new NoticeDeleteResponse(id);
            }
        }

        return noticeDeleteResponse;
    }
}
