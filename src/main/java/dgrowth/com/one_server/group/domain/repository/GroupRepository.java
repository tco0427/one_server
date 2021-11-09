package dgrowth.com.one_server.group.domain.repository;

import dgrowth.com.one_server.group.domain.entity.Group;
import dgrowth.com.one_server.group.domain.enumeration.Category;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
    List<Group> findByHostId(Long hostId);

    List<Group> findAllByCategory(Category category);
}
