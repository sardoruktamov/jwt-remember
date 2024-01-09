package uz.online.speingsecuritynew.security;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private long tokenValidateMillisecondRemember;
    private long tokenValidateMilliseconds;
    public String createToken(Authentication authentication, boolean rememberMe) {
        String authorities = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));
        long now = (new Date()).getTime();
        Date validate;
        if (rememberMe){
            validate = new Date(now + tokenValidateMillisecondRemember);
        }else {
            validate = new Date(now + tokenValidateMilliseconds);
        }
        return null;
    }
}
