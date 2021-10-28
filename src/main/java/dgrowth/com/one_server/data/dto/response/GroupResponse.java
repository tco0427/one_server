package dgrowth.com.one_server.data.dto.response;

import dgrowth.com.one_server.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.domain.entity.User;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {

    private Long id;

    private String title;

    private Long hostId;

    private List<ParticipantGroup> participantGroups;
}
