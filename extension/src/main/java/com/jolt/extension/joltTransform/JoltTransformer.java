package com.jolt.extension.joltTransform;

import com.bazaarvoice.jolt.Chainr;
import com.bazaarvoice.jolt.JsonUtils;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
@Scope("prototype")
public class JoltTransformer {
    public String transformData(String mapName, String payload) {
        try {
            ClassLoader classLoader = JoltTransformer.class.getClassLoader();
            String map = new BufferedReader(
                    new InputStreamReader(Objects.requireNonNull(classLoader.getResourceAsStream("joltMaps/" + mapName)), StandardCharsets.UTF_8)).lines()
                    .collect(Collectors.joining("\n"));
            List chainrSpecJSON = JsonUtils.jsonToList(map);
            Object inputJSON = JsonUtils.jsonToObject(payload);
            Chainr chainr = Chainr.fromSpec(chainrSpecJSON);
            Object transformedOutput = chainr.transform(inputJSON);
            return JsonUtils.toJsonString(transformedOutput);
        } catch(Exception e){
            System.out.println("Error transforming data");
        }
        return "";
    }
}
