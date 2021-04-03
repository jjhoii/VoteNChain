package ssafy.a306.vnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssafy.a306.vnc.entity.UserVo;
import ssafy.a306.vnc.model.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserVo,Long> {
	boolean existsByuserEmail(String userEmail);
	UserVo findByuserEmail(String userEmail);
}
