package dgrowth.com.one_server.domain.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

import javax.persistence.*;

import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.*;
import static lombok.AccessLevel.*;

@Entity
@Getter
@ToString
@NoArgsConstructor(access = PROTECTED)
public class ParticipantGroup extends BaseEntity{

    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @ManyToOne(fetch = LAZY)
    private Group group;

    @ManyToOne(fetch = LAZY)
    private User user;

    public ParticipantGroup(User user, Group group) {
        this.user = user;
        this.group = group;
    }
}
