package dgrowth.com.one_server.data.dto.request;

import com.fasterxml.jackson.annotation.JsonFormat;
import dgrowth.com.one_server.domain.enumeration.Category;
import java.time.DayOfWeek;
import java.time.LocalTime;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupRequest {
    private String title;   //그룹명

    private String groupImageUrl;   //그룹사진

    private String description; //그룹설명

    private String joinCondition;   //가입조건

    private String place;   //장소

    private String appointment;     //요일 및 시간

    private Category category;  // 카테고리

    private DayOfWeek dayOfWeek;     //요일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time;

}
