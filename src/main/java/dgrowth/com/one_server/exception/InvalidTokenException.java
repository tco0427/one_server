package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidTokenException extends Exception{
    HttpStatus httpStatus = HttpStatus.UNAUTHORIZED;
    String message = ResponseMessage.INVALID_TOKEN;
}
