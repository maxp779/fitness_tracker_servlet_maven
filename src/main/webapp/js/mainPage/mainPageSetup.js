/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Setup code
$(document).ready(function () {
    viewDate = new Date();

    //create food datepicker and macro datepicker
    //both datepickers will be synced so they show the same date at all times
    $('#foodDatepicker').datepicker({
        autoclose: true,
        todayBtn: "linked",
        format: "dd/mm/yyyy"
    });

    $('#macroDatepicker').datepicker({
        autoclose: true,
        todayBtn: "linked",
        format: "dd/mm/yyyy"
    });

    //ensure todays date is shown in the datepickers textbox initially
    $('#foodDatepicker').datepicker('setDate', viewDate);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', viewDate);
    $('#macroDatepicker').datepicker('update');

    //get and populate custom food list and eaten food list
    getCustomFoodList();
    getEatenFoodList();
    getSelectedAttributeList();
});
