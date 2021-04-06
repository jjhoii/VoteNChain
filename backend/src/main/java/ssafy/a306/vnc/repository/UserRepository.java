package ssafy.a306.vnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import ssafy.a306.vnc.entity.UserEntity;
import ssafy.a306.vnc.model.UserDto;

@Repository
public interface UserRepository extends JpaRepository<UserEntity,Long> {
	boolean existsByuserEmail(String userEmail);
	UserEntity findByuserEmail(String userEmail);
}
