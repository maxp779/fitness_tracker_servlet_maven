/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    document.getElementById("myStats").href = serverAPI.requests.MY_STATS_PAGE_REQUEST;
    document.getElementById("workoutLog").href = serverAPI.requests.WORKOUT_LOG_PAGE_REQUEST;
    document.getElementById("logout").href = serverAPI.requests.LOGOUT_REQUEST;
    document.getElementById("mainPage").href = serverAPI.requests.MAIN_PAGE_REQUEST;
    document.getElementById("customFoods").href = serverAPI.requests.CUSTOM_FOODS_PAGE_REQUEST;
    document.getElementById("settings").href = serverAPI.requests.SETTINGS_PAGE_REQUEST;
    document.getElementById("aboutPage").href = "../aboutPage/aboutPage.html";
    document.getElementById("cookiesPage").href = "../cookiesPolicyPage/cookiesPolicyPage.html";
    
    //#mainPage works differently because AJAX is needed to give feedback when the user is trying to login
//    $(document).on("click", "#mainPage", function () {
//        globalFunctionsAJAX.makeRedirectRequestAJAX("/" + serverAPI.requests.frontController + "/" + serverAPI.requests.mainPageRequest);
//    });
      
});
