import com.fasterxml.jackson.annotation.JsonProperty;

public class Env {
    @JsonProperty("api.users.base")
    private String apiUsersBase;

    @JsonProperty("api.users.getById")
    private String apiUsersGetById;

    public String getApiUsersBase() {
        return apiUsersBase;
    }

    public void setApiUsersBase(String apiUsersBase) {
        this.apiUsersBase = apiUsersBase;
    }

    public String getApiUsersGetById() {
        return apiUsersGetById;
    }

    public void setApiUsersGetById(String apiUsersGetById) {
        this.apiUsersGetById = apiUsersGetById;
    }
}

