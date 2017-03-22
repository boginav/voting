package by.bogin.response;

public class VotingDataResponse extends BaseResponse {
	private Long id;
	private String theme;
	private VoteStatus votestatus;
	private String[] option;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public VoteStatus getVotestatus() {
		return votestatus;
	}
	public void setVotestatus(VoteStatus votestatus) {
		this.votestatus = votestatus;
	}
	public String[] getOption() {
		return option;
	}
	public void setOption(String[] option) {
		this.option = option;
	}
	
	
	

}
