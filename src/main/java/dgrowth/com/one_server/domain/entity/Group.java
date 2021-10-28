package dgrowth.com.one_server.domain.entity;

import dgrowth.com.one_server.data.dto.response.GroupResponse;
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
@Table(name = "Grup")
public class Group {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    private String title;

    @OneToOne
    private User host;

    @OneToMany(mappedBy = "group")
    private List<ParticipantGroup> participantGroups = new ArrayList<>();

    public Group(String title, User host) {
        this.title = title;
        this.host = host;
    }

    public GroupResponse toResponse() {
        return new GroupResponse(id, title, host, participantGroups);
    }
}
