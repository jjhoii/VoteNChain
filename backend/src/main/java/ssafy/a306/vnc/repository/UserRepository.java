package ssafy.a306.vnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ssafy.a306.vnc.entity.User;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

}
