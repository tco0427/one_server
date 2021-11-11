package dgrowth.com.one_server.chat.domain.repository;

import dgrowth.com.one_server.chat.domain.entity.ChatRoom;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {

}
