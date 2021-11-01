package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidUserException extends RuntimeException{
    private final static String message = ResponseMessage.INVALID_USER;

    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public InvalidUserException() {
        super(message);
    }
}
