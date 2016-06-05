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
    /**
     * gets the users stats from the server
     * 
     * @param {type} callback
     * @returns {undefined}
     */
    getUserStats: function (callback)
    {
        console.log("getUserStats()");
        $.ajax({
            url: serverAPI.requests.GET_USER_STATS,
            type: "GET",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("getUserStats() succeded " + JSON.stringify(returnObject.data));
                    setGlobalValues.setUserStats(returnObject.data);
                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    getEatenFoodList: function (callback)
    {
        console.log("getEatenFoodList()");
        //get the date from the datepicker
        var UnixTimeJson = {};
        UnixTimeJson.UnixTime = getSelectedUNIXdate();

        console.log("getEatenFoodList()" + JSON.stringify(UnixTimeJson));
        $.ajax({
            url: serverAPI.requests.GET_EATEN_FOOD_LIST,
            type: "GET",
            data: UnixTimeJson,
            contentType: "application/json",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("getEatenFoodList() succeeded " + JSON.stringify(returnObject.data));
                    setGlobalValues.setEatenFoodsArray(returnObject.data);
                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }

                if (callback)
                {
                    callback();
                }

            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    getCustomFoodList: function (callback)
    {
        console.log("getCustomFoodList()");
        $.ajax({
            url: serverAPI.requests.GET_CUSTOM_FOOD_LIST,
            type: "GET",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("getCustomFoodList() succeded " + JSON.stringify(returnObject.data));
                    setGlobalValues.setCustomFoodsArray(returnObject.data);
                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    getFriendlyNamesJSON: function (callback)
    {
        console.log("getFriendlyNamesJSON()");
        $.ajax({
            url: serverAPI.requests.GET_FRIENDLY_NAMES,
            type: "GET",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("getFriendlyNamesJSON() succeded " + JSON.stringify(returnObject.data));
                    setGlobalValues.setFriendlyNames(returnObject.data);
                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    getFoodAttributes: function (callback)
    {
        console.log("getFoodAttributes()");
        $.ajax({
            url: serverAPI.requests.GET_VIEWABLE_ATTRIBUTES,
            type: "GET",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("getFoodAttributes() succeded " + JSON.stringify(returnObject.data));
                    setGlobalValues.setFoodAttributes(returnObject.data);
                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
                if (callback)
                {
                    callback();
                }
            }
        });
    },
    updateSelectedAttributes: function (newFoodAttributes, callback)
    {
        console.log("updateSelectedAttributes()");
        $.ajax({
            url: serverAPI.requests.MODIFY_SELECTED_ATTRIBUTES,
            type: "POST",
            data: JSON.stringify(newFoodAttributes),
            contentType: "application/json",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("updateSelectedAttributes() succeeded");
                    setGlobalValues.setFoodAttributes(newFoodAttributes);
                    document.getElementById("attributeFeedback").innerHTML = "<div class=\"alert alert-success\" role=\"alert\">Attributes updated successfully</div>";
                } else
                {
                    document.getElementById("attributeFeedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">" + serverAPI.errorCodes[returnObject.errorCode] + ", no action taken</div>";
                }
                if (callback)
                {
                    callback();
                }
            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
            }
        });

    },
    /**
     *  This method is currently redundant however it has been left in as an 
     *  optional way to redirect the user to another page via an AJAX request.
     * @param {type} URL
     * @returns {undefined}
     */
    makeRedirectRequestAJAX: function (URL)
    {
        console.log("makeRedirectRequestAJAX()");
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
                console.log("AJAX request failed:" + error.toString());
            }
        });
    },
    logout: function ()
    {
        $.ajax({
            url: serverAPI.requests.LOGOUT_REQUEST,
            type: "POST",
            dataType: "json",
            success: function (returnObject)
            {
                console.log("logout() returnObject.data:" + returnObject.data);
                if (returnObject.success === true)
                {
                    window.location.href = returnObject.data;
                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }

            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
            }
        });
    },
    getAllClientData: function (callback)
    {
        console.log("getAllClientData()");
        //get the date from the datepicker
        var UnixTimeJson = {};
        UnixTimeJson.UnixTime = getSelectedUNIXdate();

        console.log("getAllClientData()" + JSON.stringify(UnixTimeJson));
        $.ajax({
            url: serverAPI.requests.GET_ALL_CLIENT_DATA,
            type: "GET",
            data: UnixTimeJson,
            contentType: "application/json",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("getAllClientData() succeeded " + JSON.stringify(returnObject.data));
                    setGlobalValues.setCustomFoodsArray(returnObject.data.customFoods);
                    setGlobalValues.setFriendlyNames(returnObject.data.friendlyNames);
                    setGlobalValues.setFoodAttributes(returnObject.data.viewableAttributes)
                    setGlobalValues.setUserStats(returnObject.data.userStats)
                    setGlobalValues.setEatenFoodsArray(returnObject.data.eatenFoods);

                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }

                if (callback)
                {
                    callback();
                }

            },
            error: function (xhr, status, error)
            {
                console.log("AJAX request failed:" + error.toString());
                if (callback)
                {
                    callback();
                }
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
//    getAllGlobalValues: function (callback)
//    {
//        globalFunctionsAJAX["getFriendlyNamesJSON"](function () {
//            globalFunctionsAJAX["getFoodAttributes"](function () {
//                globalFunctionsAJAX["getUserStats"](function () {
//                    globalFunctionsAJAX["getCustomFoodList"](function () {
//                        globalFunctionsAJAX["getEatenFoodList"](function () {
//                            if (callback)
//                            {
//                                callback();
//                            }
//                        });
//                    });
//                });
//            });
//        });
//    },
    /**
     * A small but important method.
     * 
     * Each html element relating to a food that is dynamically generated is given an id. This id is based on
     * the numeric primary key in the database for that particular element e.g 
     * For a custom food id_customfood is the primary key which could be 1084. 
     * For an eaten food id_eatenfood is the primary key which could be 982.
     * 
     * To prevent conflict i.e in a situation where two elements having the same numeric id, a string is added to the end to differentiate the elements.
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