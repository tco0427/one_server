package dgrowth.com.one_server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import java.util.ArrayList;
import java.util.List;

import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@NoArgsConstructor(access = PROTECTED)
@ToString
@Table(name = "\"Group\"")
public class Group {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private Long hostId;    //그룹을 만든 UserId

    private String title;   //그룹명

    private String groupImageUrl;   //그룹사진

    private String description; //그룹설명

    private String joinCondition;   //가입조건

    private String place;   //장소

    private String appointment;     //시간 및 장소

    @OneToMany(mappedBy = "group")
    private List<ParticipantGroup> participantGroups = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Notice> notices = new ArrayList<>();

    @OneToMany(mappedBy = "group")
    private List<Gallery> galleries = new ArrayList<>();

    public Group(String title, Long hostId) {
        this.title = title;
        this.hostId = hostId;
    }

}
