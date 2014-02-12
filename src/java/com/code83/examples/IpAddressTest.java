package com.code83.examples;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.UnknownHostException;
import java.util.Enumeration;

public class IpAddressTest {
	public static void main(String [] args){
		try {
	        InetAddress addr = InetAddress.getLocalHost();
	    
	        // Get IP Address
	        String ipAddr = addr.getHostAddress();
	    
	        // Get hostname
	        String hostname = addr.getHostName();
	        
	        
	        String ipStr = new String(ipAddr);
	        System.out.println("IP: "+ipStr+", hostname: "+hostname);
	    } catch (UnknownHostException e) {
	    }
	    
	    try {
	         Enumeration<?> e = NetworkInterface.getNetworkInterfaces();

	         while(e.hasMoreElements()) {
	            NetworkInterface ni = (NetworkInterface) e.nextElement();
	            System.out.println("Net interface: "+ni.getName());

	            Enumeration<?> e2 = ni.getInetAddresses();

	            while (e2.hasMoreElements()){
	               InetAddress ip = (InetAddress) e2.nextElement();
	               if (ip.isLoopbackAddress()) {
	            	   System.out.println("LOOP BACK");
	               } 
	               if (!ip.isLoopbackAddress() && ip instanceof Inet4Address) {
	            	   System.out.println("********* woo hoo *********");
	               }
	               System.out.println("IP address: "+ ip.toString());
	            }
	         }
	      }
	      catch (Exception e) {
	         e.printStackTrace();
	      }


	}
}
