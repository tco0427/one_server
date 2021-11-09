package dgrowth.com.one_server.auth.domain.repository;

import dgrowth.com.one_server.auth.domain.entity.UserToken;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserTokenRepository extends JpaRepository<UserToken, Long> {

}
