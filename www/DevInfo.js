/*global cordova, module*/

module.exports = {
    getTemperature: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getTemperature", []);
    },
   getIPv6: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getIPv6", []);
    },
  getIPv4: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getIPv4", []);
    },
   getMacAddress: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getMacAddress", []);
    },
   getEUI48MacAddress: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getEUI48MacAddress", []);
    },
   getInterfacesNames: function(successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "getInterfacesNames", []);
    },
   setInterfaceByName: function(name,successCallback) {
        cordova.exec(successCallback, null, "DevInfo", "setInterfaceByName", [name]);
    }

};

