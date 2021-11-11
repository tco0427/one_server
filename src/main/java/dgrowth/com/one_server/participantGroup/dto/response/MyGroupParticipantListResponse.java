package dgrowth.com.one_server.participantGroup.dto.response;

import dgrowth.com.one_server.group.dto.response.GroupResponse;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyGroupParticipantListResponse {
    private List<GroupResponse> myGroupParticipantResponses;
}
