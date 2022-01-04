import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;

public class Converter {
    private final static String basePath = "src/main/resources/";

    public static void writeToNewJson () {
        Env env = new Env();
        LocalEnv localEnv = new LocalEnv();
        ObjectMapper mapper = new ObjectMapper();

        try {
            localEnv = mapper.readValue(new File(basePath + "local-env.json"), LocalEnv.class);
            env = mapper.readValue(new File(basePath + "env.json"), Env.class);

            localEnv.setApplicationApiUrl(localEnv.getApplicationApiUrl().replaceAll("application.url" ,
                    localEnv.getApplicationUrl()).replace("${", "").replace("}", ""));

            env.setApiUsersBase(env.getApiUsersBase().replaceAll("application.api.url",
                    localEnv.getApplicationApiUrl()).replace("${", "").replace("}", ""));

            env.setApiUsersGetById(env.getApiUsersGetById().replaceAll("api.users.base",
                    env.getApiUsersBase()).replaceFirst("\\$\\{", "").replaceFirst("}", ""));

            mapper.writeValue(new File(basePath + "result-local-env.json" ), localEnv);
            mapper.writeValue(new File(basePath + "result-env.json" ), env);
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

    }
}
