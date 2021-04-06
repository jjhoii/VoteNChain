package ssafy.a306.vnc.model;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.a306.vnc.entity.UserEntity;

@Getter
@Data
@NoArgsConstructor
public class UserDto {
	private Long userIdx;
	private String userEmail;
	private String userName;
	private String account;

	@Builder
	public UserDto(Long userIdx,String userEmail, String userName, String account) {
		this.userIdx=userIdx;
		this.userEmail = userEmail;
		this.userName = userName;
		this.account = account;
	}

	public UserEntity toEntity(){
		return UserEntity.builder()
				.userName(userName)
				.userEmail(userEmail)
				.account(account)
				.build();
	}
}
