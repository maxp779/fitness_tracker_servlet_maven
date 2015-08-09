/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
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

    //SQL Commands
    private static final String checkforuserSQL = "SELECT email FROM usertable WHERE email = ?";
    private static final String getusercredentialsSQL = "SELECT email,password FROM usertable WHERE email = ?";
    private static final String adduserSQL = "INSERT INTO usertable (email,password) VALUES (?,?)";
    private static final String getid_userSQL = "SELECT id_user FROM usertable WHERE email = ?";
    private static final String getcustomfoodlistSQL = "SELECT * FROM customfoodtable WHERE id_user = ?";
    private static final String getfoodeatenlistSQL = "SELECT * FROM eatenfoodtable WHERE id_user = ? AND timestamp >= ? AND timestamp < ?";
    private static final String removecustomfoodSQL = "DELETE FROM customfoodtable WHERE id_customfood= ?";
    private static final String removeeatenfoodSQL = "DELETE FROM eatenfoodtable WHERE id_eatenfood= ?";
    //private static final String addcustomfoodSQL = "INSERT INTO customfoodtable (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES (?,?,?,?,?,?)";
    //private static final String addeatenfoodSQL = "INSERT INTO eatenfoodtable (id_user,foodname,protein,carbohydrate,fat,calorie,timestamp) VALUES (?,?,?,?,?,?,?)";
    //private static final String editcustomfoodSQL = "UPDATE customfoodtable SET foodname=?,protein=?,carbohydrate=?,fat=?,calorie=? WHERE id_customfood=?";
    private static final String searchForFoodSQL = "SELECT * FROM searchablefoodtable WHERE FOODNAME LIKE ?;";
    private static final String setupFoodAttributesSQL = "INSERT INTO selectedattributestable (id_user) VALUES (?)";
