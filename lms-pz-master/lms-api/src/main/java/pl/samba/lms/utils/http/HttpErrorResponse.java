package pl.samba.lms.utils.http;

import lombok.Getter;
import org.springframework.http.HttpStatus;

@Getter
public class HttpErrorResponse extends HttpResponse{
    private String request;
    public HttpErrorResponse(HttpStatus status, String message, String error, String request) {
        super(status, message, error);
        this.request = request;
    }
}
