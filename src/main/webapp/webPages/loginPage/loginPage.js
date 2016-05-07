/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {

    getServerAPI(function () {
        setupForms();
    });

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

function setupForms()
{
    //document.getElementById("loginForm").action = serverAPI.requests.LOGIN_REQUEST;
    document.getElementById("createAccountForm").action = serverAPI.requests.CREATE_ACCOUNT_PAGE_REQUEST;
    document.getElementById("forgotPasswordForm").action = serverAPI.requests.FORGOT_PASSWORD_PAGE_REQUEST;

    //autologin for development
    document.getElementById("email").value = "test@test.com";
    document.getElementById("password").value = "testtest";
}

function getServerAPI(callback)
{
    $.ajax({
        dataType: "json",
        type: "GET",
        url: "/FrontControllerServlet/GET_SERVER_API",
        success: function (returnedJSON)
        {
            console.log(returnedJSON);
            if (returnedJSON.success === true)
            {
                serverAPI = returnedJSON.data;
                localStorage.setItem("serverAPI", JSON.stringify(returnedJSON.data));
                if (callback)
                {
                    callback();
                }
            }
            else
            {
                console.log("Error 0: Failed to fetch API from server");
            }

        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}


function loginRequestAJAX()
{
    //get data from form, it is formatted as an array of JSON objects with the
    //form data held in name/value pairs like so:
    //[{"name":"email", "value":"test@test.com"},{"name":"password", "value":"testtest"}]
    var formData = $("#loginForm").serializeArray();
    
    $.ajax({
        url: serverAPI.requests.LOGIN_REQUEST,
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function (data)
        {
            var returnObject = JSON.parse(data);

            if (returnObject.success === "true")
            {
                console.log("valid credentials");

                window.location = serverAPI.requests.MAIN_PAGE_REQUEST;
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