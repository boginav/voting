package by.bogin.entity;

public class OptionObject {
	private Long id;
	private String optionName;
	private Long idTheme;

	public OptionObject(Long id, String optionName, Long idTheme) {
		this.id = id;
		this.optionName = optionName;
		this.idTheme = idTheme;
	}

	public OptionObject(String optionName, Long idTheme) {
		this.optionName = optionName;
		this.idTheme = idTheme;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getOptionName() {
		return optionName;
	}

	public void setOptionName(String optionName) {
		this.optionName = optionName;
	}

	public Long getIdTheme() {
		return idTheme;
	}

	public void setIdTheme(Long idTheme) {
		this.idTheme = idTheme;
	}

}
