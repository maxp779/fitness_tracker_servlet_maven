/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//this focuses the page on the currently opened accordion panel
$(function () {
    $('#accordion2').on('shown.bs.collapse', function (e) {
        var offset = $('.panel.panel-default > .panel-collapse.in').offset();
        if (offset) {
            $('html,body').animate({
                scrollTop: $('.panel-title .bottomAccordion').offset().top - 20
            }, 500);
        }
    });
});

/**
 * increment the viewDate the user is looking at
 * these two functions are used with the buttons near the datepickers
 * they will change the datepickers internal states
 * @returns {undefined}
 */
function incrementDate()
{
    viewDate.setDate(viewDate.getDate() + 1);
    $('#foodDatepicker').datepicker('setDate', viewDate);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', viewDate);
    $('#macroDatepicker').datepicker('update');
    //getEatenFoodList();
}
function decrementDate()
{
    viewDate.setDate(viewDate.getDate() - 1);
    $('#foodDatepicker').datepicker('setDate', viewDate);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', viewDate);
    $('#macroDatepicker').datepicker('update');
    //getEatenFoodList();
}

/**
 * A method to populate the custom food list for a particular user.
 * @returns {undefined} 
 */
function populateCustomFoodList()
{
    $('#customFoodList').empty();
    var innerHTML = "";

    //iterate through each JSON object (currentFood) in the Array
    //populates the foods eaten table, one row = one food item
    for (var index = 0; index < globalValues.customFoodsArray.length; index++)
    {
        var currentFoodJSON = globalValues.customFoodsArray[index];

        innerHTML = innerHTML.concat("<a href='javascript:void(0)' class='list-group-item customfood' id='" + currentFoodJSON["id_customfood"] + "customfood" + "'>"
                + globalFunctions.createFoodAttributesHTML(currentFoodJSON, "id_customfood")
                + "</a>"
                );

    }
    document.getElementById("customFoodList").innerHTML = innerHTML;
}

//figure this one out later...
var sort_by = function (field, reverse, primer) {

    var key = primer ?
            function (x) {
                return primer(x[field])
            } :
            function (x) {
                return x[field]
            };

    reverse = !reverse ? 1 : -1;

    return function (a, b) {
        return a = key(a), b = key(b), reverse * ((a > b) - (b > a));
    }
}

/**
 * A method to populate the search result list when the user searches the database
 * for a food.
 * @returns {undefined} 
 */
function populateSearchResultList()
{
    //empty table
    $('#searchResultList').empty();

    if ($.isEmptyObject(globalValues.searchResultsArray))
    {
        document.getElementById("searchResultList").innerHTML = "<li class='list-group-item searchresult'> No results </li>";
    } else
    {

        //sort list by size of "foodname" property
        globalValues.searchResultsArray.sort(function (a, b) {
            return a.foodname.length - b.foodname.length;
        });

        var innerHTML = "";

        //iterate through each JSON object (currentFood) in the Array
        //populates the foods eaten table, one row = one food item
        for (var index = 0; index < globalValues.searchResultsArray.length; index++)
        {
            var currentFoodObject = globalValues.searchResultsArray[index];
            innerHTML = innerHTML.concat("<div class='row'>"
                    + "<div class='col-sm-12'>"
                    + "<a href='javascript:void(0)' class='list-group-item searchresult' id='" + currentFoodObject["id_searchablefood"] + "searchablefood" + "'>"
                    + "<div class='row'>"
                    + "<div class='col-sm-8'>"

                    + globalFunctions.createFoodAttributesHTML(currentFoodObject, "id_searchablefood")

                    + "</div>"
                    + "<div class='col-sm-4'>"
                    + "<strong>Weight(g):</strong>"
                    + "<div class='input-group'>"
                    + "<span class='input-group-btn'>"
                    + "<button class='btn btn-primary decrementWeightButton' type='button' id='" + currentFoodObject["id_searchablefood"] + "decrementweight" + "'>-</button>"
                    + "</span>"
                    + "<input type='number' class='form-control searchresultInput' placeholder='Weight(g)' step='100' min='0' max='100000' id='" + currentFoodObject["id_searchablefood"] + "weight'" + "value='" + currentFoodObject["weight"] + "'>"
                    + "<span class='input-group-btn'>"
                    + "<button class='btn btn-primary incrementWeightButton' type='button' id='" + currentFoodObject["id_searchablefood"] + "incrementweight" + "'>+</button>"
                    + "</span>"
                    + "</div>"
                    + "</div>"
                    + "</div>"
                    + "</a>"

                    + "</div>"
                    + "</div>"
                    );
        }
        document.getElementById("searchResultList").innerHTML = innerHTML;
    }
}


