package by.bogin.services;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import by.bogin.entity.OptionObject;
import by.bogin.entity.ThemeObject;
import by.bogin.entity.VoiceObject;
import by.bogin.repository.DataRepository;
import by.bogin.requests.BaseRequest;
import by.bogin.requests.CreateThemeRequest;
import by.bogin.requests.VoiceRegistrationRequest;
import by.bogin.response.BaseResponse;
import by.bogin.response.CreateThemeResponse;
import by.bogin.response.StartVoteResponse;
import by.bogin.response.StatisticsResponse;
import by.bogin.response.StatusResponse;
import by.bogin.response.VoteStatus;
import by.bogin.response.VotingDataResponse;

@Service("voteService")
public class VoteServiceImpl implements VoteService {

	@Autowired
	private DataRepository dataRepository;
	@Value("${url.getInfoVote}")
	private String getInfoVoteUrl;

	@Override
	public CreateThemeResponse createTheme(CreateThemeRequest request) {

		Long themeId = dataRepository.persist(new ThemeObject(request.getTheme(), false));
		
		if (request.getTheme() == null) {
			CreateThemeResponse createThemeResponse = new CreateThemeResponse();
			createThemeResponse.setStatus(StatusResponse.ERROR);
			createThemeResponse.setErrorMrssage("Enter theme for voting!");
			return createThemeResponse;
		}
		
		if (request.getOptions() == null) {
			CreateThemeResponse createThemeResponse = new CreateThemeResponse();
			createThemeResponse.setStatus(StatusResponse.ERROR);
			createThemeResponse.setErrorMrssage("Enter points for voting!");
			return createThemeResponse;
		}
		
		for (int i = 0; i < request.getOptions().length; i++) {
			String optionName = request.getOptions()[i];
			dataRepository.persist(new OptionObject(optionName, themeId));
		}

		CreateThemeResponse createThemeResponse = new CreateThemeResponse();
		createThemeResponse.setId(themeId);
		createThemeResponse.setStatus(StatusResponse.SUCCESS);

		return createThemeResponse;
	}

	@Override
	public BaseResponse closeVote(BaseRequest request) {

		ThemeObject themeById = dataRepository.findThemeById(request.getId());

		if (themeById == null) {
			BaseResponse response = new BaseResponse();
			response.setStatus(StatusResponse.ERROR);
			response.setErrorMrssage("Theme id incorrect!");
			return response;
		}
		if (themeById.getActive().equals(false)) {
			BaseResponse response = new BaseResponse();
			response.setStatus(StatusResponse.ERROR);
			response.setErrorMrssage("Voting is already closed!");
			return response;
		}
		if (!themeById.getThemeName().equals(request.getTheme())) {
			BaseResponse response = new BaseResponse();
			response.setStatus(StatusResponse.ERROR);
			response.setErrorMrssage("Theme id and name are not correct!");
			return response;
		}
		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(StatusResponse.SUCCESS);
		themeById.setActive(false);
		dataRepository.persist(themeById);

		return baseResponse;
	}

	@Override
	public StatisticsResponse getStatistic(BaseRequest request) {

		if (request.getId() == null || request.getTheme() == null) {
			StatisticsResponse statMessage = new StatisticsResponse();
			statMessage.setErrorMrssage("Enter theme id and name!");
			statMessage.setStatus(StatusResponse.ERROR);
			return statMessage;
		}

		Long idTheme = request.getId();
		ThemeObject themeObject = dataRepository.findThemeById(idTheme);

		if (themeObject == null) {
			StatisticsResponse statMessage = new StatisticsResponse();
			statMessage.setErrorMrssage("This id was not found!");
			statMessage.setStatus(StatusResponse.ERROR);
			return statMessage;
		}

		if (!themeObject.getThemeName().equals(request.getTheme())) {
			StatisticsResponse statMessage = new StatisticsResponse();
			statMessage.setErrorMrssage("Chek the theme name!");
			statMessage.setStatus(StatusResponse.ERROR);
			return statMessage;
		}

		Set<OptionObject> optionObjects = dataRepository.findOptionById(idTheme);
		Iterator<OptionObject> iterator = optionObjects.iterator();
		Map<Long, Long> voiceMap = dataRepository.findVoiceStatistic(idTheme);
		Map<String, Long> statistic = new HashMap<String, Long>();

		while (iterator.hasNext()) {
			OptionObject optionObject = iterator.next();
			if (voiceMap.containsKey(optionObject.getId())) {
				statistic.put(optionObject.getOptionName(), voiceMap.get(optionObject.getId()));
			} else {
				statistic.put(optionObject.getOptionName(), (long) 0);
			}
		}

		final StatisticsResponse statisticsResponse = new StatisticsResponse();
		statisticsResponse.setId(idTheme);
		statisticsResponse.setNameTheme(request.getTheme());
		statisticsResponse.setStatistic(statistic);
		statisticsResponse.setStatus(StatusResponse.SUCCESS);

		return statisticsResponse;
	}

