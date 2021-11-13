package dgrowth.com.one_server.participantGroup.domain.repository;


import dgrowth.com.one_server.group.domain.entity.Group;
import dgrowth.com.one_server.participantGroup.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.user.domain.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ParticipantGroupRepository extends JpaRepository<ParticipantGroup, Long> {

    Slice<ParticipantGroup> findByUserId(Long userId, Pageable pageable);

    List<ParticipantGroup> findAllByGroup(Group group);

    Integer countParticipantGroupByGroup(Group group);
}
