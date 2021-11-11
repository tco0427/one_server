package dgrowth.com.one_server.chat.dto.request;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateRoomRequest {

    private String name;

    private List<Long> participantsId;
}
