package dgrowth.com.one_server.service;

import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.repository.GroupRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GroupService {

    private final GroupRepository groupRepository;

    public List<Group> findAll() {
        return groupRepository.findAll();
    }

    @Transactional
    public Long save(Group group) {
        Group savedGroup = groupRepository.save(group);
        return savedGroup.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        groupRepository.deleteById(id);
    }
}
