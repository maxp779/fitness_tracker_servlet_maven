/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class deals with accessing the database
 * @author max
 */
public class DatabaseAccess
{
    /**
     * 
     * @param loginAttemptEmail
     * @return A Map object containing the users email and password or null
     * if they were not found
     */
    public static Map<String,String> getUserCredentials(String loginAttemptEmail)
    {
        System.out.println("DatabaseAccess: getting user credentials");
        Map output = new HashMap<>();
        ResultSet resultSet = null;
        String retrievedEmail = null;
        String retrievedPassword = null;
        PreparedStatement getEmailStatement = null;
        Connection databaseConnection = null;
        
        //if user exists
        if(DatabaseAccess.userAlreadyExistsCheck(loginAttemptEmail))
        {
            databaseConnection = DatabaseUtils.getDatabaseConnection();
            try
            {
                getEmailStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getUserCredentialsSQL());
                getEmailStatement.setString(1,loginAttemptEmail);

                resultSet = getEmailStatement.executeQuery();
                resultSet.next();
                retrievedEmail = resultSet.getString("email");
                retrievedPassword = resultSet.getString("password");

            }
            catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                DatabaseUtils.closeConnections(databaseConnection, resultSet, getEmailStatement);
            }

            output.put(retrievedEmail, retrievedPassword);
            return output;
        }
        else //user dosent exist
        {
            return null;
        }
    }
    
    public static boolean addUser(String anEmail, String aPassword)
    {
        boolean userAdded;
        
        //if user already exists
        if (DatabaseAccess.userAlreadyExistsCheck(anEmail))
        {
            userAdded = false;
            System.out.println("DatabaseAccess: user already exists, no action taken");
        }
        else //if user does not exist
        {
            System.out.println("DatabaseAccess: adding user");
            PreparedStatement addUserStatement = null;
            Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
            try
            {
                addUserStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getAddUserSQL());
                addUserStatement.setString(1,anEmail);
                addUserStatement.setString(2,aPassword);
                addUserStatement.executeUpdate();
            }
            catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            finally
            {
                DatabaseUtils.closeConnections(databaseConnection, null, addUserStatement);
            }
            userAdded = true;
        }
        
        return userAdded;
    }
    
    public static boolean userAlreadyExistsCheck(String anEmail)
    {
        System.out.println("DatabaseAccess: checking if the user is already in the database");
        boolean userAlreadyExists = true;
        PreparedStatement checkUserStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        
        try
        {
            checkUserStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getCheckForUserSQL());
            checkUserStatement.setString(1,anEmail);
            resultSet = checkUserStatement.executeQuery();
            
            if(resultSet.next())
            {
                System.out.println("DatabaseAccess: user already in the database");
                userAlreadyExists = true;
            }
            else
            {
                System.out.println("DatabaseAccess: user is not in the database");
                userAlreadyExists = false;
            }
            
        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, checkUserStatement);
        }
        
        return userAlreadyExists;
    }

}
