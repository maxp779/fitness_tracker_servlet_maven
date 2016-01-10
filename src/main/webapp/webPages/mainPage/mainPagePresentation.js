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


//create a DD/MM/YYYY string from a dateObject
//function getDateString(dateObject)
//{
//    var day = dateObject.getDate();
//    var month = dateObject.getMonth() + 1;
//    var year = dateObject.getFullYear();
//    if (day < 10) {
//        day = '0' + day;
//    }
//
//    if (month < 10) {
//        month = '0' + month;
//    }
//
//    var aDateString = day + '/' + month + '/' + year;
//    return aDateString;
//}

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
    //empty list
    $('#customFoodList').empty();


    var innerHTML = "";
    //iterate through each JSON object (currentFood) in the Array
    //populates the foods eaten table, one row = one food item
//    for (var currentFood in globalValues["eatenFoodsArray"])
    for (var index = 0; index < globalValues["customFoodsArray"].length; index++)
    {
        var currentFoodJSON = globalValues["customFoodsArray"][index];

        innerHTML = innerHTML.concat("<a href='javascript:void(0)' class='list-group-item customfood' id='" + currentFoodJSON["id_customfood"] + "customfood" + "'>"
                + globalFunctions.createFoodAttributesHTML(currentFoodJSON, "id_customfood")
                + "</a>"
                );


//        innerHTML = innerHTML.concat("<div class='row'>"
//                + "<div class='col-sm-12'>"
//
//
//
//                + "<li class='list-group-item' id='" + currentFoodJSON["id_eatenfood"] + "eatenfood" + "'>"
//                + "<div class='row'>"
//                + "<div class='col-sm-8'>"
//
//                + globalFunctions.createFoodAttributesHTML(currentFoodJSON)
//
//                + "</div>"
//                + "<div class='col-sm-4'>"
//                + "<p><button type='button' class='btn btn-danger btn-md pull-right' id='" + currentFoodJSON["id_eatenfood"] + "eatenfoodremove" + "'>Remove <span class='glyphicon glyphicon-remove'></span></button>"
//                + "<button type='button' class='btn btn-info btn-md pull-right' id='" + currentFoodJSON["id_eatenfood"] + "eatenfooddetails" + "'>Details <span class='glyphicon glyphicon-info-sign'></span></button></p>"
//
//
//
//                + "</div>"
//                + "</div>"
//                + "</li>"
//                + "</div>"
//                + "</div>"
//                );

    }
    document.getElementById("customFoodList").innerHTML = innerHTML;


