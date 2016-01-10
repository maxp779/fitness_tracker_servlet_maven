/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


$(document).ready(function () {
    
    //document.getElementById("forgottenPasswordForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["forgotPasswordEmailRequest"];
    document.getElementById("loginPageForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["loginPageRequest"];
    
    $('#forgottenPasswordForm').submit(function () {
        forgottonPasswordRequestAJAX();
        return false;
    });
    
    $('#email').on('keyup', function (event) {
        document.getElementById("feedback").innerHTML = "";
    });
    
    //auto selects form input text when clicked
    $(document).on('click', 'input', function () {
        this.select();
    });

});



function forgottonPasswordRequestAJAX()
{  
    var formData = $("#forgottenPasswordForm").serializeArray();
    var email = document.getElementById("email").value;
    $.ajax({
        url: "/" + serverAPI.requests.frontController + "/" + serverAPI.requests.forgotPasswordEmailRequest,
        type: "POST",
        data: JSON.stringify(formData),
        contentType: "application/json",
        success: function (data)
        {
            if (data === "true")
            {
                console.log("valid credentials");
                document.getElementById("feedback").innerHTML = "<div class=\"alert alert-success\" role=\"alert\">Email sent to "+email+" with details on how to reset your password.</div>";
            } else
            {
                document.getElementById("feedback").innerHTML = "<div class=\"alert alert-danger\" role=\"alert\">Error: "+errorCodes[data]+"</div>";
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}