package com.jolt.extension.joltTransform.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class DateOperations {
    public static IOperations transform(){
        return (operation, data) -> dateTransformation((String)data, operation.substring(17, operation.length()-2), "yyyy-MM-dd'T'HH:mm:ss.SSS");
    }

    public static IOperations stringDateMin(){
        return (operation, data) -> {
            if(!(data instanceof ArrayList) || ((ArrayList<?>) data).size() == 0){
                return null;
            }
            ArrayList<String> dataList = (ArrayList<String>)data;
            String min = dataList.get(0);
            for(int i=1; i<dataList.size(); i++){
                if(dataList.get(i).compareTo(min) < 0){
                    min = dataList.get(i);
                }
            }
            return min;
        };
    }

    public static IOperations stringDateMax(){
        return (operation, data) -> {
            if(!(data instanceof ArrayList) || ((ArrayList<?>) data).size() == 0){
                return null;
            }
            ArrayList<String> dataList = (ArrayList<String>)data;
            String max = dataList.get(0);
            for(int i=1; i<dataList.size(); i++){
                if(dataList.get(i).compareTo(max) > 0){
                    max = dataList.get(i);
                }
            }
            return max;
        };
    }

    private static String dateTransformation(String value, String sourceFormat, String destinationFormat) {
        SimpleDateFormat format = new SimpleDateFormat(sourceFormat);
        SimpleDateFormat targetFormat = new SimpleDateFormat(destinationFormat);
        try {
            Date date = format.parse(value);
            return targetFormat.format(date);
        } catch (ParseException e){
            //FkLogger.error(BasicTransformation.class, "Error parsing Date");
            System.out.println("Error parsing data");
        }
        return null;
    }
}
