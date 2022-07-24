package com.jolt.extension.joltTransform.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public class ArrayOperations {
    public static IOperations compact(){
        return (operation, data) -> {
            if(data instanceof ArrayList){
                ((ArrayList) data).removeIf(obj -> obj == null || (obj instanceof LinkedHashMap && (((LinkedHashMap<String, Object>) obj).size()) == 0));
                return data;
            }
            return null;
        };
    }
}
