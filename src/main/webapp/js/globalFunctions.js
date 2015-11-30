/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var globalFunctionsAJAX = {
    getUserStats: function (callback)
    {
        $.ajax({
            url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_GetUserStats"],
            type: "GET",
            //async : false,
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get user stats failed" + returnedJSON);
                }
                else
                {
                    console.log("get user stats succeded" + JSON.stringify(returnedJSON));
                    globalValues["userStats"] = returnedJSON[0];
//                    populateUserStats();
//                    updateMyStatsPieChart();
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    getEatenFoodList: function (callback)
    {
        //get the date from the datepicker
        var UNIXtimeJSON = {};
        UNIXtimeJSON["UNIXtime"] = getSelectedUNIXdate();

        console.log("AJAX request: AJAX_GetEatenFoodList for date " + JSON.stringify(UNIXtimeJSON));
        $.ajax({
            url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_GetEatenFoodList"],
            type: "GET",
            data: JSON.stringify(UNIXtimeJSON),
            dataType: "json",
            success: function (returnedJSON)
            {
                console.log("AJAX request: AJAX_GetEatenFoodList gotten list " + JSON.stringify(returnedJSON));
                globalValues["eatenFoodJSONArray"] = returnedJSON;
                //populateEatenFoodList();
                //calculateTotalMacros();
                //updatePieCharts();
                //updateGraphs();
                if (callback)
                {
                    callback();
                }

            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
                if (callback)
                {
                    callback();
                }
            }
        });

    },
    getCustomFoodList: function (callback)
    {
        $.ajax({
            url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_GetCustomFoodList"],
            type: "GET",
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get custom food list failed" + returnedJSON);
                }
                else
                {
                    console.log("get custom food list succeded" + returnedJSON);
                    globalValues["customFoodJSONArray"] = returnedJSON;
                    //populateCustomFoodList();
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    getFriendlyNamesJSON: function (callback)
    {
        $.ajax({
            url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_GetFriendlyNames"],
            type: "GET",
            //async : false,
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get friendly names failed" + returnedJSON);
                }
                else
                {
                    console.log("get friendly names succeded" + returnedJSON);
                    globalValues["friendlyNamesJSON"] = returnedJSON;
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    getSelectedAttributeList: function (callback)
    {

        $.ajax({
            url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_GetSelectedAttributesList"],
            type: "GET",
            //async : false,
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get selected attributes failed" + returnedJSON);
                }
                else
                {
                    console.log("get selected attributes succeded" + returnedJSON);
                    globalValues["selectedFoodAttributeJSONArray"] = returnedJSON;
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    updateSelectedAttributes: function (callback)
    {
        //iterate through each attribute
        var attributesJSON = globalValues["selectedFoodAttributeJSONArray"][0];
        for (var currentAttribute in attributesJSON)
        {
            var currentAttributeElementName = currentAttribute + "checkbox";
            var currentElement = document.getElementById(currentAttributeElementName);

            //if the a checkbox matching the attribute exists, take appropriate action
            if (currentElement !== null)
            {
                if (currentElement.checked)
                {
                    attributesJSON[currentAttribute] = "t";
                }
                else
                {
                    attributesJSON[currentAttribute] = "f";
                }
            }
        }
        globalValues["selectedFoodAttributeJSONArray"][0] = attributesJSON;

        //send updated selectedFoodAttributeJSON to server for severside update
        $.ajax({
            url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_ModifySelectedAttributes"],
            type: "POST",
            data: JSON.stringify(globalValues["selectedFoodAttributeJSONArray"][0]),
            contentType: "application/json",
            success: function (data)
            {
                if (data === "true")
                {
                    console.log("update selected attributes suceeded");
                    //getSelectedAttributeList(); <-- do not refactor, was already commented out
//                    populateEatenFoodList();
//                    populateCustomFoodList();
//                    populateSearchResultList();
                    if (callback)
                    {
                        callback();
                    }
                }
                else
                {
                    console.log("update selected attributes failed");
                    if (callback)
                    {
                        callback();
                    }
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
            }
        });
    }

};

var globalFunctions = {
    editSelectedAttributes: function (callback)
    {
        console.log("editSelectedAttributes(): current selected attributes " + JSON.stringify(globalValues["selectedFoodAttributeJSONArray"]));

        //clear form
        document.getElementById("editSelectedAttributesForm").reset();

        var attributesJSON = globalValues["selectedFoodAttributeJSONArray"][0];

        for (var currentProperty in attributesJSON)
        {
            var currentElementName = currentProperty.toString();
            currentElementName = currentElementName + "checkbox";
            var currentElement = document.getElementById(currentElementName);
            if (currentElement !== null)
            {
                if (attributesJSON[currentProperty] === "t")
                {
                    currentElement.checked = true;
                }
            }
        }

        if (callback)
        {
            callback();
        }
    },
    isUndefinedOrNull: function (aVariable)
    {
        var output;
        if (typeof aVariable === 'undefined' || aVariable === null)
        {
            output = true;
        }
        else
        {
            output = false;
        }
        return output;
    }
//    getServerAPI: function (callback)
//    {
//        $.ajax({
//            url: "frontController/AJAX_GetAPI",
//            type: "GET",
//            contentType: "application/json",
//            dataType: "json",
//            success: function (APIString)
//            {
//                localStorage.setItem("serverAPI", APIString);
//                if (callback)
//                {
//                    callback();
//                }
//            },
//            error: function (xhr, status, error)
//            {
//                // check status && error
//                console.log("ajax failed");
//            }
//        });
//    },
//    getServerAPILocal: function (callback)
//    {
//        var serverAPI = localStorage.getItem("serverAPI");
//        
//        if(serverAPI === null)
//        {
//            globalFunctions["getServerAPI"];
//        }
//        
//        
//    }
    
};
