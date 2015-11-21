/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.google.gson.Gson;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains the commands the client will send to FrontController, e.g
 * "FrontController/desktopMainPage" these are kept separate from
 * GlobalVariables to avoid confusion
 *
 * @author max
 */
public class ClientAPI
{
//    protected static Map<String, String> clientAPIMap = new HashMap<>();
//    
//    protected static void setupAPIMap()
//    {
//        clientAPIMap.put("frontController", "FrontControllerServlet");
//        clientAPIMap.put("login", "AuthenticationServlet");
//        clientAPIMap.put("loginPage", "LoginPageServlet");
//        clientAPIMap.put("logout", "LogoutServlet");
//        clientAPIMap.put("mainPage", "MainPageServlet");
//        clientAPIMap.put("createAccount", "CreateAccountServlet");
//        clientAPIMap.put("createAccountPage", "CreateAccountPageServlet");
//        clientAPIMap.put("workoutLogPage", "WorkoutLogPageServlet");
//        clientAPIMap.put("customFoodsPage", "CustomFoodsPageServlet");
//        clientAPIMap.put("myStatsPage", "MyStatsPageServlet");
//        clientAPIMap.put("forgotPasswordPage", "ForgotPasswordPageServlet");
//        clientAPIMap.put("forgotPasswordEmail", "ForgotPasswordEmailServlet");
//        clientAPIMap.put("changePasswordPage", "ChangePasswordPageServlet");
//        clientAPIMap.put("changePassword", "ChangePasswordServlet");
//        
//        //AJAX REQUESTS
//        clientAPIMap.put("AJAX_GetCustomFoodList", "AJAX_GetCustomFoodList");
//        clientAPIMap.put("AJAX_RemoveCustomFood", "AJAX_RemoveCustomFood");
//        clientAPIMap.put("AJAX_AddCustomFood", "AJAX_AddCustomFood");
//        clientAPIMap.put("AJAX_EditCustomFood", "AJAX_EditCustomFood");
//        clientAPIMap.put("AJAX_AddEatenFood", "AJAX_AddEatenFood");
//        clientAPIMap.put("AJAX_RemoveEatenFood", "AJAX_RemoveEatenFood");
//        clientAPIMap.put("AJAX_SearchForFood", "AJAX_SearchForFood");
//        clientAPIMap.put("AJAX_ModifySelectedAttributes", "AJAX_ModifySelectedAttributes");
//        clientAPIMap.put("AJAX_GetSelectedAttributesList", "AJAX_GetSelectedAttributesList");
//        clientAPIMap.put("AJAX_GetFriendlyNames", "AJAX_GetFriendlyNames");
//        clientAPIMap.put("AJAX_ModifyUserStats", "AJAX_ModifyUserStats");
//        clientAPIMap.put("AJAX_GetUserStats", "AJAX_GetUserStats");
//        clientAPIMap.put("AJAX_GetIdentifierTokenEmail", "AJAX_GetIdentifierTokenEmail");      
//    }
//    
//    protected static Map<String,String> getClientAPI()
//    {    
//        return clientAPIMap;
//    }
    
    
    //using what the client will login with, email, username etc, in this case it is email
    private static final String clientRequestIdentifier = "email";

    //Client API
    private static final String frontController = "FrontControllerServlet";
    private static final String loginRequest = "login";
    private static final String loginPageRequest = "loginPage";
    private static final String logoutRequest = "logout";
    private static final String mainPageRequest = "mainPage";
    //private static final String sessionPlaceholderPageRequest = "sessionPlaceholderPage";
    private static final String createAccountRequest = "createAccount";
    private static final String createAccountPageRequest = "createAccountPage";
    private static final String workoutLogPageRequest = "workoutLogPage";
    private static final String customFoodsPageRequest = "customFoodsPage";
    private static final String myStatsPageRequest = "myStatsPage";
    private static final String forgotPasswordPageRequest = "forgotPasswordPage";
    private static final String forgotPasswordEmailRequest = "forgotPasswordEmail";
    private static final String changePasswordPageRequest = "changePasswordPage";
    private static final String changePasswordRequest = "changePassword";
    private static final String changeEmailRequest = "changeEmail";
    private static final String settingsPageRequest = "settingsPage";

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
    private static final String AJAX_GetFriendlyNames = "AJAX_GetFriendlyNames";
    private static final String AJAX_ModifyUserStats = "AJAX_ModifyUserStats";
    private static final String AJAX_GetUserStats = "AJAX_GetUserStats";
    private static final String AJAX_GetIdentifierTokenEmail = "AJAX_GetIdentifierTokenEmail";

