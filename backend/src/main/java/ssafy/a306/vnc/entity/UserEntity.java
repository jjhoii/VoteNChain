package ssafy.a306.vnc.entity;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class UserEntity {
	
	@Id
	private Long userIdx;
	private String userEmail;
	private String userName;
	//private String loginType;
	//private String privateKey;
	private String account;
	
	public Long getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(Long userIdx) {
		this.userIdx = userIdx;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}


	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
	}

	
}
