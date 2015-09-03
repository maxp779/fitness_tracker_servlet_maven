/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

//currentMacroSplitPieSetup(function () {
//getUserStats = globalAJAXFunctions["getUserStats"];
//        getUserStats.call(globalAJAXFunctions);
//        populateUserStats();
//        updateMyStatsPieChart();


    getUserStats = globalFunctionsAJAX["getUserStats"];
    getUserStats.call(globalFunctionsAJAX, function () {
        populateUserStats(function () {
            updateMyStatsPieChart(function () {
            });
        });
    });
});
