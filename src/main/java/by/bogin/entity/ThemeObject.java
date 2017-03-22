package by.bogin.entity;



public class ThemeObject {
	
	private Long id;
    private String themeName;
    private Boolean active;

    public ThemeObject(Long id, String themeName, Boolean active) {
        this.id = id;
        this.themeName = themeName;
        this.active = active;
    }

    public ThemeObject(String themeName,Boolean active  ) {
        this.themeName = themeName; 
        this.active = active;
    }

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getThemeName() {
		return themeName;
	}

	public void setThemeName(String themeName) {
		this.themeName = themeName;
	}

	public Boolean getActive() {
		return active;
	}

	public void setActive(Boolean active) {
		this.active = active;
	}

}
