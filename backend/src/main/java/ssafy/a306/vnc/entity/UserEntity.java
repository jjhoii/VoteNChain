package ssafy.a306.vnc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;


@NoArgsConstructor
@Getter
@Entity(name="User")
public class UserEntity {
	@Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long userIdx;

    private String userEmail;
    private String userName;
    private String account;
    
    @Builder
    public UserEntity(Long userIdx, String userEmail, String userName, String account) {
        this.userIdx = userIdx;
        this.userEmail = userEmail;
        this.userName = userName;
        this.account = account;
    }
}