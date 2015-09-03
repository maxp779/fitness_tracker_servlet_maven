/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import javax.servlet.http.HttpServletRequest;
//COULD POTENTIALLY REMOVE THIS CLASS.. PRETTY SURE request.getQueryString() does the same thing...
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
        String output = "";
        String currentRequestURL = request.getRequestURI();
        StringBuilder currentRequestStringBuilder = new StringBuilder(currentRequestURL);
        
        //get the command
        int slashIndex = currentRequestStringBuilder.lastIndexOf("/");
        currentRequestStringBuilder = currentRequestStringBuilder.delete(0, slashIndex+1);

        //if jsessionid is part of the URI remove it
        //strangely enough this seems to be needed with tomee and not glassfish
        int jsessionidIndex = currentRequestStringBuilder.indexOf(";jsessionid=");            
        if(jsessionidIndex != -1)
        {
            currentRequestStringBuilder = currentRequestStringBuilder.delete(jsessionidIndex, currentRequestStringBuilder.length());

        }

        return currentRequestStringBuilder.toString();
    }
    
}
