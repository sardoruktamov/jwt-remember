package uz.online.speingsecuritynew.security;

import io.jsonwebtoken.JwtParser;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Base64;
import java.util.Date;
import java.util.stream.Collectors;

@Component
public class TokenProvider {

    private long tokenValidateMillisecondRemember;
    private long tokenValidateMilliseconds;

    private final Key key;
    private final JwtParser jwtParser;

    public TokenProvider(){
        byte[] keyByte;
        String secret = "c2Fsb21zYWxvbXNhbG9t";
        keyByte = Decoders.BASE64.decode(secret);
        key = Keys.hmacShaKeyFor(keyByte);
        jwtParser = Jwts.parserBuilder().setSigningKey(key).build();
        this.tokenValidateMillisecondRemember = 1000 * 86400;
        this.tokenValidateMilliseconds = 1000 * 3600;
    }
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
        return Jwts
                .builder()
                .setSubject(authentication.getName())
                .claim("auth",authorities)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(validate)
                .compact();
    }
}
