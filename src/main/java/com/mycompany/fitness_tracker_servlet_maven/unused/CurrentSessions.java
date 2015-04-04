/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import com.mycompany.fitness_tracker_servlet_maven.core.Jsessionid;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import javax.json.JsonObject;

/**
 *
 * @author max
 */
public class CurrentSessions
{
    private static Map<String, Jsessionid> sessionMap = new HashMap<>();
    private static long timeoutHours = 24;
    
    public static synchronized void addSession(String jSessionID, Jsessionid user)
    {
        sessionMap.put(jSessionID, user);
    }
    
    public static synchronized boolean sessionValid(String jSessionID)
    {
        boolean isValid = false;
        Jsessionid aJSessionID = sessionMap.get(jSessionID);
        if(aJSessionID != null)
        {
            if(aJSessionID.getCreationTime().isBefore(LocalDateTime.now().plusHours(timeoutHours)))
            {
                isValid = true;
            }
            else
            {
                sessionMap.remove(jSessionID);
            }
        }
        return isValid;
    }
    
    public static synchronized void removeSession(String jSessionID)
    {
        sessionMap.remove(jSessionID);
    }


    public static long getTimeoutHours()
    {
        return timeoutHours;
    }

    public static void setTimeoutHours(long timeoutHours)
    {
        CurrentSessions.timeoutHours = timeoutHours;
    }
    
    
}
