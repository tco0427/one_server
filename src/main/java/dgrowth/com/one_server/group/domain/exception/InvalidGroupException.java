package dgrowth.com.one_server.group.domain.exception;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.web.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidGroupException extends BaseException {

    private final static String message = ResponseMessage.INVALID_USER;

    private final static HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public InvalidGroupException() {
        super(message, httpStatus);
    }
}
