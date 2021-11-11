package dgrowth.com.one_server.data.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class MyGroupParticipantListResponse {
    private List<GroupResponse> groupResponses;
}
