package dgrowth.com.one_server.auth.domain.exception;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.web.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpiredTokenException extends BaseException {
    private static final String message = ResponseMessage.EXPIRED_TOKEN;

    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public ExpiredTokenException() {
        super(message, httpStatus);
    }
}
