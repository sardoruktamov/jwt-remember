package uz.online.speingsecuritynew.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.online.speingsecuritynew.domain.User;
import uz.online.speingsecuritynew.repository.UserRepository;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public User save(User user){
        return userRepository.save(user);
    }
}