/**
 * A function to populate the table containing the foods that 
 * the user has eaten
 * @returns {undefined}
 */
function populateEatenFoodList()
{
    $('#eatenFoodList').empty();

    var innerHTML = "";
    //iterate through each JSON object (currentFood) in the Array
    //populates the foods eaten table, one row = one food item
    for (var index = 0; index < globalValues.eatenFoodsArray.length; index++)
    {
        var currentFoodJSON = globalValues.eatenFoodsArray[index];

        innerHTML = innerHTML.concat("<div class='row'>"
                + "<div class='col-sm-12'>"
                + "<li class='list-group-item' id='" + currentFoodJSON["id_eatenfood"] + "eatenfood" + "'>"
                + "<div class='row'>"
                + "<div class='col-sm-8'>"

                + globalFunctions.createFoodAttributesHTML(currentFoodJSON)

                + "</div>"
                + "<div class='col-sm-4'>"
                + "<p><button type='button' class='btn btn-danger btn-md pull-right' id='" + currentFoodJSON["id_eatenfood"] + "eatenfoodremove" + "'>Remove <span class='glyphicon glyphicon-remove'></span></button>"
                + "<button type='button' class='btn btn-info btn-md pull-right' id='" + currentFoodJSON["id_eatenfood"] + "eatenfooddetails" + "'>Details <span class='glyphicon glyphicon-info-sign'></span></button></p>"
                + "</div>"
                + "</div>"
                + "</li>"
                + "</div>"
                + "</div>"
                );

    }
    document.getElementById("eatenFoodList").innerHTML = innerHTML;

}

/**
 * This method gets the date that the user has selected with the datepicker and returns it in UNIX time
 * format. This is the number of seconds since the 1st of january 1970
 * @returns {Number}
 */
function getSelectedUNIXdate()
{
    var currentDate = new Date();
    var selectedDate = $('#foodDatepicker').datepicker('getUTCDate');


    var currentDateUTCUNIX = Date.UTC(currentDate.getUTCFullYear(), currentDate.getUTCMonth(),
            currentDate.getUTCDate(), currentDate.getUTCHours(), currentDate.getUTCMinutes(), currentDate.getUTCSeconds());
    var selectedDateUNIX = Math.floor(selectedDate.getTime() / 1000); // we need /1000 to get seconds, otherwise milliseconds is returned
    var startOfCurrentDateUNIX = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());

    //if a date in the future or past i.e. tomorrow or yesterday is selected then
    //that will be used as the foods timestamp, otherwise todays date and time will
    //be used. This allows the user to modify a previous days food log or plan ahead
    //and modify a future dates food log.
    if (selectedDateUNIX > currentDateUTCUNIX || selectedDateUNIX < startOfCurrentDateUNIX)
    {
        return selectedDateUNIX;
    } else
    {
        return currentDateUTCUNIX;
    }
}

/**
 * A function to calculate the macros of a food based on the weight of the food.
 * The database only stores the food macros for 100g of each food.
 * So if user enters 250g of milk this method will calculate the amount of protein,carbs,fat,calories
 * for milk at that weight based off the values for 100g of milk.
 * 
 * @param {type} id_searchablefood
 * @param {type} aFoodJSON
 * @returns {JSON}
 */
function calculateMacrosFromWeight(id_searchablefood, foodObject)
{
    console.log("calculating macros for:" + id_searchablefood);
    var currentWeightID = id_searchablefood + "weight";
    var currentWeightValue = document.getElementById(currentWeightID).value;
    //ensure a valid weight is entered
    var maxWeight = 100000;
    var minWeight = 0;
    if (currentWeightValue < minWeight)
    {
        document.getElementById(currentWeightID).value = minWeight;
        currentWeightValue = minWeight;
    }

    if (currentWeightValue > maxWeight)
    {
        document.getElementById(currentWeightID).value = maxWeight;
        currentWeightValue = maxWeight;
    }

    //calculate the macros from the stored weight of the food
    var multiplier = currentWeightValue / foodObject["weight"];

    for (var aProperty in foodObject)
    {
        var currentValue = foodObject[aProperty];

        //if non operable e.g "foodname" then ignore
        if (globalValues.nonOperableAttributes.indexOf(aProperty) === -1)
        {
            //if operable but treated as float to 1 decimal place
            if (globalValues.wholeIntegerAttributes.indexOf(aProperty) === -1)
            {
                currentValue = currentValue * multiplier;
                foodObject[aProperty] = currentValue.toFixed(1);
            } else //if operable but integer
            {
                currentValue = currentValue * multiplier;
                foodObject[aProperty] = currentValue.toFixed(0);
            }
        }
    }

    return foodObject;
}

