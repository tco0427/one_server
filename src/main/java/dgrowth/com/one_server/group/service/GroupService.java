package dgrowth.com.one_server.group.service;


import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dgrowth.com.one_server.auth.service.AuthService;
import dgrowth.com.one_server.group.domain.exception.InvalidGroupException;
import dgrowth.com.one_server.group.dto.mapper.CategoryMapper;
import dgrowth.com.one_server.group.dto.mapper.GroupMapper;
import dgrowth.com.one_server.group.dto.request.GroupRequest;
import dgrowth.com.one_server.group.dto.response.*;
import dgrowth.com.one_server.group.domain.entity.Group;
import dgrowth.com.one_server.notice.domain.entity.Notice;
import dgrowth.com.one_server.notice.domain.repository.NoticeRepository;
import dgrowth.com.one_server.notice.dto.NoticeMapper;
import dgrowth.com.one_server.participantGroup.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.file.service.S3Uploader;
import dgrowth.com.one_server.user.domain.entity.User;
import dgrowth.com.one_server.group.domain.enumeration.Category;
import dgrowth.com.one_server.group.domain.repository.GroupRepository;
import dgrowth.com.one_server.user.service.UserService;
import javax.servlet.http.HttpServletRequest;

import dgrowth.com.one_server.participantGroup.domain.repository.ParticipantGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static dgrowth.com.one_server.participantGroup.domain.entity.QParticipantGroup.participantGroup;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final ParticipantGroupRepository participantGroupRepository;
    private final JPAQueryFactory queryFactory;
    private final S3Uploader s3Uploader;
    private final UserService userService;
    private final NoticeRepository noticeRepository;

    private Boolean isUserInGroup(Group group, Long userId) {
        if (group == null) {
            throw new InvalidGroupException();
        }

        List<ParticipantGroup> participantGroups = participantGroupRepository.findAllByGroup(group);

        for (ParticipantGroup participantGroup : participantGroups) {
            if (participantGroup.getUser().getId() == userId) {
                return true;
            }
        }

        return false;
    }

    public Group findById(Long id) {
        return groupRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
    }

    public GroupResponse groupInfoById(HttpServletRequest httpServletRequest, Long id) {

        GroupResponse groupResponse = null;
        // 1. ???????????? ?????? ??????
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. ?????? ????????? ?????? ??? ?????? ????????????
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. ?????? ??????
        Group group = findById(id);

        // 4. Response ??????
        groupResponse = GroupMapper.INSTANCE.toDto(group);

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        List<Notice> notices = noticeRepository.findByGroup(group, sort);

        groupResponse.setNotices(NoticeMapper.multipleToResponses(notices, 2));
        groupResponse.setIsParticipant(isUserInGroup(group, userId));
        groupResponse.setNumParticipants(participantGroupRepository.countParticipantGroupByGroup(group));

        return groupResponse;
    }

    public List<GroupResponse> findAll(Category category, HttpServletRequest httpServletRequest) {

        List<GroupResponse> groupResponsesList = new ArrayList<>();

        // 1. ???????????? ?????? ??????
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. ?????? ????????? ?????? ??? ?????? ????????????
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. ?????? ?????? ??????
        List<Group> groups = null;

        if (category == null) {
            groups = groupRepository.findAll();
        } else {
            groups = groupRepository.findAllByCategory(category);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, "createdDate");

        for (Group group : groups) {

            GroupResponse groupResponse = GroupMapper.INSTANCE.toDto(group);

            List<Notice> notices = noticeRepository.findByGroup(group, sort);

            groupResponse.setNotices(NoticeMapper.multipleToResponses(notices, 2));
            groupResponse.setIsParticipant(isUserInGroup(group, userId));
            groupResponse.setNumParticipants(participantGroupRepository.countParticipantGroupByGroup(group));
            groupResponsesList.add(groupResponse);
        }

        return groupResponsesList;
    }

    @Transactional
    public GroupResponse save(GroupRequest groupRequest, HttpServletRequest httpServletRequest) {
        GroupResponse groupResponse = null;
        // 1. ???????????? ?????? ??????
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. ?????? ????????? ?????? ??? ?????? ????????????
        Long hostId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(hostId);

        String url = null;
        try{
            if(groupRequest.getGroupImage() != null ) {
                url = s3Uploader.upload(groupRequest.getGroupImage(), "static");
            }
            // 3. ?????? ?????? ??? ??????

            Group newGroup = GroupMapper.INSTANCE.requestToEntity(groupRequest, hostId);
            newGroup.setGroupImageUrl(url);
            Group savedGroup = groupRepository.save(newGroup);

            ParticipantGroup participantGroup = new ParticipantGroup(user, savedGroup);

            participantGroupRepository.save(participantGroup);

            // 4. Response ??????
            groupResponse = GroupMapper.INSTANCE.toDto(savedGroup);

            groupResponse.setIsParticipant(true);
            groupResponse.setNumParticipants(1);
        } catch(IOException e) {
            e.getMessage();
        }

        return groupResponse;
    }

    @Transactional
    public DeleteGroupResponse deleteById(HttpServletRequest httpServletRequest, Long groupId) {
        DeleteGroupResponse deleteGroupResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        List<Group> groupList = groupRepository.findByHostId(userId);

        for (Group group : groupList) {
            if (group.getId() == groupId) {
                groupRepository.deleteById(groupId);
                deleteGroupResponse = new DeleteGroupResponse(group);
            }
        }

        return deleteGroupResponse;
    }

    public HotGroupListResponse findHotGroup() {
        HotGroupListResponse hotGroupListResponse = null;

        List<HotGroupResponse> list = new ArrayList<>();

        Date startDatetime = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0))); //?????? 00:00:00
        Date endDatetime = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59))); //?????? 23:59:59

        List<Tuple> fetch = queryFactory
                .select(participantGroup.group, participantGroup.count())
                .from(participantGroup)
                .where(participantGroup.createdDate.after(startDatetime).and(participantGroup.createdDate.before(endDatetime)))
                .groupBy(participantGroup.group)
                .orderBy(participantGroup.count().desc())
                .fetch();

        for (Tuple tuple : fetch) {
            Group group = tuple.get(participantGroup.group);
            Long count = tuple.get(participantGroup.count());

            HotGroupResponse hotGroupResponse = new HotGroupResponse(group, count);

            if(list.size() <= 10) {
                list.add(hotGroupResponse);
            }
        }

        hotGroupListResponse = new HotGroupListResponse(list);

        return hotGroupListResponse;
    }

    public List<CategoryResponse> categories() {
        List<CategoryResponse> categoryResponses = CategoryMapper.toDto();

        return categoryResponses;
    }
}
