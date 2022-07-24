package com.jolt.extension.joltTransform.utils;

import com.bazaarvoice.jolt.ContextualTransform;
import com.bazaarvoice.jolt.SpecDriven;

import java.util.*;

public class BasicTransformation implements SpecDriven, ContextualTransform {
    Object spec;

    public BasicTransformation(Object spec){
        this.spec = spec;
    }

    @Override
    public Object transform(Object data, Map<String, Object> map) {
        recurseAndModify(data, (Map<String, Object>)spec);
        return data;
    }

    public void recurseAndModify(Object data, Map<String, Object> spec) {
        if(data == null){
            return;
        }
        for(Map.Entry<String, Object> child: spec.entrySet()) {
            if (child.getKey().equals("*")) {
                if (data instanceof ArrayList) {
                    arrayRecurse((ArrayList) data, (LinkedHashMap<String, Object>) child.getValue());
                } else {
                    //TODO: Implement hash *
                }
            } else if (child.getValue() instanceof LinkedHashMap) {
                recurseAndModify(((LinkedHashMap) data).get(child.getKey()), (LinkedHashMap<String, Object>) child.getValue());
            } else {
                LinkedHashMap<String, Object> currData = (LinkedHashMap) data;
                if (currData.containsKey(child.getKey())) {
                    currData.put(child.getKey(), checkAndApplyOperation((String) child.getValue(), currData.get(child.getKey())));
                }
            }
        }
    }

    public void arrayRecurse(ArrayList<Object> data, Map<String, Object> spec){
        Set<Integer> indices = new HashSet<>();
        for(int i=0; i<data.size(); i++){
            recurseAndModify(data.get(i), (LinkedHashMap<String, Object>)spec);
        }
    }

    public Object checkAndApplyOperation(String operation, Object data){
        IOperations function = AllOperations.getOperation(operation);
        if(function == null){
            return null;
        }
        return function.run(operation, data);
    }
}
