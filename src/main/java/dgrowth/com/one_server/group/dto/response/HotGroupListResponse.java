package dgrowth.com.one_server.group.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class HotGroupListResponse {
    List<HotGroupResponse> hotGroupResponses;
}
