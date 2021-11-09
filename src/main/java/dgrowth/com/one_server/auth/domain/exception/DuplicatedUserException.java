package dgrowth.com.one_server.auth.domain.exception;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.web.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicatedUserException extends BaseException {
    private final static String message = ResponseMessage.DUPLICATED_USER;

    private final static HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public DuplicatedUserException() {
        super(message, httpStatus);
    }
}
