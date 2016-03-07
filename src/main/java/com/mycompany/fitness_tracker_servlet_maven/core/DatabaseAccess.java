/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.fitness_tracker_servlet_maven.core;

import com.google.gson.Gson;
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
import java.util.UUID;
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

//    Old SQL Commands, methods that used these were refactored to create their command dynamically as they execute with a StringBuilder object
//    private static final String addcustomfoodSQL = "INSERT INTO custom_foods_table (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES (?,?,?,?,?,?)";
//    private static final String addeatenfoodSQL = "INSERT INTO eaten_foods_table (id_user,foodname,protein,carbohydrate,fat,calorie,timestamp) VALUES (?,?,?,?,?,?,?)";
//    private static final String editcustomfoodSQL = "UPDATE custom_foods_table SET foodname=?,protein=?,carbohydrate=?,fat=?,calorie=? WHERE id_customfood=?";
//    private static final String modifyUserStatsSQL = "UPDATE viewable_attributes_table SET foodcode=?,foodname=?,foodnameoriginal=?,description=?,foodgroup=?,previous=?,foodreferences=?,footnote=?,water=?,"
//            + "totnit=?,protein=?,fat=?,carbohydrate=?,calorie=?,kj=?,star=?,oligo=?,totsug=?,gluc=?,galact=?,fruct=?,sucr=?,malt=?,lact=?,alco=?,engfib=?,aoacfib=?,satfac=?,"
//            + "satfod=?,totn6pfac=?,totn6pfod=?,totn3pfac=?,totn3pfod=?,monofacc=?,monofodc=?,monofac=?,monofod=?,polyfacc=?,polyfodc=?,polyfac=?,polyfod=?,satfacx6=?,satfodx6=?,"
//            + "totbrfac=?,totbrfod=?,factrans=?,fodtrans=?,chol=?,weight=?,sodium=? WHERE id_user=?";
    //Supported food attributes, this omits items that are added automatically
    private static final List<String> supportedFoodAttributeList = (Arrays.asList("foodcode", "foodname", "foodnameoriginal", "description", "foodgroup", "previous", "foodreferences", "footnote", "water",
            "totnit", "protein", "fat", "carbohydrate", "calorie", "kj", "star", "oligo", "totsug", "gluc", "galact", "fruct", "sucr", "malt", "lact", "alco", "engfib", "aoacfib", "satfac",
            "satfod", "totn6pfac", "totn6pfod", "totn3pfac", "totn3pfod", "monofacc", "monofodc", "monofac", "monofod", "polyfacc", "polyfodc", "polyfac", "polyfod", "satfacx6", "satfodx6",
            "totbrfac", "totbrfod", "factrans", "fodtrans", "chol", "weight", "sodium"));

    private static final List<String> varcharAttributeList = (Arrays.asList("foodcode", "foodname", "foodnameoriginal", "description", "foodgroup", "previous", "foodreferences", "footnote"));
    private static final List<String> integerAttributeList = (Arrays.asList("calorie", "kj", "weight", "id_user"));

    //Supported user stats
    private static final List<String> supportedUserStatsList = (Arrays.asList("weight", "height", "protein_goal", "carbohydrate_goal", "fat_goal", "tee", "tee_goal", "date_of_birth",
               "gender", "activity_level", "excercise_intensity", "excercise_days_per_week", "excercise_minutes_per_day"));

    /**
     *
     * @param loginAttemptEmail
     * @return A Map object containing the users email and password or null if
     * they were not found
     */
    public static Map<String, String> getUserCredentialsFromEmail(String loginAttemptEmail)
    {
        String getusercredentialsfromemailSQL = "SELECT email,password,id_user FROM user_table WHERE email = ?";

        System.out.println("DatabaseAccess: getUserCredentialsFromEmail() for email " + loginAttemptEmail);
        String retrievedEmail = null;
        String retrievedPassword = null;
        String retrieved_id_user = null;
        //Connection databaseConnection = null;

        //if user exists
        if (DatabaseAccess.userAlreadyExistsCheckEmail(loginAttemptEmail))
        {
            try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                    PreparedStatement getEmailStatement = databaseConnection.prepareStatement(getusercredentialsfromemailSQL);)
            {
                getEmailStatement.setString(1, loginAttemptEmail);

                try (ResultSet resultSet = getEmailStatement.executeQuery())
                {
                    if (resultSet.next())
                    {
                        retrievedEmail = resultSet.getString("email");
                        retrievedPassword = resultSet.getString("password");
                        retrieved_id_user = resultSet.getString("id_user");
                    }
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            Map output = new HashMap<>();
            output.put("email", retrievedEmail);
            output.put("hashedPassword", retrievedPassword);
            output.put("id_user", retrieved_id_user);
            return output;
        } else //user dosent exist
        {
            return null;
        }
    }

    public static Map<String, String> getUserCredentialsFromid_user(String id_user)
    {
        String getusercredentialsfromid_userSQL = "SELECT email,password,id_user FROM user_table WHERE id_user = ?";
        System.out.println("DatabaseAccess: getUserCredentialsFromid_user() for user " + id_user);

        Map output = new HashMap<>();
        String retrievedEmail = null;
        String retrievedPassword = null;
        String retrieved_id_user = null;

        //if user exists
        if (DatabaseAccess.userAlreadyExistsCheckid_user(id_user))
        {
            try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                    PreparedStatement getEmailStatement = databaseConnection.prepareStatement(getusercredentialsfromid_userSQL);)
            {
                getEmailStatement.setLong(1, Long.parseLong(id_user));

                try (ResultSet resultSet = getEmailStatement.executeQuery();)
                {
                    resultSet.next();
                    retrievedEmail = resultSet.getString("email");
                    retrievedPassword = resultSet.getString("password");
                    retrieved_id_user = resultSet.getString("id_user");
                }

            } catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }

            output.put("email", retrievedEmail);
            output.put("hashedPassword", retrievedPassword);
            output.put("id_user", retrieved_id_user);
            return output;
        } else //user dosent exist
        {
            return null;
        }
    }

    public static boolean addUser(String anEmail, String aPassword)
    {
        String adduserSQL = "INSERT INTO user_table (email,password) VALUES (?,?)";
        System.out.println("DatabaseAccess: addUser() email " + anEmail);

        int userAdded = 0;
        //if user already exists
        if (DatabaseAccess.userAlreadyExistsCheckEmail(anEmail))
        {
            System.out.println("DatabaseAccess: user already exists, no action taken");
        } else //if user does not exist
        {
            try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                    PreparedStatement addUserStatement = databaseConnection.prepareStatement(adduserSQL);)
            {
                addUserStatement.setString(1, anEmail);
                addUserStatement.setString(2, aPassword);
                userAdded = addUserStatement.executeUpdate();

                //setup defaults for the new user
                if (userAdded != 0)
                {
                    String id_user = getid_user(anEmail);
                    setupSelectedAttributes(id_user);
                    setupUserStats(id_user);
                }
            } catch (SQLException ex)
            {
                Logger.getLogger(AuthenticationServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return userAdded != 0;
    }

    public static boolean userAlreadyExistsCheckEmail(String anEmail)
    {
        String checkforuseremailSQL = "SELECT email FROM user_table WHERE email = ?";
        System.out.println("DatabaseAccess: userAlreadyExistsCheckEmail() for email " + anEmail);

        boolean userAlreadyExists = true;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement checkUserStatement = databaseConnection.prepareStatement(checkforuseremailSQL);)
        {
            checkUserStatement.setString(1, anEmail);
            try (ResultSet resultSet = checkUserStatement.executeQuery();)
            {

                if (resultSet.next())
                {
                    System.out.println("DatabaseAccess: user already in the database");
                    userAlreadyExists = true;
                } else
                {
                    System.out.println("DatabaseAccess: user is not in the database");
                    userAlreadyExists = false;
                }
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userAlreadyExists;
    }

    public static boolean userAlreadyExistsCheckid_user(String id_user)
    {
        String checkforuserid_userSQL = "SELECT id_user FROM user_table WHERE id_user = ?";
        System.out.println("DatabaseAccess: userAlreadyExistsCheckid_user() for user" + id_user);

        boolean userAlreadyExists = true;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement checkUserStatement = databaseConnection.prepareStatement(checkforuserid_userSQL);)
        {
            checkUserStatement.setLong(1, Long.parseLong(id_user));

            try (ResultSet resultSet = checkUserStatement.executeQuery();)
            {
                if (resultSet.next())
                {
                    System.out.println("DatabaseAccess: user already in the database");
                    userAlreadyExists = true;
                } else
                {
                    System.out.println("DatabaseAccess: user is not in the database");
                    userAlreadyExists = false;
                }
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return userAlreadyExists;
    }

    public static String getid_user(String anEmail)
    {
        String getid_userSQL = "SELECT id_user FROM user_table WHERE email = ?";
        System.out.println("DatabaseAccess: getid_user() for email" + anEmail);

        Long id_user_long = null;
        String id_user_string = null;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement getuserIDStatement = databaseConnection.prepareStatement(getid_userSQL);)
        {
            getuserIDStatement.setString(1, anEmail);

            try (ResultSet resultSet = getuserIDStatement.executeQuery();)
            {
                if (resultSet.next())
                {
                    id_user_long = resultSet.getLong(1);
                    id_user_string = id_user_long.toString();
                }
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return id_user_string;
    }

    public static String getCustomFoodList(String id_user)
    {
        String getcustomfoodlistSQL = "SELECT * FROM custom_foods_table WHERE id_user = ?";
        System.out.println("DatabaseAccess: getCustomFoodList() " + id_user);

        String JSONString = null;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement getCustomFoodListStatement = databaseConnection.prepareStatement(getcustomfoodlistSQL);)
        {
            getCustomFoodListStatement.setLong(1, Long.parseLong(id_user));

            try (ResultSet resultSet = getCustomFoodListStatement.executeQuery();)
            {
                List resultSetList = convertResultSetToList(resultSet);
                JSONString = convertListToJSONString(resultSetList);

            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JSONString;
    }

    public static boolean removeCustomFood(String id_customfood)
    {
        String removecustomfoodSQL = "DELETE FROM custom_foods_table WHERE id_customfood= ?";
        System.out.println("DatabaseAccess: removeCustomFood() " + id_customfood);

        int returnValue = 0;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement removeCustomFoodStatement = databaseConnection.prepareStatement(removecustomfoodSQL);)
        {
            removeCustomFoodStatement.setLong(1, Long.parseLong(id_customfood));
            returnValue = removeCustomFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        return returnValue != 0;
    }

    private static List convertResultSetToList(ResultSet aResultSet) throws SQLException
    {
        //turn resultset into arraylist with maps in iterator
        //each attributeMap represents a single row
        ResultSetMetaData resultSetMetaData = aResultSet.getMetaData();
        List mainList = new ArrayList<>();
        int columnCount = resultSetMetaData.getColumnCount();
        while (aResultSet.next())
        {
            int currentColumn = 1;
            Map currentRecord = new HashMap();

            while (currentColumn <= columnCount)
            {
                currentRecord.put(resultSetMetaData.getColumnName(currentColumn), aResultSet.getString(currentColumn));
                currentColumn++;
            }
            mainList.add(currentRecord);
        }

        return mainList;
    }

    private static String convertListToJSONString(List aList) throws SQLException
    {
        //turn the arraylist with maps into a JSON object using googles GSON
        Gson gson = new Gson();

        //use below if you want nulls to appear e.g protein:50, carbohydrate:null
        //Gson gson = new GsonBuilder().serializeNulls().create();
        String JSONString = gson.toJson(aList);

        return JSONString;
    }
    
    /**
     * This method filters out results which do not contain ALL of the users 
     * search terms. e.g if the user typed "skimmed milk" then foods with names that
     * do not contain both the words "skimmed" and "milk" are removed.
     * 
     * 
     * @param aList a list of Map objects, each map represents a food
     * @param searchedString the string the user typed into the search box
     * @return a list with probable irrelevant results removed
     * @throws SQLException 
     */
    private static List filterResults(List aList, String searchedString) throws SQLException
    {
        String[] searchWords = searchedString.split(" ");
        List outputList = new ArrayList<>();

        for (int count = 0; count < aList.size(); count++)
        {
            Map currentFood = (Map) aList.get(count);
            String currentFoodName = (String) currentFood.get("foodname");

            int searchWordMatches = 0;          
            for (String searchWord : searchWords)
            {
                if(currentFoodName.contains(searchWord))
                {
                    searchWordMatches++;
                }
            }
            
            if(searchWordMatches == searchWords.length)
            {
                outputList.add(currentFood);
            }
        }
        return outputList;
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
//        String outputJSONString = gson.toJson(currentRecord);
//
//        return outputJSONString;
//    }
    /**
     * addCustomFood() creates an SQL query dynamically with a StringBuilder
     * object the reason for this is to allow for support for more custom food
     * attributes in the future without too much hassle.
     *
     * example query: INSERT INTO custom_foods_table
     * (id_user,foodname,protein,carbohydrate,fat,calorie) VALUES
     * (100,"oats",15,30,12,400)
     *
     * The query is built in 3 parts The first part is always "INSERT INTO
     * custom_foods_table " The second part is "(colunm1,column2)" based on the
     * attribute names provided by the client The third part is
     * "(value1,column2)" based on the attribute values provided by the client
     * At the end they are concatanated together to form a full SQL query
     *
     * @param customFoodMap a map object representing a new custom food
     * @param id_user the identifier for the current user
     * @return a boolean indicating whether the custom food was added
     * successfully or not
     * @throws java.sql.SQLException
     */
    public static boolean addCustomFood(Map<String, String> customFoodMap, String id_user) throws SQLException
    {
        System.out.println("DatabaseAccess: addCustomFood() " + customFoodMap);
        StringBuilder addCustomFoodSQL = new StringBuilder();
        addCustomFoodSQL.append("INSERT INTO custom_foods_table ");
        StringBuilder addCustomFoodColumns = new StringBuilder("(");
        StringBuilder addCustomFoodValues = new StringBuilder("VALUES (");

        for (int count = 0; count < supportedFoodAttributeList.size(); count++)
        {
            String currentAttribute = supportedFoodAttributeList.get(count);

            addCustomFoodColumns.append(currentAttribute);

            if (count < supportedFoodAttributeList.size())
            {
                //if value is a varchar aka string such as the food name it must be surrounded
                //in quotes e.g 'oats'
                if (varcharAttributeList.contains(currentAttribute))
                {
                    addCustomFoodValues.append("'").append(customFoodMap.get(currentAttribute)).append("'");
                } else //otherwise it is numeric and can be added without quotes
                {
                    addCustomFoodValues.append(customFoodMap.get(currentAttribute));
                }
            }

            if (count != supportedFoodAttributeList.size() - 1)
            {
                addCustomFoodColumns.append(",");
                addCustomFoodValues.append(",");
            } else //id_user is not part of supportedFoodAttributeList and cannot be treated the same so it is added manually here
            {
                addCustomFoodColumns.append(",id_user)");
                addCustomFoodValues.append(",").append(id_user).append(")");
            }
        }

        addCustomFoodSQL.append(addCustomFoodColumns);
        addCustomFoodSQL.append(addCustomFoodValues);
        String addCustomFoodSQLString = addCustomFoodSQL.toString(); //finished SQL query

        System.out.println("DatabaseAccess: SQL query is:" + addCustomFoodSQLString);
        int returnValue = 0;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement addCustomFoodStatement = databaseConnection.prepareStatement(addCustomFoodSQLString);)
        {
            returnValue = addCustomFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL ERROR CODE " + ex.getSQLState());
        }

        return returnValue != 0; //a value of 0 would mean nothing was modified so the query failed and false is returned
    }

    /**
     * editCustomFood() creates an SQL query dynamically, its purpose is to
     * update the attributes of a particular custom food in the database. Here
     * is an example query: "UPDATE custom_foods_table SET
     * foodname=?,protein=?,carbohydrate=?,fat=?,calorie=? WHERE
     * id_customfood=?"
     *
     *
     * @param customFoodMap a Map containing attribute values for a particular
     * custom food that is to be updated
     * @param id_user the identifier for the current user
     * @return a boolean indicating whether the custom food was added
     * successfully or not
     */
    public static boolean editCustomFood(Map<String, String> customFoodMap, String id_user)
    {

        System.out.println("DatabaseAccess: editCustomFood() " + customFoodMap);
        StringBuilder editCustomFoodSQL = new StringBuilder();
        editCustomFoodSQL.append("UPDATE custom_foods_table SET ");

        for (int count = 0; count < supportedFoodAttributeList.size(); count++)
        {
            String currentAttribute = supportedFoodAttributeList.get(count);

            if (!"id_user".equals(currentAttribute))
            {
                if (varcharAttributeList.contains(currentAttribute))
                {
                    //if value is a varchar aka string such as the food name it must be surrounded
                    //in quotes e.g 'oats'
                    editCustomFoodSQL.append(currentAttribute).append("=").append("'").append(customFoodMap.get(currentAttribute)).append("'");
                } else
                {
                    editCustomFoodSQL.append(currentAttribute).append("=").append(customFoodMap.get(currentAttribute));
                }
            }

            //if not at the last attribute put a comma to separate it from the next attribute
            if (count != supportedFoodAttributeList.size() - 1)
            {
                editCustomFoodSQL.append(",");
            } else
            {
                editCustomFoodSQL.append(",id_user=").append(id_user);
                editCustomFoodSQL.append(" WHERE id_customfood=").append(customFoodMap.get("id_customfood"));
            }
        }

        String editCustomFoodSQLString = editCustomFoodSQL.toString(); //finished SQL query
        int returnValue = 0;
        System.out.println("DatabaseAccess: editCustomFood() SQL query is:" + editCustomFoodSQLString);

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement editCustomFoodStatement = databaseConnection.prepareStatement(editCustomFoodSQLString);)
        {
            returnValue = editCustomFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL ERROR CODE " + ex.getSQLState());
        }

        return returnValue != 0; //a value of 0 would mean nothing was modified so the query failed and false is returned
    }

    public static String getEatenFoodList(String id_user, Timestamp timestamp)
    {
        String getfoodeatenlistSQL = "SELECT * FROM eaten_foods_table WHERE id_user = ? AND timestamp >= ? AND timestamp < ?";

        System.out.println("DatabaseAccess: getEatenFoodList() for user " + id_user);

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
        String JSONString = null;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement getFoodEatenStatement = databaseConnection.prepareStatement(getfoodeatenlistSQL);)
        {
            getFoodEatenStatement.setLong(1, Long.parseLong(id_user));
            getFoodEatenStatement.setTimestamp(2, currentDayStartTimestamp);
            getFoodEatenStatement.setTimestamp(3, nextDayStartTimestamp);
            try (ResultSet resultSet = getFoodEatenStatement.executeQuery();)
            {
                List resultSetList = convertResultSetToList(resultSet);
                JSONString = convertListToJSONString(resultSetList);
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return JSONString;
    }

    public static boolean addEatenFood(Map<String, String> eatenFoodMap, String id_user)
    {
        System.out.println("DatabaseAccess: addEatenFood() " + eatenFoodMap);

        //create SQL query e.g
        //INSERT INTO eaten_foods_table (id_user,foodname,protein,carbohydrate,fat,calorie,timestamp) VALUES (?,?,?,?,?,?,?)
        StringBuilder addEatenFoodSQL = new StringBuilder();
        addEatenFoodSQL.append("INSERT INTO eaten_foods_table ");

        StringBuilder addEatenFoodColumns = new StringBuilder("(");

        StringBuilder addEatenFoodValues = new StringBuilder("VALUES (");

        for (int count = 0; count < supportedFoodAttributeList.size(); count++)
        {
            String currentAttribute = supportedFoodAttributeList.get(count);
            addEatenFoodColumns.append(currentAttribute);

            if (varcharAttributeList.contains(currentAttribute))
            {
                addEatenFoodValues.append("'").append(eatenFoodMap.get(currentAttribute)).append("'");
            } else
            {
                addEatenFoodValues.append(eatenFoodMap.get(currentAttribute));
            }

            //if not at the last attribute put a comma to separate it from the next attribute
            if (count != supportedFoodAttributeList.size() - 1)
            {
                addEatenFoodColumns.append(",");
                addEatenFoodValues.append(",");
            } else //last attribute added, stick a timestamp and id_user on the end
            {
                addEatenFoodColumns.append(",timestamp,id_user)");
                String UNIXtimeString = eatenFoodMap.get("UNIXtime");
                Timestamp timestamp = new Timestamp(Long.parseLong(UNIXtimeString));
                Long timestampLong = timestamp.getTime(); //get unix time in milliseconds
                timestampLong = timestampLong / 1000; //convert to seconds
                addEatenFoodValues.append(",").append("to_timestamp(").append(timestampLong).append("),").append(id_user).append(")");
            }
        }

        addEatenFoodSQL.append(addEatenFoodColumns);
        addEatenFoodSQL.append(addEatenFoodValues);
        String addEatenFoodSQLString = addEatenFoodSQL.toString(); //finished SQL query

        int returnValue = 0;
        System.out.println("DatabaseAccess: SQL query is:" + addEatenFoodSQLString);

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement addEatenFoodStatement = databaseConnection.prepareStatement(addEatenFoodSQLString);)
        {
            returnValue = addEatenFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("SQL ERROR CODE " + ex.getSQLState());
        }

        return returnValue != 0;
    }

    public static boolean removeEatenFood(long id_eatenfood)
    {
        String removeeatenfoodSQL = "DELETE FROM eaten_foods_table WHERE id_eatenfood= ?";

        boolean output = false;
        int returnValue = 0;
        System.out.println("DatabaseAccess: removeEatenFood() " + id_eatenfood);

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement removeEatenFoodStatement = databaseConnection.prepareStatement(removeeatenfoodSQL);)
        {
            removeEatenFoodStatement.setLong(1, id_eatenfood);
            returnValue = removeEatenFoodStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return returnValue != 0;
    }

    public static String searchForFood(String food)
    {

        String[] individualFoodWords = food.split(" ");

        //% indicates a wildcard
        StringBuilder searchForFoodSQL = new StringBuilder("SELECT * FROM searchable_foods_table WHERE foodname LIKE ").append("'").append(food).append("%'");

        if (individualFoodWords.length > 1)
        {
            for (String foodWord : individualFoodWords)
            {
                searchForFoodSQL.append(" UNION ");
                searchForFoodSQL.append(" SELECT * FROM searchable_foods_table WHERE foodname LIKE ").append("'").append(foodWord).append("%'");
            }
        }

        System.out.println("DatabaseAccess: searchForFood() " + food);
        System.out.println("DatabaseAccess: searchForFood() query: " + searchForFoodSQL);
        String JSONString = null;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement searchForFood = databaseConnection.prepareStatement(searchForFoodSQL.toString());)
        {
            try (ResultSet resultSet = searchForFood.executeQuery();)
            {
                List resultSetList = convertResultSetToList(resultSet);
                resultSetList = filterResults(resultSetList, food);
                JSONString = convertListToJSONString(resultSetList);
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return JSONString;
    }

    public static boolean setupSelectedAttributes(String id_user)
    {
        String setupFoodAttributesSQL = "INSERT INTO viewable_attributes_table (id_user) VALUES (?)";

        boolean output = false;
        int returnValue = 0;

        System.out.println("DatabaseAccess: setupSelectedAttributes() for user " + id_user);

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement setupFoodAttributesStatement = databaseConnection.prepareStatement(setupFoodAttributesSQL);)
        {
            setupFoodAttributesStatement.setLong(1, Long.parseLong(id_user));
            returnValue = setupFoodAttributesStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return returnValue != 0;
    }

    public static boolean modifySelectedAttributes(Map<String, String> selectedAttributesMap, String id_user)
    {
        System.out.println("DatabaseAccess: modifySelectedAttributes() for user " + id_user);

        //change string attributes into boolean, "t" = Boolean.TRUE, "f" = BOOLEAN.FALSE
        Map<String, Boolean> attributeMapBoolean = new LinkedHashMap();
        Iterator it = selectedAttributesMap.entrySet().iterator();
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
        //UPDATE viewable_attributes_table SET foodcode=?,foodname=?,foodnameoriginal=?,description=?,foodgroup=? WHERE id_user=?
        StringBuilder modifyFoodAttributesSQL = new StringBuilder();
        modifyFoodAttributesSQL.append("UPDATE viewable_attributes_table SET ");
        for (int count = 0; count < supportedFoodAttributeList.size(); count++)
        {
            String currentAttribute = supportedFoodAttributeList.get(count);

            if (currentAttribute.equals("id_user"))
            {
                modifyFoodAttributesSQL.append(currentAttribute).append("=").append(id_user);
            } else
            {
                modifyFoodAttributesSQL.append(currentAttribute).append("=").append(attributeMapBoolean.get(currentAttribute));
            }

            //if not at the last attribute put a comma to separate it from the next attribute
            if (count != supportedFoodAttributeList.size() - 1)
            {
                modifyFoodAttributesSQL.append(",");
            }
        }
        modifyFoodAttributesSQL.append(" WHERE id_user=").append(id_user);
        String modifyFoodAttributesSQLString = modifyFoodAttributesSQL.toString();
        System.out.println("DatabaseAccess: SQL query is:" + modifyFoodAttributesSQLString);

        int returnValue = 0;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement modifySelectedAttributesStatement = databaseConnection.prepareStatement(modifyFoodAttributesSQLString);)
        {

            returnValue = modifySelectedAttributesStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        return returnValue != 0;
    }

    public static String getViewableAttributesList(String id_user)
    {
        String getSelectedAttributesSQL = "SELECT * FROM viewable_attributes_table WHERE id_user = ?";

        System.out.println("DatabaseAccess: getViewableAttributesList() for user " + id_user);
        String JSONString = null;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement getSelectedAttributesListStatement = databaseConnection.prepareStatement(getSelectedAttributesSQL);)
        {
            getSelectedAttributesListStatement.setLong(1, Long.parseLong(id_user));
            try (ResultSet resultSet = getSelectedAttributesListStatement.executeQuery();)
            {
                List resultSetList = convertResultSetToList(resultSet);
                JSONString = convertListToJSONString(resultSetList);
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return JSONString;
    }

    public static boolean setupUserStats(String id_user)
    {
        String setupUserStatsSQL = "INSERT INTO user_stats_table (id_user, protein_goal, carbohydrate_goal, fat_goal, tee) VALUES (?,?,?,?,?)";
        System.out.println("DatabaseAccess: setupUserStats() for user " + id_user);

        String defaultProtein = "160";
        String defaultCarbohydrate = "200";
        String defaultFat = "80";
        String defaultTEE = "2160"; //calories
        long returnValue = 0;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement setupUserStatsStatement = databaseConnection.prepareStatement(setupUserStatsSQL);)
        {
            setupUserStatsStatement.setLong(1, Long.parseLong(id_user));
            setupUserStatsStatement.setLong(2, Long.parseLong(defaultProtein));
            setupUserStatsStatement.setLong(3, Long.parseLong(defaultCarbohydrate));
            setupUserStatsStatement.setLong(4, Long.parseLong(defaultFat));
            setupUserStatsStatement.setLong(5, Long.parseLong(defaultTEE));

            returnValue = setupUserStatsStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        return returnValue != 0;
    }

    public static boolean modifyUserStats(Map<String, String> userStatsMap, String id_user)
    {
        System.out.println("DatabaseAccess: modifyUserStats() for user " + id_user);

        //create SQL query e.g
        //UPDATE user_stats_table SET weight=?,height=?,protein_goal=?,carbohydrate_goal=?,fat_goal=? WHERE id_user=?
        StringBuilder modifyUserStatsSQL = new StringBuilder();
        modifyUserStatsSQL.append("UPDATE user_stats_table SET ");
        for (int count = 0; count < supportedUserStatsList.size(); count++)
        {
            String currentStat = supportedUserStatsList.get(count);

            if (userStatsMap.get(currentStat) != null)
            {
                modifyUserStatsSQL.append(currentStat).append("=").append(userStatsMap.get(currentStat));
                modifyUserStatsSQL.append(",");

            }

            //if at end of list remove the trailing ","
//            if (count == supportedUserStatsList.size() - 1)
//            {
//                modifyUserStatsSQL.deleteCharAt(modifyUserStatsSQL.length() - 1);
//            }
        }

        //at end of list remove the trailing ","
        modifyUserStatsSQL.deleteCharAt(modifyUserStatsSQL.length() - 1);

        //complete SQL Query
        modifyUserStatsSQL.append(" WHERE id_user=").append(id_user);
        System.out.println("DatabaseAccess: SQL query is:" + modifyUserStatsSQL);

        int returnValue = 0;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement modifyUserStatsStatement = databaseConnection.prepareStatement(modifyUserStatsSQL.toString());)
        {
            returnValue = modifyUserStatsStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        //0 if nothing was modified in the database, otherwise the rowcount is returned if something was modified
        return returnValue != 0;
    }

    public static String getUserStats(String id_user)
    {
        String getuserstatsSQL = "SELECT * FROM user_stats_table WHERE id_user = ?";

        System.out.println("DatabaseAccess: getting user stats for user " + id_user);
        String JSONString = null;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement getUserStatsStatement = databaseConnection.prepareStatement(getuserstatsSQL);)
        {
            getUserStatsStatement.setLong(1, Long.parseLong(id_user));
            try (ResultSet resultSet = getUserStatsStatement.executeQuery();)
            {
                List resultSetList = convertResultSetToList(resultSet);
                JSONString = convertListToJSONString(resultSetList);
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return JSONString;
    }

    public static UUID createForgotPasswordRecord(String id_user, String email)
    {
        String forgotPasswordRecordSQL = "INSERT INTO forgot_passwords_table (id_user,identifier_token,expiry_date,email) VALUES (?,?,?,?)";

        System.out.println("DatabaseAccess: creating forgot password record for user " + id_user);

        //this is used to identify each password request
        UUID identifierToken = UUID.randomUUID();

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.plusMinutes(60);
        Timestamp expiryTimestamp = Timestamp.valueOf(expiryTime);

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement forgotPasswordStatement = databaseConnection.prepareStatement(forgotPasswordRecordSQL);)
        {
            forgotPasswordStatement.setLong(1, Long.parseLong(id_user));
            forgotPasswordStatement.setString(2, identifierToken.toString());
            forgotPasswordStatement.setTimestamp(3, expiryTimestamp);
            forgotPasswordStatement.setString(4, email);
            forgotPasswordStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return identifierToken;
    }

    public static boolean validateForgotPasswordRequest(String identifierToken)
    {
        String validateForgotPasswordTokenSQL = "SELECT * FROM forgot_passwords_table WHERE identifier_token = ?";

        //check if identifierToken exists in database
        boolean output = false;
        System.out.println("DatabaseAccess: validating forgotten password identifierToken: " + identifierToken);

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement getForgotPasswordToken = databaseConnection.prepareStatement(validateForgotPasswordTokenSQL);)
        {
            getForgotPasswordToken.setString(1, identifierToken);

            try (ResultSet resultSet = getForgotPasswordToken.executeQuery();)
            {
                //if token exists check if it is expired
                if (!resultSet.next())
                {
                    System.out.println("DatabaseAccess: token does not exist " + identifierToken);
                    return output;
                }

                Timestamp expiryTimestamp = resultSet.getTimestamp("expiry_date");
                LocalDateTime expiry = expiryTimestamp.toLocalDateTime();

                if (LocalDateTime.now().isBefore(expiry))
                {
                    System.out.println("DatabaseAccess: token is valid " + identifierToken);
                    output = true; //token is valid
                    return output;                    
                } else
                {
                    System.out.println("DatabaseAccess: token has expired " + identifierToken);
                    return output;
                }
            }

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output;
    }

    public static void removeExpiredTokens()
    {
        String removeExpiredTokensSQL = "DELETE FROM forgot_passwords_table WHERE expiry_date < ?";

        System.out.println("DatabaseAccess: removing expired forgot password tokens");

        LocalDateTime currentTime = LocalDateTime.now();
        LocalDateTime expiryTime = currentTime.minusMinutes(10); //tokens last 10 minutes
        Timestamp expiryTimestamp = Timestamp.valueOf(expiryTime);

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement removeTokensStatement = databaseConnection.prepareStatement(removeExpiredTokensSQL);)
        {
            removeTokensStatement.setTimestamp(1, expiryTimestamp);
            removeTokensStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static boolean removeToken(String identifierToken)
    {
        String removeExpiredTokensSQL = "DELETE FROM forgot_passwords_table WHERE identifier_token = ?";

        System.out.println("DatabaseAccess: removing token" + identifierToken);

        int output = 0;
        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement removeTokenStatement = databaseConnection.prepareStatement(removeExpiredTokensSQL);)
        {
            removeTokenStatement.setString(1, identifierToken);
            output = removeTokenStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output != 0;
    }

    public static String getIdentifierTokenEmail(String identifierToken)
    {
        String getIdentifierTokenEmailSQL = "SELECT email FROM forgot_passwords_table WHERE identifier_token = ?";

        System.out.println("DatabaseAccess: getting email linked to identifier token " + identifierToken);
        String email = null;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement getEmailStatement = databaseConnection.prepareStatement(getIdentifierTokenEmailSQL);)
        {
            getEmailStatement.setString(1, identifierToken);
            try (ResultSet resultSet = getEmailStatement.executeQuery();)
            {
                if (resultSet.next())
                {
                    email = resultSet.getString("email");
                    System.out.println("DatabaseAccess: email linked to identifier token was " + email);
                }
            }
        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return email;
    }

    protected static boolean changePasswordByEmail(String email, String password)
    {
        String changePasswordSQL = "UPDATE user_table SET password =? WHERE email=?";

        System.out.println("DatabaseAccess: changing password for user with email" + email);
        int output = 0;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement changePasswordStatement = databaseConnection.prepareStatement(changePasswordSQL);)
        {
            changePasswordStatement.setString(1, password);
            changePasswordStatement.setString(2, email);
            output = changePasswordStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output != 0;
    }
    
    protected static boolean changePassword(String id_user, String password)
    {
        String changePasswordSQL = "UPDATE user_table SET password =? WHERE id_user=?";

        System.out.println("DatabaseAccess: changing password for id_user " + id_user);
        int output = 0;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement changePasswordStatement = databaseConnection.prepareStatement(changePasswordSQL);)
        {
            changePasswordStatement.setString(1, password);
            changePasswordStatement.setLong(2, Long.parseLong(id_user));
            output = changePasswordStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output != 0;
    }

    protected static boolean changeEmail(String newEmail, String id_user)
    {
        String changeEmailSQL = "UPDATE user_table SET email =? WHERE id_user=?";

        System.out.println("DatabaseAccess: changing email for user " + id_user);
        int output = 0;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement changeEmailStatement = databaseConnection.prepareStatement(changeEmailSQL);)
        {
            changeEmailStatement.setString(1, newEmail);
            changeEmailStatement.setLong(2, Long.parseLong(id_user));
            output = changeEmailStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }
        return output != 0;
    }

    protected static boolean deleteAccount(String id_user)
    {
        String deleteAccountSQL = "DELETE FROM user_table WHERE id_user = ?";
        int output = 0;

        try (Connection databaseConnection = DatabaseUtils.getDatabaseConnection();
                PreparedStatement deleteAccountStatement = databaseConnection.prepareStatement(deleteAccountSQL);)
        {
            deleteAccountStatement.setLong(1, Long.parseLong(id_user));
            output = deleteAccountStatement.executeUpdate();

        } catch (SQLException ex)
        {
            Logger.getLogger(DatabaseAccess.class.getName()).log(Level.SEVERE, null, ex);
        }

        return output != 0;
    }

}
