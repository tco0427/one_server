package dgrowth.com.one_server.user.domain.repository;

import dgrowth.com.one_server.user.domain.enumeration.PlatformType;
import dgrowth.com.one_server.user.domain.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByPlatformTypeAndPlatformId(PlatformType platformType, String platformId);
    boolean existsByPlatformTypeAndPlatformId(PlatformType platformType, String platformId);
}
