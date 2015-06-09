/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

//this focuses the page on the currently opened accordion panel
$(function () {
    $('#accordion1').on('shown.bs.collapse', function (e) {
        var offset = $('.panel.panel-default > .panel-collapse.in').offset();
        if (offset) {
            $('html,body').animate({
                scrollTop: $('.panel-title .bottomAccordion').offset().top - 20
            }, 500);
        }
    });
});


/**
 * DATE RELATED METHODS
 * @type Date|newDate
 */

//global viewDate object
//this is the date the user is currently viewing
var viewDate;
var eventTriggered = false;
$(document).ready(function () {
    viewDate = new Date();

        //create food datepicker
    $('#foodDatepicker').datepicker({
        autoclose: true,
        todayBtn: "linked",
        format: "dd/mm/yyyy"
    });

    //create macro datepicker
    $('#macroDatepicker').datepicker({
        autoclose: true,
        todayBtn: "linked",
        format: "dd/mm/yyyy"
    });

    //ensure todays date is shown in the datepickers textbox initially
    $('#foodDatepicker').datepicker('setDate', viewDate);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', viewDate);
    $('#macroDatepicker').datepicker('update'); 
    
    //when fooddatepicker changes the date, update the viewDate object
    //eventTriggered is needed to prevent both datepicker events triggering
    //eachother in an infinite loop
    $('#foodDatepicker').datepicker().on('changeDate', function () {
        var updatedDate = $("#foodDatepicker").datepicker("getDate");
        viewDate = updatedDate;
        if(!eventTriggered)
        {
            eventTriggered = true;
            
                $('#macroDatepicker').datepicker('setDate', viewDate);
            
        }
        eventTriggered = false;
    });
    
    //same as above but for macroDatepicker
    $('#macroDatepicker').datepicker().on('changeDate', function () {
        var updatedDate = $("#macroDatepicker").datepicker("getDate");
        viewDate = updatedDate;
        if(!eventTriggered)
        {
            eventTriggered = true;
            
                $('#foodDatepicker').datepicker('setDate', viewDate);
            
        }
        eventTriggered = false;
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

//increment the viewDate the user is looking at
//these functions are used with the buttons near the datepickers
//they will change the datepickers internal states
function incrementDate()
{
    viewDate.setDate(viewDate.getDate() + 1);
    $('#foodDatepicker').datepicker('setDate', viewDate);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', viewDate);
    $('#macroDatepicker').datepicker('update');
}

function decrementDate()
{
    viewDate.setDate(viewDate.getDate() - 1);
    $('#foodDatepicker').datepicker('setDate', viewDate);
    $('#foodDatepicker').datepicker('update');
    $('#macroDatepicker').datepicker('setDate', viewDate);
    $('#macroDatepicker').datepicker('update');
}

function removeFood()
{
    //PLACEHOLDER
}


/**
 * PIE CHART SETUP RELATED METHODS
 */

//macro pie chart
$(document).ready(function () {
    var pie = new d3pie("macroPie", {
        header: {
            title: {
                text: "Macros (g)",
                color: "#333333",
                fontSize: 18,
                font: "arial",
            },
            subtitle: {
                text: "",
                color: "#666666",
                fontSize: 14,
                font: "arial"
            },
            location: "top-center",
            titleSubtitlePadding: 8
        },
        footer: {
            text: "",
            color: "#666666",
            fontSize: 14,
            font: "arial",
            location: "left"
        },
        size: {
            canvasHeight: 250,
            canvasWidth: 260,
            pieInnerRadius: "50%",
            pieOuterRadius: null
        },
        data: {
            sortOrder: "none",
            ignoreSmallSegments: {
                enabled: false,
                valueType: "percentage",
                value: null
            },
            smallSegmentGrouping: {
                enabled: false,
                value: 1,
                valueType: "percentage",
                label: "Other",
                color: "#cccccc"
            },
            content: []
        },
        labels: {
            outer: {
                format: "label",
                hideWhenLessThanPercentage: null,
                pieDistance: 4
            },
            inner: {
                format: "value",
                hideWhenLessThanPercentage: null
            },
            mainLabel: {
                color: "#333333",
                font: "arial",
                fontSize: 12
            },
            percentage: {
                color: "#dddddd",
                font: "arial",
                fontSize: 20,
                decimalPlaces: 0
            },
            value: {
                color: "#dddddd",
                font: "arial",
                fontSize: 12
            },
            lines: {
                enabled: true,
                style: "straight",
                color: "segment"
            },
            truncation: {
                enabled: false,
                length: 30
            }
        },
        effects: {
            load: {
                effect: "default",
                speed: 1000
            },
            pullOutSegmentOnClick: {
                effect: "bounce",
                speed: 300,
                size: 10
            },
            highlightSegmentOnMouseover: true,
            highlightLuminosity: -0.2
        },
        tooltips: {
            enabled: false,
            type: "placeholder", // caption|placeholder
            string: "",
            placeholderParser: null,
            styles: {
                fadeInSpeed: 250,
                backgroundColor: "#000000",
                backgroundOpacity: 0.5,
                color: "#efefef",
                borderRadius: 2,
                font: "arial",
                fontSize: 10,
                padding: 4
            }
        },
        misc: {
            colors: {
                background: null,
                segments: [
                    "#2484c1", "#65a620", "#7b6888", "#a05d56", "#961a1a", "#d8d23a", "#e98125", "#d0743c", "#635222", "#6ada6a",
                    "#0c6197", "#7d9058", "#207f33", "#44b9b0", "#bca44a", "#e4a14b", "#a3acb2", "#8cc3e9", "#69a6f9", "#5b388f",
                    "#546e91", "#8bde95", "#d2ab58", "#273c71", "#98bf6e", "#4daa4b", "#98abc5", "#cc1010", "#31383b", "#006391",
                    "#c2643f", "#b0a474", "#a5a39c", "#a9c2bc", "#22af8c", "#7fcecf", "#987ac6", "#3d3b87", "#b77b1c", "#c9c2b6",
                    "#807ece", "#8db27c", "#be66a2", "#9ed3c6", "#00644b", "#005064", "#77979f", "#77e079", "#9c73ab", "#1f79a7"
                ],
                segmentStroke: "#ffffff"
            },
            gradient: {
                enabled: false,
                percentage: 95,
                color: "#000000"
            },
            canvasPadding: {
                top: 5,
                right: 5,
                bottom: 5,
                left: 5
            },
            pieCenterOffset: {
                x: 0,
                y: 0
            },
            cssPrefix: null
        },
        callbacks: {
            onload: null,
            onMouseoverSegment: null,
            onMouseoutSegment: null,
            onClickSegment: null
        },
        data: {
            content: [
                {label: "Protein", value: 150, color: "#FF0000"},
                {label: "Carbs", value: 250, color: "#008000"},
                {label: "Fats", value: 80, color: "#0080FF"}
            ]
        }
    });

    //macrogoals pie chart
    var pie = new d3pie("macroGoalsPie", {
        header: {
            title: {
                text: "Macro goal (g)",
                color: "#333333",
                fontSize: 18,
                font: "arial",
            },
            subtitle: {
                text: "",
                color: "#666666",
                fontSize: 14,
                font: "arial"
            },
            location: "top-center",
            titleSubtitlePadding: 8
        },
        footer: {
            text: "",
            color: "#666666",
            fontSize: 14,
            font: "arial",
            location: "left"
        },
        size: {
            canvasHeight: 250,
            canvasWidth: 260,
            pieInnerRadius: "50%",
            pieOuterRadius: null
        },
        data: {
            sortOrder: "none",
            ignoreSmallSegments: {
                enabled: false,
                valueType: "percentage",
                value: null
            },
            smallSegmentGrouping: {
                enabled: false,
                value: 1,
                valueType: "percentage",
                label: "Other",
                color: "#cccccc"
            },
            content: []
        },
        labels: {
            outer: {
                format: "label",
                hideWhenLessThanPercentage: null,
                pieDistance: 4
            },
            inner: {
                format: "value",
                hideWhenLessThanPercentage: null
            },
            mainLabel: {
                color: "#333333",
                font: "arial",
                fontSize: 12
            },
            percentage: {
                color: "#dddddd",
                font: "arial",
                fontSize: 12,
                decimalPlaces: 0
            },
            value: {
                color: "#dddddd",
                font: "arial",
                fontSize: 12
            },
            lines: {
                enabled: true,
                style: "straight",
                color: "segment"
            },
            truncation: {
                enabled: false,
                length: 30
            }
        },
        effects: {
            load: {
                effect: "default",
                speed: 1000
            },
            pullOutSegmentOnClick: {
                effect: "bounce",
                speed: 300,
                size: 10
            },
            highlightSegmentOnMouseover: true,
            highlightLuminosity: -0.2
        },
        tooltips: {
            enabled: false,
            type: "placeholder", // caption|placeholder
            string: "",
            placeholderParser: null,
            styles: {
                fadeInSpeed: 250,
                backgroundColor: "#000000",
                backgroundOpacity: 0.5,
                color: "#efefef",
                borderRadius: 2,
                font: "arial",
                fontSize: 10,
                padding: 4
            }
        },
        misc: {
            colors: {
                background: null,
                segments: [
                    "#2484c1", "#65a620", "#7b6888", "#a05d56", "#961a1a", "#d8d23a", "#e98125", "#d0743c", "#635222", "#6ada6a",
                    "#0c6197", "#7d9058", "#207f33", "#44b9b0", "#bca44a", "#e4a14b", "#a3acb2", "#8cc3e9", "#69a6f9", "#5b388f",
                    "#546e91", "#8bde95", "#d2ab58", "#273c71", "#98bf6e", "#4daa4b", "#98abc5", "#cc1010", "#31383b", "#006391",
                    "#c2643f", "#b0a474", "#a5a39c", "#a9c2bc", "#22af8c", "#7fcecf", "#987ac6", "#3d3b87", "#b77b1c", "#c9c2b6",
                    "#807ece", "#8db27c", "#be66a2", "#9ed3c6", "#00644b", "#005064", "#77979f", "#77e079", "#9c73ab", "#1f79a7"
                ],
                segmentStroke: "#ffffff"
            },
            gradient: {
                enabled: false,
                percentage: 95,
                color: "#000000"
            },
            canvasPadding: {
                top: 5,
                right: 5,
                bottom: 5,
                left: 5
            },
            pieCenterOffset: {
                x: 0,
                y: 0
            },
            cssPrefix: null
        },
        callbacks: {
            onload: null,
            onMouseoverSegment: null,
            onMouseoutSegment: null,
            onClickSegment: null
        },
        data: {
            content: [
                {label: "Protein", value: 160, color: "#FF0000"},
                {label: "Carbs", value: 160, color: "#008000"},
                {label: "Fats", value: 80, color: "#0080FF"}
            ]
        }
    });

    //calorie pie chart
    var pie = new d3pie("caloriePie", {
        header: {
            title: {
                text: "",
                color: "#333333",
                fontSize: 18,
                font: "arial",
            },
            subtitle: {
                text: "",
                color: "#666666",
                fontSize: 14,
                font: "arial"
            },
            location: "top-center",
            titleSubtitlePadding: 8
        },
        footer: {
            text: "",
            color: "#666666",
            fontSize: 14,
            font: "arial",
            location: "left"
        },
        size: {
            canvasHeight: 250,
            canvasWidth: 260,
            pieInnerRadius: "50%",
            pieOuterRadius: null
        },
        data: {
            sortOrder: "none",
            ignoreSmallSegments: {
                enabled: false,
                valueType: "percentage",
                value: null
            },
            smallSegmentGrouping: {
                enabled: false,
                value: 1,
                valueType: "percentage",
                label: "Other",
                color: "#cccccc"
            },
            content: []
        },
        labels: {
            outer: {
                format: "label",
                hideWhenLessThanPercentage: null,
                pieDistance: 4
            },
            inner: {
                format: "value",
                hideWhenLessThanPercentage: null
            },
            mainLabel: {
                color: "#333333",
                font: "arial",
                fontSize: 12
            },
            percentage: {
                color: "#dddddd",
                font: "arial",
                fontSize: 20,
                decimalPlaces: 0
            },
            value: {
                color: "#dddddd",
                font: "arial",
                fontSize: 12
            },
            lines: {
                enabled: true,
                style: "straight",
                color: "segment"
            },
            truncation: {
                enabled: false,
                length: 30
            }
        },
        effects: {
            load: {
                effect: "default",
                speed: 1000
            },
            pullOutSegmentOnClick: {
                effect: "bounce",
                speed: 300,
                size: 10
            },
            highlightSegmentOnMouseover: true,
            highlightLuminosity: -0.2
        },
        tooltips: {
            enabled: false,
            type: "placeholder", // caption|placeholder
            string: "",
            placeholderParser: null,
            styles: {
                fadeInSpeed: 250,
                backgroundColor: "#000000",
                backgroundOpacity: 0.5,
                color: "#efefef",
                borderRadius: 2,
                font: "arial",
                fontSize: 10,
                padding: 4
            }
        },
        misc: {
            colors: {
                background: null,
                segments: [
                    "#2484c1", "#65a620", "#7b6888", "#a05d56", "#961a1a", "#d8d23a", "#e98125", "#d0743c", "#635222", "#6ada6a",
                    "#0c6197", "#7d9058", "#207f33", "#44b9b0", "#bca44a", "#e4a14b", "#a3acb2", "#8cc3e9", "#69a6f9", "#5b388f",
                    "#546e91", "#8bde95", "#d2ab58", "#273c71", "#98bf6e", "#4daa4b", "#98abc5", "#cc1010", "#31383b", "#006391",
                    "#c2643f", "#b0a474", "#a5a39c", "#a9c2bc", "#22af8c", "#7fcecf", "#987ac6", "#3d3b87", "#b77b1c", "#c9c2b6",
                    "#807ece", "#8db27c", "#be66a2", "#9ed3c6", "#00644b", "#005064", "#77979f", "#77e079", "#9c73ab", "#1f79a7"
                ],
                segmentStroke: "#ffffff"
            },
            gradient: {
                enabled: false,
                percentage: 95,
                color: "#000000"
            },
            canvasPadding: {
                top: 5,
                right: 5,
                bottom: 5,
                left: 5
            },
            pieCenterOffset: {
                x: 0,
                y: 0
            },
            cssPrefix: null
        },
        callbacks: {
            onload: null,
            onMouseoverSegment: null,
            onMouseoutSegment: null,
            onClickSegment: null
        },
        data: {
            content: [
                {label: "Free", value: 1436, color: "#008000"},
                {label: "Eaten", value: 1064, color: "#FF0000"},
            ]
        }
    });


});