package ssafy.a306.vnc.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.a306.vnc.entity.User;
import ssafy.a306.vnc.repository.UserRepository;
import ssafy.a306.vnc.model.UserDto;


@Service
public class UserService {
    private JdbcTemplate jdbcTemplate;
    private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private UserRepository userRepository;

//    @Override
//    public UserDto get(String email) {
////        return this.userRepository.get(email);
//        return null;
//    }

    @Transactional
    public String add(UserDto user) {

//        long id = this.userRepository.create(user);
//        return this.userRepository.get(id);
//        userRepository.save(User.builder()
//                .userName("Test")
//                .userEmail("Test@test.com")
//                .loginType("google")
//                .privateKey("123")
//                .build());

        return userRepository.save(user.toEntity()).getUserName();
    }
}