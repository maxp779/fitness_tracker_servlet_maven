/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

/**
 * This class contains the commands the client will send to FrontController,
 * e.g "FrontController/desktopMainPage"
 * these are kept separate from GlobalVariables to avoid confusion
 * @author max
 */
public class ClientAPI
{
    //Client API
    private static final String frontController = "/FrontControllerServlet";
    
    private static final String loginPageRequest = "loginPageRequest";
    private static final String loginRequest = "loginRequest";
    private static final String logoutRequest = "logoutRequest";
    private static final String mainPageRequest = "mainPageRequest";
    private static final String sessionPlaceholderRequest = "sessionPlaceholderRequest";
    private static final String createAccountRequest = "createAccountRequest";
    
    //using what the client will login with, email, username etc, in this case it is email
    private static final String clientRequestIdentifier = "email";

    public static String getClientRequestIdentifier()
    {
        return clientRequestIdentifier;
    }
   
    public static String getSessionPlaceholderRequest()
    {
        return sessionPlaceholderRequest;
    }

    public static String getCreateAccountRequest()
    {
        return createAccountRequest;
    }
    
    public static String getFrontController()
    {
        return frontController;
    }
    
    public static String getLoginPageRequest()
    {
        return loginPageRequest;
    }

    public static String getLoginRequest()
    {
        return loginRequest;
    }

    public static String getLogoutRequest()
    {
        return logoutRequest;
    }

    public static String getMainPageRequest()
    {
        return mainPageRequest;
    }
    
}
