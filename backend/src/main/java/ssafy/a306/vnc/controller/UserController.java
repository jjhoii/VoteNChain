package ssafy.a306.vnc.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ssafy.a306.vnc.model.UserDto;
import ssafy.a306.vnc.model.service.JwtService;
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

	@Autowired
	private JwtService jwtService;

	@PostMapping(value = "login")
	public String login(@RequestBody UserDto user) {
		System.out.println("되냐");

		return userService.add(user);
	}

	// 소셜 로그인 시 회원가입 or 로그인
	@PostMapping("/checkUser")
	public ResponseEntity<Map<String, Object>> checkUser(@RequestBody UserDto userDto) throws IOException {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;
		String email = userDto.getUserEmail();
		System.out.println("checkUser / email : " + userDto.getUserEmail());
		System.out.println("checkUser / key : " + userDto.getPrivateKey());
		try {
			// 이메일 중복 검사
			int userExists = userService.selectUserEmail(userDto);

			if (userExists > 0) { // 이메일 있음 : 이미 가입된 경우.
				String token = jwtService.create("userEmail", email, "access-token");
				resultMap.put("access-token", token);
				resultMap.put("message", "success");
				status = HttpStatus.ACCEPTED;
			} else if (userExists < 1) { // 이메일 없음 : 신규 가입.
				resultMap.put("message", "needSignup");
				status = HttpStatus.ACCEPTED;
			}
		} catch (Exception e) {
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}

	// 회원가입
	@PostMapping("/signup")
	public ResponseEntity<Map<String, Object>> signup(@RequestBody UserDto userDto) {
		Map<String, Object> resultMap = new HashMap<>();
		HttpStatus status = null;

		System.out.println("signup / email : " + userDto.getUserEmail());
		System.out.println("signup / key : " + userDto.getPrivateKey());
		try {
			userService.add(userDto);
			resultMap.put("message", "success");
			status = HttpStatus.ACCEPTED;

		} catch (Exception e) {
			logger.error("회원가입 실패 : {}", e);
			resultMap.put("message", e.getMessage());
			status = HttpStatus.INTERNAL_SERVER_ERROR;
		}
		return new ResponseEntity<Map<String, Object>>(resultMap, status);
	}
}
