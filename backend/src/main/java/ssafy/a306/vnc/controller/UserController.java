package ssafy.a306.vnc.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import ssafy.a306.vnc.model.UserDto;
import ssafy.a306.vnc.model.service.UserService;

//@CrossOrigin(origins = { "https://votenchain.tk" })
@CrossOrigin(origins = { "*" })
@RestController
@RequestMapping("/user")
public class UserController {

	public static final Logger logger = LoggerFactory.getLogger(UserController.class);
	private static final String SUCCESS = "success";
	private static final String FAIL = "fail";

	@Autowired
	private UserService userService;

	@PostMapping(value = "login")
	public String login(@RequestBody UserDto user){
		System.out.println("되냐");
//		UserDto userFetched=userService.get(user.getUserEmail());
//		if(userFetched==null){
//			return userService.add(user);
//		}
		return userService.add(user);
	}

//	@RequestMapping(method = RequestMethod.GET,path="/getRequest")
//	public String getRequest(@RequestParam String id,@RequestParam String email) {
//		return "아이디는 " + id + "이메일은" + email;
//	}
	@GetMapping(value="getMultiParameters")
	public UserDto getMultiPrameters(UserDto userDto) {
		System.out.println("테스트");
		return userDto;
	}


	@GetMapping(value = "/login")
	public void getTest(@RequestBody UserDto user){
		System.out.println("겟테스트");
//		UserDto userFetched=userService.get(user.getUserEmail());
//		if(userFetched==null){
//			return userService.add(user);
//		}
//		return userService.add(user);
	}
}
