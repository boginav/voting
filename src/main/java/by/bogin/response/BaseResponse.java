package by.bogin.response;

public class BaseResponse {
	private StatusResponse statusResponse;
	private String errorMessage;


	public String getErrorMrssage() {
		return errorMessage;
	}

	public void setErrorMrssage(String errorMrssage) {
		this.errorMessage = errorMrssage;
	}

	public StatusResponse getStatus() {
		return statusResponse;
	}

	public void setStatus(StatusResponse status) {
		this.statusResponse = status;
	}

	

}
