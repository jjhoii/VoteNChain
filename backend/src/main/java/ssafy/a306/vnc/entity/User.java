package ssafy.a306.vnc.entity;


import lombok.*;

import javax.persistence.*;

//@Data
@NoArgsConstructor
@Getter
@Entity
public class User {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int userIdx;

//    @Column(name="userEmail")
    private String userEmail;
//    @Column(name="userName")
    private String userName;
//    @Column(name="loginType")
    private String loginType;
//    @Column(name="privateKey")
    private String privateKey;

    @Builder
    public User(int userIdx, String userEmail, String userName, String loginType, String privateKey) {
        this.userIdx = userIdx;
        this.userEmail = userEmail;
        this.userName = userName;
        this.loginType = loginType;
        this.privateKey = privateKey;
    }
}
