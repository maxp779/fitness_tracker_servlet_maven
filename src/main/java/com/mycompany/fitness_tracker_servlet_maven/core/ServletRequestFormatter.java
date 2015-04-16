/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import javax.servlet.http.HttpServletRequest;

/**
 * This class contains helper methods that are called by servlets enough
 * to warrent a central location avoiding code duplication. It deals with getting
 * sections of URLs and returning them to the requested servlet, getting the request 
 * and the jsessionid parts are currently dealt with here.
 */
public class ServletRequestFormatter
{
    /**
     * This method returns the end part of an HttpServletRequest object
     * e.g an HttpServletRequest of /SomeServlet/Stuff/mobile.html would 
     * return "mobile.html"
     * @param request
     * @return A String representing the request made
     */
    public static String getRequest(HttpServletRequest request)
    {
        //get and format the request from the URL given
            String currentRequestURL = request.getRequestURI();
            StringBuilder currentRequestStringBuilder = new StringBuilder();
            char[] currentRequestCharArray = currentRequestURL.toCharArray();
            boolean foundSlash = false;
            int count = currentRequestCharArray.length-1;
            while(!foundSlash)
            {
                char currentChar = currentRequestCharArray[count];
                if(currentChar == '/')
                {
                    foundSlash = true;
                }
                else
                {
                    currentRequestStringBuilder.append(currentChar);
                }
                count--;
            }
            currentRequestStringBuilder.reverse();
            return currentRequestStringBuilder.toString();
    }
    
}
