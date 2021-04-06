package ssafy.a306.vnc.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssafy.a306.vnc.entity.UserVo;
import ssafy.a306.vnc.model.UserDto;
import ssafy.a306.vnc.repository.UserRepository;


@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    //insert
    @Transactional
    public String insertUser(UserDto userDto) {
    	
        return userRepository.save(userDto.toEntity()).getUserName();
    }
    
    public boolean selectUserEmail(UserDto userDto) {
    	boolean result = userRepository.existsByuserEmail(userDto.getUserEmail());
    	
    	return result;
    }
    
    public UserVo selectUserAccount(UserDto userDto) {
    	UserVo userInfo= userRepository.findByuserEmail(userDto.getUserEmail());
    	return userInfo;
    }
}