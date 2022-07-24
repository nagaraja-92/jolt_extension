package com.jolt.extension.joltTransform.utils;

import java.util.HashMap;
import java.util.Map;

public class AllOperations {
    static Map<String, IOperations> commands = new HashMap<>();

    static {
        commands.put("=date_stringMin", DateOperations.stringDateMin());
        commands.put("=date_stringMax", DateOperations.stringDateMax());
        commands.put("=date_transform", DateOperations.transform());
        commands.put("=array_compact", ArrayOperations.compact());
    }

    public static IOperations getOperation(String operation){
        String[] list = operation.split("\\(");
        return commands.get(list[0]);
    }
}
