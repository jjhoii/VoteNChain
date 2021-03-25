package ssafy.a306.vnc.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ssafy.a306.vnc.VncApplicationTests;
import ssafy.a306.vnc.entity.User;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

//@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTest extends VncApplicationTests {

    @Autowired
    UserRepository userRepository;

//    @AfterAll
//    public void cleanUp(){
//        userRepository.deleteAll();;
//    }

    @Test
    public void create(){
//        User user=new User();
//        user.setUserName("user1");
//        user.setUserEmail("user@ssafy.com");
//        user.setLoginType("Google");
//        user.setPrivateKey("123");
//
//        User newUser=userRepository.save(user);
//        System.out.println(newUser);


        userRepository.save(User.builder()
                .userName("Test")
                .userEmail("Test@test.com")
                .loginType("google")
                .privateKey("123")
                .build());

        //when
        List<User> userList= userRepository.findAll();

        //then

        User user= userList.get(0);
//        assertThat(user.getUserName().isEqualTo(userName));
    }

}
