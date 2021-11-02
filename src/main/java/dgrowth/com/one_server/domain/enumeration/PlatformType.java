package dgrowth.com.one_server.domain.enumeration;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum PlatformType {
    KAKAO("KAKAO"), NAVER("NAVER");

    private final String value;
}
