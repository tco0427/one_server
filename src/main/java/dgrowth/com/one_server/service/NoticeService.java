package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.dto.request.CreateNoticeGroupRequest;
import dgrowth.com.one_server.data.dto.request.CreateNoticeMajorRequest;
import dgrowth.com.one_server.data.dto.request.NoticeUpdateRequest;
import dgrowth.com.one_server.data.dto.response.*;
import dgrowth.com.one_server.domain.entity.*;
import dgrowth.com.one_server.repository.NoticeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
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

        Major major = majorService.findById(request.getMajorId());

        if(major.getHostId() == userId) {
            Notice notice = new Notice(request.getTitle(), request.getContent(), major);

            Notice savedNotice = noticeRepository.save(notice);

            createNoticeMajorResponse = new CreateNoticeMajorResponse(savedNotice.getId(), major.getId(), savedNotice.getTitle(), savedNotice.getContent());
        }

        return createNoticeMajorResponse;
    }

    /**
     * 그룹 공지 조회
     */
    public NoticeGroupResponse findGroupNoticeByUser(HttpServletRequest httpServletRequest) {

        MyGroupParticipantResponse myGroupParticipantResponse = participantGroupService.findByUser(0, httpServletRequest)
                .getMyGroupParticipantResponses().get(0);

        GroupResponse groupResponse = myGroupParticipantResponse.getGroupResponse();

        Group group = groupService.findById(groupResponse.getId());

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        Notice notice = noticeRepository.findByGroup(group, sort).get(0);

        NoticeGroupResponse noticeGroupResponse = new NoticeGroupResponse(notice.getId(), notice.getTitle(), group.getGroupImageUrl());

        return noticeGroupResponse;
    }

    /**
     * 학과 공지 조회
     */
    public NoticeMajorResponse findMajorNoticeByUser(HttpServletRequest httpServletRequest) {

        MajorResponse majorResponse = majorService.majorInfoByUserId(httpServletRequest);

        Major major = majorService.findById(majorResponse.getId());

        Sort sort = Sort.by(Sort.Direction.DESC, "created_date");

        Notice notice = noticeRepository.findByMajor(major, sort).get(0);

        NoticeMajorResponse  noticeMajorResponse = new NoticeMajorResponse(notice.getId(), notice.getTitle(), major.getProfileUrl());

        return noticeMajorResponse;
    }

    /**
     * 공지 수정
     */
    @Transactional
    public NoticeResponse updateNoticeByUser(HttpServletRequest httpServletRequest, NoticeUpdateRequest noticeUpdateRequest, Long id) {
        NoticeResponse noticeResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        Notice notice = noticeRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);

        String title = noticeUpdateRequest.getTitle();
        String content = noticeUpdateRequest.getContent();

        if(notice.getMajor() != null) {
            Long hostId = notice.getMajor().getHostId();
            if(hostId == userId) {
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
            Long hostId = notice.getMajor().getHostId();
            if(hostId == userId) {
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
