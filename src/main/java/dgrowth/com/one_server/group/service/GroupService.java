package dgrowth.com.one_server.group.service;


import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dgrowth.com.one_server.auth.service.AuthService;
import dgrowth.com.one_server.group.dto.mapper.CategoryMapper;
import dgrowth.com.one_server.group.dto.mapper.GroupMapper;
import dgrowth.com.one_server.group.dto.request.GroupRequest;
import dgrowth.com.one_server.group.dto.response.*;
import dgrowth.com.one_server.group.domain.entity.Group;
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

    public Group findById(Long id) {
        return groupRepository.findById(id)
            .orElseThrow(NoSuchElementException::new);
    }

    public GroupResponse groupInfoById(HttpServletRequest httpServletRequest, Long id) {

        GroupResponse groupResponse = null;
        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long hostId = authService.getUserInfoByToken(token).getId();

        // 3. 그룹 조회
        Group group = findById(id);

        // 4. Response 생성
        groupResponse = GroupMapper.INSTANCE.toDto(group);

        groupResponse.setNotices(NoticeMapper.multipleToResponses(group.getNotices(), 2));

        return groupResponse;
    }


    public List<GroupResponse> findAll(Category category, HttpServletRequest httpServletRequest) {

        List<GroupResponse> groupResponsesList = new ArrayList<>();

        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long userId = authService.getUserInfoByToken(token).getId();

        // 3. 그룹 전체 조회
        List<Group> groups = null;

        if (category == null) {
            groups = groupRepository.findAll();
        } else {
            groups = groupRepository.findAllByCategory(category);
        }

        for (Group group : groups) {

            GroupResponse groupResponse = GroupMapper.INSTANCE.toDto(group);

            groupResponse.setNotices(NoticeMapper.multipleToResponses(group.getNotices(), 2));

            groupResponsesList.add(groupResponse);
        }

        return groupResponsesList;
    }

    @Transactional
    public GroupResponse save(GroupRequest groupRequest, HttpServletRequest httpServletRequest) {
        GroupResponse groupResponse = null;
        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long hostId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(hostId);

        String url = null;
        try{
            if(groupRequest.getGroupImage() != null ) {
                url = s3Uploader.upload(groupRequest.getGroupImage(), "static");
            }
            // 3. 그룹 생성 및 저장

            Group newGroup = GroupMapper.INSTANCE.requestToEntity(groupRequest, hostId);
            newGroup.setGroupImageUrl(url);
            Group savedGroup = groupRepository.save(newGroup);

            ParticipantGroup participantGroup = new ParticipantGroup(user, savedGroup);

            participantGroupRepository.save(participantGroup);

            // 4. Response 생성
            groupResponse = GroupMapper.INSTANCE.toDto(savedGroup);

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

        Date startDatetime = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now().minusDays(1), LocalTime.of(0,0,0))); //어제 00:00:00
        Date endDatetime = java.sql.Timestamp.valueOf(LocalDateTime.of(LocalDate.now(), LocalTime.of(23,59,59))); //오늘 23:59:59

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
