package ssafy.a306.vnc.model.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ssafy.a306.vnc.entity.UserVo;
import ssafy.a306.vnc.repository.UserRepository;
import ssafy.a306.vnc.model.UserDto;


@Service
public class UserService {
    //private JdbcTemplate jdbcTemplate;
    //private SimpleJdbcInsert simpleJdbcInsert;

    @Autowired
    private UserRepository userRepository;

    //insert
    @Transactional
    public String add(UserDto userDto) {
    	
        return userRepository.save(userDto.toEntity()).getUserName();
    }
    
    public int selectUserEmail(UserDto userDto) {
    	int result = userRepository.selectUser(userDto.getUserEmail());
    	
    	return result;
    }
}