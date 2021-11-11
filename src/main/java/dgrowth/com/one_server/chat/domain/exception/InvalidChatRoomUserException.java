package dgrowth.com.one_server.chat.domain.exception;

import dgrowth.com.one_server.base.exception.BaseException;
import dgrowth.com.one_server.web.property.ResponseMessage;
import org.springframework.http.HttpStatus;

public class InvalidChatRoomUserException extends BaseException {
    private final static String message = ResponseMessage.INVALID_CHAT_ROOM_USER;

    private final static HttpStatus httpStatus = HttpStatus.NOT_FOUND;

    public InvalidChatRoomUserException() {
        super(message, httpStatus);
    }
}
