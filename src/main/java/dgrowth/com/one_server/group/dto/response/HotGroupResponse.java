package dgrowth.com.one_server.group.dto.response;

import dgrowth.com.one_server.group.domain.entity.Group;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class HotGroupResponse {
    private Long id;
    private String title;   //그룹명
    private String groupImageUrl;   //그룹사진
    private Long memberCount; //그룹인원

    public HotGroupResponse(Group group, Long count) {
        id = group.getId();
        title = group.getTitle();
        groupImageUrl = group.getGroupImageUrl();
        memberCount = count;
    }
}
