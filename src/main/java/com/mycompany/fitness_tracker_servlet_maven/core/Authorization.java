/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.mycompany.fitness_tracker_servlet_maven.database.DatabaseAccess;
import java.util.Map;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 *
 * @author max
 */
public class Authorization
{

    private static final Logger log = LoggerFactory.getLogger(Authorization.class);

    protected static boolean isCurrentUserAuthorized(String password, String id_user)
    {
        log.trace("isCurrentUserAuthorized");
        boolean output = false;

        Map<String, String> userCredentials = DatabaseAccess.getUserCredentialsFromid_user(id_user);
        String storedHashedPassword = userCredentials.get("hashedPassword");
        if (PasswordEncoder.passwordMatch(password, storedHashedPassword))
        {
            output = true;
        }
        log.debug("user authorization:"+String.valueOf(output));
        return output;
    }
}
