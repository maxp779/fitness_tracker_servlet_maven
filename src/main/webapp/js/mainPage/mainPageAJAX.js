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
    searchInputJSON["searchInput"] = searchInput;
    console.log("AJAX request: searching for food: " + JSON.stringify(searchInputJSON));
    $.ajax({
        url: frontController + AJAX_SearchForFood,
        type: "GET",
        data: JSON.stringify(searchInputJSON),
        contentType: "application/json",
        dataType: "json",
        success: function (data)
        {
            searchResultFoodJSONArray = data;
            populateSearchResultList();
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}

/**
 * This method gets the foods the user has eaten from the server
 * @returns {undefined}
 */

function getEatenFoodList()
{
    //get the date from the datepicker
    var UNIXtimeJSON = {};
    UNIXtimeJSON["UNIXtime"] = getSelectedUNIXdate();

    //this is an AJAX request to the server, it invokes the AJAX_GetCustomFoodsServlet which returns a JSON object
    //containing all custom foods in full detail from the database
//    $.getJSON(frontController + AJAX_GetEatenFoodList,timestampJSON, function (JSONObject) {
//
//        console.log("getEatenFoodList " + JSON.stringify(JSONObject));
//        eatenFoodListJSON = JSONObject;
//        populateEatenFoodTable();
//    });

    console.log("AJAX request: AJAX_GetEatenFoodList for date " + JSON.stringify(UNIXtimeJSON));
    $.ajax({
        url: frontController + AJAX_GetEatenFoodList,
        type: "GET",
        data: JSON.stringify(UNIXtimeJSON),
        dataType: "json",
        success: function (returnedJSON)
        {
            console.log("AJAX request: AJAX_GetEatenFoodList gotten list " + JSON.stringify(returnedJSON));
            eatenFoodJSONArray = returnedJSON;
            populateEatenFoodTable();
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });


}

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
    for (var currentFood in customFoodJSONArray)
    {
        if (customFoodJSONArray[currentFood]["id_customfood"] === id_customfood)
        {
            outputJSON = customFoodJSONArray[currentFood];
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
    for (var currentFood in searchResultFoodJSONArray)
    {
        if (searchResultFoodJSONArray[currentFood]["id_searchablefood"] === id_searchablefood)
        {
            var matchingFood = searchResultFoodJSONArray[currentFood];

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
function removeCharacters(id)
{
    var numericid = id.replace(/[a-z]/g, '');
    return numericid;
}

/**
 * Here the macros for foods are updated based on their weight. If the user searches for
 * "milk" and changes the default weight of 100 to 500 then this method is called and will
 * update the page reflecting that change. Instead of say 5g of protein it would show 25g etc.
 * 
 * @param {type} id
 * @returns {undefined}
 */
function updateSearchResultMacros(id)
{
    //get the numbers from the id, it is the id of the inputbox that is passed
    //which takes the form of "id_searchablefood + weight" e.g. 2500weight
    //we need the number which alone links to the searchablefood we need to change
    var id_searchablefood = id.replace(/[a-z]/g, '');
    console.log("updating macros for:" + id_searchablefood);
    var id_searchablefoodmacros = document.getElementById(id_searchablefood + "macros");
    var currentFood = {};


    for (var aFood in searchResultFoodJSONArray)
    {
        if (searchResultFoodJSONArray[aFood]["id_searchablefood"] === id_searchablefood)
        {
            var matchingFood = searchResultFoodJSONArray[aFood];
            for (var currentProperty in matchingFood)
            {
                currentFood[currentProperty] = matchingFood[currentProperty];
            }
        }
    }
    console.log("current food: " + JSON.stringify(currentFood));
    currentFood = calculateMacrosFromWeight(id_searchablefood, currentFood);
    console.log("current food: " + JSON.stringify(currentFood));


    var updatedHTML = "<strong>Macros: </strong>"
            + " <font color='green'>Protein: " + currentFood["protein"] + "</font>"
            + " <font color='blue'>Carb: " + currentFood["carbohydrate"] + "</font>"
            + " <font color='orange'>Fat: " + currentFood["fat"] + "</font>"
            + " <font color='red'>Cals: " + currentFood["calorie"] + "</font>";

    id_searchablefoodmacros.innerHTML = updatedHTML;

}

/**
 * A method to calculate the macros of a food based on the weight of the food.
 * For example the database stores the protein content of 100g of milk as 5g and the
 * user enters 250g of milk this method will calculate the amount of protein,carbs,fat,cals
 * for milk at that weight.
 * 
 * @param {type} id_searchablefood
 * @param {type} aFoodJSON
 * @returns {JSON}
 */
function calculateMacrosFromWeight(id_searchablefood, aFoodJSON)
{
    console.log("calculating macros for:" + id_searchablefood);
    var currentWeightID = id_searchablefood + "weight";
    var currentWeightValue = document.getElementById(currentWeightID).value;

    //ensure a valid weight is entered
    var maxWeight = 100000;
    var minWeight = 0;
    if (currentWeightValue < minWeight)
    {
        document.getElementById(currentWeightID).value = minWeight;
        currentWeightValue = minWeight;
    }

    if (currentWeightValue > maxWeight)
    {
        document.getElementById(currentWeightID).value = maxWeight;
        currentWeightValue = maxWeight;
    }

    //calculate the macros from the stored weight of the food
    var multiplier = currentWeightValue / aFoodJSON["weight"];
    var decimalPlaces = 1;

    var protein = aFoodJSON["protein"] * multiplier;
    protein = protein.toFixed(decimalPlaces);
    aFoodJSON["protein"] = protein;

    var carbohydrate = aFoodJSON["carbohydrate"] * multiplier;
    carbohydrate = carbohydrate.toFixed(decimalPlaces);
    aFoodJSON["carbohydrate"] = carbohydrate;

    var fat = aFoodJSON["fat"] * multiplier;
    fat = fat.toFixed(decimalPlaces);
    aFoodJSON["fat"] = fat;

    var calorie = aFoodJSON["calorie"] * multiplier;
    calorie = calorie.toFixed(0); //calorie is supposed to be an integer not a float, therefore 0 decimal places
    aFoodJSON["calorie"] = calorie;

    return aFoodJSON;
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
        url: frontController + AJAX_AddEatenFood,
        type: "POST",
        data: JSON.stringify(foodJSON),
        contentType: "application/json",
        success: function (data)
        {
            if (data === "true")
            {
                console.log("food eaten add suceeded");
                getEatenFoodList();

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
            url: frontController + AJAX_RemoveEatenFood,
            type: "POST",
            data: JSON.stringify(eatenfoodJSON),
            contentType: "application/json",
            success: function (data)
            {
                if (data === "true")
                {
                    console.log("removal suceeded");
                    getEatenFoodList();
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
function getCustomFoodList()
{
    //this is an AJAX request to the server, it invokes the AJAX_GetCustomFoodsServlet which returns a JSON object
    //containing all custom foods in full detail from the database
    $.getJSON(frontController + AJAX_GetCustomFoodList, function (JSONObject) {

        console.log("AJAX request: AJAX_GetCustomFoodList " + JSON.stringify(JSONObject));
        customFoodJSONArray = JSONObject;
        populateCustomFoodList();

    });
}

function getSelectedAttributeList()
{
    $.getJSON(frontController + AJAX_GetSelectedAttributesList, function (JSONObject) {

        console.log("AJAX request: AJAX_GetSelectedAttributesList " + JSON.stringify(JSONObject));
        selectedFoodAttributeJSONArray = JSONObject;
    });
}

function updateSelectedAttributes()
{   
    //iterate through each attribute
    var attributesJSON = selectedFoodAttributeJSONArray[0];
    for(var currentAttribute in attributesJSON)
    {
        var currentAttributeElementName = currentAttribute + "checkbox";
        var currentElement = document.getElementById(currentAttributeElementName);
        
        //if the a checkbox matching the attribute exists, take appropriate action
        if(currentElement !== null)
        {
            if(currentElement.checked)
            {
                attributesJSON[currentAttribute] = "t"; 
            }
            else
            {
                attributesJSON[currentAttribute] = "f"; 
            }
        }
    }

    //send updated selectedFoodAttributeJSON to server for severside update
    $.ajax({
        url: frontController + AJAX_ModifySelectedAttributes,
        type: "POST",
        data: JSON.stringify(selectedFoodAttributeJSONArray[0]),
        contentType: "application/json",
        success: function (data)
        {
            if (data === "true")
            {
                console.log("update selected attributes suceeded");
                getSelectedAttributeList();
            }
            else
            {
                console.log("update selected attributes failed");
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}

function editSelectedAttributes()
{
    console.log("editSelectedAttributes(): current selected attributes " + JSON.stringify(selectedFoodAttributeJSONArray));

    //clear form
    document.getElementById("editSelectedAttributesForm").reset();

    var attributesJSON = selectedFoodAttributeJSONArray[0];

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

    //populate form
//    if (selectedFoodObject.hasOwnProperty("foodname"))
//    {
//        document.getElementById("editfoodname").value = selectedFoodObject["foodname"];
//    }
//    if (selectedFoodObject.hasOwnProperty("protein"))
//    {
//        document.getElementById("editprotein").value = selectedFoodObject["protein"];
//    }
//    if (selectedFoodObject.hasOwnProperty("carbohydrate"))
//    {
//        document.getElementById("editcarbohydrate").value = selectedFoodObject["carbohydrate"];
//    }
//    if (selectedFoodObject.hasOwnProperty("fat"))
//    {
//        document.getElementById("editfat").value = selectedFoodObject["fat"];
//    }
//    if (selectedFoodObject.hasOwnProperty("calorie"))
//    {
//        document.getElementById("editcalorie").value = selectedFoodObject["calorie"];
//    }
}