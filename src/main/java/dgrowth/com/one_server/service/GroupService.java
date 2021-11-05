package dgrowth.com.one_server.service;


import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQueryFactory;
import dgrowth.com.one_server.data.dto.mapper.GroupMapper;
import dgrowth.com.one_server.data.dto.request.GroupRequest;
import dgrowth.com.one_server.data.dto.response.DeleteGroupResponse;
import dgrowth.com.one_server.data.dto.response.GroupResponse;
import dgrowth.com.one_server.data.dto.response.HotGroupListResponse;
import dgrowth.com.one_server.data.dto.response.HotGroupResponse;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.domain.enumeration.Category;
import dgrowth.com.one_server.repository.GroupRepository;
import javax.servlet.http.HttpServletRequest;

import dgrowth.com.one_server.repository.ParticipantGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;

import static dgrowth.com.one_server.domain.entity.QParticipantGroup.participantGroup;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final AuthService authService;
    private final ParticipantGroupRepository participantGroupRepository;
    private final JPAQueryFactory queryFactory;
    private final S3Uploader s3Uploader;

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

        return groupResponse;
    }

    public List<GroupResponse> findAll(Category category, HttpServletRequest httpServletRequest) {
        List<GroupResponse> groupResponseList = null;
        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long hostId = authService.getUserInfoByToken(token).getId();

        // 3. 그룹 전체 조회
        List<Group> groups = null;
        if (category == null) {
            groups = groupRepository.findAll();
        } else {
            groups = groupRepository.findAllByCategory(category);
        }

        // 4. Response 생성
        groupResponseList = GroupMapper.INSTANCE.toDto(groups);

        return groupResponseList;
    }

    @Transactional
    public GroupResponse save(GroupRequest groupRequest, HttpServletRequest httpServletRequest,
                              MultipartFile multipartFile) {
        GroupResponse groupResponse = null;
        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long hostId = authService.getUserInfoByToken(token).getId();

        String url = null;
        try{
            url = s3Uploader.upload(multipartFile, "static");
        } catch(IOException e) {
            return groupResponse;
        }

        // 3. 그룹 생성 및 저장

        Group newGroup = GroupMapper.INSTANCE.requestToEntity(groupRequest, hostId);
        newGroup.setGroupImageUrl(url);
        Group savedGroup = groupRepository.save(newGroup);

        // 4. Response 생성
        groupResponse = GroupMapper.INSTANCE.toDto(savedGroup);

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

    public List<Group> findByHostId(Long hostId) {
        return groupRepository.findByHostId(hostId);
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
}