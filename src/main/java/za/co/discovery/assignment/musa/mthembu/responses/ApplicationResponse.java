package za.co.discovery.assignment.musa.mthembu.responses;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ApplicationResponse {

    @JsonProperty("code")
    private int code;
    @JsonProperty("message")
    private String message;

    public ApplicationResponse(String message,int code) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
