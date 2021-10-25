package dgrowth.com.one_server.data.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Gender {
    FEMALE("FEMALE"), MALE("MALE");

    private final String value;
}
