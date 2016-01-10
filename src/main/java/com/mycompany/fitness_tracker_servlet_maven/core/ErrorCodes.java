/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

/**
 *
 * @author max
 */
public class ErrorCodes
{

    private static final String PASSWORD_TOO_SHORT = "10";
    private static final String ACCOUNT_ALREADY_EXISTS = "11";
    private static final String ACCOUNT_DOSENT_EXIST = "12";
    private static final String FORGOT_PASSWORD_TOKEN_EXPIRED_OR_ALREADY_USED = "13";
    private static final String PASSWORD_CHANGE_FAILED = "14";
    private static final String ACCOUNT_OR_PASSWORD_INCORRECT = "15";
    private static final String USER_NOT_AUTHORIZED = "16";
    private static final String CHANGE_EMAIL_FAILED = "17";
    private static final String UPDATE_ATTRIBUTES_FAILED = "18";
    private static final String ADD_CUSTOM_FOOD_FAILED = "19";

    public static String getADD_CUSTOM_FOOD_FAILED()
    {
        return ADD_CUSTOM_FOOD_FAILED;
    }

    public static String getUPDATE_ATTRIBUTES_FAILED()
    {
        return UPDATE_ATTRIBUTES_FAILED;
    }

    public static String getCHANGE_EMAIL_FAILED()
    {
        return CHANGE_EMAIL_FAILED;
    }

    public static String getUSER_NOT_AUTHORIZED()
    {
        return USER_NOT_AUTHORIZED;
    }

    public static String getACCOUNT_OR_PASSWORD_INCORRECT()
    {
        return ACCOUNT_OR_PASSWORD_INCORRECT;
    }

    public static String getPASSWORD_CHANGE_FAILED()
    {
        return PASSWORD_CHANGE_FAILED;
    }

    public static String getFORGOT_PASSWORD_TOKEN_EXPIRED_OR_ALREADY_USED()
    {
        return FORGOT_PASSWORD_TOKEN_EXPIRED_OR_ALREADY_USED;
    }

    public static String getPASSWORD_TOO_SHORT()
    {
        return PASSWORD_TOO_SHORT;
    }

    public static String getACCOUNT_ALREADY_EXISTS()
    {
        return ACCOUNT_ALREADY_EXISTS;
    }

    public static String getACCOUNT_DOSENT_EXIST()
    {
        return ACCOUNT_DOSENT_EXIST;
    }

}
