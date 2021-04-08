package ssafy.a306.vnc.entity;

import javax.persistence.*;

import lombok.*;

@NoArgsConstructor
@Getter
@Entity(name="Vote")
public class VoteVo {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int voteIdx;
	private int userIdx;
	private String contractAddress;
	private String hashKey;
	
	@Builder
	public VoteVo(int userIdx, String contractAddress, String hashKey) {
		super();
		this.userIdx = userIdx;
		this.contractAddress = contractAddress;
		this.hashKey = hashKey;
	}


	@Override
	public String toString() {
		return "VoteVo [voteIdx=" + voteIdx + ", userIdx=" + userIdx + ", contractAddress=" + contractAddress
				+ ", hashKey=" + hashKey + "]";
	}


	

}
