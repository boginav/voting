package by.bogin.response;

import java.util.Map;

public class StatisticsResponse extends BaseResponse {
	private Long id;
	private String nameTheme;
	private Map<String, Long> statistic;

	public Map<String, Long> getStatistic() {
		return statistic;
	}

	public void setStatistic(Map<String, Long> statistic) {
		this.statistic = statistic;

	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNameTheme() {
		return nameTheme;
	}

	public void setNameTheme(String nameTheme) {
		this.nameTheme = nameTheme;
	}

}
