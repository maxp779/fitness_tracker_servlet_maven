/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 *
 * @author max
 */
public class Token
{
    private final String separator = "|";
    private final UUID aUUID = UUID.randomUUID();
    private final String email;
    private final LocalDateTime creationTime = LocalDateTime.now();

    protected Token(String anEmail)
    {
        email = anEmail;
    }

    public UUID getaUUID()
    {
        return aUUID;
    }

    public String getEmail()
    {
        return email;
    }

    public LocalDateTime getCreationTime()
    {
        return creationTime;
    }
    
   /**
    * Returns the separator that separates the UUID, email and time values
    * of the token e.g with the token "d78a5b5e-2cd8-494d-a168-6008f197a84c|noname@nodomain.com|2015-03-04T22:53:55.282"
    * then the String "|" would be returned
    * @return The string separator of the token
    */
    public String getSeparator()
    {
        return separator;
    }
    
    @Override
    public String toString()
    {
        return aUUID +separator+ email +separator+ creationTime;
    }

}
