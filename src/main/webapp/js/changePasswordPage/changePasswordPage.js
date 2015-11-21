/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
var urlParams;

$(document).ready(function () {

    document.getElementById("changePasswordForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["changePasswordRequest"];
    document.getElementById("loginPageForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["loginPageRequest"];


    //var currentURL = window.location.href;
    //var identifierTokenSearchParameter = "?";
    //var identifierTokenLocation = currentURL.search(identifierTokenSearchParameter);
    getQueryStringParameters(function()
    {
        getEmail();
    });

//    location.search="?";   
//    var identifierToken = location.search;


//function getParameterByName(name) {
//    name = name.replace(/[\[]/, "\\[").replace(/[\]]/, "\\]");
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
        pl     = /\+/g,  // Regex for replacing addition symbol with a space
        search = /([^&=]+)=?([^&]*)/g,
        decode = function (s) { return decodeURIComponent(s.replace(pl, " ")); },
        query  = window.location.search.substring(1);

    urlParams = {};
    while (match = search.exec(query))
       urlParams[decode(match[1])] = decode(match[2]);
   
   if(callback)
   {
       callback();
   }
}

function getEmail()
{
    var identifierToken = urlParams["identifierToken"];
    console.log(identifierToken);
    $.ajax({
        url: "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["AJAX_GetIdentifierTokenEmail"],
        type: "POST",
        data: identifierToken.toString(),
        contentType: "text/plain",
        success: function (returnedEmail)
        {
            if (returnedEmail !== null)
            {
                document.getElementById("email").value = returnedEmail;
            }
            else
            {
                
            }
        },
        error: function (xhr, status, error)
        {
            // check status && error
            console.log("ajax failed");
        }
    });
}
