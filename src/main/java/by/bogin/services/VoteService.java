package by.bogin.services;

import by.bogin.requests.BaseRequest;
import by.bogin.requests.CreateThemeRequest;
import by.bogin.requests.VoiceRegistrationRequest;
import by.bogin.response.BaseResponse;
import by.bogin.response.CreateThemeResponse;
import by.bogin.response.StartVoteResponse;
import by.bogin.response.StatisticsResponse;
import by.bogin.response.VotingDataResponse;


public interface VoteService {
	public CreateThemeResponse createTheme( CreateThemeRequest request);
	public StartVoteResponse srartVote(BaseRequest request);
	public BaseResponse closeVote(BaseRequest request);
	public StatisticsResponse getStatistic(BaseRequest request);
	public VotingDataResponse getInfoVote(BaseRequest request);
	public VotingDataResponse getInfoVoteByRef(Long ref);
	public BaseResponse regVote(VoiceRegistrationRequest request);
	
	
	
	


}
