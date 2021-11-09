package dgrowth.com.one_server.participantGroup.service;

import dgrowth.com.one_server.auth.service.AuthService;
import dgrowth.com.one_server.group.dto.mapper.GroupMapper;
import dgrowth.com.one_server.participantGroup.dto.ParticipantGroupRequest;
import dgrowth.com.one_server.group.dto.response.GroupResponse;
import dgrowth.com.one_server.group.service.GroupService;
import dgrowth.com.one_server.participantGroup.dto.response.MyGroupParticipantListResponse;
import dgrowth.com.one_server.participantGroup.dto.response.MyGroupParticipantResponse;
import dgrowth.com.one_server.participantGroup.dto.response.ParticipantGroupResponse;
import dgrowth.com.one_server.user.dto.response.UserResponse;
import dgrowth.com.one_server.user.domain.entity.User;
import dgrowth.com.one_server.participantGroup.domain.repository.ParticipantGroupRepository;
import dgrowth.com.one_server.group.domain.entity.Group;
import dgrowth.com.one_server.participantGroup.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantGroupService {

    private final ParticipantGroupRepository participantGroupRepository;
    private final GroupService groupService;
    private final AuthService authService;
    private final UserService userService;

    public MyGroupParticipantListResponse findByUser(Integer page,
        HttpServletRequest httpServletRequest) {

        MyGroupParticipantListResponse myGroupParticipantListResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(userId);

        PageRequest pageRequest = PageRequest.of(page, 10,
            Sort.by(Sort.Direction.DESC, "createdDate"));

        List<ParticipantGroup> content = participantGroupRepository.findByUserId(user.getId(),
            pageRequest).getContent();

        List<MyGroupParticipantResponse> myGroupParticipantResponseList = new ArrayList<>();

        for (ParticipantGroup participantGroup : content) {
            Long groupId = participantGroup.getGroup().getId();
            Group group = groupService.findById(groupId);

            GroupResponse groupResponse = GroupMapper.INSTANCE.toDto(group);

            UserResponse userResponse = user.toResponse();

            MyGroupParticipantResponse myGroupParticipantResponse = new MyGroupParticipantResponse(
                groupResponse);

            myGroupParticipantResponseList.add(myGroupParticipantResponse);
        }

        myGroupParticipantListResponse = new MyGroupParticipantListResponse(
            myGroupParticipantResponseList);

        return myGroupParticipantListResponse;
    }

    @Transactional
    public ParticipantGroupResponse participant(ParticipantGroupRequest request,
        HttpServletRequest httpServletRequest) {
        ParticipantGroupResponse participantGroupResponse = null;

        String token = authService.getTokenByHeader(httpServletRequest);

        Long userId = authService.getUserInfoByToken(token).getId();

        User user = userService.findById(userId);

        Long groupId = request.getGropuId();

        Group group = groupService.findById(groupId);

        ParticipantGroup participantGroup = new ParticipantGroup(user, group);

        participantGroupRepository.save(participantGroup);

        participantGroupResponse = new ParticipantGroupResponse(group.getId(), group.getTitle(),
            group.getDescription());

        return participantGroupResponse;
    }

    @Transactional
    public Long save(ParticipantGroup participantGroup) {
        ParticipantGroup savedParticipantGroup = participantGroupRepository.save(participantGroup);
        return savedParticipantGroup.getId();
    }
}
