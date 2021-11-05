package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class InvalidTokenException extends BaseException{
    private final static String message = ResponseMessage.INVALID_TOKEN;

    private final static HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public InvalidTokenException() {
        super(message, httpStatus);
    }
}
