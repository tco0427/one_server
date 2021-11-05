package dgrowth.com.one_server.exception;

import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
public class CoreException extends BaseException {

    private final String message;

    private final static HttpStatus httpStatus = HttpStatus.INTERNAL_SERVER_ERROR;

    public CoreException(String message) {
        super(message, httpStatus);
        this.message = message;
    }
}
