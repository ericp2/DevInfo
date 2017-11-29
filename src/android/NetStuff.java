/**
*	NetStuff.java : network related functions to retrieve mac address, etc.
*
*	@author: E.P. Nov 2017
*/
package cordova_plugin_DevInfo;

import java.net.InetAddress;
import java.net.Inet4Address;
import java.net.Inet6Address;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

public class NetStuff
{
	protected NetworkInterface selectedNI;
	protected List<String> names;	// interface names found
	/**
	* constructor: iterate existing interfaces and pick first one which makes sense
	**/
	public NetStuff()
	{
		//this.names = new List<String>;
		this.getNetworkInterface();
	}
	
	/**
	* get an appropriate network interface
	**/
	protected  NetworkInterface getNetworkInterface()
	{
		if(this.selectedNI  != null)
			return this.selectedNI;
		this.names = new ArrayList<String>();

		try
		{
			// getting the list of interfaces in the local machine
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			
			// find first one which will be usefull in determining information
			while( n.hasMoreElements()) //for each interface
			{
				NetworkInterface e = n.nextElement();
				//System.out.println("Interface Name: " + e.getName());
				String name = e.getName();
				this.names.add(name);
				//if(name.equals("lo"))
				//	continue;	// this one is not interresting.
				if(this.selectedNI == null)
					this.selectedNI = e;
				}
		}
		catch(Exception e)
		{
			return null;
			//return e.getMessage();
		}
		return this.selectedNI;
	}

	/**
	 * set selected interface from name
	 * @param String selname the name to set
	 * @return boolean true or false
	 */
	public boolean setInterfaceByName(String selname)
	{
		try
		{
			// getting the list of interfaces in the local machine
			Enumeration<NetworkInterface> n = NetworkInterface.getNetworkInterfaces();
			
			// find first one which will be usefull in determining information
			while( n.hasMoreElements()) //for each interface
			{
				NetworkInterface e = n.nextElement();
				//System.out.println("Interface Name: " + e.getName());
				String name = e.getName();
				if(name.equals(selname))		// found it.
				{
					this.selectedNI = e;
					return true;
				}
			}
		}
		catch(Exception e)
		{
			;
			//return false;
			//return e.getMessage();
		}
		return false;
	}

	/**
	* return list of interface names found
	* @return String[]
	**/
	protected String[] getNames()
	{
		return this.names.toArray(new String[names.size()]);
	}

	/**
	*	getIPv4 address from selected network interface.
	*	@return String ipv4 address
	**/
	protected  String getIPv4()
	{
		if(this.selectedNI  == null)
			return "no Network interface selected";
		/* A interface may be binded to many IP addresses like IPv4 and IPv6
		hence getting the Enumeration of list of IP addresses  */
		Enumeration<InetAddress> a = this.selectedNI .getInetAddresses();
		while( a.hasMoreElements())
		{
			InetAddress addr = a.nextElement();
			if (addr instanceof Inet4Address)
			{
				String add = addr.getHostAddress().toString();
				return add;		// found it;
			}
		}
		return this.selectedNI.getName() + " has no IPv4 address";
	}
	
	/**
	*	getIPv4 address from selected network interface.
	*	@return String ipv6 address
	**/
	public  String getIPv6()
	{
		if(this.selectedNI  == null)
			return "no Network interface selected";
		/* A interface may be binded to many IP addresses like IPv4 and IPv6
		hence getting the Enumeration of list of IP addresses  */
		Enumeration<InetAddress> a = this.selectedNI.getInetAddresses();
		while( a.hasMoreElements())
		{
			InetAddress addr = a.nextElement();
			if (addr instanceof Inet6Address)
			{
				String add = addr.getHostAddress().toString();
				return add;		// found it;
			}
		}
		return this.selectedNI.getName() + " has no IPv6 address";	
	}
	
	/**
	*	getIPv4 address from selected network interface.
	*	@return String ipv6 address
	**/
	public  String getMacAddress()
	{
		if(this.selectedNI  == null)
			return "no Network interface selected";
		try {
			if (this.selectedNI.getHardwareAddress() != null) {
				// getting the mac address of the particular network interface
				byte[] mac = this.selectedNI.getHardwareAddress();
				// properly formatting the mac address
				StringBuilder macAddress = new StringBuilder();
				for (int i = 0; i < mac.length; i++) {
					macAddress.append(String.format("%03X%s", mac[i], (i < mac.length - 1) ? "-" : ""));
				}
				return macAddress.toString();
			}
		}
		catch (Exception e) {
			return this.selectedNI.getName() + "throws exception " + e.getMessage();
		}
		return this.selectedNI.getName() +  " has no mac address";	
	}

