/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var customFoodListJSON;
var selectedFood = null; //the id_food of the food currently selected by the user

//this focuses the page on the currently opened accordion panel
//$(function () {
//    $('#accordion1').on('shown.bs.collapse', function (e) {
//        var offset = $('.panel.panel-default > .panel-collapse.in').offset();
//        if (offset) {
//            $('html,body').animate({
//                scrollTop: $('.panel-title .bottomAccordion').offset().top - 20
//            }, 500);
//        }
//    });
//});

$(document).ready(function () {

    getCustomFoodsList();

    //listener to keep track of which food the user has selected
    $(document).on("click", ".list-group-item", function () {
        selectedFood = $(this).attr("id");
        console.log("id_food " + selectedFood + " selected");
    });

    //remove food listener
    $(document).on("click", "#removeFoodButton", function () {
        removeCustomFood();
        console.log("remove food button clicked");
    });

    //edit food listener
    $(document).on("click", "#editFoodButton", function () {
        editCustomFood(); //load the selected foods current values onto the form
        console.log("edit button clicked");
    });

    //save food listener
    $(document).on("click", "#saveEditButton", function () {
        saveEditedCustomFood();
        console.log("save button clicked");
    });

    //add food listener, not needed as form is used
//    $(document).on("click", "#addFoodButton", function () {
//        addCustomFood();
//    });
});

function populateCustomFoodList()
{
    //list must be emptied first, if not then the final list item will still show
    //even if it was deleted from the database
    $('#customFoodList').empty();

    //iterate through each array (database record) in the JSON
    for (var index in customFoodListJSON)
    {
        var innerHTML = "";

        //populate the list, give the links an id that corresponds to the id_food value of the food from the database
        // e.g. if there are two foods named "tasty pie" the id_food will allow us to tell them apart
        for (var index in customFoodListJSON)
        {
            innerHTML = innerHTML.concat("<a href=\"javascript:void(0)\" class=\"list-group-item\" id=\"" + customFoodListJSON[index].id_food + "\">" + customFoodListJSON[index].foodname + "</a>");
        }
        document.getElementById("customFoodList").innerHTML = innerHTML;
    }
}

//AJAX REQUESTS
function getCustomFoodsList()
{
    //this is an AJAX request to the server, it invokes the AJAX_GetCustomFoodsServlet which returns a JSON object
    //containing all custom foods in full detail from the database
    $.getJSON(frontController + AJAX_GetCustomFoodList, function (JSONObject) {

        console.log("getCustomFoodsList " + JSON.stringify(JSONObject));
        customFoodListJSON = JSONObject;
        populateCustomFoodList();

    });
}

function removeCustomFood()
{
    if (selectedFood !== null)
    {
        console.log("attempting to remove " + selectedFood);
        $.ajax({
            url: frontController + AJAX_RemoveCustomFood,
            type: "POST",
            data: JSON.stringify({id_food: selectedFood}),
            contentType: "application/json",
            success: function (data)
            {
                if (data === "true")
                {
                    console.log("removal suceeded");
                    selectedFood = null;
                    getCustomFoodsList();
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
        console.log("currently selected food is " + selectedFood + " no action taken");
    }
}

function addCustomFood()
{
    //get data from form, it is formatted as an array of JSON object with the
    //form data held in name/value pairs like so:
    //[{"name":"foodname", "value":"tasty pie"},{"name":"protein", "value":"25"}]
    var formData = $("#addFoodForm").serializeArray();

    //prepare the JSON to send to server
    var outputJSON = {};
    for (var count in formData)
    {
        if (formData[count]["value"] !== "")
        {
            outputJSON[formData[count]["name"]] = formData[count]["value"];
        }
    }
    console.log("attempting to add food " + JSON.stringify(outputJSON));
    $.ajax({
        url: frontController + AJAX_AddCustomFood,
        type: "POST",
        data: JSON.stringify(outputJSON),
        contentType: "application/json",
        success: function (data)
        {
            if (data === "true")
            {
                console.log("food add suceeded");
                getCustomFoodsList();
            }
            else
            {
                console.log("food add failed");
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}

function editCustomFood()
{
    var selectedFoodObject = findFood(selectedFood);
    
    console.log(JSON.stringify(selectedFoodObject)); 
    
    //clear form
    document.getElementById("editfoodname").value = "";
    document.getElementById("editprotein").value = "";
    document.getElementById("editcarbohydrate").value = "";
    document.getElementById("editfat").value = "";
    document.getElementById("editcalorie").value = "";
    
    //populate form
    if(selectedFoodObject.hasOwnProperty("foodname"))
    {
        document.getElementById("editfoodname").value = selectedFoodObject["foodname"];
    }
    if(selectedFoodObject.hasOwnProperty("protein"))
    {
        document.getElementById("editprotein").value = selectedFoodObject["protein"];
    }
    if(selectedFoodObject.hasOwnProperty("carbohydrate"))
    {
        document.getElementById("editcarbohydrate").value = selectedFoodObject["carbohydrate"];
    }
    if(selectedFoodObject.hasOwnProperty("fat"))
    {
        document.getElementById("editfat").value = selectedFoodObject["fat"];
    }
    if(selectedFoodObject.hasOwnProperty("calorie"))
    {
        document.getElementById("editcalorie").value = selectedFoodObject["calorie"];
    }
}

function saveEditedCustomFood()
{
    //get data from form, it is formatted as an array of JSON object with the
    //form data held in name/value pairs like so:
    //[{"name":"foodname", "value":"tasty pie"},{"name":"protein", "value":"25"}]
    var formData = $("#editFoodForm").serializeArray();

    //prepare the JSON to send to server
    var outputJSON = {"id_food":selectedFood};
    for (var count in formData)
    {
        if (formData[count]["value"] !== "")
        {
            outputJSON[formData[count]["name"]] = formData[count]["value"];
        }
    }
    console.log("attempting to edit food " + JSON.stringify(outputJSON));
    $.ajax({
        url: frontController + AJAX_EditCustomFood,
        type: "POST",
        data: JSON.stringify(outputJSON),
        contentType: "application/json",
        success: function (data)
        {
            if (data === "true")
            {
                console.log("food edit suceeded");
                getCustomFoodsList();
            }
            else
            {
                console.log("food edit failed");
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}






//find food from id_food, this may be useful in future
function findFood(id_food)
{
    var foodObject;

    for (var index in customFoodListJSON)
    {
        if (customFoodListJSON[index].id_food === id_food)
        {
            foodObject = customFoodListJSON[index];
        }
    }
    return foodObject;
}