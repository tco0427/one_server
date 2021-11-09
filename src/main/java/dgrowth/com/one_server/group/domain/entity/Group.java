package dgrowth.com.one_server.group.domain.entity;

import dgrowth.com.one_server.base.entity.BaseEntity;
import dgrowth.com.one_server.notice.domain.entity.Notice;
import dgrowth.com.one_server.participantGroup.domain.entity.ParticipantGroup;
import dgrowth.com.one_server.group.domain.enumeration.Category;
import java.time.DayOfWeek;
import java.time.LocalTime;

import lombok.*;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;

import static javax.persistence.EnumType.STRING;
import static javax.persistence.GenerationType.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "\"Group\"")
public class Group extends BaseEntity {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long hostId;    //그룹을 만든 UserId

    private String title;   //그룹명

    @Setter
    private String groupImageUrl;   //그룹사진

    private String description; //그룹설명

    private String joinCondition;   //가입조건

    private String place;   //장소

    private DayOfWeek dayOfWeek;     //요일

    @DateTimeFormat(pattern = "HH:mm")
    private LocalTime time; // 시간

    @Enumerated(STRING)
    private Category category;  // 카테고리

    @OneToMany(mappedBy = "group")
    private List<ParticipantGroup> participantGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Notice> notices = new ArrayList<>();

    public Group(String title, String groupImageUrl, String description,
                 String joinCondition, String place, DayOfWeek dayOfWeek, LocalTime time, Category category) {
        this.title = title;
        this.groupImageUrl = groupImageUrl;
        this.description = description;
        this.joinCondition = joinCondition;
        this.place = place;
        this.dayOfWeek = dayOfWeek;
        this.time = time;
        this.category = category;
    }
}
