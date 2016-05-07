/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.serverAPI;

/**
 *
 * @author max
 */
public enum Request
{    
    FRONTCONTROLLER,
    LOGIN_REQUEST,
    LOGIN_PAGE_REQUEST,
    LOGOUT_REQUEST,
    MAIN_PAGE_REQUEST,
    CREATE_ACCOUNT_REQUEST,
    CREATE_ACCOUNT_PAGE_REQUEST,
    WORKOUT_LOG_PAGE_REQUEST,
    CUSTOM_FOODS_PAGE_REQUEST,
    MY_STATS_PAGE_REQUEST,
    FORGOT_PASSWORD_PAGE_REQUEST,
    FORGOT_PASSWORD_EMAIL_REQUEST,
    CHANGE_PASSWORD_PAGE_REQUEST,
    CHANGE_PASSWORD_REQUEST,
    CHANGE_EMAIL_REQUEST,
    SETTINGS_PAGE_REQUEST,
    DELETE_ACCOUNT_REQUEST,
    GET_CUSTOM_FOOD_LIST,
    DELETE_CUSTOM_FOOD,
    CREATE_CUSTOM_FOOD,
    EDIT_CUSTOM_FOOD,
    ADD_EATEN_FOOD,
    GET_EATEN_FOOD_LIST,
    REMOVE_EATEN_FOOD,
    SEARCH_FOR_FOOD,
    MODIFY_SELECTED_ATTRIBUTES,
    GET_VIEWABLE_ATTRIBUTES,
    GET_FRIENDLY_NAMES,
    MODIFY_USER_STATS,
    GET_USER_STATS,
    GET_IDENTIFIER_TOKEN_EMAIL,
    GET_SERVER_API
}
