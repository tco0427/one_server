package dgrowth.com.one_server.chat.domain.exception;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.web.property.ResponseMessage;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class InvalidChatRoomIdException extends BaseException {
    private final static String message = ResponseMessage.INVALID_CHAT_ROOM_ID;

    private final static HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public InvalidChatRoomIdException() {
        super(message, httpStatus);
    }
}