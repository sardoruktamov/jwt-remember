package uz.online.speingsecuritynew.security;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.security.core.AuthenticationException;


public class UserNotActivateException extends AuthenticationException {

    public UserNotActivateException(String msg) {
        super(msg);
    }

    public UserNotActivateException(String message, Throwable throwable) {
        super(message,throwable);
    }
}
