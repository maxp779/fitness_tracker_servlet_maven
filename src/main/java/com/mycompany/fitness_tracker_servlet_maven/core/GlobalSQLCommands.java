/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

/**
 *
 * @author max
 */
public class GlobalSQLCommands
{
    private static final String checkForUserSQL = "SELECT email FROM usertable WHERE email = ?";;
    private static final String getUserCredentialsSQL = "SELECT email,password FROM usertable WHERE email = ?";
    private static final String addUserSQL = "INSERT INTO usertable (email,password) VALUES (?,?)";

    public static String getCheckForUserSQL()
    {
        return checkForUserSQL;
    }  
    
    public static String getUserCredentialsSQL()
    {
        return getUserCredentialsSQL;
    }

    public static String getAddUserSQL()
    {
        return addUserSQL;
    }
    
    
}
