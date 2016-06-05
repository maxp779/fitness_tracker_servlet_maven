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

    //check for invalid search parameters, empty strings, null values etc
    if (globalFunctions.isUndefinedOrNull(searchInput) || searchInput === "")
    {
        console.log("invalid search parameter, aborting search");
        setGlobalValues.setSearchResultsArray([]) //empty this otherwise the previous successful search results will show when updateMainPage() is called
        //localStorage.setItem("globalValues",globalValues);
        var innerHTML = "<li class='list-group-item searchresult'> Invalid search parameter please retry.</li>";
        document.getElementById("searchResultList").innerHTML = innerHTML;
    } else
    {
        searchInputJSON.searchInput = searchInput.toLowerCase(); //database is lower case so user input must be converted to lower case
        console.log("AJAX request: searching for food: " + JSON.stringify(searchInputJSON));
        $.ajax({
            url: serverAPI.requests.SEARCH_FOR_FOOD,
            type: "GET",
            data: searchInputJSON,
            contentType: "application/json",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    setGlobalValues.setSearchResultsArray(returnObject.data)
                    updateMainPage();
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
    }
}

/**
 * This method prepares a JSON to be used by addEatenFood(). This method directly deals with
 * the user adding a food from their own custom foods list.
 * 
 * @param {type} id_customfood
 * @returns {undefined}
 */

function addCustomFood(id_customfood)
{
    var outputJSON = {};

    //search customFoodListJSON for the food the user wants to add to their food log
    for (var currentFood in globalValues.customFoodsArray)
    {
        if (globalValues.customFoodsArray[currentFood].id_customfood === id_customfood)
        {
            outputJSON = globalValues.customFoodsArray[currentFood];
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
    for (var currentFood in globalValues.searchResultsArray)
    {
        if (globalValues.searchResultsArray[currentFood].id_searchablefood === id_searchablefood)
        {
            var matchingFood = globalValues.searchResultsArray[currentFood];

            for (var currentProperty in matchingFood)
            {
                outputJSON[currentProperty] = matchingFood[currentProperty];
            }
        }
    }

    outputJSON = calculateMacrosFromWeight(id_searchablefood, outputJSON);
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
    //date to add the food, user may wish to update the previous days log etc
    foodJSON.UNIXTime = getSelectedUNIXdate();
    console.log("addEatenFood(): attempting to add food that was eaten " + JSON.stringify(foodJSON));

    $.ajax({
        url: serverAPI.requests.ADD_EATEN_FOOD,
        type: "POST",
        data: JSON.stringify(foodJSON),
        contentType: "application/json",
        dataType: "json",
        success: function (returnObject)
        {
            if (returnObject.success === true)
            {
                console.log("addEatenFood() suceeded");
                globalFunctionsAJAX.getEatenFoodList(function () {
                    updateMainPage();
                });

                //clear form
                document.getElementById("addEatenFoodForm").reset();
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
            url: serverAPI.requests.REMOVE_EATEN_FOOD,
            type: "POST",
            data: JSON.stringify(eatenfoodJSON),
            contentType: "application/json",
            dataType: "json",
            success: function (returnObject)
            {
                if (returnObject.success === true)
                {
                    console.log("eaten food removal suceeded");
                    globalFunctionsAJAX.getEatenFoodList(function () {
                        updateMainPage();
                    });
                } else
                {
                    console.log("Error:" + serverAPI.errorCodes[returnObject.errorCode]);
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("AJAX request failed:" + error.toString());
            }
        });
    } else
    {
        console.log("currently selected food is " + selectedEatenFood + " no action taken");
    }
}

/**
 * This method gets the date that the user has selected with the datepicker and returns it in Unix time
 * format.
 * @returns {Number}
 */
function getSelectedUNIXdate()
{
    var currentDate = new Date();
    var selectedDate = $('#foodDatepicker').datepicker('getUTCDate');


    var currentDateUtcUnix = Date.UTC(currentDate.getUTCFullYear(), currentDate.getUTCMonth(),
            currentDate.getUTCDate(), currentDate.getUTCHours(), currentDate.getUTCMinutes(), currentDate.getUTCSeconds());
    var selectedDateUnix = Math.floor(selectedDate.getTime() / 1000); // we need /1000 to get seconds, otherwise milliseconds is returned
    var startOfCurrentDateUnix = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());

    //if a date in the future or past i.e. tomorrow or yesterday is selected then
    //that will be used as the foods timestamp, otherwise todays date and time will
    //be used. This allows the user to modify a previous days food log or plan ahead
    //and modify a future dates food log.
    if (selectedDateUnix > currentDateUtcUnix || selectedDateUnix < startOfCurrentDateUnix)
    {
        return selectedDateUnix;
    } else
    {
        return currentDateUtcUnix;
    }
}
