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
            url: serverAPI.requests.MODIFY_USER_STATS,
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
            url: serverAPI.requests.MODIFY_USER_STATS,
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

