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
    getEatenFoodList();
}
function decrementDate()
{
    viewDate.setDate(viewDate.getDate() - 1);
    $('#foodDatepicker').datepicker('setDate', viewDate);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', viewDate);
    $('#macroDatepicker').datepicker('update');
    getEatenFoodList();
}

/**
 * A method to populate the custom food list for a particular user.
 * @returns {undefined} 
 */
function populateCustomFoodList()
{
    //list must be emptied first, if not then the final list item will still show
    //even if it was deleted from the database
    $('#customFoodList').empty();

    var innerHTML = "";
    //iterate through each JSON (database record) in the Array
    //populate the list, give the links an id that corresponds to the id_customfood value of the food from the database
    // e.g. if there are two foods named "tasty pie" the id_customfood will allow us to tell them apart
    for (var currentFood in customFoodJSONArray)
    {
        innerHTML = innerHTML.concat("<a href='javascript:void(0)' class='list-group-item customfood' id='" + customFoodJSONArray[currentFood].id_customfood + "customfood" + "'>" + customFoodJSONArray[currentFood].foodname
                + "<br>"
                + " <font color='green'>Protein: " + customFoodJSONArray[currentFood].protein + "</font>"
                + " <font color='blue'>Carb: " + customFoodJSONArray[currentFood].carbohydrate + "</font>"
                + " <font color='orange'>Fat: " + customFoodJSONArray[currentFood].fat + "</font>"
                + " <font color='red'>Cals: " + customFoodJSONArray[currentFood].calorie + "</font>"
                + "</a>"
                );
    }

    document.getElementById("customFoodList").innerHTML = innerHTML;
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


    if ($.isEmptyObject(searchResultFoodJSONArray))
    {
        document.getElementById("searchResultList").innerHTML = "<a href='javascript:void(0)' class='list-group-item searchresult'> No results </a>";
    }
    else
    {

        var innerHTML = "";
        
        //iterate through each JSON object (currentFood) in the Array
        //populates the foods eaten table, one row = one food item
        for (var currentFood in searchResultFoodJSONArray)
        {
            innerHTML = innerHTML.concat("<div class='row'>"
                    + "<div class='col-sm-12'>"
                    + "<a href='javascript:void(0)' class='list-group-item searchresult' id='" + searchResultFoodJSONArray[currentFood].id_searchablefood + "searchablefood" + "'>"
                    + "<div class='row'>"
                    + "<div class='col-sm-8'>"
                    + "<strong>Name: </strong>" + searchResultFoodJSONArray[currentFood].foodname
                    + "<br>"
                    + "<div id='" + searchResultFoodJSONArray[currentFood].id_searchablefood + "macros'>"
                    + " <strong>Macros: </strong>"
                    + " <font color='green'>Protein:" + searchResultFoodJSONArray[currentFood].protein + "</font>"
                    + " <font color='blue'>Carb:" + searchResultFoodJSONArray[currentFood].carbohydrate + "</font>"
                    + " <font color='orange'>Fat:" + searchResultFoodJSONArray[currentFood].fat + "</font>"
                    + " <font color='red'>Cals:" + searchResultFoodJSONArray[currentFood].calorie + "</font>"
                    + "</div>"
                    + "</div>"
                    + "<div class='col-sm-4'>"
                    + "<strong>Weight(g):</strong>"
                    + "<div class='input-group'>"
                    + "<span class='input-group-btn'>"
                    + "<button class='btn btn-primary decrementWeightButton' type='button' id='" + searchResultFoodJSONArray[currentFood].id_searchablefood + "decrementweight" + "'>-</button>"
                    + "</span>"
                    + "<input type='number' class='form-control searchresultInput' placeholder='Weight(g)' step='100' min='0' max='100000' id='" + searchResultFoodJSONArray[currentFood].id_searchablefood + "weight'" + "value='" + searchResultFoodJSONArray[currentFood].weight + "'>"
                    + "<span class='input-group-btn'>"
                    + "<button class='btn btn-primary incrementWeightButton' type='button' id='" + searchResultFoodJSONArray[currentFood].id_searchablefood + "incrementweight" + "'>+</button>"
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
        
    }
    document.getElementById("searchResultList").innerHTML = innerHTML;
}


/**
 * A method to populate the table containing the foods that 
 * the user has eaten
 * @returns {undefined}
 */
function populateEatenFoodTable()
{
    //empty list
    $('#eatenFoodList').empty();


    var innerHTML = "";
    //iterate through each JSON object (currentFood) in the Array
    //populates the foods eaten table, one row = one food item
    for (var currentFood in eatenFoodJSONArray)
    {
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
        
        
        
                + "<li class='list-group-item searchresult' id='" + eatenFoodJSONArray[currentFood].id_searchablefood + "eatenfood" + "'>"
                + "<div class='row'>"
                + "<div class='col-sm-8'>"
                + "<strong>Name: </strong>" + eatenFoodJSONArray[currentFood].foodname
                + "<br>"
                + "<div id='" + eatenFoodJSONArray[currentFood].id_searchablefood + "macros'>"
                + "<strong>Macros: </strong>"
                + " <font color='green'>Protein:" + eatenFoodJSONArray[currentFood].protein + "</font>"
                + " <font color='blue'>Carb:" + eatenFoodJSONArray[currentFood].carbohydrate + "</font>"
                + " <font color='orange'>Fat:" + eatenFoodJSONArray[currentFood].fat + "</font>"
                + " <font color='red'>Cals:" + eatenFoodJSONArray[currentFood].calorie + "</font>"
       +" <font color='orange'>Sugar:" + eatenFoodJSONArray[currentFood].totsug + "</font>"
                + "</div>"
//                        + "<div class='row'>"
//                        + "<div class='col-sm-3'>"
//                        + "</div>"
//                        + "</div>"
//                        + "<button id='addSearchableFoodButton" + searchResultFoodListJSON[index].id_searchablefood + "' class='btn btn-primary'>Add food</button>"
                + "</div>"
                + "<div class='col-sm-4'>"
                + "<p><button type='button' class='btn btn-danger btn-md pull-right' id='" + eatenFoodJSONArray[currentFood].id_eatenfood + "eatenfoodremove" + "'>Remove <span class='glyphicon glyphicon-remove'></span></button>"
                + "<button type='button' class='btn btn-info btn-md pull-right' id='" + eatenFoodJSONArray[currentFood].id_eatenfood + "eatenfooddetails" + "'>Details <span class='glyphicon glyphicon-info-sign'></span></button></p>"
                
                
                
                + "</div>"
                + "</div>"
                + "</li>"
                + "</div>"
                + "</div>"
                );

//        innerHTML = innerHTML.concat("<div class='row'>"
//                + "<div class='col-sm-12'>"
//                + "<li class='list-group-item searchresult' id='" + eatenFoodJSONArray[currentFood].id_searchablefood + "eatenfood" + "'>"
//                + "<div class='row'>"
//                + "<div class='col-sm-8'>"
//                + "<strong>Name: </strong>" + eatenFoodJSONArray[currentFood].foodname
//                + "<br>"
//                + "<div id='" + eatenFoodJSONArray[currentFood].id_searchablefood + "macros'>"
//                + "<strong>Macros: </strong>"
//                + " <font color='green'>Protein:" + eatenFoodJSONArray[currentFood].protein + "</font>"
//                + " <font color='blue'>Carb:" + eatenFoodJSONArray[currentFood].carbohydrate + "</font>"
//                + " <font color='orange'>Fat:" + eatenFoodJSONArray[currentFood].fat + "</font>"
//                + " <font color='red'>Cals:" + eatenFoodJSONArray[currentFood].calorie + "</font>"
//                + "</div>"
////                        + "<div class='row'>"
////                        + "<div class='col-sm-3'>"
////                        + "</div>"
////                        + "</div>"
////                        + "<button id='addSearchableFoodButton" + searchResultFoodListJSON[index].id_searchablefood + "' class='btn btn-primary'>Add food</button>"
//                + "</div>"
//                + "<div class='col-sm-4'>"
//                + "<p><button type='button' class='btn btn-danger btn-md pull-right' id='" + eatenFoodJSONArray[currentFood].id_eatenfood + "eatenfoodremove" + "'>Remove <span class='glyphicon glyphicon-remove'></span></button>"
//                + "<button type='button' class='btn btn-info btn-md pull-right' id='" + eatenFoodJSONArray[currentFood].id_eatenfood + "eatenfooddetails" + "'>Details <span class='glyphicon glyphicon-info-sign'></span></button></p>"
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
    }
    else
    {
        return currentDateUTCUNIX;
    }
}


function calculateTotalMactos()
{

}



