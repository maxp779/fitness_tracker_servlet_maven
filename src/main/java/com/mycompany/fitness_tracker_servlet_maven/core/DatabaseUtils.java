/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Enumeration;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

/**
 * This class contains various database related methods, most of which are
 * self explanatory.
 * @author max
 */
public class DatabaseUtils
{
        /**
     * This method returns a database connection, the connection MUST be closed
     * by whatever class calls this method otherwise connections will leak and
     * eventually there wont be any left.
     * 
     * <p>In addition all ResultSet and Statement objects derived from the Connection must
 also be closed. This can be done manually or via DatabaseUtils.closeConnections()</p>
     * 
     * <p>If done manually the objects should be closed in the following order:
     * ResultSet --> Statement --> Connection</p>
     * 
     * @return a Connection object to connect with the database
     */
    public static Connection getDatabaseConnection()
    {       
        InitialContext initialContext = null;
        try
        {
            initialContext = new InitialContext();
        } catch (NamingException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        DataSource source = null;
        
        try
        {
            source = (DataSource) initialContext.lookup(GlobalValues.getDatabaseConnectionPool());
        } catch (NamingException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        Connection aConnection = null;

        try
        {
            aConnection = source.getConnection();
        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        
         return aConnection;
    }
    
    public static void loadDatabaseDriver()
    {
        try
        {
            System.out.println("DatabaseUtilities: loading database driver");
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException ex)
        {
            System.out.println("DatabaseUtilities: loading database driver failed");
            Logger.getLogger(StartupWebListener.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void dersgisterDatabaseDriver()
    {
        System.out.println("DatabaseUtilities: getting driver list");
        ClassLoader aClassLoader = Thread.currentThread().getContextClassLoader();       
        Enumeration<Driver> driverEnumeration = DriverManager.getDrivers();
        
        while(driverEnumeration.hasMoreElements())
        {
            Driver currentDriver = driverEnumeration.nextElement();
            
            //if currentDriver was registered by this application deregister it
            if(currentDriver.getClass().getClassLoader().equals(aClassLoader))
            {
                try
                {
                    System.out.println("DatabaseUtilities: deregistering driver");
                    DriverManager.deregisterDriver(currentDriver);
                } catch (SQLException ex)
                {
                    System.out.println("DatabaseUtilities: deregistering driver failed");
                    Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
            else
            {
                //currentDriver is not linked with this application
                //do not deregister it
            }
        }
    }
    
    
    /**
     * This method closes database related objects and connections, null can be passed
     * as an argument. Say for example you only want to close a Statement and a ResultSet
     * objects, the method call would look like:
     * 
     * 
     * <p>closeConnections(null, aResultSetObject, aStatementObject);</p>
     * 
     * @param aConnection can be null
     * @param aResultSet can be null
     * @param aStatement can be null
     */
    public static void closeConnections(Connection aConnection, ResultSet aResultSet, Statement aStatement)
    {
        if(aResultSet !=null)
        {
            try
            {
                System.out.println("DatabaseUtilities: closing ResultSet object");
                aResultSet.close();
            } catch (SQLException ex)
            {
                System.out.println("DatabaseUtilities: closing ResultSet object failed");
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("DatabaseUtilities: ResultSet object null detected, no action taken on ResultSet");
        }
        
        if(aStatement !=null)
        {
            try
            {
                System.out.println("DatabaseUtilities: closing Statement object");
                aStatement.close();
            } catch (SQLException ex)
            {
                System.out.println("DatabaseUtilities: closing Statement object failed");
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("DatabaseUtilities: Statement object null detected, no action taken on Statement");
        }
        
        if(aConnection !=null)
        {
            try
            {
                System.out.println("DatabaseUtilities: closing Connection object");
                aConnection.close();
            } catch (SQLException ex)
            {
                System.out.println("DatabaseUtilities: closing Connection object failed");
                Logger.getLogger(DatabaseUtils.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else
        {
            System.out.println("DatabaseUtilities: Connection object null detected, no action taken on Connection");
        }
    }   
}