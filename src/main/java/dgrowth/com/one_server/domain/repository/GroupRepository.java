package dgrowth.com.one_server.domain.repository;

import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.ParticipantGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
