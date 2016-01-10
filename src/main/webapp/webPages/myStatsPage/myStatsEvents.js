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
        getNewMacros(function () {
            updateUserStats();
        });
        return false;
    });

    $(document).on("click", "#saveCalculatedMacros", function (e) {
        console.log("saving calculated macros");
        updateUserStats();
    });

//    $(document).on("click", ".selectAttributesButton", function (e) {
//        console.log("edit attributes button clicked");
//        globalFunctions.editSelectedAttributes(function () {
//            updateMainPage();
//        });
//    });
//
//    $('#editSelectedAttributesForm').submit(function () {
//        globalFunctionsAJAX.updateSelectedAttributes(function () {
//            updateMainPage();
//        });
//        $('#foodAttributeModal').modal('hide');
//        return false;
//    });

//auto selects form input text when clicked
    $(document).on('click', 'input', function () {
        this.select();
    });


});

function getNewMacros(callback)
{
    var caloriesInProtein = 4;
    var caloriesInCarbs = 4;
    var caloriesInFat = 9;

    globalValues.userStats.protein_goal = document.getElementById("protein_goal").value;
    globalValues.userStats.carbohydrate_goal = document.getElementById("carbohydrate_goal").value;
    globalValues.userStats.fat_goal = document.getElementById("fat_goal").value;

    //fat has 9 calories per gram, protein and carbs both have 4 calories per gram. So to get
    //the total energy expenditure (calories) we must multiply accordingly e.g 160g of protein = 160*4 = 640 calories
    globalValues.userStats.teegoal = (globalValues.userStats.fat_goal * caloriesInFat) + (globalValues.userStats.carbohydrate_goal * caloriesInCarbs)
            + (globalValues.userStats.protein_goal * caloriesInProtein);

    if (callback)
    {
        callback();
    }
}