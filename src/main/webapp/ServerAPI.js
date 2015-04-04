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

var loginPageRequest = "loginPageRequest";
var loginRequest = "loginRequest";
var logoutRequest = "logoutRequest";
var mainPageRequest = "mainPageRequest";
var sessionPlaceholderRequest = "sessionPlaceholderRequest";
var createNewAccountPage = "createNewAccount.html";
var createAccountRequest = "createAccountRequest";