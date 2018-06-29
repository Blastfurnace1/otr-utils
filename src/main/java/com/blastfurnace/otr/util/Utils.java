package com.blastfurnace.otr.util;

public class Utils {
	
	/** Get the long value for a String; if unable to parse - return a -1. */
	public static long getLong(String value) {
		try {
			return Long.parseLong(value);
		} catch (Exception e) {	}
		return -1;
	}
}
