package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.EXPIRED_TOKEN) // main으로부터 생성됨
public class ExpiredTokenException extends RuntimeException{
    private static final String message = ResponseMessage.EXPIRED_TOKEN;

    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;

    public ExpiredTokenException() {
        super(message);
    }
}
