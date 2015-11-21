/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
$(document).ready(function () {
    
    document.getElementById("changePasswordForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["changePasswordRequest"];
    document.getElementById("changeEmailForm").action = "/"+serverAPI["requests"]["frontController"] +"/"+ serverAPI["requests"]["changeEmailRequest"];

//    $("#changeEmailForm").submit(function (event) {
//        event.preventDefault(); //this prevents the default actions of the form
//        
//        //code to change email goes here
//       
//    });
    
    
//    $("#changePasswordForm").submit(function (event) {
//        event.preventDefault(); //this prevents the default actions of the form
//        
//        //code to change password goes here
//       
//    });

});