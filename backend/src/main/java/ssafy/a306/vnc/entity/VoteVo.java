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
	private String title;
	private int category;
	private int isPublic;
	
	public VoteVo() {
		// TODO Auto-generated constructor stub
	}

	@Builder
	public VoteVo(int voteIdx, int userIdx, String contractAddress, String title, int category, int isPublic) {
		super();
		//this.voteIdx = voteIdx;
		this.userIdx = userIdx;
		this.contractAddress = contractAddress;
		this.title = title;
		this.category = category;
		this.isPublic = isPublic;
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

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public int getCategory() {
		return category;
	}

	public void setCategory(int category) {
		this.category = category;
	}

	public int getIsPublic() {
		return isPublic;
	}

	public void setIsPublic(int isPublic) {
		this.isPublic = isPublic;
	}

	@Override
	public String toString() {
		return "VoteDto [voteIdx=" + voteIdx + ", userIdx=" + userIdx + ", contractAddress=" + contractAddress
				+ ", title=" + title + ", category=" + category + ", isPublic=" + isPublic + "]";
	}
	

}
