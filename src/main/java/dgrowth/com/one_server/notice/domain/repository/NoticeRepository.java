package dgrowth.com.one_server.notice.domain.repository;

import dgrowth.com.one_server.group.domain.entity.Group;
import dgrowth.com.one_server.major.domain.entity.Major;
import dgrowth.com.one_server.notice.domain.entity.Notice;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByGroup(Group group, Sort sort);
    List<Notice> findByMajor(Major major, Sort sort);
}
