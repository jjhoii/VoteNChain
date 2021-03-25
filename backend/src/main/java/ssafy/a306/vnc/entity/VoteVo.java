package ssafy.a306.vnc.entity;

import javax.persistence.*;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
//@Entity(name="Vote")
public class VoteVo {
	
	@Id 
	@GeneratedValue(strategy = GenerationType.IDENTITY) 
	private Long voteIdx;
	
	private int userIdx;
	private String contractAddress;
	private String title;
	private int category;
	private int isPublic;
	public VoteVo() {
		// TODO Auto-generated constructor stub
		System.out.println("hihi");
	}
	@Builder
	public VoteVo(int userIdx, String contractAddress, String title, int category, int isPublic) {
		super();
		this.userIdx = userIdx;
		this.contractAddress = contractAddress;
		this.title = title;
		this.category = category;
		this.isPublic = isPublic;
		System.out.println("hihi22");
	}

	@Override
	public String toString() {
		return "VoteVo [voteIdx=" + voteIdx + ", userIdx=" + userIdx + ", contractAddress=" + contractAddress
				+ ", title=" + title + ", category=" + category + ", isPublic=" + isPublic + "]";
	}
	

}
