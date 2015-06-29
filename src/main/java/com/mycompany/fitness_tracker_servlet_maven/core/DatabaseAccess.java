/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class deals with accessing the database, the methods are largely self
 * explanatory. The accompanying SQL commands are stored in the class
 * GlobalSQLCommands
 *
 * @author max
 */
public class DatabaseAccess
{

    /**
     *
     * @param loginAttemptEmail
     * @return A Map object containing the users email and password or null if
     * they were not found
     */
    public static Map<String, String> getUserCredentials(String loginAttemptEmail)
    {
        System.out.println("DatabaseAccess: getting user credentials");
        Map output = new HashMap<>();
        ResultSet resultSet = null;
        String retrievedEmail = null;
        String retrievedPassword = null;
        PreparedStatement getEmailStatement = null;
        Connection databaseConnection = null;

        //if user exists
        if (DatabaseAccess.userAlreadyExistsCheck(loginAttemptEmail))
        {
            databaseConnection = DatabaseUtils.getDatabaseConnection();
            try
            {
                getEmailStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getGetusercredentialsSQL());
                getEmailStatement.setString(1, loginAttemptEmail);

                resultSet = getEmailStatement.executeQuery();
                resultSet.next();
                retrievedEmail = resultSet.getString("email");
                retrievedPassword = resultSet.getString("password");

            } catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                DatabaseUtils.closeConnections(databaseConnection, resultSet, getEmailStatement);
            }

