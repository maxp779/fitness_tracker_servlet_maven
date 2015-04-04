/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * This servlet deals with validation of sessions, it also invalidates sessions
 * when requested by LogoutServlet. In addition to HttpSession management this servlet
 * also deals with jSessionID based sessions, this form is session management is only
 * a fallback for users who have cookies disabled, normal HttpSessions are otherwise
 * preferred.
 * @author max
 */
public class SessionManager
{
    private static final Map<String, Jsessionid> sessionMap = new HashMap<>();
    private static long timeoutMinutes = GlobalValues.getJessionTimeout(); //only applicable to jSessionID based sessions, 1440 minutes is 24 hours
    
    public static synchronized void jsessionidAddSession(String jsessionidString, Jsessionid jsessionidObject)
    {
        SessionManager.jsessionidCleanMap();
        sessionMap.put(jsessionidString, jsessionidObject);
    }
    
    /**
     * This method returns true if the jSessionID is valid, false otherwise
     * @param jsessionid
     * @return boolean
     */
    private static boolean jsessionidValidateSession(String jsessionid)
    {
        boolean isValid = false;
        Jsessionid jsessionidObject = sessionMap.get(jsessionid);
        if(jsessionidObject != null)
        {
            if(jsessionidObject.getCreationTime().isBefore(LocalDateTime.now().plusHours(timeoutMinutes)))
            {
                isValid = true;
            }
            else
            {
                SessionManager.jsessionidRemoveSession(jsessionid);
            }
        }
        return isValid;
    }
    
    public static synchronized void jsessionidRemoveSession(String jsessionid)
    {
        sessionMap.remove(jsessionid);
    }
    
    private static synchronized void jsessionidCleanMap()
    {
//        for (Map.Entry<String, JSessionID> entry : sessionMap.entrySet())
//        {
//            LocalDateTime aCreationTime = entry.getValue().getCreationTime();
//            LocalDateTime timeout = aCreationTime.plusHours(timeoutHours);
//            if(timeout.isBefore(LocalDateTime.now()))
//            {
//                sessionMap.remove(entry);
//            }
//        }

        Iterator sessionMapIterator = sessionMap.keySet().iterator();
        while (sessionMapIterator.hasNext())
        {
            String jsessionidString = (String) sessionMapIterator.next();
            Jsessionid currentValue = sessionMap.get(jsessionidString);
            
            LocalDateTime aCreationTime = currentValue.getCreationTime();
            LocalDateTime timeout = aCreationTime.plusMinutes(timeoutMinutes);
            if(timeout.isBefore(LocalDateTime.now()))
            {
                sessionMap.remove(jsessionidString);
            }
        }
    }

    public static long jsessionidGetTimeoutHours()
    {
        return timeoutMinutes;
    }
    
    public static void jsessionidSetTimeoutHours(long timeoutHours)
    {
        SessionManager.timeoutMinutes = timeoutHours;
    }
    
    /**
     * This method returns true if the HttpSession attached to the request is still
     * valid, it will pass jSessionID based sessions to the correct method should
     * they be passed here.
     * @param request
     * @return true if session is valid, false otherwise
     */
    public static boolean sessionValidate(HttpServletRequest request)
    {       
        System.out.println("SessionManager: executing");
        System.out.println("SessionManager: current request URL = " + request.getRequestURL());
        HttpSession session = request.getSession(false);
        String jsessionid = (String) request.getAttribute("jsessionid");
        boolean sessionValid;
        
        if(jsessionid == null)
        {
        System.out.println("SessionManager: cookie based session detected");   
            if(session == null) //path for invalid session
            {
                System.out.println("SessionManager: session = null");
                sessionValid = false;
            }
            else if(session.getAttribute(ClientAPI.getClientRequestIdentifier()) == null) //path for invalid identifier
            {
                System.out.println("SessionManager: client identifier = null");
                //user may not have logged in properly
                sessionValid = false;
            }
            else //path for valid session
            {
                System.out.println("SessionManager: valid session found!");
                sessionValid = true;
            }
        }
        else
        {
            System.out.println("SessionManager: jsessionid detected: " + jsessionid);
            sessionValid = SessionManager.jsessionidValidateSession(jsessionid);
        }
        System.out.println("SessionManager: current session validation = " + sessionValid);
        return sessionValid;
    }
    
    public static void httpSessionRemove(HttpSession session)
    {
        session.invalidate();
    }
    
}
