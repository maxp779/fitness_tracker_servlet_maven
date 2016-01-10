/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    //document.getElementById("createAccountForm").action = "/"+serverAPI.requests.frontController +"/"+ serverAPI.requests.createAccountRequest;
    document.getElementById("loginPageForm").action = "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.loginPageRequest;

    $('#createAccountForm').submit(function (event) {
        event.preventDefault();
        
        if(globalValues.passwordValid)
        {
            createAccountRequestAJAX();
        }
    });
    
    //auto selects form input text when clicked
    $(document).on('click', 'input', function () {
        this.select();
    });

    
    //this adds an event from passwordStrength.js which controls globalValues.passwordValid
    passwordStrengthTester("password", "confirmPassword", "passwordStrength");

});

function createAccountRequestAJAX()
{
    var formData = $("#createAccountForm").serializeArray();
    var email = document.getElementById("email").value;
    $.ajax({
        url: "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.createAccountRequest,
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function (data)
        {
            document.getElementById("passwordStrength").innerHTML = "";
            
            if (data === "true")
            {
                console.log("valid credentials");
                document.getElementById("feedback").innerHTML = "<div class=\"alert alert-success\" role=\"alert\">Account created successfully for " + email + "</div>";
            } else
            {
                document.getElementById("feedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">" + errorCodes[data] + "</div>";
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}
