package dgrowth.com.one_server.domain.entity;

import dgrowth.com.one_server.data.dto.response.GroupResponse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "\"Group\"")
public class Group extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long hostId;    //그룹을 만든 UserId

    private String title;   //그룹명

    private String groupImageUrl;   //그룹사진

    private String description; //그룹설명

    private String joinCondition;   //가입조건

    private String place;   //장소

    private String appointment;     //요일 및 시간

    public Group(String title, String groupImageUrl, String description,
        String joinCondition, String place, String appointment) {
        this.title = title;
        this.groupImageUrl = groupImageUrl;
        this.description = description;
        this.joinCondition = joinCondition;
        this.place = place;
        this.appointment = appointment;
    }

    @OneToMany(mappedBy = "group")
    private List<ParticipantGroup> participantGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Notice> notices = new ArrayList<>();

    public GroupResponse toResponse() {
        return new GroupResponse(id, title, hostId, groupImageUrl,
                description, joinCondition, place, appointment);
    }

}
