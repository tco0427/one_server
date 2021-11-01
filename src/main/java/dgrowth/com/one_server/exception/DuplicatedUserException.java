package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicatedUserException extends RuntimeException {
    private static final String message = ResponseMessage.DUPLICATED_USER;

    HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public DuplicatedUserException() {
        super(message);
    }
}
