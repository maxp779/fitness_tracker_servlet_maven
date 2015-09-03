/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


var customFoodListJSON;
var selectedFood = null; //the id_customfood of the food currently selected by the user

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
        console.log("id_customfood " + selectedFood + " selected");
    });

    //remove food listener
    $(document).on("click", "#removeFoodButton", function () {
        removeCustomFood();
        console.log("remove food button clicked");
    });

    //edit food listener
    $(document).on("click", "#editFoodButton", function () {
        console.log("edit button clicked");
        editCustomFood(); //load the selected foods current values onto the form
    });

    //save food listener
//    $(document).on("click", "#saveEditButton", function () {
//        console.log("save button clicked");
//        saveEditedCustomFood();
//    });
    
    $(document).on("submit", "#editFoodForm", function (e) {
    console.log("EVENT FIRING!");
        saveEditedCustomFood();
            $('#editFoodModal').modal('hide');
        return false;
    });

    
//        $('#editFoodForm').submit(function () {
//            console.log("EVENT FIRING!");
//        saveEditedCustomFood();
//        return false;
//    });

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

        //populate the list, give the links an id that corresponds to the id_customfood value of the food from the database
        // e.g. if there are two foods named "tasty pie" the id_customfood will allow us to tell them apart
        for (var index in customFoodListJSON)
        {
            innerHTML = innerHTML.concat("<a href='javascript:void(0)' class='list-group-item' id='" + customFoodListJSON[index].id_customfood + "'>" + customFoodListJSON[index].foodname 
                    + "<br>"
                    + " <font color='green'>Protein: " + customFoodListJSON[index].protein + "</font>"
                    + " <font color='blue'>Carb: " + customFoodListJSON[index].carbohydrate + "</font>"
                    + " <font color='orange'>Fat: " + customFoodListJSON[index].fat + "</font>"
                    + " <font color='red'>Cals: " + customFoodListJSON[index].calorie + "</font>"                 
                    + "</a>"
                    );
        }
        document.getElementById("customFoodList").innerHTML = innerHTML;
    }
}

//AJAX REQUESTS
function getCustomFoodsList()
{
    //this is an AJAX request to the server, it invokes the AJAX_GetCustomFoodsServlet which returns a JSON object
    //containing all custom foods in full detail from the database
    $.getJSON(serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_GetCustomFoodList"], function (JSONObject) {

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
            url: serverAPI["requests"][frontController] + serverAPI["requests"][AJAX_RemoveCustomFood],
            type: "POST",
            data: JSON.stringify({id_customfood: selectedFood}),
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
        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_AddCustomFood"],
        type: "POST",
        data: JSON.stringify(outputJSON),
        contentType: "application/json",
        success: function (data)
        {
            if (data === "true")
            {
                console.log("food add suceeded");
                getCustomFoodsList();
                
                //clear form
                document.getElementById("addFoodForm").reset();
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
    var selectedFoodObject = findCustomFood(selectedFood);
    
    console.log(JSON.stringify(selectedFoodObject)); 
    
    //clear form
    document.getElementById("editFoodForm").reset();
    
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
    var outputJSON = {"id_customfood":selectedFood};
    for (var count in formData)
    {
        if (formData[count]["value"] !== "")
        {
            outputJSON[formData[count]["name"]] = formData[count]["value"];
        }
    }
    console.log("attempting to edit food " + JSON.stringify(outputJSON));
    $.ajax({
        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_EditCustomFood"],
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






//find food from id_customfood, this may be useful in future
function findCustomFood(id_customfood)
{
    var foodObject;

    for (var index in customFoodListJSON)
    {
        if (customFoodListJSON[index].id_customfood === id_customfood)
        {
            foodObject = customFoodListJSON[index];
        }
    }
    return foodObject;
}