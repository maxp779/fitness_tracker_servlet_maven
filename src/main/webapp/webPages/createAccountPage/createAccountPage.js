/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    document.getElementById("loginPageForm").action = serverAPI.requests.LOGIN_PAGE_REQUEST;

    $('#createAccountForm').submit(function (event) {
        event.preventDefault();
        
        if(globalValues.passwordValid)
        {
            createAccountRequestAjax();
        }
    });
    
    //auto selects form input text when clicked
    $(document).on('click', 'input', function () {
        this.select();
    });

    
    //this adds an event from passwordStrength.js which controls globalValues.passwordValid
    passwordStrengthTester("password", "confirmPassword", "passwordStrength");

});

function createAccountRequestAjax()
{
    var formData = $("#createAccountForm").serializeArray();
    var email = document.getElementById("email").value;
    $.ajax({
        url: serverAPI.requests.CREATE_ACCOUNT_REQUEST,
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
