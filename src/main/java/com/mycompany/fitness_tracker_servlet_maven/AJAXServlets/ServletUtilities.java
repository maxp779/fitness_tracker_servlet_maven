/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.AJAXServlets;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.LinkedHashMap;
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
    protected static String getRequestData(HttpServletRequest request) throws IOException
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
    
    protected static Map<String,String> convertJsonStringToMap(String aJSONString)
    {
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<LinkedHashMap<String, String>>()
        {
        }.getType();
        Map<String, String> outputMap = gson.fromJson(aJSONString, stringStringMap);    
        return outputMap;
    }
}