	@Override
	public VotingDataResponse getInfoVote(BaseRequest request) {
		
		return getInfo(request, true);
	}

	@Override
	public VotingDataResponse getInfoVoteByRef(Long ref) {
		
		BaseRequest request = new BaseRequest();
		request.setId(ref);
		return getInfo(request, false);
	}

	private VotingDataResponse getInfo(BaseRequest request, Boolean checkTheme) {
		Long idTheme = request.getId();
		ThemeObject themeById = dataRepository.findThemeById(idTheme);

		if (themeById == null) {
			VotingDataResponse response = new VotingDataResponse();
			response.setVotestatus(VoteStatus.NONE);
			response.setErrorMrssage("This id was not found!");
			response.setStatus(StatusResponse.ERROR);
			return response;
		}

		if (checkTheme && !themeById.getThemeName().equals(request.getTheme())) {
			VotingDataResponse response = new VotingDataResponse();
			response.setStatus(StatusResponse.ERROR);
			response.setErrorMrssage("Theme id and name are not correct!");
			return response;
		}

		Set<OptionObject> optionObjects = dataRepository.findOptionById(idTheme);
		Iterator<OptionObject> iterator = optionObjects.iterator();
		String[] optionsToSave = new String[optionObjects.size()];

		for (int i = 0; i < optionObjects.size(); i++) {
			OptionObject optionObject = iterator.next();
			optionsToSave[i] = optionObject.getOptionName();
		}

		final VotingDataResponse votingDataResponse = new VotingDataResponse();
		votingDataResponse.setId(idTheme);
		votingDataResponse.setTheme(themeById.getThemeName());
		votingDataResponse.setOption(optionsToSave);

		if (themeById.getActive()) {
			votingDataResponse.setVotestatus(VoteStatus.INPROGRESS);
		} else {
			votingDataResponse.setVotestatus(VoteStatus.CLOSED);
		}

		votingDataResponse.setStatus(StatusResponse.SUCCESS);
		return votingDataResponse;
	}

	@Override
	public BaseResponse regVote(VoiceRegistrationRequest request) {
		
		ThemeObject themeById = dataRepository.findThemeById(request.getId());
		
		if (themeById == null) {
			VotingDataResponse response = new VotingDataResponse();
			response.setVotestatus(VoteStatus.NONE);
			response.setErrorMrssage("Voting whith this  id was not found!");
			response.setStatus(StatusResponse.ERROR);
			return response;
		}
		
		if (!themeById.getActive()) {
			VotingDataResponse response = new VotingDataResponse();
			response.setVotestatus(VoteStatus.CLOSED);
			response.setErrorMrssage("This theme is not active!");
			response.setStatus(StatusResponse.ERROR);
			return response;
		}
				
		Set<OptionObject> optionObjects = dataRepository.findOptionById(request.getId());	
		String[] optionsToSave = request.getOptions();
		
		for (int i = 0; i < optionsToSave.length; i++) {
			String optionName = optionsToSave[i];
			Iterator<OptionObject> iterator = optionObjects.iterator();
			while (iterator.hasNext()) {
				OptionObject optionObject = iterator.next();
				if (optionObject.getOptionName().equals(optionName)) {
					dataRepository.persist(new VoiceObject(optionObject.getIdTheme(), optionObject.getId()));
				}
				
			}
		}

		BaseResponse baseResponse = new BaseResponse();
		baseResponse.setStatus(StatusResponse.SUCCESS);
		return baseResponse;
	}

	@Override
	public StartVoteResponse srartVote(BaseRequest request) {

		ThemeObject themeById = dataRepository.findThemeById(request.getId());

		if (themeById == null) {
			StartVoteResponse response = new StartVoteResponse();
			response.setStatus(StatusResponse.ERROR);
			response.setErrorMrssage("Theme id is not correct!");
			return response;

		}

		if (!themeById.getThemeName().equals(request.getTheme())) {
			StartVoteResponse response = new StartVoteResponse();
			response.setStatus(StatusResponse.ERROR);
			response.setErrorMrssage("Theme id and name are not correct!");
			return response;
		}

		if (themeById.getActive()) {
			StartVoteResponse response = new StartVoteResponse();
			response.setStatus(StatusResponse.ERROR);
			response.setErrorMrssage("Voting has alredy started!");
			response.setUrl(getInfoVoteUrl + "/" + themeById.getId());
			return response;
		}

		StartVoteResponse baseResponse = new StartVoteResponse();
		baseResponse.setStatus(StatusResponse.SUCCESS);
		themeById.setActive(true);
		baseResponse.setUrl(getInfoVoteUrl + "/" + themeById.getId());

		dataRepository.persist(themeById);

		return baseResponse;
	}

}
