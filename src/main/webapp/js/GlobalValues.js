/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
//var goalMacros = {protein: 40, carbohydrate: 30, fat: 30}; //dummy values

var globalValues = {
    userStats: {}, //an object containing the users ideal protein/carb/fat/calorie consumption values, they set these up themselves
    customFoodJSONArray: [], //an array of objects which represent the current users custom foods
    eatenFoodJSONArray: [], //an array of objects which represent the current users eaten foods
    searchResultFoodJSONArray: [], //an array js objects which represent the current users search results if they searched the database
    selectedFoodAttributeJSONArray: [], //a single object containing food attributes the user wants to see e.g protein,carbs,saturated fats
    friendlyNamesJSON: {}, //friendly names for the food attributes e.g {"satfod":"Saturated fat","totsug":"Total Sugar"}
    totalMacrosToday: {},
    nonOperableAttributes: ["foodcode", "foodname", "foodnameoriginal", "description",
        "foodgroup", "previous", "foodreferences", "footnote", "id_user", "id_eatenfood", "id_searchablefood", "timestamp"], //attributes that should not be operated on mathematically
    wholeIntegerAttributes: ["calorie", "kj", "weight"], //attributes that are whole integers as opposed to floats


    viewDate: null, //global viewdate object, this represents the date the user is currently viewing
    eventTriggered: false //this is used to prevent an infinite loop when syncing the two datepickers
};

var serverAPI = {
    parameters: {
        port: 8080,
        projectName: "fitness_tracker_servlet_maven",
        mainURL: "http://localhost:8080"
    },
    requests: {
        //action requests
        loginRequest: "login",
        logoutRequest: "logout",
        createAccountRequest: "createAccount",
        frontController: "/FrontControllerServlet/",
        //page requests
        customFoodsPageRequest: "customFoodsPage",
        mainPageRequest: "mainPage",
        workoutLogPageRequest: "workoutLogPage",
        sessionPlaceholderPageRequest: "sessionPlaceholderPage",
        loginPageRequest: "loginPage",
        createAccountPageRequest: "createAccountPage",
        myStatsPageRequest: "myStatsPage",
        //AJAX requests
        AJAX_GetCustomFoodList: "AJAX_GetCustomFoodList",
        AJAX_GetEatenFoodList: "AJAX_GetEatenFoodList",
        AJAX_RemoveCustomFood: "AJAX_RemoveCustomFood",
        AJAX_AddCustomFood: "AJAX_AddCustomFood",
        AJAX_EditCustomFood: "AJAX_EditCustomFood",
        AJAX_AddEatenFood: "AJAX_AddEatenFood",
        AJAX_RemoveEatenFood: "AJAX_RemoveEatenFood",
        AJAX_SearchForFood: "AJAX_SearchForFood",
        AJAX_ModifySelectedAttributes: "AJAX_ModifySelectedAttributes",
        AJAX_GetSelectedAttributesList: "AJAX_GetSelectedAttributesList",
        AJAX_GetFriendlyNames: "AJAX_GetFriendlyNames",
        AJAX_ModifyUserStats: "AJAX_ModifyUserStats",
        AJAX_GetUserStats: "AJAX_GetUserStats"
    }
};




//var customFoodJSONArray; //an array of JSON objects which represent the current users custom foods
//var eatenFoodJSONArray; //an array of JSON objects which represent the current users eaten foods
//var searchResultFoodJSONArray; //an array js JSON objects which represent the current users search results if they searched the database
//var selectedFoodAttributeJSONArray; //a single JSON object containing food attributes the user wants to see e.g protein,carbs,saturated fats
//var friendlyNamesJSON; //friendly names for the food attributes e.g {"satfod":"Saturated fat","totsug":"Total Sugar"}
//var totalMacrosToday;
//var currentMacroSplit;
//var macroGraph;
//var userStats = {};

//var nonOperableAttributes = ["foodcode", "foodname", "foodnameoriginal", "description",
//    "foodgroup", "previous", "foodreferences", "footnote", "id_user", "id_eatenfood", "id_searchablefood", "timestamp"];
//var wholeIntegerAttributes = ["calorie", "kj", "weight"];

//global viewDate object
//this is the date the user is currently viewing
//var viewDate;
//var eventTriggered = false;
