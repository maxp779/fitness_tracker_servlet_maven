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
    private static final String setupUserStatsSQL = "INSERT INTO userstatstable (id_user) VALUES (?)";
//    private static final String modifyUserStatsSQL = "UPDATE selectedattributestable SET foodcode=?,foodname=?,foodnameoriginal=?,description=?,foodgroup=?,previous=?,foodreferences=?,footnote=?,water=?,"
//            + "totnit=?,protein=?,fat=?,carbohydrate=?,calorie=?,kj=?,star=?,oligo=?,totsug=?,gluc=?,galact=?,fruct=?,sucr=?,malt=?,lact=?,alco=?,engfib=?,aoacfib=?,satfac=?,"
//            + "satfod=?,totn6pfac=?,totn6pfod=?,totn3pfac=?,totn3pfod=?,monofacc=?,monofodc=?,monofac=?,monofod=?,polyfacc=?,polyfodc=?,polyfac=?,polyfod=?,satfacx6=?,satfodx6=?,"
//            + "totbrfac=?,totbrfod=?,factrans=?,fodtrans=?,chol=?,weight=?,sodium=? WHERE id_user=?";
    private static final String getSelectedAttributesSQL = "SELECT * FROM selectedattributestable WHERE id_user = ?";
    private static final String getuserstatsSQL = "SELECT * FROM userstatstable WHERE id_user = ?";

    //Supported food attributes, this omits items that are added automatically
    private static final List<String> supportedAttributeList = (Arrays.asList("foodcode", "foodname", "foodnameoriginal", "description", "foodgroup", "previous", "foodreferences", "footnote", "water",
            "totnit", "protein", "fat", "carbohydrate", "calorie", "kj", "star", "oligo", "totsug", "gluc", "galact", "fruct", "sucr", "malt", "lact", "alco", "engfib", "aoacfib", "satfac",
            "satfod", "totn6pfac", "totn6pfod", "totn3pfac", "totn3pfod", "monofacc", "monofodc", "monofac", "monofod", "polyfacc", "polyfodc", "polyfac", "polyfod", "satfacx6", "satfodx6",
            "totbrfac", "totbrfod", "factrans", "fodtrans", "chol", "weight", "sodium"));

    private static final List<String> varcharAttributeList = (Arrays.asList("foodcode", "foodname", "foodnameoriginal", "description", "foodgroup", "previous", "foodreferences", "footnote"));
    private static final List<String> integerAttributeList = (Arrays.asList("calorie", "kj", "weight", "id_user"));

