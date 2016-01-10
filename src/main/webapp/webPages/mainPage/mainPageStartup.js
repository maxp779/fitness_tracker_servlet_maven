/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */



/*
 * Executes functions in order to avoid errors on first load of page
 * getFriendlyNamesJSON() and getFoodAttributes() must be loaded
 * before the other functions execute
 */
//function startup() {
//    console.log("startup()");
//    console.log("1:getFriendlyNamesJSON");
//    getFriendlyNamesJSON(function () {
//        console.log("2:getFoodAttributes");
//        getFoodAttributes(function () {
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



//    globalFunctionsAJAX["getFriendlyNamesJSON"](function () {
//        globalFunctionsAJAX["getFoodAttributes"](function () {
//            setupEvents(function () {
//                globalFunctionsAJAX["getUserStats"](function () {
//                    globalFunctionsAJAX["getCustomFoodList"](function () {
//                        globalFunctionsAJAX["getEatenFoodList"](function () {
//                            updateMainPage();
//                            globalFunctions["setGlobalValuesLocalStorage"]();
//                        });
//                    });
//                });
//            });
//        });
//    });
    setupEvents(function ()
    {
        globalFunctions["getAllGlobalValues"](function ()
        {
            globalFunctions["setGlobalValuesLocalStorage"](function ()
            {
                updateMainPage();
            });
        });

    });


});

