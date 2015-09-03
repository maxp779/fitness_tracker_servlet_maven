/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

$(document).ready(function () {

    $('#macroCalculatorForm').submit(function () {
        calculateMacros();
        return false;
    });
    
    $('#manualMacroForm').submit(function () {
        updateUserStatsManually();
        return false;
    });

    $(document).on("click", "#saveCalculatedMacros", function (e) {
        console.log("saving calculated macros");
        updateUserStats();
    });

});