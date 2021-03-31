package ssafy.a306.vnc.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import ssafy.a306.vnc.entity.VoteVo;
import ssafy.a306.vnc.model.VoteDto;

public interface VoteRepository extends JpaRepository<VoteVo, Long> {
	
	public List<VoteVo> findByHashKey(String hashKey);

}
