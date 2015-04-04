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
    //URL's
    private static final String webAddress = "http://localhost:8080/fitness_tracker_servlet_maven/";
    private static final String projectName = "fitness_tracker_servlet_maven";
    private static final String webPagesDirectory = "webPages";
    
    private static final String URLLoginPage = "LoginPageRequestServlet";
    
    private static final String loginPage = "loginPage.html";
    private static final String logoutPage = "logoutPage.html";
    private static final String mainPage = "mainPage.html";
    private static final String sessionPlaceholderPage = "sessionPlaceholder.html";
    private static final String createNewAccountPage = "createNewAccount.html";
    
    private static final int maxInactiveInterval = 0;
    private static final long jsessionTimeout = 1440;
    
    
    
    private static final String[] nonAuthResources =
            { "bootstrap-3.3.2-dist","index.html","ServerAPI.js","invalid.html",
                "loginPage.html","createNewAccount.html","logoutPage.html",
                "icons","LoginPageRequestServlet", 
                ClientAPI.getLoginPageRequest(),
                ClientAPI.getLoginRequest(),
                ClientAPI.getCreateAccountRequest()};

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

    /**
     * returns the amount of time in minutes that a jsession based session
     * is valid for
     * @return 
     */
    public static long getJessionTimeout()
    {
        return jsessionTimeout;
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
        return createNewAccountPage;
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

    public static String getURLLoginPage()
    {
        return URLLoginPage;
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
