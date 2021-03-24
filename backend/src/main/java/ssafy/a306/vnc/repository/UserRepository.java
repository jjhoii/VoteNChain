package ssafy.a306.vnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ssafy.a306.vnc.model.UserDto;

public interface UserRepository extends JpaRepository<UserDto,Long> {

}
