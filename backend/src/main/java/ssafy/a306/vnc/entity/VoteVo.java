package ssafy.a306.vnc.entity;

import javax.persistence.*;

import lombok.*;

//@Data
//@AllArgsConstructor
//@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity(name="Vote")
public class VoteVo {
	//d
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private int voteIdx;
	private int userIdx;
	private String contractAddress;
	private String hashKey;
	
	public VoteVo() {
		// TODO Auto-generated constructor stub
	}

	@Builder
	public VoteVo(int userIdx, String contractAddress) {
		super();
		this.userIdx = userIdx;
		this.contractAddress = contractAddress;
		
	}

	public int getVoteIdx() {
		return voteIdx;
	}

	public void setVoteIdx(int voteIdx) {
		this.voteIdx = voteIdx;
	}

	public int getUserIdx() {
		return userIdx;
	}

	public void setUserIdx(int userIdx) {
		this.userIdx = userIdx;
	}

	public String getContractAddress() {
		return contractAddress;
	}

	public void setContractAddress(String contractAddress) {
		this.contractAddress = contractAddress;
	}

	public String getHashKey() {
		return hashKey;
	}

	public void setHashKey(String hashKey) {
		this.hashKey = hashKey;
	}

	@Override
	public String toString() {
		return "VoteVo [voteIdx=" + voteIdx + ", userIdx=" + userIdx + ", contractAddress=" + contractAddress
				+ ", hashKey=" + hashKey + "]";
	}


	

}
