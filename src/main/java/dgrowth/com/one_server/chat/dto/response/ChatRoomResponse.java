package dgrowth.com.one_server.chat.dto.response;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.ElementCollection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ChatRoomResponse {

    private Long id;

    private String name;

    private List<Long> userIdList;
}
