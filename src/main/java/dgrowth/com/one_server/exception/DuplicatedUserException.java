package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.DUPLICATED_USER) // main으로부터 생성됨
public class DuplicatedUserException extends RuntimeException {
    private static final String message = ResponseMessage.DUPLICATED_USER;

    HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public DuplicatedUserException() {
        super(message);
    }
}
