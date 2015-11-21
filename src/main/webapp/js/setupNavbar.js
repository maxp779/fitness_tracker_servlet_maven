/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {
    document.getElementById("myStats").href = "/" + serverAPI["requests"]["frontController"] + "/" + serverAPI["requests"]["myStatsPageRequest"];
    document.getElementById("workoutLog").href = "/" + serverAPI["requests"]["frontController"] + "/" + serverAPI["requests"]["workoutLogPageRequest"];
    document.getElementById("logout").href = "/" + serverAPI["requests"]["frontController"] + "/" + serverAPI["requests"]["logoutRequest"];
    document.getElementById("mainPage").href = "/" + serverAPI["requests"]["frontController"] + "/" + serverAPI["requests"]["mainPageRequest"];
    document.getElementById("customFoods").href = "/" + serverAPI["requests"]["frontController"] + "/" + serverAPI["requests"]["customFoodsPageRequest"];
    document.getElementById("settings").href = "/" + serverAPI["requests"]["frontController"] + "/" + serverAPI["requests"]["settingsPageRequest"];
});
