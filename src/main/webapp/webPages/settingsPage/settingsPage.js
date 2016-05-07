/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var emailValid = false;

$(document).ready(function () {

    document.getElementById("deleteAccountForm").action = serverAPI.requests.DELETE_ACCOUNT_REQUEST;

    globalFunctions.refreshGlobalValuesFromLocalStorage(function () {
        globalFunctions.showSelectedAttributes();
    });

    //to tick/untick the checkboxes on the selected attributes form showing which are currently selected
    $('#editSelectedAttributesForm').submit(function (event) {
        event.preventDefault();

        var newFoodAttributes = {};

        getNewFoodAttributes(newFoodAttributes, function ()
        {
            globalFunctionsAJAX.updateSelectedAttributes(newFoodAttributes, function () {
                globalFunctions.showSelectedAttributes();
            });
        });
    });

    $('#changePasswordForm').submit(function (event) {
        event.preventDefault();
        if (globalValues.passwordValid)
        {
            changePasswordRequestAJAX();
        }
    });

    $('#changeEmailForm').submit(function (event) {
        event.preventDefault();
        if (globalValues.emailValid)
        {
            changeEmailRequestAJAX();
        }
    });

//    $('#oldPassword, #newPassword, #confirmNewPassword').on('keyup', function () {
//        document.getElementById("passwordFeedback").innerHTML = "";
//    });

    $("#changePasswordForm :input").focus(function () {
        document.getElementById("passwordFeedback").innerHTML = "";
    });

    $("#changeEmailForm :input").focus(function () {
        document.getElementById("emailFeedback").innerHTML = "";
    });

    $("#editSelectedAttributesForm :input").change(function () {
        document.getElementById("attributeFeedback").innerHTML = "";
    });

    //auto selects form input text when clicked
    $(document).on('click', 'input', function () {
        this.select();
    });

    //this adds an event from passwordStrength.js which gives user feedback and controls globalValues.passwordValid
    passwordStrengthTester("newPassword", "confirmNewPassword", "passwordStrength");
    //this adds an event from emailValid.js which gives user feedback and controls globalValues.emailValid
    emailMatchValidator("newEmail", "confirmNewEmail", "emailFeedback");
});

function emailsMatch()
{
    return document.getElementById("newEmail").value === document.getElementById("confirmNewEmail").value;
}

function changePasswordRequestAJAX()
{
    var formData = $("#changePasswordForm").serializeArray();
    $.ajax({
        url: serverAPI.requests.CHANGE_PASSWORD_REQUEST,
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function (data)
        {
            var returnObject = JSON.parse(data);

            if (returnObject.success === "true")
            {
                console.log("password changed");
                document.getElementById("passwordStrength").innerHTML = "";
                document.getElementById("changePasswordForm").reset();
                document.getElementById("passwordFeedback").innerHTML = "<div class=\"alert alert-success\" role=\"alert\">Password changed successfully for " + returnObject.email + "</div>";
            } else
            {
                document.getElementById("passwordStrength").innerHTML = "";
                document.getElementById("passwordFeedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">" + errorCodes[returnObject.errorCode] + ", no action taken</div>";
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}


function getNewFoodAttributes(newFoodAttributesObject, callback) {
    for (var currentAttribute in globalValues.foodAttributes)
    {
        var currentAttributeElementName = currentAttribute + "checkbox";
        var currentElement = document.getElementById(currentAttributeElementName);
        //if the a checkbox matching the attribute exists, take appropriate action
        if (currentElement !== null)
        {
            if (currentElement.checked)
            {
                newFoodAttributesObject[currentAttribute] = "t";
            } else
            {
                newFoodAttributesObject[currentAttribute] = "f";
            }
        }
    }
    if (callback)
    {
        callback();
    }
}

function changeEmailRequestAJAX()
{
    var formData = $("#changeEmailForm").serializeArray();
    $.ajax({
        url: serverAPI.requests.CHANGE_EMAIL_REQUEST,
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function (data)
        {
            var returnObject = JSON.parse(data);
            //{"success":"true", "oldEmail":"123@123.com", "newEmail":"bawbags@baws.com", "errorCode":"13"}

            if (returnObject.success === "true")
            {
                console.log("email changed");
                document.getElementById("changeEmailForm").reset();
                document.getElementById("emailFeedback").innerHTML = "<div class=\"alert alert-success\" role=\"alert\">Email changed successfully from "
                        + returnObject.oldEmail
                        + " to "
                        + returnObject.newEmail
                        + "</div>";
            } else
            {
                document.getElementById("emailFeedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">" + errorCodes[returnObject.errorCode] + ", no action taken</div>";
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}