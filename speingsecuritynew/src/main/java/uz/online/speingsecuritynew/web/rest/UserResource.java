package uz.online.speingsecuritynew.web.rest;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import uz.online.speingsecuritynew.domain.User;
import uz.online.speingsecuritynew.service.UserService;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserResource {

    private final UserService userService;

    @PostMapping("/register")
    public ResponseEntity create(@RequestBody User user){
        User result = userService.save(user);
        return ResponseEntity.ok(result);
    }

}
