/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 * Makes a search request to the server, the server will search its database of foods
 * for foods matching or similar to what the user typed in and will return that list
 * of foods.
 * @param {type} searchInput
 * @returns {undefined}
 */
function searchForFood(searchInput)
{
    var searchInputJSON = {};

    //check for empty strings/null values
    if (globalFunctions.isUndefinedOrNull(searchInput) || searchInput === "")
    {
        console.log("invalid search parameter, aborting search");
        globalValues.searchResultsArray = []; //empty this otherwise the previous successful search results will show when updateMainPage() is called
        localStorage.setItem("globalValues",globalValues); //mirror the change in localStorage for consistency
        var innerHTML = "<li class='list-group-item searchresult'> Invalid search parameter please retry.</li>";  
        document.getElementById("searchResultList").innerHTML = innerHTML;
    }
    else
    {
        searchInputJSON.searchInput = searchInput.toLowerCase(); //database is lower case so user input must be converted to lower case
        console.log("AJAX request: searching for food: " + JSON.stringify(searchInputJSON));
        $.ajax({
            url: "/"+serverAPI.requests.frontController +"/"+ serverAPI.requests.AJAX_SearchForFood,
            type: "GET",
            data: JSON.stringify(searchInputJSON),
            contentType: "application/json",
            dataType: "json",
            success: function (data)
            {
                globalValues.searchResultsArray = data;
                globalFunctions.setGlobalValuesLocalStorage();
                updateMainPage();
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
            }
        });
    }
}

/**
 * This method gets the foods the user has eaten from the server
 * @returns {undefined}
 */

//function getEatenFoodList(callback) //REARCHITECT
//{
//    //get the date from the datepicker
//    var UNIXtimeJSON = {};
//    UNIXtimeJSON["UNIXtime"] = getSelectedUNIXdate();
//
//    //this is an AJAX request to the server, it invokes the AJAX_GetCustomFoodsServlet which returns a JSON object
//    //containing all custom foods in full detail from the database
////    $.getJSON(frontController + AJAX_GetEatenFoodList,timestampJSON, function (JSONObject) {
////
////        console.log("getEatenFoodList " + JSON.stringify(JSONObject));
////        eatenFoodListJSON = JSONObject;
////        populateEatenFoodList();
////    });
//
//    console.log("AJAX request: AJAX_GetEatenFoodList for date " + JSON.stringify(UNIXtimeJSON));
//    $.ajax({
//        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_GetEatenFoodList"],
//        type: "GET",
//        data: JSON.stringify(UNIXtimeJSON),
//        dataType: "json",
//        success: function (returnedJSON)
//        {
//            console.log("AJAX request: AJAX_GetEatenFoodList gotten list " + JSON.stringify(returnedJSON));
//            globalValues["eatenFoodsArray"] = returnedJSON;
//            //populateEatenFoodList();
//            //calculateTotalMacros();
//            //updatePieCharts();
//            //updateGraphs();
//            if (callback)
//            {
//                callback();
//            }
//
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//            if (callback)
//            {
//                callback();
//            }
//        }
//    });
//
//}

/**
 * This method prepares a JSON to be used by addEatenFood(). This method directly deals with
 * the user adding a food from their own custom foods list.
 * 
 * @param {type} id_customfood
 * @returns {undefined}
 */

function addEatenFoodFromCustomFood(id_customfood)
{
    var outputJSON = {};

    //search customFoodListJSON for the food the user wants to add to their food log
    for (var currentFood in globalValues["customFoodsArray"])
    {
        if (globalValues["customFoodsArray"][currentFood]["id_customfood"] === id_customfood)
        {
            outputJSON = globalValues["customFoodsArray"][currentFood];
        }
    }
    addEatenFood(outputJSON);
}

/**
 * This method prepares a JSON object to be added by the addEatenFood() method.
 * This method directly deals with foods to be added from a database query i.e. the user
 * searched for "milk" then clicked one of the results, this method is what is called.
 * 
 * @param {type} id_searchablefood
 * @returns {undefined}
 */
