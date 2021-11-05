package dgrowth.com.one_server.data.dto.response;

import java.time.LocalTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@AllArgsConstructor
public class ErrorResponse {

    private LocalTime timeStamp;
    private String message;
    private int status;
    private String code;
}
