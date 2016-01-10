/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var globalValues = {
    userStats: {}, //an object containing the users ideal protein/carb/fat/calorie consumption values, they set these up themselves
    customFoodsArray: [], //an array of objects which represent the current users custom foods
    eatenFoodsArray: [], //an array of objects which represent the current users eaten foods
    searchResultsArray: [], //an array of objects which represent the current users search results if they searched the database
    foodAttributes: {}, //a single object containing food attributes the user wants to see e.g protein,carbs,saturated fats
    friendlyNames: {}, //friendly names for the food attributes e.g {"satfod":"Saturated fat","totsug":"Total Sugar"}
    totalMacrosToday: {},
    nonOperableAttributes: ["foodcode", "foodname", "foodnameoriginal", "description",
        "foodgroup", "previous", "foodreferences", "footnote", "id_user", "id_eatenfood", "id_searchablefood", "timestamp"], //attributes that should not be operated on mathematically
    wholeIntegerAttributes: ["calorie", "kj", "weight"], //attributes that are whole integers as opposed to floats
    passwordValid:false,
    emailValid:false
    //taken out of globalvalues, dosent really belong here 
    //viewDate: null, //global viewdate object, this represents the date the user is currently viewing
    //eventTriggered: false //this is used to prevent an infinite loop when syncing the two datepickers
};

//not currently used but may be of use in the future!
var errorCodes = {
    "10":"Error 10: password too short",
    "11":"Error 11: account with that email already exists",
    "12":"Error 12: account dosent exist",
    "13":"Error 13: change password request already used or expired",
    "14":"Error 14: password change failed",
    "15":"Error 15: account name or password incorrect",
    "16":"Error 16: your password was incorrect",
    "17":"Error 17: email change failed",
    "18":"Error 18: update attributes failed",
    "19":"Error 19: add custom food failed"

};

var serverAPI = {
    parameters: {
        port: 8080,
        projectName: "fitness_tracker_servlet_maven",
        mainURL: "http://localhost:8080" // replace with "window.location.hostname" ??
    },
    requests: {
        //action requests
        loginRequest: "login",
        logoutRequest: "logout",
        createAccountRequest: "createAccount",
        //frontController: "/fitness_tracker_servlet_maven/FrontControllerServlet/", //for when webapp is non ROOT
        frontController: "FrontControllerServlet", //for when webapp is hosted as ROOT
        //page requests
        customFoodsPageRequest: "customFoodsPage",
        mainPageRequest: "mainPage",
        workoutLogPageRequest: "workoutLogPage",
        sessionPlaceholderPageRequest: "sessionPlaceholderPage",
        loginPageRequest: "loginPage",
        createAccountPageRequest: "createAccountPage",
        myStatsPageRequest: "myStatsPage",
        forgotPasswordPageRequest: "forgotPasswordPage",
        forgotPasswordEmailRequest: "forgotPasswordEmail",
        changePasswordRequest: "changePassword",
        changeEmailRequest: "changeEmail",
        settingsPageRequest: "settingsPage",
        deleteAccountRequest: "deleteAccount",
        
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
        AJAX_GetViewableAttributesList: "AJAX_GetViewableAttributesList",
        AJAX_GetFriendlyNames: "AJAX_GetFriendlyNames",
        AJAX_ModifyUserStats: "AJAX_ModifyUserStats",
        AJAX_GetUserStats: "AJAX_GetUserStats",
        AJAX_GetIdentifierTokenEmail:"AJAX_GetIdentifierTokenEmail"
    }
};




//var customFoodsArray; //an array of JSON objects which represent the current users custom foods
//var eatenFoodsArray; //an array of JSON objects which represent the current users eaten foods
//var searchResultsArray; //an array js JSON objects which represent the current users search results if they searched the database
//var foodAttributes; //a single JSON object containing food attributes the user wants to see e.g protein,carbs,saturated fats
//var friendlyNames; //friendly names for the food attributes e.g {"satfod":"Saturated fat","totsug":"Total Sugar"}
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
