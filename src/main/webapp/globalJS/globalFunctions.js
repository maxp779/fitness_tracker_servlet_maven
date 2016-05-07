/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var setGlobalValues = {
    setUserStats: function (userStats) {
        globalValues.userStats = userStats;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setCustomFoodsArray: function (customFoods) {
        globalValues.customFoodsArray = customFoods;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setEatenFoodsArray: function (eatenFoods) {
        globalValues.eatenFoodsArray = eatenFoods;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setSearchResultsArray: function (searchResults) {
        globalValues.searchResultsArray = searchResults;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setFoodAttributes: function (foodAttributes) {
        globalValues.foodAttributes = foodAttributes;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setFriendlyNames: function (friendlyNames) {
        globalValues.friendlyNames = friendlyNames;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setTotalMacrosToday: function (totalMacros) {
        globalValues.totalMacrosToday = totalMacros;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setNonOperableAttributes: function (nonOperableAttributes) {
        globalValues.nonOperableAttributes = nonOperableAttributes;
        globalFunctions.setGlobalValuesLocalStorage();
    },
    setWholeIntegerAttributes: function (wholeIntegerAttributes) {
        globalValues.wholeIntegerAttributes = wholeIntegerAttributes;
        globalFunctions.setGlobalValuesLocalStorage();
    },
};


var globalFunctionsAJAX = {
    getUserStats: function (callback)
    {
        $.ajax({
            url:serverAPI.requests.GET_USER_STATS,
            type: "GET",
            //async : false,
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get user stats failed" + returnedJSON);
                } else
                {
                    console.log("get user stats succeded");
                    setGlobalValues.setUserStats(returnedJSON[0]);
                    //globalFunctions.setGlobalValuesLocalStorage();
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
        UNIXtimeJSON.UNIXtime = getSelectedUNIXdate();

        console.log("AJAX request: AJAX_GetEatenFoodList for date ");
        $.ajax({
            url:serverAPI.requests.GET_EATEN_FOOD_LIST,
            type: "GET",
            data: JSON.stringify(UNIXtimeJSON),
            dataType: "json",
            success: function (returnedJSON)
            {
                console.log("AJAX request: AJAX_GetEatenFoodList gotten list");
                setGlobalValues.setEatenFoodsArray(returnedJSON);
                //globalFunctions.setGlobalValuesLocalStorage();
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
            url:serverAPI.requests.GET_CUSTOM_FOOD_LIST,
            type: "GET",
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get custom food list failed" + returnedJSON);
                } else
                {
                    console.log("get custom food list succeded");
                    setGlobalValues.setCustomFoodsArray(returnedJSON);
                    //globalFunctions.setGlobalValuesLocalStorage();
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
            url:serverAPI.requests.GET_FRIENDLY_NAMES,
            type: "GET",
            //async : false,
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get friendly names failed" + returnedJSON);
                } else
                {
                    console.log("get friendly names succeded");
                    setGlobalValues.setFriendlyNames(returnedJSON);
                    //globalFunctions.setGlobalValuesLocalStorage();
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
    getFoodAttributes: function (callback)
    {
        $.ajax({
            url:serverAPI.requests.GET_VIEWABLE_ATTRIBUTES,
            type: "GET",
            //async : false,
            dataType: "json",
            success: function (returnedJSON)
            {
                if ($.isEmptyObject(returnedJSON))
                {
                    console.log("get food attributes failed" + returnedJSON);
                } else
                {
                    console.log("get food attributes succeded");
                    setGlobalValues.setFoodAttributes(returnedJSON[0]);
                    //globalFunctions.setGlobalValuesLocalStorage();
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
    updateSelectedAttributes: function (newFoodAttributes, callback)
    {
        $.ajax({
            url:serverAPI.requests.MODIFY_SELECTED_ATTRIBUTES,
            type: "POST",
            data: JSON.stringify(newFoodAttributes),
            contentType: "application/json",
            success: function (data)
            {
                var returnObject = JSON.parse(data);

                if (returnObject.success === "true")
                {
                    console.log("attributes updated");

                    setGlobalValues.setFoodAttributes(newFoodAttributes);
                    document.getElementById("attributeFeedback").innerHTML = "<div class=\"alert alert-success\" role=\"alert\">Attributes updated successfully</div>";
                } else
                {
                    document.getElementById("attributeFeedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">" + errorCodes[returnObject.errorCode] + ", no action taken</div>";
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
            }
        });

    },
    makeRedirectRequestAJAX: function (URL)
    {
        $.ajax({
            url: URL,
            type: "POST",
            contentType: "application/json",
            success: function (returnedURL)
            {
                window.location = returnedURL;
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
            }
        });
    }

};

/**
 * None of these functions are intended to be used directly! If I could I would
 * make them private
 * @type type
 */
var helperFunctions = {
}

var globalFunctions = {
    showSelectedAttributes: function (callback)
    {
        console.log("showSelectedAttributes(): current selected attributes " + JSON.stringify(globalValues.foodAttributes));

        //clear form
        document.getElementById("editSelectedAttributesForm").reset();

        //var attributesJSON = globalValues["foodAttributes"][0];

        for (var currentProperty in globalValues.foodAttributes)
        {
            var currentElementName = currentProperty.toString();
            currentElementName = currentElementName + "checkbox";
            var currentElement = document.getElementById(currentElementName);
            if (currentElement !== null)
            {
                if (globalValues.foodAttributes[currentProperty] === "t")
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
    /**
     * This method removes the need to call getAllGlobalValues() on new pages.
     * getAllGlobalValues can be called once when the user logs in and then the globalValues object
     * and the values it obtained from calling getAllGlobalValues() are stored in localStorage for retrieval later.
     * @param {type} callback
     * @returns {undefined}
     */
    refreshGlobalValuesFromLocalStorage: function (callback)
    {
        globalValues = JSON.parse(localStorage.getItem("globalValues"));

        if (callback)
        {
            callback();
        }
    },
    /**
     * This method updates the globalValues local storage object with the latest values
     * from the server
     * @param {type} callback
     * @returns {undefined}
     */
    setGlobalValuesLocalStorage: function (callback)
    {
        localStorage.setItem("globalValues", JSON.stringify(globalValues));

        if (callback)
        {
            callback();
        }
    },
    getAllGlobalValues: function (callback)
    {
        globalFunctionsAJAX["getFriendlyNamesJSON"](function () {
            globalFunctionsAJAX["getFoodAttributes"](function () {
                globalFunctionsAJAX["getUserStats"](function () {
                    globalFunctionsAJAX["getCustomFoodList"](function () {
                        globalFunctionsAJAX["getEatenFoodList"](function () {
                            if (callback)
                            {
                                callback();
                            }
                        });
                    });
                });
            });
        });
    },
    /**
     * A small but important method.
     * 
     * Each html element relating to a food that is dynamically generated is given an id. This id is based on
     * the numeric primary key in the database for that particular element e.g for a custom food
     * id_customfood is the primary key which could be 1084. For an eaten food
     * id_eatenfood is the primary key which could be 982.
     * 
     * To prevent conflict i.e two elements having the same id, a string is added to the end to differentiate the elements.
     * e.g if id_customfood is 1084 then its id will be "1084customfood". If id_eatenfood is 982
     * it will be "982eatenfood". This method simply removes the added characters leaving just the numeric id
     * for the database to operate on.
     * 
     * @param {type} id
     * @returns {id minus any characters}
     */
    removeCharacters: function (id)
    {
        var numericid = id.replace(/[a-z]/g, '');
        return numericid;
    },
    createFoodAttributesHTML: function (currentFoodJSON, foodIDType) //food ID is "id_searchablefood" or "id_customfood" etc, it defines the category of food object to look for
    {
        var outputHTML = "";
        //var selectedFoodAttributeJSON = globalValues.foodAttributes;
        var selectedAttributeArray = globalFunctions.getSelectedAttributes();
        var primaryAttributeArray = ["protein", "carbohydrate", "fat", "calorie", "weight"];
        var secondaryAttributeArray = [];
        var colorMapJSON = {"protein": "green", "carbohydrate": "blue", "fat": "orange", "calorie": "red", "weight": "black"};

        //the reason we have primary attributes is due to layout concerns
        //the primary attributes must be shown first and in the same order
        for (var index = 0; index < primaryAttributeArray.length; index++)
        {
            //if a primary attribute is not selected by the user
            if (selectedAttributeArray.indexOf(primaryAttributeArray[index]) === -1)
            {
                //remove from primaryAttributeArray
                primaryAttributeArray.splice(index, 1);
            }
        }

//        if (selectedAttributeArray.indexOf("protein") === -1)
//        {
//            var removalIndex = primaryAttributeArray.indexOf("protein");
//            primaryAttributeArray.splice(removalIndex, 1);
//        }
//        if (selectedAttributeArray.indexOf("carbohydrate") === -1)
//        {
//            var removalIndex = primaryAttributeArray.indexOf("carbohydrate");
//            primaryAttributeArray.splice(removalIndex, 1);
//        }
//        if (selectedAttributeArray.indexOf("fat") === -1)
//        {
//            var removalIndex = primaryAttributeArray.indexOf("fat");
//            primaryAttributeArray.splice(removalIndex, 1);
//        }
//        if (selectedAttributeArray.indexOf("calorie") === -1)
//        {
//            var removalIndex = primaryAttributeArray.indexOf("calorie");
//            primaryAttributeArray.splice(removalIndex, 1);
//        }
//        if (selectedAttributeArray.indexOf("weight") === -1)
//        {
//            var removalIndex = primaryAttributeArray.indexOf("weight");
//            primaryAttributeArray.splice(removalIndex, 1);
//        }


        for (var index = 0; index < selectedAttributeArray.length; index++)
        {
            var currentAttribute = selectedAttributeArray[index];
            if (!(currentAttribute === "protein" || currentAttribute === "carbohydrate" || currentAttribute === "fat"
                    || currentAttribute === "calorie" || currentAttribute === "foodname" || currentAttribute === "weight"))
            {
                secondaryAttributeArray.push(currentAttribute);
            }
        }

        outputHTML = outputHTML.concat("<div id='" + currentFoodJSON[foodIDType] + "macros'>"
                + "<strong>Name: </strong>" + currentFoodJSON["foodname"]
                + "<br>"
                + "<strong>Primary Macros: </strong>");

        for (var index = 0; index < primaryAttributeArray.length; index++)
        {
            var currentAttributeValue = currentFoodJSON[primaryAttributeArray[index]];
            outputHTML = outputHTML.concat("<font color='" + colorMapJSON[primaryAttributeArray[index]] + "'>" + globalValues["friendlyNames"][primaryAttributeArray[index]] + ":");

            if (globalFunctions.isUndefinedOrNull(currentAttributeValue))
            {
                outputHTML = outputHTML.concat("? / </font>");
            } else
            {
                outputHTML = outputHTML.concat(currentAttributeValue + " / </font>");
            }
        }

        outputHTML = outputHTML.concat("<br>"
                + "<strong>Other info: </strong>");

        for (var index = 0; index < secondaryAttributeArray.length; index++)
        {
            var currentAttributeValue = currentFoodJSON[secondaryAttributeArray[index]];
            outputHTML = outputHTML.concat("<font color='#0099FF'>" + globalValues["friendlyNames"][secondaryAttributeArray[index]] + ":");

            if (globalFunctions.isUndefinedOrNull(currentAttributeValue))
            {
                outputHTML = outputHTML.concat("? / </font>");
            } else
            {
                outputHTML = outputHTML.concat(currentAttributeValue + " / </font>");
            }

            //+ currentFoodJSON[secondaryAttributeArray[index]] + " </font>");
        }
        outputHTML = outputHTML.concat("</div>");
        return outputHTML;
    },
    getSelectedAttributes: function ()
    {
        var outputArray = [];

        for (var aProperty in globalValues.foodAttributes)
        {
            if (globalValues.foodAttributes[aProperty] === "t")
            {
                outputArray.push(aProperty);
            }
        }
        outputArray.sort();

        return outputArray;
    },
    isUndefinedOrNull: function (aVariable)
    {
        var output;
        if (typeof aVariable === 'undefined' || aVariable === null)
        {
            output = true;
        } else
        {
            output = false;
        }
        return output;
    }, //Did not write this, no idea how it works :(
    getURLParameter: function (name) {
        return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search) || [, ""])[1].replace(/\+/g, '%20')) || null;
    }
};

//function isUndefinedOrNull(aVariable)
//{
//    var output;
//    if (typeof aVariable === 'undefined' || aVariable === null)
//    {
//        output = true;
//    } else
//    {
//        output = false;
//    }
//    return output;
//}