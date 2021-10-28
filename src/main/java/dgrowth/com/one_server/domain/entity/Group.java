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

    private Long hostId;

    @OneToMany(mappedBy = "group")
    private List<ParticipantGroup> participantGroups = new ArrayList<>();

    public Group(String title, Long id) {
        this.title = title;
        this.hostId = id;
    }

    public GroupResponse toResponse() {
        return new GroupResponse(id, title, hostId, participantGroups);
    }
}
