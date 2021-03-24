package ssafy.a306.vnc.model.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.a306.vnc.model.UserDto;

@Service
public interface UserService {
    UserDto get(String email);

    @Transactional
    UserDto add(UserDto user);


}
