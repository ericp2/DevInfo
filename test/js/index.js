/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
var app = {
    // Application Constructor
    initialize: function() {
        document.addEventListener('deviceready', this.onDeviceReady.bind(this), false);
    },

    // deviceready Event Handler
    //
    // Bind any cordova events here. Common events are:
    // 'pause', 'resume', etc.
    onDeviceReady: function() {
        this.receivedEvent('deviceready');
		window.setInterval(function(){DevInfo.getTemperature (
					function(values){
					  var elmt = document.getElementById("jtemp");
					  if(elmt != null)
						  elmt.innerHTML = ""+values[0].toFixed(1);
					//console.log (values[0]);
					}
				);
			}, 
			2000);
		DevInfo.getInterfacesNames(getnamessuccess);
    },

    // Update DOM on a Received Event
    receivedEvent: function(id) {
        var parentElement = document.getElementById(id);
        var listeningElement = parentElement.querySelector('.listening');
        var receivedElement = parentElement.querySelector('.received');

        listeningElement.setAttribute('style', 'display:none;');
        receivedElement.setAttribute('style', 'display:block;');

        console.log('Received Event: ' + id);
    }
};

app.initialize();

/**
* common function for all returns . 
* @param string[] values contains return values from plugin
* @param String elmntname html element name to assign return value
**/
function onSuccessPlugin(values,elmtname)
{
	var elmt = document.getElementById(elmtname);
	// alert("onSuccessipv6" + elmt+'  elmtname='+elmtname);
	console.log ("in onSuccessPlugin, value = "+values[0]+ "for element="+elmtname);
	if(elmt != null)
		elmt.innerHTML = ""+values[0];
}
  
  
  // called when selection is made
  function getnamessuccess(values)
  {
	var elmt = document.getElementById("jiname");
	 for(i=0;i<values.length;i++)
	 {
		 var el = document.createElement("option");
		 el.textContent = values[i];
		 el.value = values[i];
		 elmt.appendChild(el);
	 }
	 // add one more to choose
	el = document.createElement("option");
	el.textContent = "Choose";
	el.value = "";
	elmt.appendChild(el);
	var index = elmt.options.length-1;
	elmt.options.selectedIndex = index;	// select the "choose 
	 
	  //alert("here"+values[0]);
  }
  
 	var elmt = document.getElementById("jibut");
	//alert(elmt);
  elmt.onclick = function()
  {
	var elmt = document.getElementById("jiname");
	var name = elmt.value;
	console.log ("in jibut.clicked, value = "+name);
	if(name != "")
	{
		DevInfo.getIPv6(name,function(values){onSuccessPlugin(values,'jipv6')});
 		DevInfo.getIPv4(name,function(values){onSuccessPlugin(values,'jipv4')});
		DevInfo.getMacAddress(name,function(values){onSuccessPlugin(values,'jmac')});
		DevInfo.getEUI48MacAddress(name,function(values){onSuccessPlugin(values,'jEUI48Mac')});
	}
  }
  
  
  