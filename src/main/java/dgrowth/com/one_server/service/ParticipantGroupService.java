package dgrowth.com.one_server.service;
import dgrowth.com.one_server.data.dto.mapper.GroupMapper;
import dgrowth.com.one_server.data.dto.request.ParticipantGroupRequest;
import dgrowth.com.one_server.data.dto.response.*;
import dgrowth.com.one_server.domain.entity.User;
import dgrowth.com.one_server.exception.ExpiredTokenException;
import dgrowth.com.one_server.exception.InvalidTokenException;
import dgrowth.com.one_server.exception.InvalidUserException;
import dgrowth.com.one_server.repository.ParticipantGroupRepository;
import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.ParticipantGroup;
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

    public MyGroupParticipantListResponse findByUser(Integer page, HttpServletRequest httpServletRequest) {

        MyGroupParticipantListResponse myGroupParticipantListResponse = null;

        try{
            String token = authService.getTokenByHeader(httpServletRequest);

            Long userId = authService.getUserInfoByToken(token).getId();

            User user = userService.findById(userId);

            PageRequest pageRequest = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "createdDate"));

            List<ParticipantGroup> content = participantGroupRepository.findByUserId(user.getId(), pageRequest).getContent();

            List<MyGroupParticipantResponse> myGroupParticipantResponseList = new ArrayList<>();

            for (ParticipantGroup participantGroup : content) {
                Long groupId = participantGroup.getGroup().getId();
                Group group = groupService.findById(groupId);

                GroupResponse groupResponse = GroupMapper.INSTANCE.toDto(group);

                UserResponse userResponse = user.toResponse();

                MyGroupParticipantResponse myGroupParticipantResponse = new MyGroupParticipantResponse(groupResponse, userResponse);

                myGroupParticipantResponseList.add(myGroupParticipantResponse);
            }

            myGroupParticipantListResponse = new MyGroupParticipantListResponse(myGroupParticipantResponseList);

        } catch (ExpiredTokenException | InvalidUserException | InvalidTokenException e) {
            e.printStackTrace();
        }

        return myGroupParticipantListResponse;
    }

    @Transactional
    public ParticipantGroupResponse participant(ParticipantGroupRequest request, HttpServletRequest httpServletRequest) {
        ParticipantGroupResponse participantGroupResponse = null;

        try{
            String token = authService.getTokenByHeader(httpServletRequest);

            Long userId = authService.getUserInfoByToken(token).getId();

            User user = userService.findById(userId);

            Long groupId = request.getGropuId();

            Group group = groupService.findById(groupId);

            ParticipantGroup participantGroup = new ParticipantGroup(user, group);

            participantGroupResponse = new ParticipantGroupResponse(group.getId(), group.getTitle(), group.getDescription());

        } catch (ExpiredTokenException | InvalidUserException | InvalidTokenException e) {
            e.printStackTrace();
        }

        return participantGroupResponse;
    }

    @Transactional
    public Long save(ParticipantGroup participantGroup) {
        ParticipantGroup savedParticipantGroup = participantGroupRepository.save(participantGroup);
        return savedParticipantGroup.getId();
    }
}
