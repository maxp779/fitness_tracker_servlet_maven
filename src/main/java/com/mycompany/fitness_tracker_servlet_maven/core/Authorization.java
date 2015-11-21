/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 *
 * @author max
 */
public class Authorization
{
    protected static boolean isCurrentUserAuthorized(String password, HttpServletRequest request)
    {
        boolean output = false;
        HttpSession session = request.getSession();
        String id_user = (String) session.getAttribute("id_user");
        Map<String, String> userCredentials = DatabaseAccess.getUserCredentialsFromid_user(id_user);
        String storedHashedPassword = userCredentials.get("hashedPassword");
        if(Security.passwordMatch(password, storedHashedPassword))
        {
            output = true;
        }
        
        return output;
    }
}
