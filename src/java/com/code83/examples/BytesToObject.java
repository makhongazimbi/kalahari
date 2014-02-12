package com.code83.examples;

import java.io.IOException;

import com.code83.utils.messages.HeartBeat;
import com.code83.utils.messages.MessageFactory;


public class BytesToObject {
	/** Converts an array of bytes back to its constituent object. The
	input array is assumed to
	* have been created from the original object. Uses the Logging
	utilities in j2sdk1.4 for
	* reporting exceptions.
	* @param bytes the byte array to convert.
	* @return the associated object.
	*/
	public static Object toObject(byte[] bytes){
		Object object = null;
	try{
		object = new java.io.ObjectInputStream(new
				java.io.ByteArrayInputStream(bytes)).readObject();
	}catch(java.io.IOException ioe){
	
	}catch(java.lang.ClassNotFoundException cnfe){
	
	}
	return object;
	}
	
	
	public static void main(String [] args) {
		HeartBeat hbt = MessageFactory.createHeartBeat();
		System.out.println("HeartBeat: " + hbt);
		try {
			byte [] bytes = hbt.getBytes();
			System.out.println("byte array length: "+bytes.length);
			
			HeartBeat beat = (HeartBeat)toObject(bytes);
			
			System.out.println("back again: " + beat);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