//    private static final String modifyFoodAttributesSQL = "UPDATE selectedattributestable SET foodcode=?,foodname=?,foodnameoriginal=?,description=?,foodgroup=?,previous=?,foodreferences=?,footnote=?,water=?,"
//            + "totnit=?,protein=?,fat=?,carbohydrate=?,calorie=?,kj=?,star=?,oligo=?,totsug=?,gluc=?,galact=?,fruct=?,sucr=?,malt=?,lact=?,alco=?,engfib=?,aoacfib=?,satfac=?,"
//            + "satfod=?,totn6pfac=?,totn6pfod=?,totn3pfac=?,totn3pfod=?,monofacc=?,monofodc=?,monofac=?,monofod=?,polyfacc=?,polyfodc=?,polyfac=?,polyfod=?,satfacx6=?,satfodx6=?,"
//            + "totbrfac=?,totbrfod=?,factrans=?,fodtrans=?,chol=?,weight=?,sodium=? WHERE id_user=?";
    private static final String getSelectedAttributesSQL = "SELECT * FROM selectedattributestable WHERE id_user = ?";

    //Supported food attributes, this omits items that are added automatically
    private static final List<String> supportedAttributeList = (Arrays.asList("foodcode", "foodname", "foodnameoriginal", "description", "foodgroup", "previous", "foodreferences", "footnote", "water",
            "totnit", "protein", "fat", "carbohydrate", "calorie", "kj", "star", "oligo", "totsug", "gluc", "galact", "fruct", "sucr", "malt", "lact", "alco", "engfib", "aoacfib", "satfac",
            "satfod", "totn6pfac", "totn6pfod", "totn3pfac", "totn3pfod", "monofacc", "monofodc", "monofac", "monofod", "polyfacc", "polyfodc", "polyfac", "polyfod", "satfacx6", "satfodx6",
            "totbrfac", "totbrfod", "factrans", "fodtrans", "chol", "weight", "sodium"));

    private static final List<String> varcharAttributeList = (Arrays.asList("foodcode", "foodname", "foodnameoriginal", "description", "foodgroup", "previous", "foodreferences", "footnote"));
    private static final List<String> integerAttributeList = (Arrays.asList("calorie", "kj", "weight", "id_user"));

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
                getEmailStatement = databaseConnection.prepareStatement(getusercredentialsSQL);
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
                addUserStatement = databaseConnection.prepareStatement(adduserSQL);
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

            setupSelectedAttributes(getid_user(anEmail));
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
            checkUserStatement = databaseConnection.prepareStatement(checkforuserSQL);
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

    public static Integer getid_user(String anEmail)
    {
        System.out.println("DatabaseAccess: getting id_user");
        Integer userID = null;
        PreparedStatement getuserIDStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            getuserIDStatement = databaseConnection.prepareStatement(getid_userSQL);
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

    public static String getCustomFoodList(Integer aUserID)
    {
        System.out.println("DatabaseAccess: getting custom food list for user " + aUserID);
        PreparedStatement getCustomFoodListStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        String JSONObject = null;

        try
        {
            getCustomFoodListStatement = databaseConnection.prepareStatement(getcustomfoodlistSQL);
            getCustomFoodListStatement.setInt(1, aUserID);
            resultSet = getCustomFoodListStatement.executeQuery();
            JSONObject = convertResultSetToJSONArray(resultSet);

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, getCustomFoodListStatement);
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
            removeCustomFoodStatement = databaseConnection.prepareStatement(removecustomfoodSQL);
            removeCustomFoodStatement.setInt(1, id_food);
            returnValue = removeCustomFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, removeCustomFoodStatement);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

    private static String convertResultSetToJSONArray(ResultSet aResultSet) throws SQLException
    {
        //turn resultset into arraylist with maps in iterator
        //each attributeMap represents a single row
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

//    private static String convertResultSetToJSON(ResultSet aResultSet) throws SQLException
//    {
//        //turn resultset into arraylist with maps in iterator
//        //each attributeMap represents a single row
//        ResultSetMetaData resultSetMetaData = aResultSet.getMetaData();
//        //List mainList = new ArrayList<>();
//        Map currentRecord = new HashMap();
//        int columnCount = resultSetMetaData.getColumnCount();
//        int currentColumn;
//        while (aResultSet.next())
//        {
//            currentColumn = 1;
//            //currentRecord = new HashMap();
//
//            while (currentColumn <= columnCount)
//            {
//                currentRecord.put(resultSetMetaData.getColumnName(currentColumn), aResultSet.getString(currentColumn));
//                currentColumn++;
//            }
//            //mainList.add(currentRecord);
//        }
//
//        //turn the arraylist with maps into a JSON object using googles GSON
//        Gson gson = new Gson();
//
//        //use below if you want nulls to appear e.g protein:50, carbohydrate:null
//        //Gson gson = new GsonBuilder().serializeNulls().create();
//        String JSONObject = gson.toJson(currentRecord);
//
//        return JSONObject;
//    }
    public static boolean addCustomFood(JsonObject jsonObject, Integer id_user)
    {
        System.out.println("DatabaseAccess: adding custom food" + jsonObject);
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<LinkedHashMap<String, String>>()
        {
        }.getType();
        Map<String, String> foodMap = gson.fromJson(jsonObject, stringStringMap);

        //create SQL query e.g
        //INSERT INTO customfoodtable (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES (?,?,?,?,?,?)
        StringBuilder addCustomFoodSQL = new StringBuilder();
        addCustomFoodSQL.append("INSERT INTO customfoodtable ");

        StringBuilder addCustomFoodColumns = new StringBuilder("(");

        StringBuilder addCustomFoodValues = new StringBuilder("VALUES (");

        for (int count = 0; count < supportedAttributeList.size(); count++)
        {
            String currentAttribute = supportedAttributeList.get(count);

            addCustomFoodColumns.append(currentAttribute);
            
            if(!"id_user".equals(currentAttribute))
            {
            if (varcharAttributeList.contains(currentAttribute))
            {
                addCustomFoodValues.append("'").append(foodMap.get(currentAttribute)).append("'");
            } else
            {
                addCustomFoodValues.append(foodMap.get(currentAttribute));
            }
            }

            //if not at the last attribute put a comma to separate it from the next attribute
            if (count != supportedAttributeList.size() - 1)
            {
                addCustomFoodColumns.append(",");
                addCustomFoodValues.append(",");
            } else
            {
                addCustomFoodColumns.append(",id_user)");
                addCustomFoodValues.append(",").append(id_user).append(")");
            }
        }

        addCustomFoodSQL.append(addCustomFoodColumns);
        addCustomFoodSQL.append(addCustomFoodValues);
        String addCustomFoodSQLString = addCustomFoodSQL.toString(); //finished SQL query

        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: SQL query is:" + addCustomFoodSQLString);
        PreparedStatement addCustomFoodStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            addCustomFoodStatement = databaseConnection.prepareStatement(addCustomFoodSQLString);

            /**
             * it is possible some attributes were not entered by the client,
             * e.g they may record the foodname and the calories but omit the
             * protein, carbohydrate and fat values, in this case we set
             * java.sql.Types.NULL because the SQL query "INSERT INTO
             * eatenfoodtable
             * (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES
             * (?,?,?,?,?,?)" is expecting 6 values
             */
//            if (jsonObject.has("id_user"))
//            {
//                addEatenFoodStatement.setInt(1, jsonObject.get("id_user").getAsInt());
//            } else
//            {
//                addEatenFoodStatement.setNull(1, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("foodname"))
//            {
//                addEatenFoodStatement.setString(2, jsonObject.get("foodname").getAsString());
//            } else
//            {
//                addEatenFoodStatement.setNull(2, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("protein"))
//            {
//                addEatenFoodStatement.setFloat(3, jsonObject.get("protein").getAsFloat());
//            } else
//            {
//                addEatenFoodStatement.setNull(3, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("carbohydrate"))
//            {
//                addEatenFoodStatement.setFloat(4, jsonObject.get("carbohydrate").getAsFloat());
//            } else
//            {
//                addEatenFoodStatement.setNull(4, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("fat"))
//            {
//                addEatenFoodStatement.setFloat(5, jsonObject.get("fat").getAsFloat());
//            } else
//            {
//                addEatenFoodStatement.setNull(5, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("calorie"))
//            {
//                addEatenFoodStatement.setInt(6, jsonObject.get("calorie").getAsInt());
//            } else
//            {
//                addEatenFoodStatement.setNull(6, java.sql.Types.NULL);
//            }
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

        // --------------------------
//        boolean output = false;
//        int returnValue = 0;
//        System.out.println("DatabaseAccess: adding custom food" + jsonObject);
//        PreparedStatement addCustomFoodStatement = null;
//        ResultSet resultSet = null;
//        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
//
//        try
//        {
//            addCustomFoodStatement = databaseConnection.prepareStatement(addcustomfoodSQL);
//
//            /**
//             * it is possible some attributes were not entered by the client,
//             * e.g they may record the foodname and the calories but omit the
//             * protein, carbohydrate and fat values, in this case we set
//             * java.sql.Types.NULL because the SQL query "INSERT INTO
//             * customfoodstable
//             * (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES
//             * (?,?,?,?,?,?)" is expecting 6 values
//             */
//            if (jsonObject.has("id_user"))
//            {
//                addCustomFoodStatement.setInt(1, jsonObject.get("id_user").getAsInt());
//            } else
//            {
//                addCustomFoodStatement.setNull(1, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("foodname"))
//            {
//                addCustomFoodStatement.setString(2, jsonObject.get("foodname").getAsString());
//            } else
//            {
//                addCustomFoodStatement.setNull(2, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("protein"))
//            {
//                addCustomFoodStatement.setInt(3, jsonObject.get("protein").getAsInt());
//            } else
//            {
//                addCustomFoodStatement.setNull(3, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("carbohydrate"))
//            {
//                addCustomFoodStatement.setInt(4, jsonObject.get("carbohydrate").getAsInt());
//            } else
//            {
//                addCustomFoodStatement.setNull(4, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("fat"))
//            {
//                addCustomFoodStatement.setInt(5, jsonObject.get("fat").getAsInt());
//            } else
//            {
//                addCustomFoodStatement.setNull(5, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("calorie"))
//            {
//                addCustomFoodStatement.setInt(6, jsonObject.get("calorie").getAsInt());
//            } else
//            {
//                addCustomFoodStatement.setNull(6, java.sql.Types.NULL);
//            }
//
//            returnValue = addCustomFoodStatement.executeUpdate();
//
//        } catch (SQLException ex)
//        {
//            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("SQL ERROR CODE " + ex.getSQLState());
//        } finally
//        {
//            DatabaseUtils.closeConnections(databaseConnection, resultSet, addCustomFoodStatement);
//        }
//        
//        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
//        if (returnValue != 0)
//        {
//            output = true;
//        }
//
//        return output;
    }

    public static boolean editCustomFood(JsonObject jsonObject, Integer id_user)
    {

        System.out.println("DatabaseAccess: editing custom food" + jsonObject);
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<LinkedHashMap<String, String>>()
        {
        }.getType();
        Map<String, String> foodMap = gson.fromJson(jsonObject, stringStringMap);
        
        //create SQL query e.g
        //UPDATE customfoodtable SET foodname=?,protein=?,carbohydrate=?,fat=?,calorie=? WHERE id_customfood=?
        StringBuilder editCustomFoodSQL = new StringBuilder();
        editCustomFoodSQL.append("UPDATE customfoodtable SET ");

        for (int count = 0; count < supportedAttributeList.size(); count++)
        {
            String currentAttribute = supportedAttributeList.get(count);

            //editCustomFoodSQL.append(currentAttribute).append("=").append(foodMap.get(currentAttribute));
            if (!"id_user".equals(currentAttribute))
            {
                if (varcharAttributeList.contains(currentAttribute))
                {
                    //editCustomFoodSQL.append("'").append(foodMap.get(currentAttribute)).append("'");
                    editCustomFoodSQL.append(currentAttribute).append("=").append("'").append(foodMap.get(currentAttribute)).append("'");
                } else
                {
                    editCustomFoodSQL.append(currentAttribute).append("=").append(foodMap.get(currentAttribute));
                }
            }

            //if not at the last attribute put a comma to separate it from the next attribute
            if (count != supportedAttributeList.size() - 1)
            {
                editCustomFoodSQL.append(",");
            } else
            {
                editCustomFoodSQL.append(",id_user=").append(id_user);
                editCustomFoodSQL.append(" WHERE id_customfood=").append(foodMap.get("id_customfood"));
            }
        }

        String editCustomFoodSQLString = editCustomFoodSQL.toString(); //finished SQL query

        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: SQL query is:" + editCustomFoodSQLString);
        PreparedStatement editCustomFoodStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            editCustomFoodStatement = databaseConnection.prepareStatement(editCustomFoodSQLString);
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

//        boolean output = false;
//        int returnValue = 0;
//        System.out.println("DatabaseAccess: editing custom food" + jsonObject);
//        PreparedStatement editCustomFoodStatement = null;
//        ResultSet resultSet = null;
//        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
//
//        try
//        {
//            editCustomFoodStatement = databaseConnection.prepareStatement(editcustomfoodSQL);
//
//            /**
//             * it is possible some attributes were not entered by the client,
//             * e.g they may record the foodname and the calories but omit the
//             * protein, carbohydrate and fat values, in this case we set
//             * java.sql.Types.NULL because the SQL query "INSERT INTO
//             * customfoodstable
//             * (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES
//             * (?,?,?,?,?,?)" is expecting 6 values
//             */
//            if (jsonObject.has("foodname"))
//            {
//                editCustomFoodStatement.setString(1, jsonObject.get("foodname").getAsString());
//            } else
//            {
//                editCustomFoodStatement.setNull(1, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("protein"))
//            {
//                editCustomFoodStatement.setFloat(2, jsonObject.get("protein").getAsFloat());
//            } else
//            {
//                editCustomFoodStatement.setNull(2, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("carbohydrate"))
//            {
//                editCustomFoodStatement.setFloat(3, jsonObject.get("carbohydrate").getAsFloat());
//            } else
//            {
//                editCustomFoodStatement.setNull(3, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("fat"))
//            {
//                editCustomFoodStatement.setFloat(4, jsonObject.get("fat").getAsFloat());
//            } else
//            {
//                editCustomFoodStatement.setNull(4, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("calorie"))
//            {
//                editCustomFoodStatement.setInt(5, jsonObject.get("calorie").getAsInt());
//            } else
//            {
//                editCustomFoodStatement.setNull(5, java.sql.Types.NULL);
//            }
//
//            editCustomFoodStatement.setInt(6, jsonObject.get("id_customfood").getAsInt());
//
//            System.out.println(editCustomFoodStatement.toString());
//            returnValue = editCustomFoodStatement.executeUpdate();
//
//        } catch (SQLException ex)
//        {
//            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
//            System.out.println("SQL ERROR CODE " + ex.getSQLState());
//        } finally
//        {
//            DatabaseUtils.closeConnections(databaseConnection, resultSet, editCustomFoodStatement);
//        }
//
//        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
//        if (returnValue != 0)
//        {
//            output = true;
//        }
//
//        return output;
    }

    public static String getEatenFoodList(Integer id_user, Timestamp timestamp)
    {
        System.out.println("DatabaseAccess: getting food eaten list for user " + id_user);
        PreparedStatement getFoodEatenStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        String JSONObject = null;

        //get start of current day and start of next day both in UNIX time
        //this is to retrieve all foods within a single day, so between those two times
        LocalDateTime toLocalDateTime = timestamp.toLocalDateTime();
        LocalDate toLocalDate = toLocalDateTime.toLocalDate();
        LocalDateTime atStartOfDay = toLocalDate.atStartOfDay();
        long currentDayStart = atStartOfDay.toEpochSecond(ZoneOffset.UTC);
        long nextDayStart = atStartOfDay.plusDays(1L).toEpochSecond(ZoneOffset.UTC);
        currentDayStart = currentDayStart * 1000; //convert to milliseconds
        nextDayStart = nextDayStart * 1000; //convert to milliseconds
        System.out.println("start of selected day " + currentDayStart);
        System.out.println("start of next day " + nextDayStart);

        Timestamp currentDayStartTimestamp = new Timestamp(currentDayStart);
        Timestamp nextDayStartTimestamp = new Timestamp(nextDayStart);

        try
        {
            getFoodEatenStatement = databaseConnection.prepareStatement(getfoodeatenlistSQL);
            getFoodEatenStatement.setInt(1, id_user);
            getFoodEatenStatement.setTimestamp(2, currentDayStartTimestamp);
            getFoodEatenStatement.setTimestamp(3, nextDayStartTimestamp);
            resultSet = getFoodEatenStatement.executeQuery();
            JSONObject = convertResultSetToJSONArray(resultSet);

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, getFoodEatenStatement);
        }
        return JSONObject;
    }

    public static boolean addEatenFood(JsonObject jsonObject, Integer id_user)
    {
        System.out.println("DatabaseAccess: adding eaten food" + jsonObject);
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<LinkedHashMap<String, String>>()
        {
        }.getType();
        Map<String, String> foodMap = gson.fromJson(jsonObject, stringStringMap);

        //create SQL query e.g
        //INSERT INTO eatenfoodtable (id_user,foodname,protein,carbohydrate,fat,calorie,timestamp) VALUES (?,?,?,?,?,?,?)
        StringBuilder addEatenFoodSQL = new StringBuilder();
        addEatenFoodSQL.append("INSERT INTO eatenfoodtable ");

        StringBuilder addEatenFoodColumns = new StringBuilder("(");

        StringBuilder addEatenFoodValues = new StringBuilder("VALUES (");

        for (int count = 0; count < supportedAttributeList.size(); count++)
        {
            String currentAttribute = supportedAttributeList.get(count);

            addEatenFoodColumns.append(currentAttribute);

            if (varcharAttributeList.contains(currentAttribute))
            {
                addEatenFoodValues.append("'").append(foodMap.get(currentAttribute)).append("'");
            } else
            {
                addEatenFoodValues.append(foodMap.get(currentAttribute));
            }

            //if not at the last attribute put a comma to separate it from the next attribute
            if (count != supportedAttributeList.size() - 1)
            {
                addEatenFoodColumns.append(",");
                addEatenFoodValues.append(",");
            } else //last attribute added, stick a timestamp and id_user on the end
            {
                addEatenFoodColumns.append(",timestamp,id_user)");
                JsonElement UNIXtimeElement = jsonObject.get("UNIXtime");
                Timestamp timestamp = new Timestamp(UNIXtimeElement.getAsLong());
                Long timestampLong = timestamp.getTime(); //get unix time in milliseconds
                timestampLong = timestampLong / 1000; //convert to seconds
                addEatenFoodValues.append(",").append("to_timestamp(").append(timestampLong).append(")").append(id_user).append(")");
            }
        }

        addEatenFoodSQL.append(addEatenFoodColumns);
        addEatenFoodSQL.append(addEatenFoodValues);
        String addEatenFoodSQLString = addEatenFoodSQL.toString(); //finished SQL query

        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: SQL query is:" + addEatenFoodSQLString);
        PreparedStatement addEatenFoodStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            addEatenFoodStatement = databaseConnection.prepareStatement(addEatenFoodSQLString);

            /**
             * it is possible some attributes were not entered by the client,
             * e.g they may record the foodname and the calories but omit the
             * protein, carbohydrate and fat values, in this case we set
             * java.sql.Types.NULL because the SQL query "INSERT INTO
             * eatenfoodtable
             * (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES
             * (?,?,?,?,?,?)" is expecting 6 values
             */
//            if (jsonObject.has("id_user"))
//            {
//                addEatenFoodStatement.setInt(1, jsonObject.get("id_user").getAsInt());
//            } else
//            {
//                addEatenFoodStatement.setNull(1, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("foodname"))
//            {
//                addEatenFoodStatement.setString(2, jsonObject.get("foodname").getAsString());
//            } else
//            {
//                addEatenFoodStatement.setNull(2, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("protein"))
//            {
//                addEatenFoodStatement.setFloat(3, jsonObject.get("protein").getAsFloat());
//            } else
//            {
//                addEatenFoodStatement.setNull(3, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("carbohydrate"))
//            {
//                addEatenFoodStatement.setFloat(4, jsonObject.get("carbohydrate").getAsFloat());
//            } else
//            {
//                addEatenFoodStatement.setNull(4, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("fat"))
//            {
//                addEatenFoodStatement.setFloat(5, jsonObject.get("fat").getAsFloat());
//            } else
//            {
//                addEatenFoodStatement.setNull(5, java.sql.Types.NULL);
//            }
//            if (jsonObject.has("calorie"))
//            {
//                addEatenFoodStatement.setInt(6, jsonObject.get("calorie").getAsInt());
//            } else
//            {
//                addEatenFoodStatement.setNull(6, java.sql.Types.NULL);
//            }
            returnValue = addEatenFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL ERROR CODE " + ex.getSQLState());
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, addEatenFoodStatement);
        }

        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

    public static boolean removeEatenFood(int id_eatenfood)
    {
        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: removing eaten food number " + id_eatenfood);
        PreparedStatement removeEatenFoodStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            removeEatenFoodStatement = databaseConnection.prepareStatement(removeeatenfoodSQL);
            removeEatenFoodStatement.setInt(1, id_eatenfood);
            returnValue = removeEatenFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, removeEatenFoodStatement);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

    public static String searchForFood(String food)
    {
        System.out.println("DatabaseAccess: searching for " + food);
        PreparedStatement searchForFood = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        String JSONObject = null;

        try
        {
            searchForFood = databaseConnection.prepareStatement(searchForFoodSQL);
            searchForFood.setString(1, food + "%"); //% indicates a wildcard
            resultSet = searchForFood.executeQuery();
            JSONObject = convertResultSetToJSONArray(resultSet);

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, searchForFood);
        }
        return JSONObject;
    }

    public static boolean setupSelectedAttributes(Integer id_user)
    {
        boolean output = false;
        int returnValue = 0;

        System.out.println("DatabaseAccess: setting up user attributes for user " + id_user);
        PreparedStatement setupFoodAttributesStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            setupFoodAttributesStatement = databaseConnection.prepareStatement(setupFoodAttributesSQL);
            setupFoodAttributesStatement.setInt(1, id_user);
            returnValue = setupFoodAttributesStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, setupFoodAttributesStatement);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

    public static boolean modifySelectedAttributes(JsonObject jsonObject, Integer id_user)
    {
        System.out.println("DatabaseAccess: modifying selected food attributes for user " + id_user);
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<LinkedHashMap<String, String>>()
        {
        }.getType();
        Map<String, String> attributeMap = gson.fromJson(jsonObject, stringStringMap);

        //change string attributes into boolean, "t" = Boolean.TRUE, "f" = BOOLEAN.FALSE
        Map<String, Boolean> attributeMapBoolean = new LinkedHashMap();
        Iterator it = attributeMap.entrySet().iterator();
        while (it.hasNext())
        {
            Map.Entry pair = (Map.Entry) it.next();
            String value = (String) pair.getValue();
            String key = (String) pair.getKey();

            if (!key.equals("id_user"))
            {
                if (value.equals("t"))
                {
                    attributeMapBoolean.put(key, Boolean.TRUE);
                } else
                {
                    attributeMapBoolean.put(key, Boolean.FALSE);
                }
            }
        }

        System.out.println("DatabaseAccess: new attributes are " + attributeMapBoolean);

        //create SQL query e.g
        //UPDATE selectedattributestable SET foodcode=?,foodname=?,foodnameoriginal=?,description=?,foodgroup=? WHERE id_user=?
        StringBuilder modifyFoodAttributesSQL = new StringBuilder();
        modifyFoodAttributesSQL.append("UPDATE selectedattributestable SET ");
        for (int count = 0; count < supportedAttributeList.size(); count++)
        {
            String currentAttribute = supportedAttributeList.get(count);

            if (currentAttribute != "id_user")
            {
                modifyFoodAttributesSQL.append(currentAttribute).append("=").append(attributeMapBoolean.get(currentAttribute));
            } else
            {
                modifyFoodAttributesSQL.append(currentAttribute).append("=").append(id_user);
            }

            //if not at the last attribute put a comma to separate it from the next attribute
            if (count != supportedAttributeList.size() - 1)
            {
                modifyFoodAttributesSQL.append(",");
            }
        }
        modifyFoodAttributesSQL.append(" WHERE id_user=").append(id_user);
        String modifyFoodAttributesSQLString = modifyFoodAttributesSQL.toString();
        System.out.println("DatabaseAccess: SQL query is:" + modifyFoodAttributesSQLString);

        PreparedStatement modifySelectedAttributesStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        boolean output = false;
        int returnValue = 0;
        try
        {
            //ALSO CHANGE DATABASE CL
            //TRY TO REJIG TO USE LINKEDHASHMAP AND A LOOP!! LINKEDHASHMAP PRESERVES INSERTION ORDER
            modifySelectedAttributesStatement = databaseConnection.prepareStatement(modifyFoodAttributesSQLString);
//            modifySelectedAttributesStatement.setBoolean(1, attributeMapBoolean.get("foodcode"));
//            modifySelectedAttributesStatement.setBoolean(2, attributeMapBoolean.get("foodname"));
//            modifySelectedAttributesStatement.setBoolean(3, attributeMapBoolean.get("foodnameoriginal"));
//            modifySelectedAttributesStatement.setBoolean(4, attributeMapBoolean.get("description"));
//            modifySelectedAttributesStatement.setBoolean(5, attributeMapBoolean.get("foodgroup"));
//            modifySelectedAttributesStatement.setBoolean(6, attributeMapBoolean.get("previous"));
//            modifySelectedAttributesStatement.setBoolean(7, attributeMapBoolean.get("foodreferences"));
//            modifySelectedAttributesStatement.setBoolean(8, attributeMapBoolean.get("footnote"));
//            modifySelectedAttributesStatement.setBoolean(9, attributeMapBoolean.get("water"));
//            modifySelectedAttributesStatement.setBoolean(10, attributeMapBoolean.get("totnit"));
//            modifySelectedAttributesStatement.setBoolean(11, attributeMapBoolean.get("protein"));
//            modifySelectedAttributesStatement.setBoolean(12, attributeMapBoolean.get("fat"));
//            modifySelectedAttributesStatement.setBoolean(13, attributeMapBoolean.get("carbohydrate"));
//            modifySelectedAttributesStatement.setBoolean(14, attributeMapBoolean.get("calorie"));
//            modifySelectedAttributesStatement.setBoolean(15, attributeMapBoolean.get("kj"));
//            modifySelectedAttributesStatement.setBoolean(16, attributeMapBoolean.get("star"));
//            modifySelectedAttributesStatement.setBoolean(17, attributeMapBoolean.get("oligo"));
//            modifySelectedAttributesStatement.setBoolean(18, attributeMapBoolean.get("totsug"));
//            modifySelectedAttributesStatement.setBoolean(19, attributeMapBoolean.get("gluc"));
//            modifySelectedAttributesStatement.setBoolean(20, attributeMapBoolean.get("galact"));
//            modifySelectedAttributesStatement.setBoolean(21, attributeMapBoolean.get("fruct"));
//            modifySelectedAttributesStatement.setBoolean(22, attributeMapBoolean.get("sucr"));
//            modifySelectedAttributesStatement.setBoolean(23, attributeMapBoolean.get("malt"));
//            modifySelectedAttributesStatement.setBoolean(24, attributeMapBoolean.get("lact"));
//            modifySelectedAttributesStatement.setBoolean(25, attributeMapBoolean.get("alco"));
//            modifySelectedAttributesStatement.setBoolean(26, attributeMapBoolean.get("engfib"));
//            modifySelectedAttributesStatement.setBoolean(27, attributeMapBoolean.get("aoacfib"));
//            modifySelectedAttributesStatement.setBoolean(28, attributeMapBoolean.get("satfac"));
//            modifySelectedAttributesStatement.setBoolean(29, attributeMapBoolean.get("satfod"));
//            modifySelectedAttributesStatement.setBoolean(30, attributeMapBoolean.get("totn6pfac"));
//            modifySelectedAttributesStatement.setBoolean(31, attributeMapBoolean.get("totn6pfod"));
//            modifySelectedAttributesStatement.setBoolean(32, attributeMapBoolean.get("totn3pfac"));
//            modifySelectedAttributesStatement.setBoolean(33, attributeMapBoolean.get("totn3pfod"));
//            modifySelectedAttributesStatement.setBoolean(34, attributeMapBoolean.get("monofacc"));
//            modifySelectedAttributesStatement.setBoolean(35, attributeMapBoolean.get("monofodc"));
//            modifySelectedAttributesStatement.setBoolean(36, attributeMapBoolean.get("monofac"));
//            modifySelectedAttributesStatement.setBoolean(37, attributeMapBoolean.get("monofod"));
//            modifySelectedAttributesStatement.setBoolean(38, attributeMapBoolean.get("polyfacc"));
//            modifySelectedAttributesStatement.setBoolean(39, attributeMapBoolean.get("polyfodc"));
//            modifySelectedAttributesStatement.setBoolean(40, attributeMapBoolean.get("polyfac"));
//            modifySelectedAttributesStatement.setBoolean(41, attributeMapBoolean.get("polyfod"));
//            modifySelectedAttributesStatement.setBoolean(42, attributeMapBoolean.get("satfacx6"));
//            modifySelectedAttributesStatement.setBoolean(43, attributeMapBoolean.get("satfodx6"));
//            modifySelectedAttributesStatement.setBoolean(44, attributeMapBoolean.get("totbrfac"));
//            modifySelectedAttributesStatement.setBoolean(45, attributeMapBoolean.get("totbrfod"));
//            modifySelectedAttributesStatement.setBoolean(46, attributeMapBoolean.get("factrans"));
//            modifySelectedAttributesStatement.setBoolean(47, attributeMapBoolean.get("fodtrans"));
//            modifySelectedAttributesStatement.setBoolean(48, attributeMapBoolean.get("chol"));
//            modifySelectedAttributesStatement.setBoolean(49, attributeMapBoolean.get("weight"));
//            modifySelectedAttributesStatement.setBoolean(50, attributeMapBoolean.get("sodium"));           

            //modifySelectedAttributesStatement.setInt(51, id_user);
            returnValue = modifySelectedAttributesStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, modifySelectedAttributesStatement);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        if (returnValue != 0)
        {
            output = true;
        }
        return output;
    }

    public static String getSelectedAttributeList(Integer aUserID)
    {
        System.out.println("DatabaseAccess: getting selected attribute list for user " + aUserID);
        PreparedStatement getSelectedAttributesListStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        String JSONObject = null;

        try
        {
            getSelectedAttributesListStatement = databaseConnection.prepareStatement(getSelectedAttributesSQL);
            getSelectedAttributesListStatement.setInt(1, aUserID);
            resultSet = getSelectedAttributesListStatement.executeQuery();
            JSONObject = convertResultSetToJSONArray(resultSet);

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, getSelectedAttributesListStatement);
        }
        return JSONObject;
    }

}
