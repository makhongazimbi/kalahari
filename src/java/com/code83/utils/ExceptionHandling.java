package com.code83.utils;

/**
 *
 * 
 * @author Makho Ngazimbi
 * @version $Id: ExceptionHandling.java 865 2011-12-15 03:35:16Z mngazimb $
 * @since 0.1
 *
 */
public class ExceptionHandling {

    public static String getStackTrace (Exception e) {
        String stackTrace = "";
        StackTraceElement[] stack = e.getStackTrace();
        stackTrace += e.toString() + "\n";
        for (StackTraceElement element : stack ) {
            stackTrace += "\t at " + element.toString() + "\n";
        }
        return stackTrace;
    }

}
