/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

var customFoodJSONArray; //an array of JSON objects which represent the current users custom foods
var eatenFoodJSONArray; //an array of JSON objects which represent the current users eaten foods
var searchResultFoodJSONArray; //an array js JSON objects which represent the current users search results if they searched the database
var selectedFoodAttributeJSONArray; //a single JSON object containing food attributes the user wants to see e.g protein,carbs,saturated fats

//global viewDate object
//this is the date the user is currently viewing
var viewDate;
var eventTriggered = false;