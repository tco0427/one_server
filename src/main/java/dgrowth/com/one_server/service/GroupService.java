package dgrowth.com.one_server.service;


import dgrowth.com.one_server.data.dto.mapper.GroupMapper;
import dgrowth.com.one_server.data.dto.request.GroupRequest;
import dgrowth.com.one_server.data.dto.response.DeleteGroupResponse;
import dgrowth.com.one_server.data.dto.response.GroupResponse;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.enumeration.Category;
import dgrowth.com.one_server.repository.GroupRepository;
import javax.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;
    private final AuthService authService;

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
    public GroupResponse save(GroupRequest groupRequest, HttpServletRequest httpServletRequest) {
        GroupResponse groupResponse = null;
        // 1. 헤더에서 토큰 체크
        String token = authService.getTokenByHeader(httpServletRequest);

        // 2. 토큰 유효성 체크 및 유저 불러오기
        Long hostId = authService.getUserInfoByToken(token).getId();

        // 3. 그룹 생성 및 저장
        Group newGroup = GroupMapper.INSTANCE.requestToEntity(groupRequest, hostId);
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
}