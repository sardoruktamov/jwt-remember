package uz.online.speingsecuritynew.security;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import uz.online.speingsecuritynew.domain.Authority;
import uz.online.speingsecuritynew.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Component("userDetailsServoce")
@RequiredArgsConstructor
public class CustomUserDetailService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        String lowerCaseUsername = username.toLowerCase();
        return userRepository
                .findByUsername(lowerCaseUsername)
                .map(user -> createSpringSecurityUser(lowerCaseUsername, user))
                .orElseThrow(() -> new UserNotActivateException("User " + username + " was not found in the database!"));
    }

    private User createSpringSecurityUser(String username, uz.online.speingsecuritynew.domain.User user){
        if (!user.isActivated()){
            throw new UserNotActivateException("User " + username + " was not activated!");
        }
        List<GrantedAuthority> grantedAuthorities = user
                .getAuthorties()
                .stream()
                .map(Authority::getName)
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        return new User(username, user.getPassword(), grantedAuthorities);
    }
}
