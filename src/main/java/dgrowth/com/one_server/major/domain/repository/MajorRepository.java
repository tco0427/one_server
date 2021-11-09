package dgrowth.com.one_server.major.domain.repository;

import dgrowth.com.one_server.major.domain.entity.Major;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MajorRepository extends JpaRepository<Major, Long> {
}