            output.put(retrievedEmail, retrievedPassword);
            return output;
        } else //user dosent exist
        {
            return null;
        }
    }

    public static boolean addUser(String anEmail, String aPassword)
    {
        boolean userAdded;
        String hashedPassword = Security.hashPassword(aPassword);
        //if user already exists
        if (DatabaseAccess.userAlreadyExistsCheck(anEmail))
        {
            userAdded = false;
            System.out.println("DatabaseAccess: user already exists, no action taken");
        } else //if user does not exist
        {
            System.out.println("DatabaseAccess: adding user");
            PreparedStatement addUserStatement = null;
            Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
            try
            {
                addUserStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getAdduserSQL());
                addUserStatement.setString(1, anEmail);
                addUserStatement.setString(2, hashedPassword);
                addUserStatement.executeUpdate();
            } catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                DatabaseUtils.closeConnections(databaseConnection, null, addUserStatement);
            }
            userAdded = true;
        }

        return userAdded;
    }

    public static boolean userAlreadyExistsCheck(String anEmail)
    {
        System.out.println("DatabaseAccess: checking if the user is already in the database");
        boolean userAlreadyExists = true;
        PreparedStatement checkUserStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            checkUserStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getCheckforuserSQL());
            checkUserStatement.setString(1, anEmail);
            resultSet = checkUserStatement.executeQuery();

            if (resultSet.next())
            {
                System.out.println("DatabaseAccess: user already in the database");
                userAlreadyExists = true;
            } else
            {
                System.out.println("DatabaseAccess: user is not in the database");
                userAlreadyExists = false;
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, checkUserStatement);
        }

        return userAlreadyExists;
    }

    public static Integer getuser_id(String anEmail)
    {
        System.out.println("DatabaseAccess: getting user_id");
        Integer userID = null;
        PreparedStatement getuserIDStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            getuserIDStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getGetid_userSQL());
            getuserIDStatement.setString(1, anEmail);
            resultSet = getuserIDStatement.executeQuery();

            if (resultSet.next())
            {
                userID = resultSet.getInt(1);
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, getuserIDStatement);
        }
        return userID;
    }

    public static String getCustomFoods(Integer aUserID)
    {
        System.out.println("DatabaseAccess: getting custom foods for user " + aUserID);
        PreparedStatement getCustomFoodsStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        String JSONObject = null;

        try
        {
            getCustomFoodsStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getGetcustomfoodsSQL());
            getCustomFoodsStatement.setInt(1, aUserID);
            resultSet = getCustomFoodsStatement.executeQuery();
            JSONObject = convertResultSetToJSON(resultSet);

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, getCustomFoodsStatement);
        }
        return JSONObject;
    }

    public static boolean removeCustomFood(Integer id_food)
    {
        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: removing custom food number " + id_food);
        PreparedStatement removeCustomFoodStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            removeCustomFoodStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getRemovecustomfoodSQL());
            removeCustomFoodStatement.setInt(1, id_food);
            returnValue = removeCustomFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, removeCustomFoodStatement);
        }

        //0 if nothing was deleted from database, the rowcount is returned if something was deleted
        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

    private static String convertResultSetToJSON(ResultSet aResultSet) throws SQLException
    {
        //turn resultset into arraylist with maps in iterator
        //each map represents a single row
        ResultSetMetaData resultSetMetaData = aResultSet.getMetaData();
        List mainList = new ArrayList<>();
        Map currentRecord;
        int columnCount = resultSetMetaData.getColumnCount();
        int currentColumn;
        while (aResultSet.next())
        {
            currentColumn = 1;
            currentRecord = new HashMap();

            while (currentColumn <= columnCount)
            {
                currentRecord.put(resultSetMetaData.getColumnName(currentColumn), aResultSet.getString(currentColumn));
                currentColumn++;
            }
            mainList.add(currentRecord);
        }

        //turn the arraylist with maps into a JSON object using googles GSON
        Gson gson = new Gson();

        //use below if you want nulls to appear e.g protein:50, carbohydrate:null
        //Gson gson = new GsonBuilder().serializeNulls().create();
        String JSONObject = gson.toJson(mainList);

        return JSONObject;
    }

    public static boolean addCustomFood(JsonObject jsonObject)
    {
        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: adding custom food" + jsonObject);
        PreparedStatement addCustomFoodStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            addCustomFoodStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getAddcustomfoodSQL());

            /**
             * it is possible some attributes were not entered by the client,
             * e.g they may record the foodname and the calories but omit the
             * protein, carbohydrate and fat values, in this case we set
             * java.sql.Types.NULL because the SQL query "INSERT INTO
             * customfoodstable
             * (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES
             * (?,?,?,?,?,?)" is expecting 6 values
             */
            if (jsonObject.has("id_user"))
            {
                addCustomFoodStatement.setInt(1, jsonObject.get("id_user").getAsInt());
            } else
            {
                addCustomFoodStatement.setNull(1, java.sql.Types.NULL);
            }
            if (jsonObject.has("foodname"))
            {
                addCustomFoodStatement.setString(2, jsonObject.get("foodname").getAsString());
            } else
            {
                addCustomFoodStatement.setNull(2, java.sql.Types.NULL);
            }
            if (jsonObject.has("protein"))
            {
                addCustomFoodStatement.setInt(3, jsonObject.get("protein").getAsInt());
            } else
            {
                addCustomFoodStatement.setNull(3, java.sql.Types.NULL);
            }
            if (jsonObject.has("carbohydrate"))
            {
                addCustomFoodStatement.setInt(4, jsonObject.get("carbohydrate").getAsInt());
            } else
            {
                addCustomFoodStatement.setNull(4, java.sql.Types.NULL);
            }
            if (jsonObject.has("fat"))
            {
                addCustomFoodStatement.setInt(5, jsonObject.get("fat").getAsInt());
            } else
            {
                addCustomFoodStatement.setNull(5, java.sql.Types.NULL);
            }
            if (jsonObject.has("calorie"))
            {
                addCustomFoodStatement.setInt(6, jsonObject.get("calorie").getAsInt());
            } else
            {
                addCustomFoodStatement.setNull(6, java.sql.Types.NULL);
            }

            returnValue = addCustomFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL ERROR CODE " + ex.getSQLState());
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, addCustomFoodStatement);
        }

        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

    public static boolean editCustomFood(JsonObject jsonObject)
    {
        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: editing custom food" + jsonObject);
        PreparedStatement editCustomFoodStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            editCustomFoodStatement = databaseConnection.prepareStatement(GlobalSQLCommands.getEditcustomfoodSQL());

            /**
             * it is possible some attributes were not entered by the client,
             * e.g they may record the foodname and the calories but omit the
             * protein, carbohydrate and fat values, in this case we set
             * java.sql.Types.NULL because the SQL query "INSERT INTO
             * customfoodstable
             * (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES
             * (?,?,?,?,?,?)" is expecting 6 values
             */
            if (jsonObject.has("foodname"))
            {
                editCustomFoodStatement.setString(1, jsonObject.get("foodname").getAsString());
            } else
            {
                editCustomFoodStatement.setNull(1, java.sql.Types.NULL);
            }
            if (jsonObject.has("protein"))
            {
                editCustomFoodStatement.setInt(2, jsonObject.get("protein").getAsInt());
            } else
            {
                editCustomFoodStatement.setNull(2, java.sql.Types.NULL);
            }
            if (jsonObject.has("carbohydrate"))
            {
                editCustomFoodStatement.setInt(3, jsonObject.get("carbohydrate").getAsInt());
            } else
            {
                editCustomFoodStatement.setNull(3, java.sql.Types.NULL);
            }
            if (jsonObject.has("fat"))
            {
                editCustomFoodStatement.setInt(4, jsonObject.get("fat").getAsInt());
            } else
            {
                editCustomFoodStatement.setNull(4, java.sql.Types.NULL);
            }
            if (jsonObject.has("calorie"))
            {
                editCustomFoodStatement.setInt(5, jsonObject.get("calorie").getAsInt());
            } else
            {
                editCustomFoodStatement.setNull(5, java.sql.Types.NULL);
            }

            editCustomFoodStatement.setInt(6, jsonObject.get("id_food").getAsInt());

            System.out.println(editCustomFoodStatement.toString());
            returnValue = editCustomFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL ERROR CODE " + ex.getSQLState());
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, editCustomFoodStatement);
        }

        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

}
