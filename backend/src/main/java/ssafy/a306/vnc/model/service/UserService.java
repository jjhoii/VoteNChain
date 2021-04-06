package ssafy.a306.vnc.model.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ssafy.a306.vnc.entity.UserEntity;
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
    
    public UserEntity selectUserAccount(UserDto userDto) {
    	UserEntity userInfo= userRepository.findByuserEmail(userDto.getUserEmail());
    	return userInfo;
    }
}