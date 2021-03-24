package ssafy.a306.vnc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ssafy.a306.vnc.model.UserDto;
import ssafy.a306.vnc.model.service.UserService;
import ssafy.a306.vnc.model.service.UserServiceImpl;

//@CrossOrigin(origins = { "https://votenchain.tk" })
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";


	private UserService userService;

	@RequestMapping(value = "/login",method = RequestMethod.POST)
	public UserDto login(@RequestBody UserDto User){
		UserDto userFetched=userService.get(User.getEmail());
		if(userFetched==null){
			return userService.add(User);
		}
		return userFetched;
	}

}
