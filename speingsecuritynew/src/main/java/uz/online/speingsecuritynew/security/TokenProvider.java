package uz.online.speingsecuritynew.security;

import io.jsonwebtoken.*;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import io.jsonwebtoken.security.SignatureException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Arrays;
import java.util.Base64;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Component
@Slf4j
public class TokenProvider {

//    private final

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

    public boolean validateToken(String jwt) {
        try {
            jwtParser.parseClaimsJwt(jwt);
            return true;
        }catch (ExpiredJwtException e){
            log.error("ExpiredJwtException: " + e.getMessage());
        }catch (UnsupportedJwtException e){
            log.error("UnsupportedJwtException: " + e.getMessage());
        }catch (MalformedJwtException e){
            log.error("MalformedJwtException: " + e.getMessage());
        }catch (SignatureException e){
            log.error("SignatureException: " + e.getMessage());
        }catch (IllegalArgumentException e){
            log.error("IllegalArgumentException: " + e.getMessage());
        }
        return false;
    }

    public Authentication getAuthentication(String jwt) {
        Claims claims = jwtParser.parseClaimsJws(jwt).getBody();
        Collection<? extends GrantedAuthority> authorities = Arrays
                .stream(claims.get("auth").toString().split(","))
                .filter(auth -> !auth.trim().isEmpty())
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        User principal = new User(claims.getSubject(),"",authorities);
        return new UsernamePasswordAuthenticationToken(principal,jwt,authorities);

    }
}
