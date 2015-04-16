/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import java.time.LocalDateTime;

/**
 * A class to store important attributes about a jsessionid such as 
 * its creation time and the user it is attached to along with the jsessionid
 * itself
 * @author max
 */
public class Jsessionid
{
    private final String jSessionID;
    private final String user;
    private final LocalDateTime creationTime;
    
    public Jsessionid(String aJSessionID, String aUser, LocalDateTime aCreationTime)
    {
        jSessionID = aJSessionID;
        user = aUser;
        creationTime = aCreationTime;
    }

    public String getjSessionID()
    {
        return jSessionID;
    }

    public String getUser()
    {
        return user;
    }

    public LocalDateTime getCreationTime()
    {
        return creationTime;
    }
    
    
}
