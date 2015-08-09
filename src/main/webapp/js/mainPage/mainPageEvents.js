/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//Events
$(document).ready(function () {

    //when fooddatepicker changes the date, update the viewDate object
    //eventTriggered is needed to prevent both datepicker events triggering
    //eachother in an infinite loop
    $('#foodDatepicker').datepicker().on('changeDate', function () {
        var updatedDate = $("#foodDatepicker").datepicker("getDate");
        viewDate = updatedDate;
        if (!eventTriggered)
        {
            eventTriggered = true;

            $('#macroDatepicker').datepicker('setDate', viewDate);
            getEatenFoodList();

        }
        eventTriggered = false;

    });

    //same as above but for macroDatepicker
    $('#macroDatepicker').datepicker().on('changeDate', function () {
        var updatedDate = $("#macroDatepicker").datepicker("getDate");
        viewDate = updatedDate;
        if (!eventTriggered)
        {
            eventTriggered = true;

            $('#foodDatepicker').datepicker('setDate', viewDate);
            getEatenFoodList();

        }
        eventTriggered = false;
    });

    //listener for adding food manually
    //.submit is used because the button is attached to a form
    $("#addEatenFoodForm").submit(function (event) {
        console.log("add food manually button triggered");
        event.preventDefault(); //this prevents the default actions of the form
        addEatenFoodManually();
    });

    //listener for remove food buttons on food eaten table
    $(document).on("click", ".btn-danger", function () {
        console.log("remove button triggered");
        var id_eatenfood = $(this).attr("id");
        console.log("id_eatenfood " + id_eatenfood + " selected for removal");
        id_eatenfood = removeCharacters(id_eatenfood);
        removeEatenFood(id_eatenfood);
    });

    //listener to add a food to eaten foods from the custom foods list
    $(document).on("click", ".customfood", function () {
        var id_customFood = $(this).attr("id");
        console.log("id_customfood " + id_customFood + " will be added");
        id_customFood = removeCharacters(id_customFood);
        addEatenFoodFromCustomFood(id_customFood);
    });

    //listener to add a food to eaten foods from the search result list
    $(document).on("click", ".searchresult", function () {
        var id_searchablefood = $(this).attr("id");
        console.log("id_searchablefood " + id_searchablefood + " will be added");
        id_searchablefood = removeCharacters(id_searchablefood);
        addEatenFoodFromSearchResult(id_searchablefood);
    });

    //listener to respond to searchButton click for searching the database
    $(document).on("click", "#searchButton", function () {
        var searchInput = document.getElementById("searchInput").value;
        console.log("searching for " + searchInput);
        searchForFood(searchInput);
    });

    //listener for datepicker increment date buttons
    $(document).on("click", "#incrementMacroDateButton, #incrementFoodDateButton", function (e) {
        incrementDate();
    });

    //listener for datepicker decrement date buttons
    $(document).on("click", "#decrementMacroDateButton, #decrementFoodDateButton", function (e) {
        decrementDate();
    });

    $(document).on("click", ".incrementWeightButton", function (e) {
        e.stopPropagation(); //stops the triggering of all parent events
        var id = $(this).attr("id");
        var id_searchablefood = removeCharacters(id);
        var id_searchablefoodweight = id_searchablefood + "weight";
        document.getElementById(id_searchablefoodweight).stepUp();
        updateSearchResultMacros(id);
    });

    $(document).on("click", ".decrementWeightButton", function (e) {
        e.stopPropagation(); //stops the triggering of all parent events
        var id = $(this).attr("id");
        var id_searchablefood = removeCharacters(id);
        var id_searchablefoodweight = id_searchablefood + "weight";
        document.getElementById(id_searchablefoodweight).stepDown();
        updateSearchResultMacros(id);
    });

    //listener for datepicker decrement date buttons
    $(document).on("click", ".searchresultInput", function (e) {
        e.stopPropagation(); //stops the triggering of all parent events
    });

    //listener for when the user changes the weight of a search result from the database
    $(document).on("change", ".searchresultInput", function (e) {
        e.stopPropagation(); //stops the triggering of all parent events
        var id = $(this).attr("id");
        console.log("search result weight change detected for input field: " + id);
        updateSearchResultMacros(id);
    });

    $(document).on("click", ".selectAttributesButton", function (e) {
        console.log("edit attributes button clicked");
        editSelectedAttributes();
    });

    $('#editSelectedAttributesForm').submit(function () {
        updateSelectedAttributes();
        $('#foodAttributeModal').modal('hide');
        return false;
    });

});