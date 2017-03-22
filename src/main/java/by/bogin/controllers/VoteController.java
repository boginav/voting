
package by.bogin.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import by.bogin.requests.BaseRequest;
import by.bogin.requests.CreateThemeRequest;
import by.bogin.requests.VoiceRegistrationRequest;
import by.bogin.response.BaseResponse;
import by.bogin.response.CreateThemeResponse;
import by.bogin.response.StartVoteResponse;
import by.bogin.response.StatisticsResponse;
import by.bogin.response.VotingDataResponse;
import by.bogin.services.VoteService;

@Controller
public class VoteController {

	@Autowired
	@Qualifier("voteService")
	private VoteService voteService;
	

	@RequestMapping(value = "${url.createTheme}", method = RequestMethod.POST)
	public @ResponseBody CreateThemeResponse createTheme(@RequestBody CreateThemeRequest request) {
		return voteService.createTheme(request);
	}

	@RequestMapping(value = "${url.srartVote}", method = RequestMethod.POST)
	public @ResponseBody StartVoteResponse srartVote(@RequestBody BaseRequest request) {
		return voteService.srartVote(request);
	}

	@RequestMapping(value = "${url.closeVote}", method = RequestMethod.POST)
	public @ResponseBody BaseResponse closeVote(@RequestBody BaseRequest request) {
		return voteService.closeVote(request);
	}

	@RequestMapping(value = "${url.getStatistic}", method = RequestMethod.POST)
	public @ResponseBody StatisticsResponse getStatistic(@RequestBody BaseRequest request) {

		return voteService.getStatistic(request);
	}

	@RequestMapping(value = "${url.getInfoVote}", method = RequestMethod.POST)
	public @ResponseBody VotingDataResponse getInfoVote(@RequestBody BaseRequest request) {
		return voteService.getInfoVote(request);
	}
	

	@RequestMapping(value = "{url.getInfoVote}/{ref}", method = RequestMethod.POST)
	public @ResponseBody VotingDataResponse getInfoVoteByRef(@PathVariable(value="ref") Long ref) {
		return voteService.getInfoVoteByRef(ref);
	}


	@RequestMapping(value = "{url.regVote}", method = RequestMethod.POST)
	public @ResponseBody BaseResponse regVote(@RequestBody VoiceRegistrationRequest request) {
		return voteService.regVote(request);
	}
}
