
//execution starts here

function hello ()
{
    return "Hello ";
}

function world ()
{
    return "world ";
}

var delay=1000;
var output = "";

setTimeout(function(){
output = output + hello(); //this executes after 1 second
}, delay); 

output = output + world(); //execution continues despite hello() not having finished yet 

console.log(output); //"world" is output