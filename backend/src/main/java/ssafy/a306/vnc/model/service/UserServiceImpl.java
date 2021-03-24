package ssafy.a306.vnc.model.service;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import ssafy.a306.vnc.model.UserDto;
import ssafy.a306.vnc.repository.UserRepository;


@Service
public class UserServiceImpl implements UserService {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    private UserRepository userRepository;

    @Override
    public UserDto get(String email) {
        return this.userRepository.get(email);
    }

    @Override
    public UserDto add(UserDto user) {
        return ;
    }
}