# DevInfo
Cordova plugin to retrieve useful device information (temperature, mac addresss...)

Currently only implemented for Android.

entry points:

float getTemperature(void): return battery temperature in degrees celsius.
String[] getInterfacesNames(void): retrieve all interfaces names declared in Android.

String getIPv4(name): retrieve  ip v4 address for interface named name. may not work if name is not valid.
String getIPv6(name): retrieve  ip v6 address for interface named name. may not work if name is not valid.
String getMacAddress(name): retrieve  mac address (hardware address) for interface named name. 
String getEUI48MacAddress(name) retrieve 48 bit mac address for interface named name. only works if starts with 0xFE80


Sample usage:  see test/index.html, and test/js/index.js provided. this shows a simple table with:

	- a temperature element: refreshed every 2 s.
	- list of interface names that device exposes. on tapping on show, it will update the values of ipv6,ipv4,MAC and EUI48MAC.

