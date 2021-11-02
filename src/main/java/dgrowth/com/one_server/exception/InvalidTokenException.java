package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Gette
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.INVALID_TOKEN) // main으로부터 생성됨
public class InvalidTokenException extends RuntimeException{
    private final static String message = ResponseMessage.INVALID_TOKEN;

    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public InvalidTokenException() {
        super(message);
    }
}
