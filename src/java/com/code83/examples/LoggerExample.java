package com.code83.examples;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerExample {
	
	public static void main (String[] args) {
		LoggerExample instance = new LoggerExample();
		Class<?> classObj = instance.getClass(); 
		Logger logger = LoggerFactory.getLogger(classObj);
		
		try {
			Integer.parseInt("e");
		} catch (Exception e) {
			e.printStackTrace();
			StackTraceElement[] stack = e.getStackTrace();
			System.out.println(">>>" + e.toString());
			for (StackTraceElement element : stack) {
				System.out.println(">\t at " + element.toString());
			}
			System.out.println(">>>>>>>"+e.getMessage());
			
			logger.error(e.toString());			
		}
		
		logger.debug("Example debug");
		logger.error("Example error");
		logger.info("Example info");
		logger.trace("Example trace");
		logger.warn("Example warn");
	}

	public LoggerExample () {
		String name = this.getClass().getName();
		System.out.println("Name of the class: " + name);
	}
	
}
