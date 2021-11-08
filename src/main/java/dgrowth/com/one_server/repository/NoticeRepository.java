package dgrowth.com.one_server.repository;

import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.Major;
import dgrowth.com.one_server.domain.entity.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByGroup(Group group, Sort sort);
    List<Notice> findByMajor(Major major, Sort sort);
}
