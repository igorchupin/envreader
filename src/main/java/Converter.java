import com.fasterxml.jackson.core.util.DefaultPrettyPrinter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Converter {
    private final static String basePath = "src/main/resources/";
    private final static String regExp = "\\$\\{[a-zA-Z0-9_.+-]*}";


    public static void writeToNewJson() throws IOException {
        Pattern pat = Pattern.compile(regExp);
        Map<String, String> mapFull = new LinkedHashMap<>();
        File localEnv = new File(basePath + "local-env.json");
        File env = new File(basePath + "env.json");
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNodeLocalEnv = mapper.readTree(localEnv);
        JsonNode jsonNodeEnv = mapper.readTree(env);
        ObjectWriter writer = mapper.writer(new DefaultPrettyPrinter());

        Iterator<Map.Entry<String, JsonNode>> fieldsLocalEnv = jsonNodeLocalEnv.fields();
        Iterator<Map.Entry<String, JsonNode>> fieldsEnv = jsonNodeEnv.fields();

        while(fieldsLocalEnv.hasNext()) {
            Map.Entry<String, JsonNode> fieldLocalEnv = fieldsLocalEnv.next();
            mapFull.put(fieldLocalEnv.getKey(), fieldLocalEnv.getValue().asText());
        }
        while(fieldsEnv.hasNext()) {
            Map.Entry<String, JsonNode> field = fieldsEnv.next();
            mapFull.put(field.getKey(), field.getValue().asText());
        }

        for (String result : mapFull.keySet()) {
            String tempValue = mapFull.get(result);
            Matcher matcher = pat.matcher(tempValue);

            if (matcher.find()) {
                String temp = matcher.group(0);
                String keyForSearch = temp.replaceAll("\\$\\{", "").replaceFirst("}", "");

                if (mapFull.containsKey(keyForSearch) && !mapFull.get(keyForSearch).contains("${"))
                {
                    tempValue = tempValue.replaceFirst(regExp, mapFull.get(keyForSearch));
                    mapFull.replace(result, tempValue);
                }
            }

        }
        writer.writeValue(new File(basePath + "result.json"), mapFull);
    }
}
