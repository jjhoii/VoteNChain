package ssafy.a306.vnc.model;
import lombok.*;
import ssafy.a306.vnc.entity.User;

@Getter
@Data
@NoArgsConstructor
public class UserDto {
	private int userIdx;
	private String userEmail;
	private String userName;
	private String loginType;
	private String privateKey;

//	@Builder
//	public UserDto(String userEmail, String userName, String loginType, String privateKey) {
//		this.userEmail = userEmail;
//		this.userName = userName;
//		this.loginType = loginType;
//		this.privateKey = privateKey;
//	}

	@Builder
	public UserDto(int userIdx,String userEmail, String userName, String loginType, String privateKey) {
		this.userIdx=userIdx;
		this.userEmail = userEmail;
		this.userName = userName;
		this.loginType = loginType;
		this.privateKey = privateKey;
	}

	public User toEntity(){
		return User.builder()
				.userName(userName)
				.userEmail(userEmail)
				.privateKey(privateKey)
				.loginType(loginType)
				.build();
	}
}
