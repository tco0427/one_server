package dgrowth.com.one_server.group.dto.response;


import com.fasterxml.jackson.annotation.JsonFormat;
import dgrowth.com.one_server.group.domain.entity.Group;
import dgrowth.com.one_server.group.domain.enumeration.Category;
import dgrowth.com.one_server.notice.domain.entity.Notice;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;

@Data
@AllArgsConstructor
public class GroupWithNoticeResponse {
    private Long id;

    private String title;

    private Long hostId;

    private String groupImageUrl;   //그룹사진

    private String description; //그룹설명

    private String joinCondition;   //가입조건

    private String place;   //장소

    private DayOfWeek dayOfWeek;     //요일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime time; // 시간

    private Category category;  // 카테고리

    private List<Notice> notices;

    public GroupWithNoticeResponse(Group group) {
        this.id = group.getId();
        this.title = group.getTitle();
        this.hostId = group.getHostId();
        this.groupImageUrl = group.getGroupImageUrl();
        this.description = group.getDescription();
        this.joinCondition = group.getJoinCondition();
        this.place = group.getPlace();
        this.dayOfWeek = group.getDayOfWeek();
        this.time = group.getTime();
        this.category = group.getCategory();
        this.notices = group.getNotices().subList(0,2);
    }
}
