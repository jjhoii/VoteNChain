package ssafy.a306.vnc.model.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ssafy.a306.vnc.entity.VoteVo;
import ssafy.a306.vnc.model.VoteDto;
import ssafy.a306.vnc.repository.VoteRepository;

@Service
public class VoteService {

	@Autowired
	private VoteRepository voteRepository;
	
	public VoteVo save(VoteVo vote) {
		voteRepository.save(vote);
		return vote;
	}
	
	public VoteVo read(String hashKey) {
		return voteRepository.findByHashKey(hashKey).get(0);
	}
	
}
