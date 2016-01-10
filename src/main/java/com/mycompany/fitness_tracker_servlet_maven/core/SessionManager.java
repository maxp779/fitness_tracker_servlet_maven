/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

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
        boolean sessionValid;

            if(session == null) //path for invalid session
            {
                System.out.println("SessionManager: session = null");
                sessionValid = false;
            }
            else if(session.getAttribute(ClientAPI.getLOGIN_IDENTIFIER()) == null) //path for invalid identifier
            {
                System.out.println("SessionManager: client identifier = null");
                //user may not have logged in properly
                sessionValid = false;
            }
            else //path for valid session
            {
                System.out.println("SessionManager: valid session found! " + session.getId());
                sessionValid = true;
            }
        System.out.println("SessionManager: current session validation = " + sessionValid);
        return sessionValid;
    }
    
    public static void httpSessionRemove(HttpSession session)
    {
        session.invalidate();
    }
    
}
