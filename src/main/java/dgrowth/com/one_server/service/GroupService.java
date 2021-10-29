package dgrowth.com.one_server.service;

import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.repository.GroupRepository;
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

    public Group findById(Long id) {
        return groupRepository.findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

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