package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.DUPLICATED_USER)
public class DuplicatedUserException extends RuntimeException {

    String message = ResponseMessage.DUPLICATED_USER;

    public DuplicatedUserException() {
        this.message = message;
    }
}
