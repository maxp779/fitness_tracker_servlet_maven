/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.unused;

/**
 *
 * @author max
 */
public class UserCredentials
{
    private final String email;
    private final String hashedPassword;
    private final String id_user;
    
    public UserCredentials(String inputEmail, String inputHashedPassword, String inputid_user)
    {
        email = inputEmail;
        hashedPassword = inputHashedPassword;
        id_user = inputid_user;
    }

    public String getEmail()
    {
        return email;
    }

    public String getHashedPassword()
    {
        return hashedPassword;
    }

    public String getId_user()
    {
        return id_user;
    }
    
}
