package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.EXPIRED_TOKEN)
public class ExpiredTokenException extends RuntimeException {

    String message = ResponseMessage.EXPIRED_TOKEN;

    public ExpiredTokenException() {
        this.message = message;
    }
}