/**
 * This function calculated the total macros for all the foods eaten on the currently
 * selected date.
 * @param {type} callback
 * @returns {undefined}
 */
function calculateTotalMacros(callback)
{
    var totalMacrosToday = {};


    for (var index = 0; index < globalValues.eatenFoodsArray.length; index++)
    {
        var currentFoodJSON = globalValues.eatenFoodsArray[index];
        for (var aProperty in currentFoodJSON)
        {
            //if non operable e.g "foodname" then ignore
            if (globalValues.nonOperableAttributes.indexOf(aProperty) === -1 && !globalFunctions.isUndefinedOrNull(currentFoodJSON[aProperty]))
            {
                //if first occurrance of aProperty
                if (globalFunctions.isUndefinedOrNull(globalValues.totalMacrosToday[aProperty]))
                {
                    totalMacrosToday[aProperty] = currentFoodJSON[aProperty];
                } else
                {
                    totalMacrosToday[aProperty] = +totalMacrosToday[aProperty] + +currentFoodJSON[aProperty];
                }
            }
        }
    }

    if (globalFunctions.isUndefinedOrNull(totalMacrosToday.protein))
    {
        totalMacrosToday.protein = 0;
    }
    if (globalFunctions.isUndefinedOrNull(totalMacrosToday.carbohydrate))
    {
        totalMacrosToday.carbohydrate = 0;
    }
    if (globalFunctions.isUndefinedOrNull(totalMacrosToday.fat))
    {
        totalMacrosToday.fat = 0;
    }
    if (globalFunctions.isUndefinedOrNull(totalMacrosToday.calorie))
    {
        totalMacrosToday.calorie = 0;
    }
    console.log("calculateTotalMacros(): " + JSON.stringify(totalMacrosToday));

    setGlobalValues.setTotalMacrosToday(totalMacrosToday);

    if (callback)
    {
        callback();
    }
}


function updateGraphs(callback)
{
    var proteinEaten = parseInt(globalValues.totalMacrosToday.protein);
    var carbohydrateEaten = parseInt(globalValues.totalMacrosToday.carbohydrate);
    var fatEaten = parseInt(globalValues.totalMacrosToday.fat);

    var proteinGoal = parseInt(globalValues.userStats.protein_goal);
    var carbohydrateGoal = parseInt(globalValues.userStats.carbohydrate_goal);
    var fatGoal = parseInt(globalValues.userStats.fat_goal);

    var macroArray = [proteinEaten, carbohydrateEaten, fatEaten, proteinGoal, carbohydrateGoal, fatGoal];

    var macroChart;
    $(function () {
        // Create the chart
        macroChart = new Highcharts.Chart({
            // Create the chart
            chart: {
                renderTo: 'macroChart',
                type: 'bar'
            },
            title: {
                text: 'Todays macros & Macro goal',
            },
            xAxis: {
                categories: ['Protein', 'Protein goal', 'Carbohydrates', 'Carbohydrates goal', 'Fats', 'Fats goal']
            },
            yAxis: chartYAxis(macroArray, "Quantity (g)"),
            legend: {
                reversed: true
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true

                    }
                }
            },
            tooltip: {
                enabled: false
            },
            series: [{
                    name: 'Protein',
                    showInLegend: false,
                }]
        });
    });

    macroChart.series[0].setData([{
            name: "Protein eaten",
            y: proteinEaten,
            color: "green"
        }, {
            name: "Protein goal",
            y: proteinGoal,
            color: "green"
        }, {
            name: "Carbohydrates eaten",
            y: carbohydrateEaten,
            color: "blue"
        }, {
            name: "Carbohydrates goal",
            y: carbohydrateGoal,
            color: "blue"
        }, {
            name: "Fats eaten",
            y: fatEaten,
            color: "orange"
        }, {
            name: "Fats goal",
            y: fatGoal,
            color: "orange"
        }]);

    var calorieGoal = parseInt(globalValues.userStats.tee);
    var calorieEaten = parseInt(globalValues.totalMacrosToday.calorie);
    var calorieArray = [calorieGoal, calorieEaten];
    var calorieChart;

    $(function () {
        // Create the chart
        calorieChart = new Highcharts.Chart({
            // Create the chart
            chart: {
                renderTo: 'calorieChart',
                type: 'bar'
            },
            title: {
                text: 'Todays calories & Calorie goal',
            },
            xAxis: {
                categories: ['Calories', 'Calorie allowance']
            },
            yAxis: chartYAxis(calorieArray, "Quantity (kcal)"),
            legend: {
                reversed: true
            },
            plotOptions: {
                series: {
                    stacking: 'normal',
                    dataLabels: {
                        enabled: true

                    }
                }
            },
            tooltip: {
                enabled: false
            },
            series: [{
                    name: 'Protein',
                    showInLegend: false,
                }]
        });
    });

    calorieChart.series[0].setData([{
            name: "Calories eaten",
            y: calorieEaten,
            color: "red"
        }, {
            name: "Calorie goal",
            y: calorieGoal,
            color: "red"
        }]);

    if (callback)
    {
        callback();
    }
}

