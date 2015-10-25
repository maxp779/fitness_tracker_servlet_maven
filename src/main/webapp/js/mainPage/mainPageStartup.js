/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/*
 * Executes functions in order to avoid errors on first load of page
 * getFriendlyNamesJSON() and getSelectedAttributeList() must be loaded
 * before the other functions execute
 */
//function startup() {
//    console.log("startup()");
//    console.log("1:getFriendlyNamesJSON");
//    getFriendlyNamesJSON(function () {
//        console.log("2:getSelectedAttributeList");
//        getSelectedAttributeList(function () {
//            console.log("3:setupEvents");
//            setupEvents(function () {
//                console.log("4:pieChartSetup");
//                piechartSetup(function () {
//                    console.log("5:graphSetup");
//                    graphSetup(function () {
//                        console.log("6:getCustomFoodList");
//                        getCustomFoodList(function () {
//                            console.log("7:getEatenFoodList");
//                            getEatenFoodList(function () {
//                            });
//                        });
//                    });
//                });
//            });
//        });
//    });
//}

//Setup code
$(document).ready(function () {
    globalValues["viewDate"] = new Date();
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
    $('#foodDatepicker').datepicker('setDate', globalValues["viewDate"]);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', globalValues["viewDate"]);
    $('#macroDatepicker').datepicker('update');


    globalFunctionsAJAX["getFriendlyNamesJSON"](function () {
        globalFunctionsAJAX["getSelectedAttributeList"](function () {
            setupEvents(function () {
                globalFunctionsAJAX["getUserStats"](function () {
                    globalFunctionsAJAX["getCustomFoodList"](function () {
                        globalFunctionsAJAX["getEatenFoodList"](function () {
                            updateMainPage();
                        });
                    });
                });
            });
        });
    });
});

