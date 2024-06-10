package pl.samba.lms.utils.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

@AllArgsConstructor
@Getter
public class HttpResponse {
    private HttpStatus status;
    private String message;
    private String error;
}