function addEatenFoodFromSearchResult(id_searchablefood)
{
    var outputJSON = {};

    //search customFoodListJSON for the food the user wants to add to their food log
    for (var currentFood in globalValues["searchResultsArray"])
    {
        if (globalValues["searchResultsArray"][currentFood]["id_searchablefood"] === id_searchablefood)
        {
            var matchingFood = globalValues["searchResultsArray"][currentFood];

            for (var currentProperty in matchingFood)
            {
                outputJSON[currentProperty] = matchingFood[currentProperty];
            }
        }
    }

    outputJSON = calculateMacrosFromWeight(id_searchablefood, outputJSON);
//    var currentWeightID = id_searchablefood + "weight";
//    var currentWeightValue = document.getElementById(currentWeightID).value;

//    //ensure a valid weight is entered
//    var maxWeight = 100000;
//    var minWeight = 1;
//    if (currentWeightValue < minWeight)
//    {
//        document.getElementById(currentWeightID).value = minWeight;
//        currentWeightValue = minWeight;
//    }
//
//    if (currentWeightValue > maxWeight)
//    {
//        document.getElementById(currentWeightID).value = maxWeight;
//        currentWeightValue = maxWeight;
//    }

//    //calculate the macros based on the weight
//    var multiplier = currentWeightValue / outputJSON["weight"];
//    outputJSON["protein"] = outputJSON["protein"] * multiplier;
//    outputJSON["carbohydrate"] = outputJSON["carbohydrate"] * multiplier;
//    outputJSON["fat"] = outputJSON["fat"] * multiplier;
//    outputJSON["calorie"] = outputJSON["calorie"] * multiplier;
    addEatenFood(outputJSON);
}

/**
 * If the user wishes to add a food manually by typing in data this is the method
 * that directly deals with that.
 * @returns {undefined}
 */
function addEatenFoodManually()
{
    //get data from HTML form, it is formatted as an array of JSON object with the
    //form data held in name/value pairs like so:
    //[{"name":"foodname", "value":"tasty pie"},{"name":"protein", "value":"25"}]
    var formData = $("#addEatenFoodForm").serializeArray();

    //prepare the JSON to send to server
    var outputJSON = {};
    for (var count in formData)
    {
        if (formData[count]["value"] !== "")
        {
            outputJSON[formData[count]["name"]] = formData[count]["value"];
        }
    }

    addEatenFood(outputJSON);
}

/**
 * A method to add a food that has been eaten to the users eatenfoodtable in the
 * database
 * @param {type} foodJSON
 * @returns {undefined}
 */
