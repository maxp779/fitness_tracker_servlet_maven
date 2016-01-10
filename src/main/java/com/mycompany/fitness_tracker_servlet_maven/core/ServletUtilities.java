/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author max
 */
public class ServletUtilities
{

    /**
     * This gets the request data which should be a string in JSON format then
     * returns it.
     *
     * @param request
     * @return a JSON string with the request data
     * @throws IOException
     */
    public static String getRequestData(HttpServletRequest request) throws IOException
    {
        BufferedReader reader = request.getReader();
        StringBuilder buffer = new StringBuilder();
        String currentLine;

        //reader.readLine() is within the while head to avoid "null" being
        //appended on at the end, this happens if it is in the body
        while ((currentLine = reader.readLine()) != null)
        {
            buffer.append(currentLine);
        }

        String jsonString = buffer.toString();
        return jsonString;
    }

    public static Map<String, String> convertJsonStringToMap(String aJSONString)
    {
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<LinkedHashMap<String, String>>()
        {
        }.getType();
        Map<String, String> outputMap = gson.fromJson(aJSONString, stringStringMap);
        return outputMap;
    }
    
    public static String convertMapToJSONString(Map<String,String> aMap)
    {
        Gson gson = new Gson();
        String JSONString = gson.toJson(aMap);
        return JSONString;
    }

    /**
     * This method deals with values from an HTML form when jQuerys serializeArray() method is used.
     * The JSON String sent to the server will be in the form:
     * [{name=email, value=test@test.com}, {name=password, value=testtest}]
     * This method will extract the values and return a single map like so:
     * ["email":"test@test.com", "password":"testtest"]
     * @param aJSONArray a string with JSON formatting
     * @return Map<String,String> containing the relevant values from an HTML form
     */
    public static Map<String,String> convertJSONFormDataToMap(String aJSONArray)
    {
        Gson gson = new Gson();
        Type arrayListMap = new TypeToken<ArrayList<Map>>()
        {
        }.getType();
        
        //list will be in the form
        //list=[{name=email, value=test@test.com}, {name=password, value=testtest}]
        List<Map> loginList = gson.fromJson(aJSONArray, arrayListMap);
        Map<String, String> output = new HashMap<>();
                
        for (Map<String,String> currentMap : loginList)
        {
            String currentKey = currentMap.get("name");
            String currentValue = currentMap.get("value");
            if(currentValue.equals(""))
            {
                currentValue = null;
            }
            output.put(currentKey, currentValue);
        }
        return output;
    }
}
