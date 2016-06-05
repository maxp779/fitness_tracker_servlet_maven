/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.globalvalues;

import com.mycompany.fitness_tracker_servlet_maven.serverAPI.Request;
import java.util.HashMap;
import java.util.Map;

/**
 * This class contains important values all in one location
 *
 * @author max
 */
public class GlobalValues
{

    //info
    //private static final String WEB_ADDRESS = "http://localhost:8080/fitness_tracker_servlet_maven/";
    private static final String PROJECT_NAME = "fitness_tracker_servlet_maven";
    private static final String WEB_PAGES_DIRECTORY = "webPages";
    private static final String FIRST_LOGIN_SERVLET = "LoginPageServlet";
    private static final String CHANGE_PASSWORD_PAGE_SERVLET = "ChangePasswordPageServlet";
    private static final String LOGIN_PAGE_FOLDER = "loginPage";
    private static final String MAIN_PAGE_FOLDER = "mainPage";
    private static final String WORKOUT_LOG_PAGE_FOLDER = "workoutLogPage";
    private static final String SETTINGS_PAGE_FOLDER = "settingsPage";
    private static final String CREATE_ACCOUNT_PAGE_FOLDER = "createAccountPage";
    private static final String CUSTOM_FOODS_PAGE_FOLDER = "customFoodsPage";
    private static final String MY_STATS_PAGE_FOLDER = "myStatsPage";
    private static final String FORGOT_PASSWORD_PAGE_FOLDER = "forgotPasswordPage";
    private static final String CHANGE_PASSWORD_PAGE_FOLDER = "changePasswordPage";

    //html pages
    private static final String LOGIN_PAGE = "loginPage.html";
    private static final String COOKIES_POLICY = "cookiesPolicyPage.html";
    //private static final String LOGOUT_PAGE = "logoutPage.html";
    private static final String ABOUT_PAGE = "aboutPage.html";
    private static final String MAIN_PAGE = "mainPage.html";
    private static final String WORKOUT_LOG_PAGE = "workoutLogPage.html";
    private static final String SETTINGS_PAGE = "settingsPage.html";
    private static final String CREATE_ACCOUNT_PAGE = "createAccountPage.html";
    private static final String CUSTOM_FOODS_PAGE = "customFoodsPage.html";
    private static final String MY_STATS_PAGE = "myStatsPage.html";
    private static final String FORGOT_PASSWORD_PAGE = "forgotPasswordPage.html";
    private static final String CHANGE_PASSWORD_PAGE = "changePasswordPage.html";

    //misc values
    private static final int SESSION_TIMEOUT_VALUE = 21600; // session timeout, 0 or less will never timeout, this value is in seconds, currently 21600 is 6 hours
    
    private static final int MIN_PASSWORD_LENGTH = 6;
    //database values
    private static final String DATABASE_URL = "jdbc:postgresql://localhost:5432/fitnessTrackerDatabase";
    private static final String DATABASE_CONNECTION_POOL = "jdbc/fitnessTrackerDB"; //JNDI name for connection pool

    //AuthenticationFilter will skip the session check when these resources are requested
//    private static final String[] NON_AUTH_RESOURCES =
//    {
//        "invalid.html", ".ico", ".css", ".js", ".png", ".jpeg", ".jpg", "testPage.html", "testDatabase", "http://localhost:8080/",
//        GlobalValues.COOKIES_POLICY,
//        GlobalValues.LOGIN_PAGE,
//        GlobalValues.LOGOUT_PAGE,
//        GlobalValues.FIRST_LOGIN_SERVLET,
//        GlobalValues.CREATE_ACCOUNT_PAGE,
//        GlobalValues.ABOUT_PAGE,
//        GlobalValues.FORGOT_PASSWORD_PAGE,
//        GlobalValues.CHANGE_PASSWORD_PAGE,
//        GlobalValues.CHANGE_PASSWORD_PAGE_SERVLET,
//        ServerAPIReference.get(Request.LOGIN_PAGE_REQUEST),
//        ServerAPIReference.get(Request.LOGIN_REQUEST),
//        ServerAPIReference.get(Request.CREATE_ACCOUNT_REQUEST),
//        ServerAPIReference.get(Request.CREATE_ACCOUNT_PAGE_REQUEST),
//        ServerAPIReference.get(Request.FORGOT_PASSWORD_PAGE_REQUEST),
//        ServerAPIReference.get(Request.FORGOT_PASSWORD_EMAIL_REQUEST),
//        ServerAPIReference.get(Request.CHANGE_PASSWORD_PAGE_REQUEST),
//        ServerAPIReference.get(Request.GET_IDENTIFIER_TOKEN_EMAIL),
//        ServerAPIReference.get(Request.CHANGE_PASSWORD_REQUEST),
//        ServerAPIReference.get(Request.GET_SERVER_API)
//    };

