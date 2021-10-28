package dgrowth.com.one_server.data.dto.request;

import dgrowth.com.one_server.domain.entity.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateGroupRequest {

    private String title;
}
