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
    private static final String LOGIN_IDENTIFIER = "email";

    //Client API
    private static final String FRONTCONTROLLER = "FrontControllerServlet";
    private static final String LOGIN_REQUEST = "login";
    private static final String LOGIN_PAGE_REQUEST = "loginPage";
    private static final String LOGOUT_REQUEST = "logout";
    private static final String MAIN_PAGE_REQUEST = "mainPage";
    private static final String CREATE_ACCOUNT_REQUEST = "createAccount";
    private static final String CREATE_ACCOUNT_PAGE_REQUEST = "createAccountPage";
    private static final String WORKOUTLOG_PAGE_REQUEST = "workoutLogPage";
    private static final String CUSTOM_FOODS_PAGE_REQUEST = "customFoodsPage";
    private static final String MY_STATS_PAGE_REQUEST = "myStatsPage";
    private static final String FORGOT_PASSWORD_PAGE_REQUEST = "forgotPasswordPage";
    private static final String FORGOT_PASSWORD_EMAIL_REQUEST = "forgotPasswordEmail";
    private static final String CHANGE_PASSWORD_PAGE_REQUEST = "changePasswordPage";
    private static final String CHANGE_PASSWORD_REQUEST = "changePassword";
    private static final String CHANGE_EMAIL_REQUEST = "changeEmail";
    private static final String SETTINGS_PAGE_REQUEST = "settingsPage";
    private static final String DELETE_ACCOUNT_REQUEST = "deleteAccount";

    //AJAX requests
    private static final String AJAX_GET_CUSTOM_FOOD_LIST = "AJAX_GetCustomFoodList";
    private static final String AJAX_REMOVE_CUSTOM_FOOD = "AJAX_RemoveCustomFood";
    private static final String AJAX_ADD_CUSTOM_FOOD = "AJAX_AddCustomFood";
    private static final String AJAX_EDIT_CUSTOM_FOOD = "AJAX_EditCustomFood";
    private static final String AJAX_ADD_EATEN_FOOD = "AJAX_AddEatenFood";
    private static final String AJAX_GET_EATEN_FOOD_LIST = "AJAX_GetEatenFoodList";
    private static final String AJAX_REMOVE_EATEN_FOOD = "AJAX_RemoveEatenFood";
    private static final String AJAX_SEARCH_FOR_FOOD = "AJAX_SearchForFood";
    private static final String AJAX_MODIFY_SELECTED_ATTRIBUTES = "AJAX_ModifySelectedAttributes";
    private static final String AJAX_GET_VIEWABLE_ATTRIBUTES = "AJAX_GetViewableAttributesList";
    private static final String AJAX_GET_FRIENDLY_NAMES = "AJAX_GetFriendlyNames";
    private static final String AJAX_MODIFY_USER_STATS = "AJAX_ModifyUserStats";
    private static final String AJAX_GET_USER_STATS = "AJAX_GetUserStats";
    private static final String AJAX_GET_IDENTIFIER_TOKEN_EMAIL = "AJAX_GetIdentifierTokenEmail";

    //Values client will need
    private static final Map<String, String> FRIENDLY_VALUES_MAP;
    static
    {
        FRIENDLY_VALUES_MAP = new HashMap<>();
        FRIENDLY_VALUES_MAP.put("gluc", "Glucose");
        FRIENDLY_VALUES_MAP.put("totsug", "Total Sugar");
        FRIENDLY_VALUES_MAP.put("fruct", "Fructose");
        FRIENDLY_VALUES_MAP.put("carbohydrate", "Carbohydrates");
        FRIENDLY_VALUES_MAP.put("protein", "Protein");
        FRIENDLY_VALUES_MAP.put("fat", "Fat");
        FRIENDLY_VALUES_MAP.put("polyfod", "Polyunsaturated fat");
        FRIENDLY_VALUES_MAP.put("weight", "Weight (g)");
        FRIENDLY_VALUES_MAP.put("chol", "Cholesterol(mg)");
        FRIENDLY_VALUES_MAP.put("water", "Water");
        FRIENDLY_VALUES_MAP.put("malt", "Maltose");
        FRIENDLY_VALUES_MAP.put("calorie", "Calories");
        FRIENDLY_VALUES_MAP.put("foodname", "Food Name");
        FRIENDLY_VALUES_MAP.put("description", "Description");
        FRIENDLY_VALUES_MAP.put("monofod", "Monounsaturated fat");
        FRIENDLY_VALUES_MAP.put("kj", "Energy(kj)");
        FRIENDLY_VALUES_MAP.put("satfod", "Saturated fat");
        FRIENDLY_VALUES_MAP.put("totnit", "Total Nitrogen");
        FRIENDLY_VALUES_MAP.put("star", "Starch");
        FRIENDLY_VALUES_MAP.put("sucr", "Sucrose");
        FRIENDLY_VALUES_MAP.put("engfib", "Dietary Fibre");
        FRIENDLY_VALUES_MAP.put("galact", "Galactose");
        FRIENDLY_VALUES_MAP.put("lact", "Lactose");
        FRIENDLY_VALUES_MAP.put("alco", "Alcohol");
        FRIENDLY_VALUES_MAP.put("aoacfib", "AOAC Fibre");
        FRIENDLY_VALUES_MAP.put("fodtrans", "Trans Fats");
        FRIENDLY_VALUES_MAP.put("sodium", "Sodium");
        FRIENDLY_VALUES_MAP.put("potassium", "Potassium");
        FRIENDLY_VALUES_MAP.put("calcium", "Calcium");
        FRIENDLY_VALUES_MAP.put("magnesium", "Magnesium");
        FRIENDLY_VALUES_MAP.put("phosphorus", "Phosphorus");
        FRIENDLY_VALUES_MAP.put("iron", "Iron");
        FRIENDLY_VALUES_MAP.put("copper", "Copper");
        FRIENDLY_VALUES_MAP.put("zinc", "Zinc");
        FRIENDLY_VALUES_MAP.put("chloride", "Chloride");
        FRIENDLY_VALUES_MAP.put("manganese", "Manganese");
        FRIENDLY_VALUES_MAP.put("selenium", "Selenium");
        FRIENDLY_VALUES_MAP.put("iodine", "Iodine");
    }
    
    
    
    
    
