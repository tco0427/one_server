package dgrowth.com.one_server.participantGroup.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ParticipantGroupResponse {
    private Long groupId;
    private String title;
    private String description;
}
