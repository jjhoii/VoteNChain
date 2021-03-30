package ssafy.a306.vnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import ssafy.a306.vnc.entity.UserVo;

@Repository
public interface UserRepository extends JpaRepository<UserVo,Long> {
	boolean existsByuserEmail(String userEmail);
}
