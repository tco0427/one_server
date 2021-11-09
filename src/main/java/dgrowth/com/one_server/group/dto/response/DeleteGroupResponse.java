package dgrowth.com.one_server.group.dto.response;

import dgrowth.com.one_server.group.domain.entity.Group;
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
