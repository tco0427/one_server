package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.INVALID_USER)
public class InvalidUserException extends RuntimeException {

    String message = ResponseMessage.INVALID_USER;

    public InvalidUserException() {
        this.message = message;
    }
}