	/**
	*	getIPv4 address from selected network interface.
	*	@return String ipv6 address
	**/
	public  String getEUI48MacAddress()
	{
		if(this.selectedNI  == null)
			return "no Network interface selected";
		Enumeration<InetAddress> a = this.selectedNI.getInetAddresses();
		while( a.hasMoreElements())
		{
			InetAddress addr = a.nextElement();
			if (addr instanceof Inet6Address)
			{
				Inet6Address addr6 = (Inet6Address) addr;
				String add = addr.getHostAddress().toString();
				byte[]  eui48mac = this.getMacAddressFromIpv6(addr6);
				StringBuilder macAddress48 = new StringBuilder();
				for(int i =0; i < eui48mac.length; i++) 
				{
					macAddress48.append(String.format("%02X%s", eui48mac[i], (i < eui48mac.length - 1) ? "-" : ""));
				}
				return macAddress48.toString();
			}
		}
		return this.selectedNI.getName() + " Mac address has wrong prefix for this";	
	}
	

	/**
	 * Gets an EUI-48 MAC address from an IPv6 link-local address.
	 * E.g., the IPv6 address "fe80::1034:56ff:fe78:9abc"
	 * corresponds to the MAC address "12-34-56-78-9a-bc".
	 * <p/>
	 * See the note about "local addresses" fe80::/64 and the section about "Modified EUI-64" in
	 * the Wikipedia article "IPv6 address" at https://en.wikipedia.org/wiki/IPv6_address
	 *
	 * @param ipv6 An Inet6Address object.
	 * @return The EUI-48 MAC address as a byte array, null on error.
	 */
	public byte[] getMacAddressFromIpv6(final Inet6Address ipv6)
	{
		byte[] eui48mac = new byte[6];


		if (ipv6 != null) {
			/*
			 * Make sure that this is an fe80::/64 link-local address.
			 */
			final byte[] ipv6Bytes = ipv6.getAddress();
			/* debug!
			// FE80::fe64:baff:fe9a:d465
			//byte[] ipv6Bytes = new byte[16];
			ipv6Bytes[0]  = (byte) 0xFE;
			ipv6Bytes[1]  = (byte)0x80;
			ipv6Bytes[8]  = (byte)0xFE;
			ipv6Bytes[9]  = (byte)0x64;
			ipv6Bytes[10]  = (byte)0xBA;
			ipv6Bytes[11]  = (byte)0xFF;
			ipv6Bytes[12]  = (byte)0xFE;
			ipv6Bytes[13]  = (byte)0x9A;
			ipv6Bytes[14]  = (byte)0xd4;
			ipv6Bytes[15]  = (byte)0x65;
			 */

			if ((ipv6Bytes != null) &&
					(ipv6Bytes.length == 16) &&
					(ipv6Bytes[0] == (byte) 0xfe) &&
					(ipv6Bytes[1] == (byte) 0x80) &&
					(ipv6Bytes[11] == (byte) 0xff) &&
					(ipv6Bytes[12] == (byte) 0xfe)) {
				/*
				 * Allocate a byte array for storing the EUI-48 MAC address, then fill it
				 * from the appropriate bytes of the IPv6 address. Invert the 7th bit
				 * of the first byte and discard the "ff:fe" portion of the modified
				 * EUI-64 MAC address.
				 */
				eui48mac[0] = (byte) (ipv6Bytes[8] ^ 0x2);
				eui48mac[1] = ipv6Bytes[9];
				eui48mac[2] = ipv6Bytes[10];
				eui48mac[3] = ipv6Bytes[13];
				eui48mac[4] = ipv6Bytes[14];
				eui48mac[5] = ipv6Bytes[15];
			}
			else
			{
				eui48mac[0] = 0;
				eui48mac[1] = 0;
				eui48mac[2] = 0;
				eui48mac[3] =  0;
				eui48mac[4] = 0;
				eui48mac[5] =  2;
			}
		}
		return eui48mac;
	}

}