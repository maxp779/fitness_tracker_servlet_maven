/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

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
    //using what the client will login with, email, username etc, in this case it is email
    private static final String clientRequestIdentifier = "email";

    //Client API
    private static final String frontController = "/FrontControllerServlet";
    private static final String loginRequest = "login";
    private static final String loginPageRequest = "loginPage";
    private static final String logoutRequest = "logout";
    private static final String mainPageRequest = "mainPage";
    private static final String sessionPlaceholderPageRequest = "sessionPlaceholderPage";
    private static final String createAccountRequest = "createAccount";
    private static final String createAccountPageRequest = "createAccountPage";
    private static final String workoutLogPageRequest = "workoutLogPage";
    private static final String customFoodsPageRequest = "customFoodsPage";
    private static final String myStatsPageRequest = "myStatsPage";

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
