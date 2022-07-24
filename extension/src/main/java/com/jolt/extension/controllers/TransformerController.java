package com.jolt.extension.controllers;

import com.jolt.extension.joltTransform.JoltTransformer;
import org.springframework.beans.factory.annotation.Lookup;
import org.springframework.web.bind.annotation.*;

@RestController
public class TransformerController {

    @Lookup
    public JoltTransformer joltTransformer(){
        return null;
    }

    @PostMapping(path = "/transform")
    public String transformData(@RequestParam(name = "map_name") String mapName, @RequestBody String body){
        return joltTransformer().transformData(mapName, body);
    }
}
