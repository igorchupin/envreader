import com.fasterxml.jackson.annotation.JsonProperty;

public class LocalEnv {

    @JsonProperty("application.url")
    private String applicationUrl;

    @JsonProperty("application.api.url")
    private String applicationApiUrl;


    public String getApplicationUrl() {
        return applicationUrl;
    }

    public void setApplicationUrl(String applicationUrl) {
        this.applicationUrl = applicationUrl;
    }

    public String getApplicationApiUrl() {
        return applicationApiUrl;
    }

    public void setApplicationApiUrl(String applicationApiUrl) {
        this.applicationApiUrl = applicationApiUrl;
    }
}
