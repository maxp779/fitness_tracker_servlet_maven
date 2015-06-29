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
public class GlobalSQLCommands
{
    private static final String checkforuserSQL = "SELECT email FROM usertable WHERE email = ?";
    private static final String getusercredentialsSQL = "SELECT email,password FROM usertable WHERE email = ?";
    private static final String adduserSQL = "INSERT INTO usertable (email,password) VALUES (?,?)";
    
    
    
    private static final String getid_userSQL = "SELECT id_user FROM usertable WHERE email = ?";
    private static final String getcustomfoodsSQL = "SELECT * FROM customfoodstable WHERE id_user = ?";
    private static final String removecustomfoodSQL ="DELETE FROM customfoodstable WHERE id_food= ?";
    private static final String addcustomfoodSQL ="INSERT INTO customfoodstable (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES (?,?,?,?,?,?)";
    private static final String editcustomfoodSQL ="UPDATE customfoodstable SET foodname=?,protein=?,carbohydrate=?,fat=?,calorie=? WHERE id_food=?";
    
    
    public static String getEditcustomfoodSQL()
    {
        return editcustomfoodSQL;
    }
    
    public static String getAddcustomfoodSQL()
    {
        return addcustomfoodSQL;
    }
    
    public static String getCheckforuserSQL()
    {
        return checkforuserSQL;
    }

    public static String getGetusercredentialsSQL()
    {
        return getusercredentialsSQL;
    }

    public static String getAdduserSQL()
    {
        return adduserSQL;
    }

    public static String getGetid_userSQL()
    {
        return getid_userSQL;
    }

    public static String getGetcustomfoodsSQL()
    {
        return getcustomfoodsSQL;
    }

    public static String getRemovecustomfoodSQL()
    {
        return removecustomfoodSQL;
    }


    


    
    
}
