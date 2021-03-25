package ssafy.a306.vnc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import ssafy.a306.vnc.entity.VoteVo;
import ssafy.a306.vnc.model.VoteDto;
import ssafy.a306.vnc.model.service.VoteService;

@RestController
@RequestMapping("/vote")
@CrossOrigin(origins = "*",allowedHeaders="*")
public class VoteController {

	@Autowired
	VoteService voteService;
	
	@PostMapping(value ="/create")
	public ResponseEntity<VoteVo> create(@RequestBody VoteVo vote){
		//System.out.println(vote.toString());
		ResponseEntity<VoteVo> entity = null;
		try {
			entity = new ResponseEntity<VoteVo>(voteService.save(vote),HttpStatus.OK);
		}catch(Exception e) {
			e.printStackTrace();
		}
		return entity;
		//return new ResponseEntity<VoteVo>(voteService.save(vote),HttpStatus.OK);
	}
	
	@GetMapping(value = "/read")
	public ResponseEntity read() {
		
		ResponseEntity entity = null;
		Map result = new HashMap();
		
		List<VoteVo> list = voteService.findAll();
//        System.out.println(list);
        if(list != null) {
            result.put("list", list);
            result.put("success", "success");
            entity = new ResponseEntity(result, HttpStatus.OK);
//            System.out.println(entity);
        } else {
        	result.put("success", "fail");
        	entity = new ResponseEntity(result, HttpStatus.OK);
        }
        
        return entity;
	}
}
