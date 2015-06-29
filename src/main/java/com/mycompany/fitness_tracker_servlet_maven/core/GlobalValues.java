/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

/**
 * This class contains important values all in one location
 * @author max
 */
public class GlobalValues
{
    //info
    private static final String webAddress = "http://localhost:8080/fitness_tracker_servlet_maven/";
    private static final String projectName = "fitness_tracker_servlet_maven";
    private static final String webPagesDirectory = "webPages";
    private static final String FirstLoginServlet = "LoginPageServlet";
    
    //html pages
    private static final String loginPage = "loginPage.html";
    private static final String cookiesPolicy = "cookiesPolicyPage.html";
    private static final String logoutPage = "logoutPage.html";
    private static final String aboutPage = "aboutPage.html";
    private static final String mainPage = "mainPage.html";
    private static final String workoutTrackerPage = "workoutTracker.html";
    private static final String sessionPlaceholderPage = "sessionPlaceholder.html";
    private static final String createAccountPage = "createAccount.html";
    private static final String customFoodsPage = "customFoods.html";
    
    //misc values
    private static final int maxInactiveInterval = -1; // session timeout, not currently used
    
    //database values
    private static final String databaseURL = "jdbc:postgresql://localhost:5432/fitnessTrackerDatabase";
    private static final String databaseConnectionPool = "jdbc/fitnessTrackerDB"; //JNDI name for connection pool
    
    //AuthenticationFilter will skip the session check when these resources are requested
    private static final String[] nonAuthResources =
            { "invalid.html",".ico",".css",".js",".png",".jpeg","testPage.html","testDatabase",
                GlobalValues.cookiesPolicy,
                GlobalValues.loginPage,
                GlobalValues.logoutPage,
                GlobalValues.FirstLoginServlet,
                GlobalValues.createAccountPage,
                GlobalValues.aboutPage,
                ClientAPI.getLoginPageRequest(),
                ClientAPI.getLoginRequest(),
                ClientAPI.getCreateAccountRequest(),
                ClientAPI.getCreateAccountPageRequest()
            };

    public static String getCustomFoodsPage()
    {
        return customFoodsPage;
    } 

    public static String getWorkoutTrackerPage()
    {
        return workoutTrackerPage;
    }
    
    public static String getDatabaseConnectionPool()
    {
        return databaseConnectionPool;
    }   
    
    public static String getCookiesPolicy()
    {
        return cookiesPolicy;
    }

    public static String getAboutPage()
    {
        return aboutPage;
    }

    public static String getCreateAccountPage()
    {
        return createAccountPage;
    }

    public static String getDatabaseURL()
    {
        return databaseURL;
    }

    /**
     * returns the amount of time in seconds that a cookie based session is 
     * valid for, a value of 0 or less means the session will never expire on
     * its own
     * @return 
     */
    public static int getMaxInactiveInterval()
    {
        return maxInactiveInterval;
    }
  
    public static String[] getNonAuthResources()
    {
        return nonAuthResources;
    }
    
    public static String getWebAddress()
    {
        return webAddress;
    }

    public static String getCreateNewAccountPage()
    {
        return createAccountPage;
    }

    public static String getSessionPlaceholderPage()
    {
        return sessionPlaceholderPage;
    }
    
    public static String getLogoutPage()
    {
        return logoutPage;
    }

    public static String getProjectName()
    {
        return projectName;
    }

    public static String getWebPagesDirectory()
    {
        return webPagesDirectory;
    }

    public static String getFirstLoginServlet()
    {
        return FirstLoginServlet;
    }

    public static String getLoginPage()
    {
        return loginPage;
    }

    public static String getMainPage()
    {
        return mainPage;
    }
       
}
