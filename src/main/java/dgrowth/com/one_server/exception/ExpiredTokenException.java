package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class ExpiredTokenException extends BaseException{
    private static final String message = ResponseMessage.EXPIRED_TOKEN;

    private static final HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public ExpiredTokenException() {
        super(message, httpStatus);
    }
}
