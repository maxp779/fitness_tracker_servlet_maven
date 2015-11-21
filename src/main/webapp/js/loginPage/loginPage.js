/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//$(document).ready(function () {
//    getAPI(function () {
//        setupserverAPI(function () {
//            setupLoginPage();
//        });
//    });
//});


//function getServerAPI(callback)
//{
//    $.ajax({
//        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_SearchForFood"],
//        type: "GET",
//        data: JSON.stringify(searchInputJSON),
//        contentType: "application/json",
//        dataType: "json",
//        success: function (APIString)
//        {
//            localStorage.setItem("serverAPI",APIString);
//            if (callback)
//            {
//                callback();
//            }
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//        }
//    });
//}

//function setupserverAPI(callback)
//{
//    serverAPI = localStorage.getItem('serverAPI');
//    
//    if(callback)
//    {
//        callback();
//    }
//}

$(document).ready(function (){

    document.getElementById("loginForm").action =  "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["loginRequest"];
    document.getElementById("createAccountForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["createAccountPageRequest"];
    document.getElementById("forgotPasswordForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["forgotPasswordPageRequest"];

    //autologin for development
    document.getElementById("email").value = "test@test.com";
    document.getElementById("password").value = "testtest";
});