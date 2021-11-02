package dgrowth.com.one_server.exception;

import dgrowth.com.one_server.data.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@Getter
@ResponseStatus(value = HttpStatus.UNAUTHORIZED, reason = ResponseMessage.FAILED_TO_AUTH_KAKAO)
public class ExpiredKakaoTokenException extends RuntimeException {

    String message = ResponseMessage.FAILED_TO_AUTH_KAKAO;

    public ExpiredKakaoTokenException() {
        this.message = message;
    }
}
