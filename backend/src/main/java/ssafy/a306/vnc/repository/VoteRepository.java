package ssafy.a306.vnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ssafy.a306.vnc.entity.VoteVo;
import ssafy.a306.vnc.model.VoteDto;

public interface VoteRepository extends JpaRepository<VoteDto, Long> {

}
