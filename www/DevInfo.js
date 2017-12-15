/*global cordova, module*/

module.exports = {
    getTemperature: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getTemperature", []);
    },
   getIPv6: function(name,successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getIPv6", [name]);
    },
  getIPv4: function(name,successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getIPv4", [name]);
    },
   getMacAddress: function(name,successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getMacAddress", [name]);
    },
   getEUI48MacAddress: function(name,successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getEUI48MacAddress", [name]);
    },
   getInterfacesNames: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getInterfacesNames", []);
    }

};

