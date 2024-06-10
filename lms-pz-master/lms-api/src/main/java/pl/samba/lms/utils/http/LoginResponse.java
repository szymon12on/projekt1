package pl.samba.lms.utils.http;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Setter
@Getter
public class LoginResponse {
    private Integer id;
    private String login;
    private String token;
}
