/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//parameters
var port = 8080;
var projectName = "fitness_tracker_servlet_maven";
var mainURL = "http://localhost:"+ port + "/" + projectName + "/";

//Server API
var URLLoginPage = "index.html";

//FrontController
var frontController = "/fitness_tracker_servlet_maven/FrontControllerServlet/";


//action requests
var loginRequest = "login";
var logoutRequest = "logout";
var createAccountRequest = "createAccount";
var testDatabase = "testDatabase";

//page requests
var customFoodsPageRequest = "customFoodsPage";
var mainPageRequest = "mainPage";
var workoutTrackerPageRequest = "workoutTrackerPage";
var sessionPlaceholderPageRequest = "sessionPlaceholderPage";
var loginPageRequest = "loginPage";
var createAccountPageRequest = "createAccountPage";

//AJAX requests
var AJAX_GetCustomFoodList = "AJAX_GetCustomFood";
var AJAX_RemoveCustomFood = "AJAX_RemoveCustomFood";
var AJAX_AddCustomFood = "AJAX_AddCustomFood";
var AJAX_EditCustomFood = "AJAX_EditCustomFood";