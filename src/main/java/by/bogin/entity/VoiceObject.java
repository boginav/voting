package by.bogin.entity;

public class VoiceObject {

	private Long idTheme;
	private Long idOption;
	private Long id;
	
	public VoiceObject(Long idTheme, Long idOption, Long id) {
		this.id = id;
		this.idTheme = idTheme;
		this.idOption = idOption;
	}
	
	public VoiceObject(Long idTheme, Long idOption) {
		this.idTheme = idTheme;
		this.idOption = idOption;
	}
	
	public Long getIdTheme() {
		return idTheme;
	}

	public void setIdTheme(Long idTheme) {
		this.idTheme = idTheme;
	}

	public Long getIdOption() {
		return idOption;
	}

	public void setIdOption(Long idOption) {
		this.idOption = idOption;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

}