//    //list must be emptied first, if not then the final list item will still show
//    //even if it was deleted from the database
//    $('#customFoodList').empty();
//
//    var innerHTML = "";
//    //iterate through each JSON (database record) in the Array
//    //populate the list, give the links an id that corresponds to the id_customfood value of the food from the database
//    // e.g. if there are two foods named "tasty pie" the id_customfood will allow us to tell them apart
//    for (var currentFood in globalValues["customFoodsArray"])
//    {
//        innerHTML = innerHTML.concat("<a href='javascript:void(0)' class='list-group-item customfood' id='" + globalValues["customFoodsArray"][currentFood].id_customfood + "customfood" + "'>" + globalValues["customFoodsArray"][currentFood].foodname
//                + "<br>"
//                + " <font color='green'>Protein: " + globalValues["customFoodsArray"][currentFood].protein + "</font>"
//                + " <font color='blue'>Carb: " + globalValues["customFoodsArray"][currentFood].carbohydrate + "</font>"
//                + " <font color='orange'>Fat: " + globalValues["customFoodsArray"][currentFood].fat + "</font>"
//                + " <font color='red'>Cals: " + globalValues["customFoodsArray"][currentFood].calorie + "</font>"
//                + "</a>"
//                );
//    }
//
//    document.getElementById("customFoodList").innerHTML = innerHTML;
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

    if ($.isEmptyObject(globalValues["searchResultsArray"]))
    {
        document.getElementById("searchResultList").innerHTML = "<li class='list-group-item searchresult'> No results </li>";
    } else
    {
        
        //sort list by size of "foodname" property
        globalValues["searchResultsArray"].sort(function (a, b) {
            return a.foodname.length - b.foodname.length;
        });
        
        var innerHTML = "";

        //iterate through each JSON object (currentFood) in the Array
        //populates the foods eaten table, one row = one food item
        for (var index = 0; index < globalValues["searchResultsArray"].length; index++)
        {
            var currentFoodObject = globalValues["searchResultsArray"][index];
            innerHTML = innerHTML.concat("<div class='row'>"
                    + "<div class='col-sm-12'>"
                    + "<a href='javascript:void(0)' class='list-group-item searchresult' id='" + currentFoodObject["id_searchablefood"] + "searchablefood" + "'>"
                    + "<div class='row'>"
                    + "<div class='col-sm-8'>"
//                    + "<strong>Name: </strong>" + currentFoodJSON["foodname"]
//                    + "<br>"
//                    + "<div id='" + currentFoodJSON["id_searchablefood"] + "macros'>"


                    + globalFunctions.createFoodAttributesHTML(currentFoodObject, "id_searchablefood")
//                    + " <strong>Macros: </strong>"
//                    + " <font color='green'>Protein:" + globalValues["searchResultsArray"][currentFood].protein + "</font>"
//                    + " <font color='blue'>Carb:" + globalValues["searchResultsArray"][currentFood].carbohydrate + "</font>"
//                    + " <font color='orange'>Fat:" + globalValues["searchResultsArray"][currentFood].fat + "</font>"
//                    + " <font color='red'>Cals:" + globalValues["searchResultsArray"][currentFood].calorie + "</font>"
//                    + "</div>"
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
//                        + "<div class='row'>"
//                        + "<div class='col-sm-3'>"
//                        + "</div>"
//                        + "</div>"
//                        + "<button id='addSearchableFoodButton" + searchResultFoodListJSON[index].id_searchablefood + "' class='btn btn-primary'>Add food</button>"
                    + "</div>"
                    + "</a>"

                    + "</div>"
                    + "</div>"
                    );
        }
        document.getElementById("searchResultList").innerHTML = innerHTML;
    }
}

function getSelectedAttributes(aJSON)
{
    var outputArray = [];

    for (var aProperty in aJSON)
    {
        if (aJSON[aProperty] === "t")
        {
            outputArray.push(aProperty);
        }
    }
    outputArray.sort();

    return outputArray;
}


function isUndefinedOrNull(aVariable)
{
    var output;
    if (typeof aVariable === 'undefined' || aVariable === null)
    {
        output = true;
    } else
    {
        output = false;
    }
    return output;
}

/**
 * A method to populate the table containing the foods that 
 * the user has eaten
 * @returns {undefined}
 */
