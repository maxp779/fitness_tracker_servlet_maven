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
    
    private static final String loginRequest = "login";
    private static final String loginPageRequest = "loginPage";
    private static final String logoutRequest = "logout";
    private static final String mainPageRequest = "mainPage";
    private static final String sessionPlaceholderPageRequest = "sessionPlaceholderPage";
    private static final String createAccountRequest = "createAccount";
    private static final String createAccountPageRequest = "createAccountPage";
    private static final String workoutTrackerPageRequest = "workoutTrackerPage";
    private static final String customFoodsPageRequest = "customFoodsPage";
    
    //AJAX requests
    private static final String AJAXgetCustomFood = "AJAX_GetCustomFood";
    private static final String AJAXremoveCustomFood = "AJAX_RemoveCustomFood";
    private static final String AJAXaddCustomFood = "AJAX_AddCustomFood";
    private static final String AJAXeditCustomFood = "AJAX_EditCustomFood";
    
    
    //using what the client will login with, email, username etc, in this case it is email
    private static final String clientRequestIdentifier = "email";

    public static String getAJAXeditCustomFood()
    {
        return AJAXeditCustomFood;
    }

        
    public static String getAJAXaddCustomFood()
    {
        return AJAXaddCustomFood;
    }

    public static String getAJAXremoveCustomFood()
    {
        return AJAXremoveCustomFood;
    }

    public static String getAJAXgetCustomFood()
    {
        return AJAXgetCustomFood;
    }

    public static String getWorkoutTrackerPageRequest()
    {
        return workoutTrackerPageRequest;
    }

    public static String getCustomFoodsPageRequest()
    {
        return customFoodsPageRequest;
    }

    
    
    
    public static String getCreateAccountPageRequest()
    {
        return createAccountPageRequest;
    }
  
    

    public static String getClientRequestIdentifier()
    {
        return clientRequestIdentifier;
    }
   
    public static String getSessionPlaceholderPageRequest()
    {
        return sessionPlaceholderPageRequest;
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
