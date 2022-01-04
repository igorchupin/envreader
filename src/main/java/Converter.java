import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    private final static String basePath = "src/main/resources/";

    public static void writeToNewJson() throws IOException {
        Gson gson = new Gson();
        TypeToken typeToken = new TypeToken<Map<String, String>>() {};
        String localEnv = new String(Files.readAllBytes(Paths.get(basePath + "local-env.json")), StandardCharsets.UTF_8);
        String env = new String(Files.readAllBytes(Paths.get(basePath + "env.json")), StandardCharsets.UTF_8);
        Pattern pat = Pattern.compile("\\$\\{[a-zA-Z0-9_.+-]*}");

        Map<String, String> mapFull = gson.fromJson(localEnv, typeToken.getType());
        mapFull.putAll(gson.fromJson(env, typeToken.getType()));

        for (String result : mapFull.keySet()) {
            String tempValue = mapFull.get(result);
            Matcher matcher = pat.matcher(tempValue);

            if (matcher.find()) {
                String temp = matcher.group(0);
                String keyForSearch = temp.replaceAll("\\$\\{", "").replaceFirst("}", "");

                if (mapFull.containsKey(keyForSearch) && !mapFull.get(keyForSearch).contains("${"))
                {
                    tempValue = tempValue.replaceFirst("\\$\\{[a-zA-Z0-9_.+-]*}", mapFull.get(keyForSearch));
                    mapFull.replace(result, tempValue);
                }
                else {
                    tempValue = tempValue.replaceFirst("\\$\\{[a-zA-Z0-9_.+-]*}", "not found");
                }
            }

        }
        try (FileWriter writer = new FileWriter(basePath + "result.json")) {
            writer.write(gson.toJson(mapFull));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
