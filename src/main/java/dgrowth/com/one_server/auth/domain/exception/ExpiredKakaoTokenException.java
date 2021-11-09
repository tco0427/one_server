package dgrowth.com.one_server.auth.domain.exception;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.web.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpiredKakaoTokenException extends BaseException {

    private final static String message = ResponseMessage.FAILED_TO_AUTH_KAKAO;

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public ExpiredKakaoTokenException() {
        super(message, httpStatus);
    }
}
