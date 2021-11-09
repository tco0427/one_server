package dgrowth.com.one_server.group.domain.enumeration;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Category {
    EXERCISE("EXERCISE"), CULTURE("CULTURE"), STUDY("STUDY"), MEAL("MEAL");

    private final String value;
}
