package ssafy.a306.vnc.model;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import ssafy.a306.vnc.entity.VoteVo;

@Getter
@Data
@NoArgsConstructor
public class VoteDto {

	private int voteIdx;
	private int userIdx;
	private String contractAddress;
	private String hashKey;
	

	public VoteDto(int voteIdx, int userIdx, String contractAddress, String hashKey) {
		this.voteIdx = voteIdx;
		this.userIdx = userIdx;
		this.contractAddress = contractAddress;
		this.hashKey = hashKey;
	}

	public VoteVo toEntity(){
		return VoteVo.builder()
				.userIdx(userIdx)
				.contractAddress(contractAddress)
				.hashKey(hashKey)
				.build();
	}

	@Override
	public String toString() {
		return "VoteDto [voteIdx=" + voteIdx + ", userIdx=" + userIdx + ", contractAddress=" + contractAddress
				+ ", hashKey=" + hashKey + "]";
	}


	
	
}