function addEatenFood(foodJSON)
{
    console.log("AJAX request: attempting to add food that was eaten " + JSON.stringify(foodJSON));
    //date to add the food, user may wish to update the previous days log etc
    foodJSON["UNIXtime"] = getSelectedUNIXdate();
    $.ajax({
        url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_AddEatenFood"],
        type: "POST",
        data: JSON.stringify(foodJSON),
        contentType: "application/json",
        success: function (data)
        {
            if (data === "true")
            {
                console.log("food eaten add suceeded");
                globalFunctionsAJAX["getEatenFoodList"](function () {
                    updateMainPage();
                });

                //clear form
                document.getElementById("addEatenFoodForm").reset();
            }
            else
            {
                console.log("food eaten add failed");
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}

/**
 * A method to remove a food that has already been added to the users eatenfoodtable in the
 * database
 * @param {type} id_eatenfood
 * @returns {undefined}
 */
function removeEatenFood(id_eatenfood)
{
    if (id_eatenfood !== null)
    {
        console.log("AJAX request: attempting to remove " + id_eatenfood);
        var eatenfoodJSON = {};
        eatenfoodJSON["id_eatenfood"] = id_eatenfood;
        $.ajax({
            url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_RemoveEatenFood"],
            type: "POST",
            data: JSON.stringify(eatenfoodJSON),
            contentType: "application/json",
            success: function (data)
            {
                if (data === "true")
                {
                    console.log("removal suceeded");
                    globalFunctionsAJAX["getEatenFoodList"](function () {
                        updateMainPage();
                    });
                }
                else
                {
                    console.log("removal failed");
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
            }
        });
    }
    else
    {
        console.log("currently selected food is " + selectedEatenFood + " no action taken");
    }
}

/**
 * Gets the users custom foods from the server
 * @returns {undefined}
 */
//function getCustomFoodList(callback)
//{
//    //this is an AJAX request to the server, it invokes the AJAX_GetCustomFoodsServlet which returns a JSON object
//    //containing all custom foods in full detail from the database
////    $.getJSON(frontController + AJAX_GetCustomFoodList, function (JSONObject) {
////
////        console.log("AJAX request: AJAX_GetCustomFoodList " + JSON.stringify(JSONObject));
////        globalValues["customFoodsArray"] = JSONObject;
////        populateCustomFoodList();
////
////    });
//
//    $.ajax({
//        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_GetCustomFoodList"],
//        type: "GET",
//        dataType: "json",
//        success: function (returnedJSON)
//        {
//            if ($.isEmptyObject(returnedJSON))
//            {
//                console.log("get custom food list failed" + returnedJSON);
//            }
//            else
//            {
//                console.log("get custom food list succeded" + returnedJSON);
//                globalValues["customFoodsArray"] = returnedJSON;
//                populateCustomFoodList();
//            }
//            if (callback)
//            {
//                callback();
//            }
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//            if (callback)
//            {
//                callback();
//            }
//        }
//    });
//}

//function getFriendlyNamesJSON(callback)
//{
//    $.ajax({
//        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_GetFriendlyNames"],
//        type: "GET",
//        //async : false,
//        dataType: "json",
//        success: function (returnedJSON)
//        {
//            if ($.isEmptyObject(returnedJSON))
//            {
//                console.log("get friendly names failed" + returnedJSON);
//            }
//            else
//            {
//                console.log("get friendly names succeded" + returnedJSON);
//                globalValues["friendlyNames"] = returnedJSON;
//            }
//            if (callback)
//            {
//                callback();
//            }
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//            if (callback)
//            {
//                callback();
//            }
//        }
//    });
//}

//function getFoodAttributes(callback)
//{
////    $.getJSON(frontController + AJAX_GetViewableAttributesList, function (JSONObject) {
////
////        console.log("AJAX request: AJAX_GetViewableAttributesList " + JSON.stringify(JSONObject));
////        globalValues["selectedFoodAttributesArray"] = JSONObject;
////    });
////    
//    $.ajax({
//        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_GetViewableAttributesList"],
//        type: "GET",
//        //async : false,
//        dataType: "json",
//        success: function (returnedJSON)
//        {
//            if ($.isEmptyObject(returnedJSON))
//            {
//                console.log("get selected attributes failed" + returnedJSON);
//            }
//            else
//            {
//                console.log("get selected attributes succeded" + returnedJSON);
//                globalValues["selectedFoodAttributesArray"] = returnedJSON;
//            }
//            if (callback)
//            {
//                callback();
//            }
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//            if (callback)
//            {
//                callback();
//            }
//        }
//    });
//}

//function updateSelectedAttributes()
//{
//    //iterate through each attribute
//    var attributesJSON = globalValues["selectedFoodAttributesArray"][0];
//    for (var currentAttribute in attributesJSON)
//    {
//        var currentAttributeElementName = currentAttribute + "checkbox";
//        var currentElement = document.getElementById(currentAttributeElementName);
//
//        //if the a checkbox matching the attribute exists, take appropriate action
//        if (currentElement !== null)
//        {
//            if (currentElement.checked)
//            {
//                attributesJSON[currentAttribute] = "t";
//            }
//            else
//            {
//                attributesJSON[currentAttribute] = "f";
//            }
//        }
//    }
//    globalValues["selectedFoodAttributesArray"][0] = attributesJSON;
//
//    //send updated selectedFoodAttributeJSON to server for severside update
//    $.ajax({
//        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_ModifySelectedAttributes"],
//        type: "POST",
//        data: JSON.stringify(globalValues["selectedFoodAttributesArray"][0]),
//        contentType: "application/json",
//        success: function (data)
//        {
//            if (data === "true")
//            {
//                console.log("update selected attributes suceeded");
//                //getFoodAttributes();
//                populateEatenFoodList();
//                populateCustomFoodList();
//                populateSearchResultList();
//            }
//            else
//            {
//                console.log("update selected attributes failed");
//            }
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//        }
//    });
//}

//function editSelectedAttributes()
//{
//    console.log("editSelectedAttributes(): current selected attributes " + JSON.stringify(globalValues["selectedFoodAttributesArray"]));
//
//    //clear form
//    document.getElementById("editSelectedAttributesForm").reset();
//
//    var attributesJSON = globalValues["selectedFoodAttributesArray"][0];
//
//    for (var currentProperty in attributesJSON)
//    {
//        var currentElementName = currentProperty.toString();
//        currentElementName = currentElementName + "checkbox";
//        var currentElement = document.getElementById(currentElementName);
//        if (currentElement !== null)
//        {
//            if (attributesJSON[currentProperty] === "t")
//            {
//                currentElement.checked = true;
//            }
//        }
//    }
//}