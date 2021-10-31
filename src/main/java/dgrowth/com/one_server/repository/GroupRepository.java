package dgrowth.com.one_server.repository;

import dgrowth.com.one_server.domain.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByHostId(Long hostId);
}
