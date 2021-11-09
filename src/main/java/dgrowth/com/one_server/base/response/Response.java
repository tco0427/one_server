package dgrowth.com.one_server.base.response;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.HttpStatus;

@ToString
@Getter
public class Response<T> {
    @ApiModelProperty(value = "상태코드")
    private int status;

    @ApiModelProperty(value = "메세지")
    private String message;

    @ApiModelProperty(value = "JSON 응답값")
    private T data;

    public Response() {
        this.status = HttpStatus.OK.value();
        this.message = HttpStatus.OK.toString();
        this.data = null;
    }

    public Response(T data) {
        this.data = data;
    }

    public Response(HttpStatus status, String message) {
        this.status = status.value();
        this.message = message;
        this.data = null;
    }

    public Response(HttpStatus status, String message, T data) {
        this.status = status.value();
        this.message = message;
        this.data = data;
    }
}
