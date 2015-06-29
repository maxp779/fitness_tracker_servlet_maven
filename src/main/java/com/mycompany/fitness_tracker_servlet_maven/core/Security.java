/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.util.Map;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 *
 * @author max
 */
public class Security
{    
    public static String hashPassword(String aPassword)
    {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        return passwordEncoder.encode(aPassword);
    }
    
    public static boolean passwordMatch(String inputPassword, String storedHashedPassword)
    {       
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();      
        return passwordEncoder.matches(inputPassword, storedHashedPassword);
    }
}