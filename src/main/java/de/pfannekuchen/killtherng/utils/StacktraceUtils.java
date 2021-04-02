package de.pfannekuchen.killtherng.utils;

/**
 * 
 * Utils for localizing a method call {@link #getStacktrace()},
 * and more.
 * 
 * @author Pancake
 */
public class StacktraceUtils {
	
	/**
	 * Returns from where a Method has been called
	 * @author Pancake
	 * @return Returns a compact Call Trace
	 */
	public static String getStacktrace() {
		StackTraceElement error = Thread.currentThread().getStackTrace()[3];
		return error.getClassName() + "." + error.getMethodName() + ":" + error.getLineNumber();
	}
	
}