    //Values client will need
    private static final Map<String, String> friendlyValuesMap;
    static
    {
        friendlyValuesMap = new HashMap<String, String>();
        friendlyValuesMap.put("gluc", "Glucose");
        friendlyValuesMap.put("totsug", "Total Sugar");
        friendlyValuesMap.put("fruct", "Fructose");
        friendlyValuesMap.put("carbohydrate", "Carbohydrates");
        friendlyValuesMap.put("protein", "Protein");
        friendlyValuesMap.put("fat", "Fat");
        friendlyValuesMap.put("polyfod", "Polyunsaturated fat");
        friendlyValuesMap.put("weight", "Weight (g)");
        friendlyValuesMap.put("chol", "Cholesterol(mg)");
        friendlyValuesMap.put("water", "Water");
        friendlyValuesMap.put("malt", "Maltose");
        friendlyValuesMap.put("calorie", "Calories");
        friendlyValuesMap.put("foodname", "Food Name");
        friendlyValuesMap.put("description", "Description");
        friendlyValuesMap.put("monofod", "Monounsaturated fat");
        friendlyValuesMap.put("kj", "Energy(kj)");
        friendlyValuesMap.put("satfod", "Saturated fat");
        friendlyValuesMap.put("totnit", "Total Nitrogen");
        friendlyValuesMap.put("star", "Starch");
        friendlyValuesMap.put("sucr", "Sucrose");
        friendlyValuesMap.put("engfib", "Dietary Fibre");
        friendlyValuesMap.put("galact", "Galactose");
        friendlyValuesMap.put("lact", "Lactose");
        friendlyValuesMap.put("alco", "Alcohol");
        friendlyValuesMap.put("aoacfib", "AOAC Fibre");
        friendlyValuesMap.put("fodtrans", "Trans Fats");
        friendlyValuesMap.put("sodium", "Sodium");
        friendlyValuesMap.put("potassium", "Potassium");
        friendlyValuesMap.put("calcium", "Calcium");
        friendlyValuesMap.put("magnesium", "Magnesium");
        friendlyValuesMap.put("phosphorus", "Phosphorus");
        friendlyValuesMap.put("iron", "Iron");
        friendlyValuesMap.put("copper", "Copper");
        friendlyValuesMap.put("zinc", "Zinc");
        friendlyValuesMap.put("chloride", "Chloride");
        friendlyValuesMap.put("manganese", "Manganese");
        friendlyValuesMap.put("selenium", "Selenium");
        friendlyValuesMap.put("iodine", "Iodine");
    }

    public static String getChangeEmailRequest()
    {
        return changeEmailRequest;
    }

    public static String getSettingsPageRequest()
    {
        return settingsPageRequest;
    }

    
    public static String getAJAX_GetIdentifierTokenEmail()
    {
        return AJAX_GetIdentifierTokenEmail;
    }

    
    public static String getChangePasswordRequest()
    {
        return changePasswordRequest;
    }
    

    public static String getForgotPasswordEmailRequest()
    {
        return forgotPasswordEmailRequest;
    }
    
    public static String getChangePasswordPageRequest()
    {
        return changePasswordPageRequest;
    }

    public static String getForgotPasswordPageRequest()
    {
        return forgotPasswordPageRequest;
    }
    
    public static String getAJAX_GetUserStats()
    {
        return AJAX_GetUserStats;
    } 

    public static String getAJAX_ModifyUserStats()
    {
        return AJAX_ModifyUserStats;
    }

    public static String getMyStatsPageRequest()
    {
        return myStatsPageRequest;
    }
   
    public static Map<String, String> getFriendlyValuesMap()
    {
        return friendlyValuesMap;
    }

    public static String getAJAX_GetFriendlyNames()
    {
        return AJAX_GetFriendlyNames;
    }

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

    public static String getWorkoutLogPageRequest()
    {
        return workoutLogPageRequest;
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