/**
 * This method is needed otherwise the chart looks weird with all zero values.
 * @param {type} macroArray
 * @returns {chartYAxis.yAxis}
 */
function chartYAxis(macroArray, titleText)
{
    var allZeroValues = true;
    var yAxis;

    for (var count = 0; count < macroArray.length; count++)
    {
        if (macroArray[count] === 0)
        {

        } else
        {
            allZeroValues = false;
        }
    }

    if (allZeroValues)
    {
        yAxis = {
            minPadding: 0,
            maxPadding: 0,
            min: 0,
            max: 1,
            showLastLabel: false,
            tickInterval: 1,
            title: {
                text: titleText
            }
        };
    } else
    {
        yAxis = {
            title: {
                text: titleText
            }
        };
    }

    return yAxis;
}




/**
 * Here the macros for foods are updated based on their weight. If the user searches for
 * "milk" and changes the default weight of 100 to 500 then this method is called and will
 * update the page reflecting that change. Instead of say 5g of protein it would show 25g etc.
 * 
 * @param {type} id
 * @returns {undefined}
 */
function updateSearchResultMacros(id)
{
    //get the numbers from the id, it is the id of the inputbox that is passed
    //which takes the form of "id_searchablefood + weight" e.g. 2500weight
    //we need the number which alone links to the searchablefood we need to change
    var id_searchablefood = id.replace(/[a-z]/g, '');
    console.log("updating macros for:" + id_searchablefood);
    var id_searchablefoodmacros = document.getElementById(id_searchablefood + "macros");
    var currentFood = {};


    for (var aFood in globalValues.searchResultsArray)
    {
        if (globalValues.searchResultsArray[aFood].id_searchablefood === id_searchablefood)
        {
            var matchingFood = globalValues.searchResultsArray[aFood];
            for (var currentProperty in matchingFood)
            {
                currentFood[currentProperty] = matchingFood[currentProperty];
            }
        }
    }
    var updatedFood = calculateMacrosFromWeight(id_searchablefood, currentFood);

    var innerHTML = globalFunctions.createFoodAttributesHTML(updatedFood, "id_searchablefood");
    id_searchablefoodmacros.innerHTML = innerHTML;

}

function updateMacrosNeededPanel()
{
    var macroPanel = document.getElementById("currentMacros");
    var innerHTML = "";
    var proteinNeeded = parseInt(globalValues.userStats.protein_goal) - parseInt(globalValues.totalMacrosToday.protein);
    var carbohydrateNeeded = parseInt(globalValues.userStats.carbohydrate_goal) - parseInt(globalValues.totalMacrosToday.carbohydrate);
    var fatNeeded = parseInt(globalValues.userStats.fat_goal) - parseInt(globalValues.totalMacrosToday.fat);
    var calorieRemaining = parseInt(globalValues.userStats.tee) - parseInt(globalValues.totalMacrosToday.calorie);

    if (proteinNeeded <= 0)
    {
        proteinNeeded = " Goal reached <span class='glyphicon glyphicon-ok'></span>";
    }
    if (carbohydrateNeeded <= 0)
    {
        carbohydrateNeeded = " Goal reached <span class='glyphicon glyphicon-ok'></span>";
    }
    if (fatNeeded <= 0)
    {
        fatNeeded = " Goal reached <span class='glyphicon glyphicon-ok'></span>";
    }
    if (calorieRemaining <= 0)
    {
        calorieRemaining = " Goal reached <span class='glyphicon glyphicon-ok'></span>";
    }


    innerHTML = innerHTML.concat("<p><font color='green'>Protein needed: " + proteinNeeded + "</font></p>"
            + "<p><font color='blue'>Carbs needed: " + carbohydrateNeeded + "</font></p>"
            + "<p><font color='orange'>Fats needed: " + fatNeeded + "</font></p>"
            + "<hr>"
            + "<p>Calories left to eat: " + calorieRemaining + "</p>");

    macroPanel.innerHTML = innerHTML;
}

function updateMainPage()
{
    populateEatenFoodList();
    populateCustomFoodList();
    populateSearchResultList();

    calculateTotalMacros(function () {
        updateGraphs();
        updateMacrosNeededPanel();
    });
}