//    
//
//    public static String getChangeEmailRequest()
//    {
//        return CHANGE_EMAIL_REQUEST;
//    }
//
//    public static String getSettingsPageRequest()
//    {
//        return SETTINGS_PAGE_REQUEST;
//    }
//
//    
//    public static String getAJAX_GetIdentifierTokenEmail()
//    {
//        return AJAX_GET_IDENTIFIER_TOKEN_EMAIL;
//    }
//
//    
//    public static String getChangePasswordRequest()
//    {
//        return CHANGE_PASSWORD_REQUEST;
//    }
//    
//
//    public static String getForgotPasswordEmailRequest()
//    {
//        return FORGOT_PASSWORD_EMAIL_REQUEST;
//    }
//    
//    public static String getChangePasswordPageRequest()
//    {
//        return CHANGE_PASSWORD_PAGE_REQUEST;
//    }
//
//    public static String getForgotPasswordPageRequest()
//    {
//        return FORGOT_PASSWORD_PAGE_REQUEST;
//    }
//    
//    public static String getAJAX_GetUserStats()
//    {
//        return AJAX_GET_USER_STATS;
//    } 
//
//    public static String getAJAX_ModifyUserStats()
//    {
//        return AJAX_MODIFY_USER_STATS;
//    }
//
//    public static String getMyStatsPageRequest()
//    {
//        return MY_STATS_PAGE_REQUEST;
//    }
//   
//    public static Map<String, String> getFriendlyValuesMap()
//    {
//        return FRIENDLY_VALUES_MAP;
//    }
//
//    public static String getAJAX_GetFriendlyNames()
//    {
//        return AJAX_GET_FRIENDLY_NAMES;
//    }
//
//    public static String getAJAX_GetSelectedAttributesList()
//    {
//        return AJAX_GET_SELECTED_ATTRIBUTES;
//    }
//
//    public static String getAJAX_GetCustomFoodList()
//    {
//        return AJAX_GET_CUSTOM_FOOD_LIST;
//    }
//
//    public static String getAJAX_RemoveCustomFood()
//    {
//        return AJAX_REMOVE_CUSTOM_FOOD;
//    }
//
//    public static String getAJAX_AddCustomFood()
//    {
//        return AJAX_ADD_CUSTOM_FOOD;
//    }
//
//    public static String getAJAX_EditCustomFood()
//    {
//        return AJAX_EDIT_CUSTOM_FOOD;
//    }
//
//    public static String getAJAX_AddEatenFood()
//    {
//        return AJAX_ADD_EATEN_FOOD;
//    }
//
//    public static String getAJAX_GetEatenFoodList()
//    {
//        return AJAX_GET_EATEN_FOOD_LIST;
//    }
//
//    public static String getAJAX_RemoveEatenFood()
//    {
//        return AJAX_REMOVE_EATEN_FOOD;
//    }
//
//    public static String getAJAX_SearchForFood()
//    {
//        return AJAX_SEARCH_FOR_FOOD;
//    }
//
//    public static String getAJAX_ModifySelectedAttributes()
//    {
//        return AJAX_MODIFY_SELECTED_ATTRIBUTES;
//    }
//
//    public static String getWorkoutLogPageRequest()
//    {
//        return WORKOUTLOG_PAGE_REQUEST;
//    }
//
//    public static String getCustomFoodsPageRequest()
//    {
//        return CUSTOM_FOODS_PAGE_REQUEST;
//    }
//
//    public static String getCreateAccountPageRequest()
//    {
//        return CREATE_ACCOUNT_PAGE_REQUEST;
//    }
//
//    public static String getClientRequestIdentifier()
//    {
//        return LOGIN_IDENTIFIER;
//    }
//
//    public static String getCreateAccountRequest()
//    {
//        return CREATE_ACCOUNT_REQUEST;
//    }
//
//    public static String getFrontController()
//    {
//        return FRONTCONTROLLER;
//    }
//
//    public static String getLoginPageRequest()
//    {
//        return LOGIN_PAGE_REQUEST;
//    }
//
//    public static String getLoginRequest()
//    {
//        return LOGIN_REQUEST;
//    }
//
//    public static String getLogoutRequest()
//    {
//        return LOGOUT_REQUEST;
//    }
//
//    public static String getMainPageRequest()
//    {
//        return MAIN_PAGE_REQUEST;
//    }

    public static String getLOGIN_IDENTIFIER() {
        return LOGIN_IDENTIFIER;
    }

    public static String getFRONTCONTROLLER() {
        return FRONTCONTROLLER;
    }

    public static String getLOGIN_REQUEST() {
        return LOGIN_REQUEST;
    }

    public static String getLOGIN_PAGE_REQUEST() {
        return LOGIN_PAGE_REQUEST;
    }

    public static String getLOGOUT_REQUEST() {
        return LOGOUT_REQUEST;
    }

    public static String getMAIN_PAGE_REQUEST() {
        return MAIN_PAGE_REQUEST;
    }

    public static String getCREATE_ACCOUNT_REQUEST() {
        return CREATE_ACCOUNT_REQUEST;
    }

    public static String getCREATE_ACCOUNT_PAGE_REQUEST() {
        return CREATE_ACCOUNT_PAGE_REQUEST;
    }

    public static String getWORKOUTLOG_PAGE_REQUEST() {
        return WORKOUTLOG_PAGE_REQUEST;
    }

    public static String getCUSTOM_FOODS_PAGE_REQUEST() {
        return CUSTOM_FOODS_PAGE_REQUEST;
    }

    public static String getMY_STATS_PAGE_REQUEST() {
        return MY_STATS_PAGE_REQUEST;
    }

    public static String getFORGOT_PASSWORD_PAGE_REQUEST() {
        return FORGOT_PASSWORD_PAGE_REQUEST;
    }

    public static String getFORGOT_PASSWORD_EMAIL_REQUEST() {
        return FORGOT_PASSWORD_EMAIL_REQUEST;
    }

    public static String getCHANGE_PASSWORD_PAGE_REQUEST() {
        return CHANGE_PASSWORD_PAGE_REQUEST;
    }

    public static String getCHANGE_PASSWORD_REQUEST() {
        return CHANGE_PASSWORD_REQUEST;
    }

    public static String getCHANGE_EMAIL_REQUEST() {
        return CHANGE_EMAIL_REQUEST;
    }

    public static String getSETTINGS_PAGE_REQUEST() {
        return SETTINGS_PAGE_REQUEST;
    }

    public static String getDELETE_ACCOUNT_REQUEST() {
        return DELETE_ACCOUNT_REQUEST;
    }

    public static String getAJAX_GET_CUSTOM_FOOD_LIST() {
        return AJAX_GET_CUSTOM_FOOD_LIST;
    }

    public static String getAJAX_REMOVE_CUSTOM_FOOD() {
        return AJAX_REMOVE_CUSTOM_FOOD;
    }

    public static String getAJAX_ADD_CUSTOM_FOOD() {
        return AJAX_ADD_CUSTOM_FOOD;
    }

    public static String getAJAX_EDIT_CUSTOM_FOOD() {
        return AJAX_EDIT_CUSTOM_FOOD;
    }

    public static String getAJAX_ADD_EATEN_FOOD() {
        return AJAX_ADD_EATEN_FOOD;
    }

    public static String getAJAX_GET_EATEN_FOOD_LIST() {
        return AJAX_GET_EATEN_FOOD_LIST;
    }

    public static String getAJAX_REMOVE_EATEN_FOOD() {
        return AJAX_REMOVE_EATEN_FOOD;
    }

    public static String getAJAX_SEARCH_FOR_FOOD() {
        return AJAX_SEARCH_FOR_FOOD;
    }

    public static String getAJAX_MODIFY_SELECTED_ATTRIBUTES() {
        return AJAX_MODIFY_SELECTED_ATTRIBUTES;
    }

    public static String getAJAX_GET_VIEWABLE_ATTRIBUTES() {
        return AJAX_GET_VIEWABLE_ATTRIBUTES;
    }

    public static String getAJAX_GET_FRIENDLY_NAMES() {
        return AJAX_GET_FRIENDLY_NAMES;
    }

    public static String getAJAX_MODIFY_USER_STATS() {
        return AJAX_MODIFY_USER_STATS;
    }

    public static String getAJAX_GET_USER_STATS() {
        return AJAX_GET_USER_STATS;
    }

    public static String getAJAX_GET_IDENTIFIER_TOKEN_EMAIL() {
        return AJAX_GET_IDENTIFIER_TOKEN_EMAIL;
    }

    public static Map<String, String> getFRIENDLY_VALUES_MAP() {
        return FRIENDLY_VALUES_MAP;
    }

}
