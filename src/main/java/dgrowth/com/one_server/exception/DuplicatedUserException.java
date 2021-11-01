package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class DuplicatedUserException extends RuntimeException {
    HttpStatus httpStatus = HttpStatus.NOT_FOUND;
    String message = ResponseMessage.DUPLICATED_USER;
}
