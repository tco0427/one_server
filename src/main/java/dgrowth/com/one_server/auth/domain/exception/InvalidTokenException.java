package dgrowth.com.one_server.auth.domain.exception;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.web.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidTokenException extends BaseException {
    private final static String message = ResponseMessage.INVALID_TOKEN;

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public InvalidTokenException() {
        super(message, httpStatus);
    }
}
