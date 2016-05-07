/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var urlParams;
var requestEmail;

$(document).ready(function () {

    //document.getElementById("changePasswordForm").action = "/"+serverAPI.requests.frontController +"/"+ serverAPI.requests.changePasswordRequest;
    document.getElementById("loginPageForm").action = serverAPI.requests.LOGIN_PAGE_REQUEST;
    document.getElementById("forgotPasswordForm").action = serverAPI.requests.FORGOT_PASSWORD_PAGE_REQUEST;


    //var currentURL = window.location.href;
    //var identifierTokenSearchParameter = "?";
    //var identifierTokenLocation = currentURL.search(identifierTokenSearchParameter);
    //
    getQueryStringParameters(function ()
    {
        getEmail();
    });


    $('#changePasswordForm').submit(function (event) {
        event.preventDefault();
        if (globalValues.passwordValid)
        {
            changePasswordRequestAJAX();
        }
    });

    passwordStrengthTester("password", "confirmPassword", "passwordStrength");

    //auto selects form input text when clicked
    $(document).on('click', 'input', function () {
        this.select();
    });



//    location.search="?";   
//    var identifierToken = location.search;


//function getParameterByName(name) {
//    name = name.replace(/[\[]/, "\\.).replace(/[\]]/, "\\]");
//    var regex = new RegExp("[\\?&]" + name + "=([^&#]*)"),
//        results = regex.exec(location.search);
//    return results === null ? "" : decodeURIComponent(results[1].replace(/\+/g, " "));
//}
//Usage:
//
//var prodId = getParameterByName('prodId');

    //console.log(identifierToken);


});

function getQueryStringParameters(callback)
{
    var match,
            pl = /\+/g, // Regex for replacing addition symbol with a space
            search = /([^&=]+)=?([^&]*)/g,
            decode = function (s) {
                return decodeURIComponent(s.replace(pl, " "));
            },
            query = window.location.search.substring(1);

    urlParams = {};
    while (match = search.exec(query))
        urlParams[decode(match[1])] = decode(match[2]);

    if (callback)
    {
        callback();
    }
}

function getEmail()
{
    var identifierToken = urlParams.identifierToken;
    console.log(identifierToken);
    $.ajax({
        url: serverAPI.requests.GET_IDENTIFIER_TOKEN_EMAIL,
        type: "POST",
        data: identifierToken.toString(),
        contentType: "text/plain",
        success: function (returnedEmail)
        {

            requestEmail = returnedEmail;
//            if (returnedEmail !== null)
//            {
//                document.getElementById("email").value = returnedEmail;
//            } else
//            {
//
//            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}

function changePasswordRequestAJAX()
{
    var formData = $("#changePasswordForm").serializeArray();

    var identifierToken = {};
    identifierToken.name = "identifierToken";
    identifierToken.value = urlParams.identifierToken;

    var email = {};
    email.name = "email";
    email.value = requestEmail;

    formData.push(identifierToken);
    formData.push(email);

    $.ajax({
        url: serverAPI.requests.CHANGE_PASSWORD_REQUEST,
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function (data)
        {
            var returnObject = JSON.parse(data);
            
            document.getElementById("passwordStrength").innerHTML = "";
            if (returnObject.success === "true")
            {
                console.log("valid credentials");
                document.getElementById("feedback").innerHTML = "<div class=\"alert alert-success\" role=\"alert\">Password changed successfully for email " + requestEmail + "</div>";
            } else
            {
                document.getElementById("feedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">" + errorCodes[returnObject.errorCode]
                        + " please make a new change password request </div>";
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}