package dgrowth.com.one_server.service;

import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.domain.repository.ParticipantGroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ParticipantGroupService {

    private final ParticipantGroupRepository participantGroupRepository;

    public List<Group> findByUserId(Long userId, PageRequest pageRequest) {
        return participantGroupRepository.findByUserId(userId, pageRequest).getContent();
    }

    public Long participant(ParticipantGroup participantGroup) {
        ParticipantGroup savedParticipantGroup = participantGroupRepository.save(participantGroup);

        return savedParticipantGroup.getId();
    }
}
