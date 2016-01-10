/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//MEN: BMR = [9.99 x weight (kg)] + [6.25 x height (cm)] - [4.92 x age (years)] + 5
//WOMEN: BMR = [9.99 x weight (kg)] + [6.25 x height (cm)] - [4.92 x age (years)] -161
function calculateMacros()
{
    globalValues.userStats.sex = $('input[name="sex"]:checked').val();
    globalValues.userStats.weight = $('input[name="weight"]').val();
    globalValues.userStats.height = $('input[name="height"]').val();
    globalValues.userStats.age = $('input[name="age"]').val();
    globalValues.userStats.activitylevel = $('#activitylevel').val(); //this should be a float
    globalValues.userStats.goal = $('input[name="goal"]:checked').val();

    //BMR = Basic Metabolic Rate, this is calories required to stay alive   
    //calculate BMR
    if (globalValues.userStats.sex === "m")
    {
        globalValues.userStats.bmr = Math.round((9.99 * globalValues.userStats.weight) + (6.25 * globalValues.userStats.height) - (4.92 * globalValues.userStats.age) + 5);
    }
    else
    {
        globalValues.userStats.bmr = Math.round((9.99 * globalValues.userStats.weight) + (6.25 * globalValues.userStats.height) - (4.92 * globalValues.userStats.age) - 161);
    }

    //TEE = Total Energy Expenditure, this is calories required including activity level
    //calculate TEE
    globalValues.userStats.tee = globalValues.userStats.bmr * globalValues.userStats.activitylevel; //this should be a float

    //goalTEE = Ideal Total Energy Expenditure based on the users goal, it is normal TEE +15% to gain weight or -15% to lose weight
    //calculate goalTEE
    if (globalValues.userStats.goal === "gain")
    {
        globalValues.userStats.tee_goal = Math.round(globalValues.userStats.tee * 1.15);
    }
    else if (globalValues.userStats.goal === "lose")
    {
        globalValues.userStats.tee_goal = Math.round(globalValues.userStats.tee * 0.85);
    }
    else
    {
        globalValues.userStats.tee_goal = globalValues.userStats.tee;
    }

    //calculate nutrient macros
    globalValues.userStats.protein_goal = globalValues.userStats.weight * 2;
    globalValues.userStats.fat_goal = globalValues.userStats.weight * 1;

    //1g of protein = 4 calories, 1g of fat = 9 calories, 1g of carbs = 4 calories.
    globalValues.userStats.proteincalorie = globalValues.userStats.protein_goal * 4;
    globalValues.userStats.fatcalorie = globalValues.userStats.fat_goal * 9;
    globalValues.userStats.carbohydratecalorie = globalValues.userStats.tee_goal - (globalValues.userStats.proteincalorie + globalValues.userStats.fatcalorie);

    globalValues.userStats.carbohydrate_goal = globalValues.userStats.carbohydratecalorie / 4;
    console.log(JSON.stringify(globalValues.userStats));

    updateCalculationResult();
}

function updateCalculationResult()
{
    var calculationResultElement = document.getElementById("calculationResult");
    var innerHTML = "";

    innerHTML = innerHTML.concat("<p><font color='green'>Daily protein: " + globalValues.userStats.protein_goal + "</font></p>"
            + "<p><font color='blue'>Daily carbohydrates: " + globalValues.userStats.carbohydrate_goal + "</font></p>"
            + "<p><font color='orange'>Daily fats: " + globalValues.userStats.fat_goal + "</font></p>"
            + "<hr>"
            + "<p>Calories from protein: " + globalValues.userStats.proteincalorie + "</p>"
            + "<p>Calories from carbohydrates: " + globalValues.userStats.carbohydratecalorie + "</p>"
            + "<p>Calories from fats: " + globalValues.userStats.fatcalorie + "</p>"
            + "<p>Total daily calories: " + globalValues.userStats.tee_goal + "</p>"
            + "<hr>"
            + "<p>Any value shown may not be 100% accurate. Please consult your physician before starting any diet or nutrition plan.</p>");

    calculationResultElement.innerHTML = innerHTML;
}

function populateUserStats(callback)
{
    var currentStatsElement = document.getElementById("currentMacros");
    var innerHTML = "";

    innerHTML = innerHTML.concat("<p><font color='green'>Daily protein: " + globalValues.userStats.protein_goal + "</font></p>"
            + "<p><font color='blue'>Daily carbohydrates: " + globalValues.userStats.carbohydrate_goal + "</font></p>"
            + "<p><font color='orange'>Daily fats: " + globalValues.userStats.fat_goal + "</font></p>"
            + "<hr>"
            + "<p>Calories from protein: " + (globalValues.userStats.protein_goal *4) + "</p>"
            + "<p>Calories from carbohydrates: " + (globalValues.userStats.carbohydrate_goal *4) + "</p>"
            + "<p>Calories from fats: " + (globalValues.userStats.fat_goal *9) + "</p>"
            + "<p>Total daily calories: " + globalValues.userStats.tee + "</p>");

    currentStatsElement.innerHTML = innerHTML;
    
    if(callback)
    {
        callback();   
    }
}

function updateMyStatsPieChart(callback)
{
    var currentMacroSplitPie;
    $(function () {
        // Create the chart
        currentMacroSplitPie = new Highcharts.Chart({
            // Create the chart
            chart: {
                renderTo: 'currentMacroSplitPie',
                type: 'pie'
            },
            credits: {
                enabled: false
            },
            title: {
                text: 'Current daily split (%)'
            },
            yAxis: {
                title: {
                    text: 'Total percent market share'
                }
            },
            plotOptions: {
                pie: {
                    shadow: false
                }
            },
            tooltip: {
                enabled: false
//                formatter: function() {
//                    return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
//                }
            },
            series: [{
                    name: 'Macro Split',
                    showInLegend: false,
                    data: [],
                    size: '35%',
                    innerSize: '60%',
                    dataLabels: {
                        enabled: true,
                        formatter: function () {
                            return this.point.name + ' ' + Math.round(this.percentage) + ' %';
                        },
                        distance: 5,
                        color: 'black'
                    }
                }]
        });
        
        var protein_goal = parseInt(globalValues.userStats.protein_goal);
        var carbohydrate_goal = parseInt(globalValues.userStats.carbohydrate_goal);
        var fat_goal = parseInt(globalValues.userStats.fat_goal);
        
        if (protein_goal === 0 && carbohydrate_goal === 0 && fat_goal === 0)
        {
            currentMacroSplitPie.series[0].setData([]);
        }
        else
        {
            currentMacroSplitPie.series[0].setData([{
                    name: "Protein",
                    y: protein_goal,
                    color: "green"
                }, {
                    name: "Carbs",
                    y: carbohydrate_goal,
                    color: "blue"
                }, {
                    name: "Fats",
                    y: fat_goal,
                    color: "orange"
                }]);
        }
    });
    
    if(callback)
    {
        callback();   
    }
}