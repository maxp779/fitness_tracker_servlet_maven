/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

/**
 * This class contains the commands the client will send to FrontController, e.g
 * "FrontController/desktopMainPage" these are kept separate from
 * GlobalVariables to avoid confusion
 *
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
    private static final String AJAX_GetCustomFoodList = "AJAX_GetCustomFoodList";
    private static final String AJAX_RemoveCustomFood = "AJAX_RemoveCustomFood";
    private static final String AJAX_AddCustomFood = "AJAX_AddCustomFood";
    private static final String AJAX_EditCustomFood = "AJAX_EditCustomFood";
    private static final String AJAX_AddEatenFood = "AJAX_AddEatenFood";
    private static final String AJAX_GetEatenFoodList = "AJAX_GetEatenFoodList";
    private static final String AJAX_RemoveEatenFood = "AJAX_RemoveEatenFood";
    private static final String AJAX_SearchForFood = "AJAX_SearchForFood";
    private static final String AJAX_ModifySelectedAttributes = "AJAX_ModifySelectedAttributes";
    private static final String AJAX_GetSelectedAttributesList = "AJAX_GetSelectedAttributesList";
    //using what the client will login with, email, username etc, in this case it is email
    private static final String clientRequestIdentifier = "email";

    public static String getAJAX_GetSelectedAttributesList()
    {
        return AJAX_GetSelectedAttributesList;
    }

    public static String getAJAX_GetCustomFoodList()
    {
        return AJAX_GetCustomFoodList;
    }

    public static String getAJAX_RemoveCustomFood()
    {
        return AJAX_RemoveCustomFood;
    }

    public static String getAJAX_AddCustomFood()
    {
        return AJAX_AddCustomFood;
    }

    public static String getAJAX_EditCustomFood()
    {
        return AJAX_EditCustomFood;
    }

    public static String getAJAX_AddEatenFood()
    {
        return AJAX_AddEatenFood;
    }

    public static String getAJAX_GetEatenFoodList()
    {
        return AJAX_GetEatenFoodList;
    }

    public static String getAJAX_RemoveEatenFood()
    {
        return AJAX_RemoveEatenFood;
    }

    public static String getAJAX_SearchForFood()
    {
        return AJAX_SearchForFood;
    }

    public static String getAJAX_ModifySelectedAttributes()
    {
        return AJAX_ModifySelectedAttributes;
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
