package ssafy.a306.vnc.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import ssafy.a306.vnc.entity.VoteVo;
import ssafy.a306.vnc.model.VoteDto;
import ssafy.a306.vnc.model.service.VoteService;

@RestController
@RequestMapping("/vote")
public class VoteController {

	@Autowired
	VoteService voteService;
	
	@PostMapping(value ="/create")
	public ResponseEntity<VoteDto> create(@RequestBody VoteDto vote){
		System.out.println(vote.toString());
		return new ResponseEntity<VoteDto>(voteService.save(vote),HttpStatus.OK);
	}
}
