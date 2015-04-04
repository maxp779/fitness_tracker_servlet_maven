/* 
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


//<script>
                 //<script type="text/javascript" src="/fitness_tracker_servlet_maven/ServerAPI.js">
            //here we get the jsessionID
            //console.log("test0");
            var currentURL = window.location.href;
            var jSessionIDSearchParameter = ";jsessionid=";
            var jSessionIDLocation = currentURL.search(jSessionIDSearchParameter);
            //document.write("test");
            if(jSessionIDLocation === -1)
            {
                //no jSessionID found
                //document.write("test1");
            }
            else
            {
                var jSessionIDStart = jSessionIDLocation + jSessionIDSearchParameter.length;
                var jSessionID = currentURL.substring(jSessionIDStart);
                //document.write("test2");
                if(typeof(Storage) !== void(0)) 
                {
                    window.sessionStorage.setItem("jSessionID", jSessionID);
                    document.write("test3");
                }
                else 
                {
                    // no web storage support
                }
            }
            //document.write(window.sessionStorage.getItem(projectName+"_jSessionID"));
        //</script>