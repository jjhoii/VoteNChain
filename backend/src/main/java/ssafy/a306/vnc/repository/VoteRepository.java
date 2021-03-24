package ssafy.a306.vnc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import ssafy.a306.vnc.entity.VoteVo;

public interface VoteRepository extends JpaRepository<VoteVo, Long> {

}
