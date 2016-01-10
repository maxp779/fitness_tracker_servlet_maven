/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

function updateUserStatsManually()
{
    //iterate through each stat
    if ($.isEmptyObject(globalValues.userStats))
    {
        
    }
    else
    {
        //send updated stats to server for severside update
        $.ajax({
            url: "/"+serverAPI.requests.frontController +"/"+ serverAPI.requests.AJAX_ModifyUserStats,
            type: "POST",
            data: JSON.stringify(globalValues.userStats),
            contentType: "application/json",
            success: function (data)
            {
                if (data === "true")
                {
                    console.log("save user stats suceeded");
                    populateUserStats();
                    updateMyStatsPieChart();
                }
                else
                {
                    console.log("save user stats failed");
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
            }
        });
    }
}

function updateUserStats()
{
    //iterate through each stat
    if ($.isEmptyObject(globalValues.userStats))
    {
        
    }
    else
    {
        //send updated stats to server for severside update
        $.ajax({
            url: "/"+serverAPI.requests.frontController +"/"+ serverAPI.requests.AJAX_ModifyUserStats,
            type: "POST",
            data: JSON.stringify(globalValues.userStats),
            contentType: "application/json",
            success: function (data)
            {
                if (data === "true")
                {
                    console.log("save user stats suceeded");
                    populateUserStats();
                    updateMyStatsPieChart();
                }
                else
                {
                    console.log("save user stats failed");
                }
            },
            error: function (xhr, status, error)
            {
                // check status && error
                console.log("ajax failed");
            }
        });
    }
}

//function getUserStats(callback)
//{
//    $.ajax({
//        url: serverAPI["requests"]["frontController"] + serverAPI["requests"]["AJAX_GetUserStats"],
//        type: "GET",
//        //async : false,
//        dataType: "json",
//        success: function (returnedJSON)
//        {
//            if ($.isEmptyObject(returnedJSON))
//            {
//                console.log("get user stats failed" + returnedJSON);
//            }
//            else
//            {
//                console.log("get user stats succeded" + returnedJSON);
//                globalValues["userStats"] = returnedJSON[0];
////                populateUserStats();
////                updateMyStatsPieChart();
//            }
//            if (callback)
//            {
//                callback();
//            }
//        },
//        error: function (xhr, status, error)
//        {
//            // check status && error
//            console.log("ajax failed");
//            if (callback)
//            {
//                callback();
//            }
//        }
//    });
//}