//    private static final List<String> supportedUserStatsList = (Arrays.asList("idealprotein", "idealcarbohydrate", "idealfat", "TEE","goalTEE"));
    //Supported user stats
    private static final List<String> supportedUserStatsList = (Arrays.asList("weight", "height", "proteingoal", "carbohydrategoal", "fatgoal", "tee", "teegoal", "dateofbirth",
            "proteingoalpercentage", "carbohydratepercentage", "fatgoalpercentage", "gender", "activitylevel", "excerciseintensity", "excercisedaysperweek", "excerciseminutesperday"));

    /**
     *
     * @param loginAttemptEmail
     * @return A Map object containing the users email and password or null if
     * they were not found
     */
    public static Map<String, String> getUserCredentials(String loginAttemptEmail)
    {
        System.out.println("DatabaseAccess: getUserCredentials() for email " + loginAttemptEmail);
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
        System.out.println("DatabaseAccess: addUser() email " + anEmail);
        boolean userAdded = false;
        String hashedPassword = Security.hashPassword(aPassword);
        //if user already exists
        if (DatabaseAccess.userAlreadyExistsCheck(anEmail))
        {
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
                userAdded = true;

                //setup defaults for the new user
                int id_user = getid_user(anEmail);
                setupSelectedAttributes(id_user);
                setupUserStats(id_user);
            } catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            } finally
            {
                DatabaseUtils.closeConnections(databaseConnection, null, addUserStatement);
            }
        }
        return userAdded;
    }

    public static boolean userAlreadyExistsCheck(String anEmail)
    {
        System.out.println("DatabaseAccess: userAlreadyExistsCheck() for email" + anEmail);
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
        System.out.println("DatabaseAccess: getid_user() for email" + anEmail);
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
        System.out.println("DatabaseAccess: getCustomFoodList() " + aUserID);
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
        System.out.println("DatabaseAccess: removeCustomFood() " + id_food);
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
        System.out.println("DatabaseAccess: addCustomFood() " + jsonObject);
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

            if (!"id_user".equals(currentAttribute))
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

        System.out.println("DatabaseAccess: editCustomFood() " + jsonObject);
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
        System.out.println("DatabaseAccess: getEatenFoodList() for user " + id_user);
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
        System.out.println("DatabaseAccess: addEatenFood() " + jsonObject);
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
                addEatenFoodValues.append(",").append("to_timestamp(").append(timestampLong).append("),").append(id_user).append(")");
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
        System.out.println("DatabaseAccess: removeEatenFood() " + id_eatenfood);
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
        System.out.println("DatabaseAccess: searchForFood() " + food);
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

        System.out.println("DatabaseAccess: setupSelectedAttributes() for user " + id_user);
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
        System.out.println("DatabaseAccess: modifySelectedAttributes() for user " + id_user);
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
//            modifyUserStatsStatement.setBoolean(1, attributeMapBoolean.get("foodcode"));
//            modifyUserStatsStatement.setBoolean(2, attributeMapBoolean.get("foodname"));
//            modifyUserStatsStatement.setBoolean(3, attributeMapBoolean.get("foodnameoriginal"));
//            modifyUserStatsStatement.setBoolean(4, attributeMapBoolean.get("description"));
//            modifyUserStatsStatement.setBoolean(5, attributeMapBoolean.get("foodgroup"));
//            modifyUserStatsStatement.setBoolean(6, attributeMapBoolean.get("previous"));
//            modifyUserStatsStatement.setBoolean(7, attributeMapBoolean.get("foodreferences"));
//            modifyUserStatsStatement.setBoolean(8, attributeMapBoolean.get("footnote"));
//            modifyUserStatsStatement.setBoolean(9, attributeMapBoolean.get("water"));
//            modifyUserStatsStatement.setBoolean(10, attributeMapBoolean.get("totnit"));
//            modifyUserStatsStatement.setBoolean(11, attributeMapBoolean.get("protein"));
//            modifyUserStatsStatement.setBoolean(12, attributeMapBoolean.get("fat"));
//            modifyUserStatsStatement.setBoolean(13, attributeMapBoolean.get("carbohydrate"));
//            modifyUserStatsStatement.setBoolean(14, attributeMapBoolean.get("calorie"));
//            modifyUserStatsStatement.setBoolean(15, attributeMapBoolean.get("kj"));
//            modifyUserStatsStatement.setBoolean(16, attributeMapBoolean.get("star"));
//            modifyUserStatsStatement.setBoolean(17, attributeMapBoolean.get("oligo"));
//            modifyUserStatsStatement.setBoolean(18, attributeMapBoolean.get("totsug"));
//            modifyUserStatsStatement.setBoolean(19, attributeMapBoolean.get("gluc"));
//            modifyUserStatsStatement.setBoolean(20, attributeMapBoolean.get("galact"));
//            modifyUserStatsStatement.setBoolean(21, attributeMapBoolean.get("fruct"));
//            modifyUserStatsStatement.setBoolean(22, attributeMapBoolean.get("sucr"));
//            modifyUserStatsStatement.setBoolean(23, attributeMapBoolean.get("malt"));
//            modifyUserStatsStatement.setBoolean(24, attributeMapBoolean.get("lact"));
//            modifyUserStatsStatement.setBoolean(25, attributeMapBoolean.get("alco"));
//            modifyUserStatsStatement.setBoolean(26, attributeMapBoolean.get("engfib"));
//            modifyUserStatsStatement.setBoolean(27, attributeMapBoolean.get("aoacfib"));
//            modifyUserStatsStatement.setBoolean(28, attributeMapBoolean.get("satfac"));
//            modifyUserStatsStatement.setBoolean(29, attributeMapBoolean.get("satfod"));
//            modifyUserStatsStatement.setBoolean(30, attributeMapBoolean.get("totn6pfac"));
//            modifyUserStatsStatement.setBoolean(31, attributeMapBoolean.get("totn6pfod"));
//            modifyUserStatsStatement.setBoolean(32, attributeMapBoolean.get("totn3pfac"));
//            modifyUserStatsStatement.setBoolean(33, attributeMapBoolean.get("totn3pfod"));
//            modifyUserStatsStatement.setBoolean(34, attributeMapBoolean.get("monofacc"));
//            modifyUserStatsStatement.setBoolean(35, attributeMapBoolean.get("monofodc"));
//            modifyUserStatsStatement.setBoolean(36, attributeMapBoolean.get("monofac"));
//            modifyUserStatsStatement.setBoolean(37, attributeMapBoolean.get("monofod"));
//            modifyUserStatsStatement.setBoolean(38, attributeMapBoolean.get("polyfacc"));
//            modifyUserStatsStatement.setBoolean(39, attributeMapBoolean.get("polyfodc"));
//            modifyUserStatsStatement.setBoolean(40, attributeMapBoolean.get("polyfac"));
//            modifyUserStatsStatement.setBoolean(41, attributeMapBoolean.get("polyfod"));
//            modifyUserStatsStatement.setBoolean(42, attributeMapBoolean.get("satfacx6"));
//            modifyUserStatsStatement.setBoolean(43, attributeMapBoolean.get("satfodx6"));
//            modifyUserStatsStatement.setBoolean(44, attributeMapBoolean.get("totbrfac"));
//            modifyUserStatsStatement.setBoolean(45, attributeMapBoolean.get("totbrfod"));
//            modifyUserStatsStatement.setBoolean(46, attributeMapBoolean.get("factrans"));
//            modifyUserStatsStatement.setBoolean(47, attributeMapBoolean.get("fodtrans"));
//            modifyUserStatsStatement.setBoolean(48, attributeMapBoolean.get("chol"));
//            modifyUserStatsStatement.setBoolean(49, attributeMapBoolean.get("weight"));
//            modifyUserStatsStatement.setBoolean(50, attributeMapBoolean.get("sodium"));           

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
        System.out.println("DatabaseAccess: getSelectedAttributeList() for user " + aUserID);
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

    public static boolean setupUserStats(Integer id_user)
    {
        boolean output = false;
        int returnValue = 0;

        System.out.println("DatabaseAccess: setupUserStats() for user " + id_user);
        PreparedStatement setupUserStatsStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();

        try
        {
            setupUserStatsStatement = databaseConnection.prepareStatement(setupUserStatsSQL);
            setupUserStatsStatement.setInt(1, id_user);
            returnValue = setupUserStatsStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, setupUserStatsStatement);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        if (returnValue != 0)
        {
            output = true;
        }

        return output;
    }

    public static boolean modifyUserStats(JsonObject jsonObject, Integer id_user)
    {
        System.out.println("DatabaseAccess: modifyUserStats() for user " + id_user);
        Gson gson = new Gson();
        Type stringStringMap = new TypeToken<LinkedHashMap<String, String>>()
        {
        }.getType();
        Map<String, String> statsMap = gson.fromJson(jsonObject, stringStringMap);

        //create SQL query e.g
        //UPDATE userstatstable SET weight=?,height=?,proteingoal=?,carbohydrategoal=?,fatgoal=? WHERE id_user=?
        StringBuilder modifyUserStatsSQL = new StringBuilder();
        modifyUserStatsSQL.append("UPDATE userstatstable SET ");
        for (int count = 0; count < supportedUserStatsList.size(); count++)
        {
            String currentStat = supportedUserStatsList.get(count);

            if (statsMap.get(currentStat) != null)
            {
                modifyUserStatsSQL.append(currentStat).append("=").append(statsMap.get(currentStat));
                modifyUserStatsSQL.append(",");

            }
            
            //if at end of list remove the trailing ","
            if (count == supportedUserStatsList.size() - 1)
            {
                modifyUserStatsSQL.deleteCharAt(modifyUserStatsSQL.length() - 1);
            }
        }
        modifyUserStatsSQL.append(" WHERE id_user=").append(id_user);
        String modifyUserStatsSQLString = modifyUserStatsSQL.toString();
        System.out.println("DatabaseAccess: SQL query is:" + modifyUserStatsSQLString);

        PreparedStatement modifyUserStatsStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        boolean output = false;
        int returnValue = 0;
        try
        {
            modifyUserStatsStatement = databaseConnection.prepareStatement(modifyUserStatsSQLString);
            returnValue = modifyUserStatsStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, modifyUserStatsStatement);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        if (returnValue != 0)
        {
            output = true;
        }
        return output;
    }

    public static String getUserStats(Integer aUserID)
    {
        System.out.println("DatabaseAccess: getting user stats for user " + aUserID);
        PreparedStatement getUserStatsStatement = null;
        ResultSet resultSet = null;
        Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
        String JSONObject = null;

        try
        {
            getUserStatsStatement = databaseConnection.prepareStatement(getuserstatsSQL);
            getUserStatsStatement.setInt(1, aUserID);
            resultSet = getUserStatsStatement.executeQuery();
            JSONObject = convertResultSetToJSONArray(resultSet);

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        } finally
        {
            DatabaseUtils.closeConnections(databaseConnection, resultSet, getUserStatsStatement);
        }
        return JSONObject;
    }

}
