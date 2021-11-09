package dgrowth.com.one_server.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Authority {
    USER("USER"), ADMIN("ADMIN"), ASSOCIATION("ASSOCIATION");

    private final String value;
}

