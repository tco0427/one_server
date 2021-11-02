package dgrowth.com.one_server.data.dto.response;

import dgrowth.com.one_server.domain.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class DeleteGroupResponse {
    private Long id;

    public DeleteGroupResponse(Group group) {
        this.id = group.getId();
    }
}
