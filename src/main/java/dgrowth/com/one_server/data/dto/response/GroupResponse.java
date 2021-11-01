package dgrowth.com.one_server.data.dto.response;

import static javax.persistence.EnumType.STRING;

import com.fasterxml.jackson.annotation.JsonFormat;
import dgrowth.com.one_server.domain.enumeration.Category;
import java.time.DayOfWeek;
import java.time.LocalTime;
import javax.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

@Data
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

    private String appointment;     //시간 및 장소

    @Enumerated(STRING)
    private Category category;  // 카테고리

    private DayOfWeek dayOfWeek;     //요일

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "HH:mm", timezone = "Asia/Seoul")
    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time; // 시간
}
