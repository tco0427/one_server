package dgrowth.com.one_server.repository;

import dgrowth.com.one_server.domain.entity.Group;
import dgrowth.com.one_server.domain.entity.ParticipantGroup;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Repository
public interface ParticipantGroupRepository extends JpaRepository<ParticipantGroup, Long> {
    Slice<ParticipantGroup> findByUserId(Long userId, Pageable pageable);
}
