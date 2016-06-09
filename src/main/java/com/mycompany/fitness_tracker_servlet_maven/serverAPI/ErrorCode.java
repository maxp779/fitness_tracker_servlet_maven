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
public enum ErrorCode
{
    PASSWORD_TOO_SHORT, 
    ACCOUNT_ALREADY_EXISTS,
    ACCOUNT_DOSENT_EXIST,
    FORGOT_PASSWORD_TOKEN_EXPIRED_OR_ALREADY_USED,
    PASSWORD_CHANGE_FAILED,
    AUTHENTICATION_FAILED,
    AUTHORIZATION_FAILED,
    CHANGE_EMAIL_FAILED,
    UPDATE_ATTRIBUTES_FAILED,
    ADD_CUSTOM_FOOD_FAILED,
    ADD_EATEN_FOOD_FAILED,
    DELETE_CUSTOM_FOOD_FAILED,
    EDIT_CUSTOM_FOOD_FAILED,
    SENDING_CLIENT_DATA_FAILED,
    GET_EATEN_FOOD_LIST_FAILED,
    EMAIL_NOT_FOUND,
    GET_USER_STATS_FAILED,
    GET_FOOD_ATTRIBUTES_FAILED,
    MODIFY_USER_STATS_FAILED,
    REMOVE_EATEN_FOOD_FAILED,
    SEARCH_FOR_FOOD_FAILED,
    GET_CUSTOM_FOOD_LIST_FAILED,
    GET_FRIENDLY_NAMES_LIST_FAILED,
    SENDING_EMAIL_FAILED,
    DATABASE_ERROR,
    LOGOUT_FAILED,
    GET_ALL_CLIENT_DATA_FAILED
    
}
