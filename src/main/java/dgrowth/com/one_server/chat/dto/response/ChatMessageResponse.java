package dgrowth.com.one_server.chat.dto.response;

import java.time.LocalDateTime;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatMessageResponse {

    private String message;

    private Long senderId;

    private LocalDateTime sentDateTime;

    private Long chatRoomId;
}
