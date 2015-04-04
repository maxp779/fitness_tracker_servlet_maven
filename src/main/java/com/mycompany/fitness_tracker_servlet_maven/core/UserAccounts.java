/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.util.HashMap;
import java.util.Map;

/**
 *
 * @author max
 */
public class UserAccounts
{
    private static Map<String, UserObject> userMap = new HashMap<>();

    public static Map<String, UserObject> getUserMap()
    {
        return userMap;
    }
    
    
}