    private static final String[] AUTH_RESOURCES =
    {
        Request.MAIN_PAGE_REQUEST.toString(),
        Request.WORKOUT_LOG_PAGE_REQUEST.toString(),
        Request.CUSTOM_FOODS_PAGE_REQUEST.toString(),
        Request.MY_STATS_PAGE_REQUEST.toString(),
        Request.CHANGE_EMAIL_REQUEST.toString(),
        Request.SETTINGS_PAGE_REQUEST.toString(),
        Request.DELETE_ACCOUNT_REQUEST.toString(),
        Request.GET_CUSTOM_FOOD_LIST.toString(),
        Request.DELETE_CUSTOM_FOOD.toString(),
        Request.CREATE_CUSTOM_FOOD.toString(),
        Request.EDIT_CUSTOM_FOOD.toString(),
        Request.ADD_EATEN_FOOD.toString(),
        Request.GET_EATEN_FOOD_LIST.toString(),
        Request.REMOVE_EATEN_FOOD.toString(),
        Request.SEARCH_FOR_FOOD.toString(),
        Request.MODIFY_SELECTED_ATTRIBUTES.toString(),
        Request.MODIFY_USER_STATS.toString(),
        Request.GET_USER_STATS.toString(),
        MAIN_PAGE,
        WORKOUT_LOG_PAGE,
        SETTINGS_PAGE,
        CREATE_ACCOUNT_PAGE,
        CUSTOM_FOODS_PAGE,
        MY_STATS_PAGE
    };

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

    public static int getMIN_PASSWORD_LENGTH()
    {
        return MIN_PASSWORD_LENGTH;
    }
    
    
    
    public static String[] getAUTH_RESOURCES()
    {
        return AUTH_RESOURCES;
    }

    public static String getCHANGE_PASSWORD_PAGE_SERVLET()
    {
        return CHANGE_PASSWORD_PAGE_SERVLET;
    }

    public static String getLOGIN_PAGE_FOLDER()
    {
        return LOGIN_PAGE_FOLDER;
    }

    public static String getMAIN_PAGE_FOLDER()
    {
        return MAIN_PAGE_FOLDER;
    }

    public static String getWORKOUT_LOG_PAGE_FOLDER()
    {
        return WORKOUT_LOG_PAGE_FOLDER;
    }

    public static String getSETTINGS_PAGE_FOLDER()
    {
        return SETTINGS_PAGE_FOLDER;
    }

    public static String getCREATE_ACCOUNT_PAGE_FOLDER()
    {
        return CREATE_ACCOUNT_PAGE_FOLDER;
    }

    public static String getCUSTOM_FOODS_PAGE_FOLDER()
    {
        return CUSTOM_FOODS_PAGE_FOLDER;
    }

    public static String getMY_STATS_PAGE_FOLDER()
    {
        return MY_STATS_PAGE_FOLDER;
    }

    public static String getFORGOT_PASSWORD_PAGE_FOLDER()
    {
        return FORGOT_PASSWORD_PAGE_FOLDER;
    }

    public static String getCHANGE_PASSWORD_PAGE_FOLDER()
    {
        return CHANGE_PASSWORD_PAGE_FOLDER;
    }

    public static String getSETTINGS_PAGE()
    {
        return SETTINGS_PAGE;
    }

    public static String getFORGOT_PASSWORD_PAGE()
    {
        return FORGOT_PASSWORD_PAGE;
    }

    public static String getCHANGE_PASSWORD_PAGE()
    {
        return CHANGE_PASSWORD_PAGE;
    }

    public static String getMY_STATS_PAGE()
    {
        return MY_STATS_PAGE;
    }

    public static String getCUSTOM_FOODS_PAGE()
    {
        return CUSTOM_FOODS_PAGE;
    }

    public static String getWORKOUT_LOG_PAGE()
    {
        return WORKOUT_LOG_PAGE;
    }

    public static String getDATABASE_CONNECTION_POOL()
    {
        return DATABASE_CONNECTION_POOL;
    }

    public static String getCOOKIES_POLICY()
    {
        return COOKIES_POLICY;
    }

    public static String getABOUT_PAGE()
    {
        return ABOUT_PAGE;
    }

    public static String getCREATE_ACCOUNT_PAGE()
    {
        return CREATE_ACCOUNT_PAGE;
    }

    public static String getDATABASE_URL()
    {
        return DATABASE_URL;
    }

    /**
     * returns the amount of time in seconds that a cookie based session is
     * valid for, a value of 0 or less means the session will never expire on
     * its own
     *
     * @return
     */
    public static int getSESSION_TIMEOUT_VALUE()
    {
        return SESSION_TIMEOUT_VALUE;
    }

    public static String getPROJECT_NAME()
    {
        return PROJECT_NAME;
    }

    public static String getWEB_PAGES_DIRECTORY()
    {
        return WEB_PAGES_DIRECTORY;
    }

    public static String getFIRST_LOGIN_SERVLET()
    {
        return FIRST_LOGIN_SERVLET;
    }

    public static String getLOGIN_PAGE()
    {
        return LOGIN_PAGE;
    }

    public static String getMAIN_PAGE()
    {
        return MAIN_PAGE;
    }

    public static Map<String, String> getFRIENDLY_VALUES_MAP()
    {
        return FRIENDLY_VALUES_MAP;
    }

}
