package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class ExpiredTokenException extends RuntimeException{
    private static final String message = ResponseMessage.EXPIRED_TOKEN;

    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public ExpiredTokenException() {
        super(message);
    }
}
