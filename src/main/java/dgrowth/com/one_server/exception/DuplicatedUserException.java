package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class DuplicatedUserException extends BaseException {
    private final static String message = ResponseMessage.DUPLICATED_USER;

    private final static HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public DuplicatedUserException() {
        super(message, httpStatus);
    }
}
