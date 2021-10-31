package dgrowth.com.one_server.service;

import dgrowth.com.one_server.data.dto.mapper.GroupMapper;
import dgrowth.com.one_server.data.dto.request.GroupRequest;
import dgrowth.com.one_server.data.dto.response.GroupResponse;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.exception.ExpiredTokenException;
import dgrowth.com.one_server.exception.InvalidTokenException;
import dgrowth.com.one_server.exception.InvalidUserException;
import dgrowth.com.one_server.repository.GroupRepository;
import java.nio.file.AccessDeniedException;
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

    public GroupResponse groupInfoById(HttpServletRequest httpServletRequest, Long id){
        GroupResponse groupResponse = null;
        try {
            // 1. 헤더에서 토큰 체크
            String token = authService.getTokenByHeader(httpServletRequest);

            // 2. 토큰 유효성 체크 및 유저 불러오기
            Long hostId = authService.getUserInfoByToken(token).getId();

            // 3. 그룹 조회
            Group group = findById(id);

            // 4. Response 생성
            groupResponse = GroupMapper.INSTANCE.toDto(group);
        } catch (InvalidTokenException | ExpiredTokenException e) {
            e.printStackTrace();
            throw new RuntimeException("Token Error");
        } catch (InvalidUserException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid User Error");
        }

        return groupResponse;
    }

    public List<GroupResponse> findAll(HttpServletRequest httpServletRequest) {
        List<GroupResponse> groupResponseList = null;
        try {
            // 1. 헤더에서 토큰 체크
            String token = authService.getTokenByHeader(httpServletRequest);

            // 2. 토큰 유효성 체크 및 유저 불러오기
            Long hostId = authService.getUserInfoByToken(token).getId();

            // 3. 그룹 전체 조회
            List<Group> groups = groupRepository.findAll();

            // 4. Response 생성
            groupResponseList = GroupMapper.INSTANCE.toDto(groups);

        } catch (InvalidTokenException | ExpiredTokenException e) {
            e.printStackTrace();
            throw new RuntimeException("Token Error");
        } catch (InvalidUserException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid User Error");
        }

        return groupResponseList;
    }

    @Transactional
    public GroupResponse save(GroupRequest groupRequest, HttpServletRequest httpServletRequest) {
        GroupResponse groupResponse = null;
        try {
            // 1. 헤더에서 토큰 체크
            String token = authService.getTokenByHeader(httpServletRequest);

            // 2. 토큰 유효성 체크 및 유저 불러오기
            Long hostId = authService.getUserInfoByToken(token).getId();

            // 3. 그룹 생성 및 저장
            Group newGroup = GroupMapper.INSTANCE.requestToEntity(groupRequest, hostId);
            Group savedGroup = groupRepository.save(newGroup);

            // 4. Response 생성
            groupResponse = GroupMapper.INSTANCE.toDto(savedGroup);
        }catch (InvalidTokenException | ExpiredTokenException e) {
            e.printStackTrace();
            throw new RuntimeException("Token Error");
        } catch (InvalidUserException e) {
            e.printStackTrace();
            throw new RuntimeException("Invalid User Error");
        }

        return groupResponse;
    }

    @Transactional
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }

    public List<Group> findByHostId(Long hostId) {
        return groupRepository.findByHostId(hostId);
    }
}