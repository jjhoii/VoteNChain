package ssafy.a306.vnc.repository;

import org.springframework.beans.factory.annotation.Autowired;

import ssafy.a306.vnc.entity.VoteVo;

public class RepositoryTest {

	@Autowired
	private VoteRepository voteRepository;
	
	public void create() {
		
		VoteVo VoteVo = new VoteVo(3,"hihi","하하호호",3,0);
				
		VoteVo newVote = voteRepository.save(VoteVo);
		
		System.out.println(newVote);

	}
}