function populateEatenFoodList()
{
    //empty list
    $('#eatenFoodList').empty();


    var innerHTML = "";
    //iterate through each JSON object (currentFood) in the Array
    //populates the foods eaten table, one row = one food item
//    for (var currentFood in globalValues["eatenFoodsArray"])
    for (var index = 0; index < globalValues["eatenFoodsArray"].length; index++)
    {
        var currentFoodJSON = globalValues["eatenFoodsArray"][index];

//            innerHTML = innerHTML.concat("<tr>"
//                    + "<td>" + eatenFoodListJSON[index].foodname + "</td>"
//                    + "<td>" + eatenFoodListJSON[index].protein + "</td>"
//                    + "<td>" + eatenFoodListJSON[index].carbohydrate + "</td>"
//                    + "<td>" + eatenFoodListJSON[index].fat + "</td>"
//                    + "<td>" + eatenFoodListJSON[index].calorie + "</td>"
//                    + "<td><button type=\"button\" class=\"btn btn-danger\" id=" + eatenFoodListJSON[index].id_eatenfood +"eatenfood" + "><span class=\"glyphicon glyphicon-remove\"></span></button></td>"
//                    + "</tr>");

//selected attrivutes supported = protein/fat/carbohydrate/calorie/satfod/monofod/polyfod/aoacfib/totsug/sodium/water/star
//galact//fruct//sucr//malt//lact

        innerHTML = innerHTML.concat("<div class='row'>"
                + "<div class='col-sm-12'>"



                + "<li class='list-group-item' id='" + currentFoodJSON["id_eatenfood"] + "eatenfood" + "'>"
                + "<div class='row'>"
                + "<div class='col-sm-8'>"

                + globalFunctions.createFoodAttributesHTML(currentFoodJSON)
//                + "<strong>Name: </strong>" + globalValues["eatenFoodsArray"][currentFood].foodname
//                + "<br>"
//        
//        
//                + "<div id='" + globalValues["eatenFoodsArray"][currentFood].id_searchablefood + "macros'>"
//                + "<strong>Macros: </strong>"
//                + " <font color='green'>Protein:" + globalValues["eatenFoodsArray"][currentFood].protein + "</font>"
//                + " <font color='blue'>Carb:" + globalValues["eatenFoodsArray"][currentFood].carbohydrate + "</font>"
//                + " <font color='orange'>Fat:" + globalValues["eatenFoodsArray"][currentFood].fat + "</font>"
//                + " <font color='red'>Cals:" + globalValues["eatenFoodsArray"][currentFood].calorie + "</font>"
//       +" <font color='orange'>Sugar:" + globalValues["eatenFoodsArray"][currentFood].totsug + "</font>"
                // + "</div>"



//                        + "<div class='row'>"
//                        + "<div class='col-sm-3'>"
//                        + "</div>"
//                        + "</div>"
//                        + "<button id='addSearchableFoodButton" + searchResultFoodListJSON[index].id_searchablefood + "' class='btn btn-primary'>Add food</button>"
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

//        innerHTML = innerHTML.concat("<div class='row'>"
//                + "<div class='col-sm-12'>"
//                + "<li class='list-group-item searchresult' id='" + globalValues["eatenFoodsArray"][currentFood].id_searchablefood + "eatenfood" + "'>"
//                + "<div class='row'>"
//                + "<div class='col-sm-8'>"
//                + "<strong>Name: </strong>" + globalValues["eatenFoodsArray"][currentFood].foodname
//                + "<br>"
//                + "<div id='" + globalValues["eatenFoodsArray"][currentFood].id_searchablefood + "macros'>"
//                + "<strong>Macros: </strong>"
//                + " <font color='green'>Protein:" + globalValues["eatenFoodsArray"][currentFood].protein + "</font>"
//                + " <font color='blue'>Carb:" + globalValues["eatenFoodsArray"][currentFood].carbohydrate + "</font>"
//                + " <font color='orange'>Fat:" + globalValues["eatenFoodsArray"][currentFood].fat + "</font>"
//                + " <font color='red'>Cals:" + globalValues["eatenFoodsArray"][currentFood].calorie + "</font>"
//                + "</div>"
////                        + "<div class='row'>"
////                        + "<div class='col-sm-3'>"
////                        + "</div>"
////                        + "</div>"
////                        + "<button id='addSearchableFoodButton" + searchResultFoodListJSON[index].id_searchablefood + "' class='btn btn-primary'>Add food</button>"
//                + "</div>"
//                + "<div class='col-sm-4'>"
//                + "<p><button type='button' class='btn btn-danger btn-md pull-right' id='" + globalValues["eatenFoodsArray"][currentFood].id_eatenfood + "eatenfoodremove" + "'>Remove <span class='glyphicon glyphicon-remove'></span></button>"
//                + "<button type='button' class='btn btn-info btn-md pull-right' id='" + globalValues["eatenFoodsArray"][currentFood].id_eatenfood + "eatenfooddetails" + "'>Details <span class='glyphicon glyphicon-info-sign'></span></button></p>"
//                + "</div>"
//                + "</div>"
//                + "</li>"
//                + "</div>"
//                + "</div>"
//                );

    }
    document.getElementById("eatenFoodList").innerHTML = innerHTML;

}

/**
 * This method gets the date that the user has selected with the datepicker and returns it in UNIX time
 * format. This is the number of milliseconds since the 1st of january 1970
 * @returns {Number}
 */
function getSelectedUNIXdate()
{
    var currentDate = new Date();
    var selectedDate = $('#foodDatepicker').datepicker('getUTCDate');


    var currentDateUTCUNIX = Date.UTC(currentDate.getUTCFullYear(), currentDate.getUTCMonth(),
            currentDate.getUTCDate(), currentDate.getUTCHours(), currentDate.getUTCMinutes(), currentDate.getUTCSeconds());
    var selectedDateUNIX = selectedDate.getTime();
    var startOfCurrentDateUNIX = new Date(currentDate.getFullYear(), currentDate.getMonth(), currentDate.getDate());

    //if a date in the future or past i.e. tomorrow or yesterday is selected then
    //that will be used as the foods timestamp, otherwise todays date and time will
    //be used
    if (selectedDateUNIX > currentDateUTCUNIX || selectedDateUNIX < startOfCurrentDateUNIX)
    {
        return selectedDateUNIX;
    } else
    {
        return currentDateUTCUNIX;
    }
}

/**
 * A method to calculate the macros of a food based on the weight of the food.
 * For example the database stores the protein content of 100g of milk as 5g and the
 * user enters 250g of milk this method will calculate the amount of protein,carbs,fat,cals
 * for milk at that weight.
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

    //attributes that should be ignored or treated as whole numbers
//    var globalValues["nonOperableAttributes"] = ["foodcode","foodname","foodnameoriginal","description",
//        "foodgroup","previous","foodreferences","footnote","id_user","id_eatenfood","id_searchablefood"];
//    var globalValues["wholeIntegerAttributes"] = ["calorie","kj","weight"];

    //calculate the macros from the stored weight of the food
    var multiplier = currentWeightValue / foodObject["weight"];

    for (var aProperty in foodObject)
    {
        var currentValue = foodObject[aProperty];

        //if non operable e.g "foodname" then ignore
        if (globalValues["nonOperableAttributes"].indexOf(aProperty) === -1)
        {
            //if operable but treated as float to 1 decimal place
            if (globalValues["wholeIntegerAttributes"].indexOf(aProperty) === -1)
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

function calculateTotalMacros(callback)
{
    globalValues["totalMacrosToday"] = {};
    //currentMacroSplit = {};

    for (var index = 0; index < globalValues["eatenFoodsArray"].length; index++)
    {
        var currentFoodJSON = globalValues["eatenFoodsArray"][index];
        for (var aProperty in currentFoodJSON)
        {
            //if non operable e.g "foodname" then ignore
            if (globalValues["nonOperableAttributes"].indexOf(aProperty) === -1 && !isUndefinedOrNull(currentFoodJSON[aProperty]))
            {
                //if first occurrance of aProperty
                if (isUndefinedOrNull(globalValues["totalMacrosToday"][aProperty]))
                {
                    globalValues["totalMacrosToday"][aProperty] = currentFoodJSON[aProperty];
                } else
                {
                    globalValues["totalMacrosToday"][aProperty] = +globalValues["totalMacrosToday"][aProperty] + +currentFoodJSON[aProperty];
                }
            }
        }
    }
    console.log(JSON.stringify("TOTAL MACROS TODAY: " + globalValues["totalMacrosToday"]));

    if (isUndefinedOrNull(globalValues["totalMacrosToday"]["protein"]))
    {
        globalValues["totalMacrosToday"]["protein"] = 0;
    }
    if (isUndefinedOrNull(globalValues["totalMacrosToday"]["carbohydrate"]))
    {
        globalValues["totalMacrosToday"]["carbohydrate"] = 0;
    }
    if (isUndefinedOrNull(globalValues["totalMacrosToday"]["fat"]))
    {
        globalValues["totalMacrosToday"]["fat"] = 0;
    }
    if (isUndefinedOrNull(globalValues["totalMacrosToday"]["calorie"]))
    {
        globalValues["totalMacrosToday"]["calorie"] = 0;
    }
    globalFunctions["setGlobalValuesLocalStorage"]();

    if (callback)
    {
        callback();
    }
}

function updatePieCharts(callback)
{
    var totalProtein = parseInt(globalValues["totalMacrosToday"]["protein"]);
    var totalCarbohydrate = parseInt(globalValues["totalMacrosToday"]["carbohydrate"]);
    var totalFat = parseInt(globalValues["totalMacrosToday"]["fat"]);


//    caloriePie
//    macroPie
//    macroGoalsPie
    //globalValues["totalMacrosToday"]["calorie"]
//    var macroPieData = [{label: "Protein", value: globalValues["totalMacrosToday"]["protein"], color: "#FF0000"},
//        {label: "Carbs", value: globalValues["totalMacrosToday"]["carbohydrate"], color: "#008000"},
//        {label: "Fats", value: globalValues["totalMacrosToday"]["fat"], color: "#0080FF"}];

//    var macroGoalPieData = [{label: "Protein", value: goalMacroSplit["protein"], color: "#FF0000"},
//        {label: "Carbs", value: goalMacroSplit["carbohydrate"], color: "#008000"},
//        {label: "Fats", value: goalMacroSplit["fat"], color: "#0080FF"}];

    // macroPie.updateProp("data.content", macroPieData);

//    if (isUndefinedOrNull(macroPie))
//    {
//
//    }
//    else
//    {
//        macroPie.destroy();
//    }
//    macroPie = new d3pie("macroPie", macroPieConfigObject);

//[{
//                            name: "Protein",
//                            y: parseInt(globalValues["totalMacrosToday"]["protein"]),
//                            color: "green"
//                        }, {
//                            name: "Carbohydrates",
//                            y: parseInt(globalValues["totalMacrosToday"]["carbohydrate"]),
//                            color: "blue"
//                        }, {
//                            name: "Fats",
//                            y: parseInt(globalValues["totalMacrosToday"]["fat"]),
//                            color: "orange"
//                        }]

    var macroPie;
    $(function () {
        // Create the chart
        macroPie = new Highcharts.Chart({
            // Create the chart
            chart: {
                renderTo: 'macroPie',
                type: 'pie'
            },
            credits: {
                enabled: false
            },
            title: {
                text: 'Todays Calories(kcal)'
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
                    size: '40%',
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

        if (totalProtein === 0 && totalCarbohydrate === 0 && totalFat === 0)
        {
            macroPie.series[0].setData([]);
        } else
        {
            macroPie.series[0].setData([{
                    name: "Protein",
                    y: totalProtein,
                    color: "green"
                }, {
                    name: "Carbohydrates",
                    y: totalCarbohydrate,
                    color: "blue"
                }, {
                    name: "Fats",
                    y: totalFat,
                    color: "orange"
                }]);
        }
    });



//var macroGoalPie = $('#macroGoalPie').highcharts();
//
//$('#macroGoalPie').highcharts({
//    chart: {
//        renderTo: 'macroGoalPie',
//        type: 'pie'
//    },
//    credits: {
//        enabled: false
//    },
//    title: {
//        text: 'Goal Split (%)'
//    },
//    yAxis: {
//        title: {
//            text: 'Total percent market share'
//        }
//    },
//    plotOptions: {
//        pie: {
//            shadow: false
//        }
//    },
//    tooltip: {
//        enabled: false
////                formatter: function() {
////                    return '<b>'+ this.point.name +'</b>: '+ this.y +' %';
////                }
//    },
//    series: [{
//            name: 'Goal Split',
//            showInLegend: false,
//            data: [{
//                    name: "Protein",
//                    y: goalMacroSplit["protein"],
//                    color: "green"
//                }, {
//                    name: "Carbohydrates",
//                    y: goalMacroSplit["carbohydrate"],
//                    color: "blue"
//                }, {
//                    name: "Fats",
//                    y: goalMacroSplit["fat"],
//                    color: "orange"
//                }],
//            size: '50%',
//            innerSize: '60%',
//            dataLabels: {
//                enabled: true,
//                formatter: function () {
//                    return this.point.name + ' ' + Math.round(this.percentage) + ' %';
//                },
//                //distance: -20,
//                color: 'black'
//            }
//        }]
//});

    var macroGoalPie;
    $(function () {
        // Create the chart
        macroGoalPie = new Highcharts.Chart({
            // Create the chart
            chart: {
                renderTo: 'macroGoalPie',
                type: 'pie'
            },
            credits: {
                enabled: false
            },
            title: {
                text: 'Daily macro goal (g)'
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
                    name: 'Daily macro goal',
                    showInLegend: false,
                    data: [],
                    size: '40%',
                    innerSize: '60%',
                    dataLabels: {
                        enabled: true,
                        formatter: function () {
                            return this.point.name + ' ' + this.y + 'g';
                        },
                        distance: 5,
                        color: 'black'
                    }
                }]
        });
    });

    var idealprotein = globalValues["userStats"]["idealprotein"];
    var idealcarbohydrate = globalValues["userStats"]["idealcarbohydrate"];
    var idealfat = globalValues["userStats"]["idealfat"];

    if (!isUndefinedOrNull(idealprotein) && !isUndefinedOrNull(idealcarbohydrate) && !isUndefinedOrNull(idealfat))
    {
        idealprotein = parseInt(idealprotein);
        idealcarbohydrate = parseInt(idealcarbohydrate);
        idealfat = parseInt(idealfat);

        if (idealprotein === 0 && idealcarbohydrate === 0 && idealfat === 0)
        {
            macroGoalPie.series[0].setData([]);
        } else
        {
            macroGoalPie.series[0].setData([{
                    name: "Protein",
                    y: idealprotein,
                    color: "green"
                }, {
                    name: "Carbohydrates",
                    y: idealcarbohydrate,
                    color: "blue"
                }, {
                    name: "Fats",
                    y: idealfat,
                    color: "orange"
                }]);
        }
    } else
    {
        macroGoalPie.series[0].setData([]);
    }


//macroGoalPie.series[0].points[0].remove();




// macroPie.updateProp("data.content", macroPieData);



//caloriePie.data.content= {label: "Eaten",value: "500", color: "#FF0000" };
//    macroPie.data.content = [
//                {label: "Free", value: 566, color: "#008000"},
//                {label: "Eaten", value: 566, color: "#FF0000"},
//            ];
//    data: {
//            content: [
//                {label: "Free", value: 1436, color: "#008000"},
//                {label: "Eaten", value: 1064, color: "#FF0000"},
//            ]
//        }
//var data = {
//    labels: ["January", "February", "March", "April", "May", "June", "July"],
//    datasets: [
//        {
//            label: "My First dataset",
//            fillColor: "rgba(220,220,220,0.2)",
//            strokeColor: "rgba(220,220,220,1)",
//            pointColor: "rgba(220,220,220,1)",
//            pointStrokeColor: "#fff",
//            pointHighlightFill: "#fff",
//            pointHighlightStroke: "rgba(220,220,220,1)",
//            data: [65, 59, 80, 81, 56, 55, 40]
//        },
//        {
//            label: "My Second dataset",
//            fillColor: "rgba(151,187,205,0.2)",
//            strokeColor: "rgba(151,187,205,1)",
//            pointColor: "rgba(151,187,205,1)",
//            pointStrokeColor: "#fff",
//            pointHighlightFill: "#fff",
//            pointHighlightStroke: "rgba(151,187,205,1)",
//            data: [28, 48, 40, 19, 86, 27, 90]
//        }
//    ]
//};
//
//var ctx = document.getElementById("myChart").getContext("2d");
//var myBarChart = new Chart(ctx).Bar(data, options);

    if (callback)
    {
        callback();
    }

}

function updateGraphs(callback)
{
    var proteinEaten = parseInt(globalValues["totalMacrosToday"]["protein"]);
    var carbohydrateEaten = parseInt(globalValues["totalMacrosToday"]["carbohydrate"]);
    var fatEaten = parseInt(globalValues["totalMacrosToday"]["fat"]);

    var proteinGoal = parseInt(globalValues["userStats"]["protein_goal"]);
    var carbohydrateGoal = parseInt(globalValues["userStats"]["carbohydrate_goal"]);
    var fatGoal = parseInt(globalValues["userStats"]["fat_goal"]);

    var macroArray = [proteinEaten, carbohydrateEaten, fatEaten, proteinGoal, carbohydrateGoal, fatGoal];


//    var macroChart;
//    $(function () {
//        // Create the chart
//        macroChart = new Highcharts.Chart({
//            // Create the chart
//            chart: {
//                renderTo: 'macroChart',
//                type: 'bar'
//            },
//            title: {
//                text: 'Todays macros (g)'
//            },
//            xAxis: {
//                categories: ['Protein', 'Carbs', 'Fat']
//            },
//            yAxis: chartYAxis(chartProtein, chartCarbohydrate, chartFat),
//            plotOptions: {
//                bar: {
//                    minPointLength: 10
//                },
//                series: {
//                    stacking:'normal',
//                    dataLabels: {
//                        enabled: true
//                        
//                    }
//                }
//                
//            },
//            tooltip: {
//                enabled: false
//            },
//            series: [{
//                    name: "todays macros",
//                    showInLegend: false,
//                    //colorByPoint: true,
//                    data: [{
//                            name: "Protein",
//                            y: chartProtein,
//                            color: "green"
//                        }, {
//                            name: "Carbohydrates",
//                            y: chartCarbohydrate,
//                            color: "blue"
//                        }, {
//                            name: "Fats",
//                            y: chartFat,
//                            color: "orange"
//                        }]
//                }]
////            
//////            series: [{
//////                    name: "Todays macros",
//////                    showInLegend: false,
//////                    data: [chartProtein, chartCarbohydrate, chartFat]
//////                }]
//        });
//        
//    });

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
//                    data: [{
//                            name: "Protein eaten",
//                            y: 70,
//                            color: "green"
//                        }, {
//                            name: "Protein goal",
//                            y: 160,
//                            color: "green"
//                        }, {
//                            name: "Carbohydrates eaten",
//                            y: 160,
//                            color: "blue"
//                        }, {
//                            name: "Carbohydrates goal",
//                            y: 200,
//                            color: "blue"
//                        }, {
//                            name: "Fats eaten",
//                            y: 40,
//                            color: "orange"
//                        }, {
//                            name: "Fats goal",
//                            y: 60,
//                            color: "orange"
//                        }]

                    //color: "green"
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

    var calorieGoal = parseInt(globalValues["userStats"]["tee"]);
    var calorieEaten = parseInt(globalValues["totalMacrosToday"]["calorie"]);
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
//                    data: [{
//                            name: "Protein eaten",
//                            y: 70,
//                            color: "green"
//                        }, {
//                            name: "Protein goal",
//                            y: 160,
//                            color: "green"
//                        }, {
//                            name: "Carbohydrates eaten",
//                            y: 160,
//                            color: "blue"
//                        }, {
//                            name: "Carbohydrates goal",
//                            y: 200,
//                            color: "blue"
//                        }, {
//                            name: "Fats eaten",
//                            y: 40,
//                            color: "orange"
//                        }, {
//                            name: "Fats goal",
//                            y: 60,
//                            color: "orange"
//                        }]

                    //color: "green"
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


    for (var aFood in globalValues["searchResultsArray"])
    {
        if (globalValues["searchResultsArray"][aFood]["id_searchablefood"] === id_searchablefood)
        {
            var matchingFood = globalValues["searchResultsArray"][aFood];
            for (var currentProperty in matchingFood)
            {
                currentFood[currentProperty] = matchingFood[currentProperty];
            }
        }
    }
    var updatedFood = calculateMacrosFromWeight(id_searchablefood, currentFood);

    var innerHTML = globalFunctions.createFoodAttributesHTML(updatedFood, "id_searchablefood");

//            "<strong>Macros: </strong>"
//            + " <font color='green'>Protein: " + updatedFood["protein"] + "</font>"
//            + " <font color='blue'>Carb: " + updatedFood["carbohydrate"] + "</font>"
//            + " <font color='orange'>Fat: " + updatedFood["fat"] + "</font>"
//            + " <font color='red'>Cals: " + updatedFood["calorie"] + "</font>";

    //$(id_searchablefood).empty();
    id_searchablefoodmacros.innerHTML = innerHTML;

}

function updateMacrosNeededPanel()
{
    var macroPanel = document.getElementById("currentMacros");
    var innerHTML = "";
    var proteinNeeded = parseInt(globalValues["userStats"]["protein_goal"]) - parseInt(globalValues["totalMacrosToday"]["protein"]);
    var carbohydrateNeeded = parseInt(globalValues["userStats"]["carbohydrate_goal"]) - parseInt(globalValues["totalMacrosToday"]["carbohydrate"]);
    var fatNeeded = parseInt(globalValues["userStats"]["fat_goal"]) - parseInt(globalValues["totalMacrosToday"]["fat"]);
    var calorieRemaining = parseInt(globalValues["userStats"]["tee"]) - parseInt(globalValues["totalMacrosToday"]["calorie"]);

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

//function sortSearchResultList(callback)
//{
//        globalValues["searchResultsArray"].sort(function (a, b) {
//            return a.foodname.length - b.foodname.length;
//        });
//        
//    if(callback)
//    {
//        callback();
//    }
//}

function updateMainPage()
{
    populateEatenFoodList();
    populateCustomFoodList();
    populateSearchResultList();
//    sortSearchResultList(function() {
//        populateSearchResultList(); 
//    });


    calculateTotalMacros(function () {
        //updatePieCharts();
        updateGraphs();
        updateMacrosNeededPanel();
    });
}