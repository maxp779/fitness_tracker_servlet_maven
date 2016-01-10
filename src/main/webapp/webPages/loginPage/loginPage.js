/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//$(document).ready(function () {
//    getAPI(function () {
//        setupserverAPI(function () {
//            setupLoginPage();
//        });
//    });
//});


//function getServerAPI(callback)
//{
//    $.ajax({
//        url: serverAPI.requests.frontController + serverAPI.requests.AJAX_SearchForFood,
//        type: "GET",
//        data: JSON.stringify(searchInputJSON),
//        contentType: "application/json",
//        dataType: "json",
//        success: function (APIString)
//        {
//            localStorage.setItem("serverAPI",APIString);
//            if (callback)
//            {
//                callback();
//            }
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//        }
//    });
//}

//function setupserverAPI(callback)
//{
//    serverAPI = localStorage.getItem('serverAPI');
//    
//    if(callback)
//    {
//        callback();
//    }
//}

$(document).ready(function () {

    //document.getElementById("loginForm").action = "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.loginRequest;
    document.getElementById("createAccountForm").action = "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.createAccountPageRequest;
    document.getElementById("forgotPasswordForm").action = "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.forgotPasswordPageRequest;

    //autologin for development
    document.getElementById("email").value = "test@test.com";
    document.getElementById("password").value = "testtest";


    $('#loginForm').submit(function () {
        loginRequestAJAX();
        return false;
    });

    $("#loginForm :input").change(function () {
        document.getElementById("feedback").innerHTML = "";
    });

    //auto selects form input text when clicked
    $(document).on('click', 'input', function () {
        this.select();
    });

});

function loginRequestAJAX()
{
    //get data from form, it is formatted as an array of JSON objects with the
    //form data held in name/value pairs like so:
    //[{"name":"email", "value":"test@test.com"},{"name":"password", "value":"testtest"}]
    var formData = $("#loginForm").serializeArray();

    $.ajax({
        url: "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.loginRequest,
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function (data)
        {
            var returnObject = JSON.parse(data);
            
            if (returnObject.success === "true")
            {
                console.log("valid credentials");

                window.location = "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.mainPageRequest;
            } else
            {
                document.getElementById("feedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">" + errorCodes[returnObject.errorCode] + " please try again</div>";
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}