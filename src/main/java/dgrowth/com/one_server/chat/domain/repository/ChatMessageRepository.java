package dgrowth.com.one_server.chat.domain.repository;

import dgrowth.com.one_server.chat.domain.entity.ChatMessage;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ChatMessageRepository extends JpaRepository<ChatMessage, Long> {

    List<ChatMessage> getChatMessagesByChatRoomId(Long chatRoomId);
}
