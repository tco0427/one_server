package dgrowth.com.one_server.group.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import dgrowth.com.one_server.group.domain.enumeration.Category;
import dgrowth.com.one_server.notice.domain.entity.Notice;
import dgrowth.com.one_server.notice.dto.response.NoticeResponse;
import java.time.DayOfWeek;
import java.time.LocalTime;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class GroupResponse {
    private Long id;

    private String title;

    private Long hostId;

    private String groupImageUrl;   //그룹사진

    private String description; //그룹설명

    private String joinCondition;   //가입조건

    private String place;   //장소

    private DayOfWeek dayOfWeek;     //요일

    @JsonFormat(shape = Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    private LocalTime time; // 시간

    private Category category;  // 카테고리

    @Setter
    private List<NoticeResponse> notices;
}
