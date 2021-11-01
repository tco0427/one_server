package dgrowth.com.one_server.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    EXERCISE("EXERCISE"), CULTURE("CULTURE"), STUDY("STUDY"), MEAL("MEAL");

    private final String value;
}
