package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.INVALID_USER) // main으로부터 생성됨
public class InvalidUserException extends RuntimeException{
    private final static String message = ResponseMessage.INVALID_USER;

    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public InvalidUserException() {
        super(message);
    }
}
