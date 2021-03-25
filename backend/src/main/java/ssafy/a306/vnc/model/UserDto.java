package ssafy.a306.vnc.model;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.a306.vnc.entity.UserVo;

@Getter
@Data
@NoArgsConstructor
public class UserDto {
	private int userIdx;
	private String userEmail;
	private String userName;
	private String loginType;
	private String privateKey;

	@Builder
	public UserDto(int userIdx,String userEmail, String userName, String loginType, String privateKey) {
		this.userIdx=userIdx;
		this.userEmail = userEmail;
		this.userName = userName;
		this.loginType = loginType;
		this.privateKey = privateKey;
	}

	public UserVo toEntity(){
		return UserVo.builder()
				.userName(userName)
				.userEmail(userEmail)
				.privateKey(privateKey)
				.loginType(loginType)
				.build();
	}
}
