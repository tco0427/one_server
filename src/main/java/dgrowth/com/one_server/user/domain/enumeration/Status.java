package dgrowth.com.one_server.user.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    APPROVED("APPROVED"), REFUSED("REFUSED"), PROCESSING("PROCESSING");

    private final String value;
}