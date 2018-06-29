package com.blastfurnace.otr.util;

import java.util.List;

public class StringUtils {

	/** Characters that are not accepted. */
	private static final char[] EXCEPTION_LIST = {'?','%','$','*','`','!','.','@','+','[',']','<','>','~','^','#','.', '&','[',']','{','}','|',';',':',','};
	
	/** Is a character in the list of unacceptable characters. */
	private static boolean inExceptionList (char compare) {
		for (int i = 0; i < EXCEPTION_LIST.length; i++) {
			if (compare == EXCEPTION_LIST[i]) {
				return true;
			}
		}
		return false;
	}

	/** Is this an upper case letter. */
	private static boolean uppercase (char compare) {
		return (compare >= 'A' && compare <= 'Z');
	}

	/** Is this an lower case letter. */
	private static boolean lowercase (char compare) {
		return (compare >= 'a' && compare <= 'z');
	}

	/** Is this a number. */
	private static boolean isANumber (char compare) {
		return (compare >= '0' && compare <= '9');
	}

	/** Replace any invalid characters. */
	public static String replaceInvalidChars (String filename) {
		String name = filename.trim();
		char[] filenameArray = name.toCharArray();
		char[] replace = new char[filenameArray.length];
		for (int i = 0; i < filenameArray.length; i++) {
			if (uppercase(filenameArray[i]) ||
					lowercase(filenameArray[i]) ||
					isANumber(filenameArray[i]) ||
					inExceptionList(filenameArray[i])) {
				replace[i] = filenameArray[i];
			} else {
				replace[i] = ' ';
			}
		}
		String replacement = new String(replace);
		// Get rid of any space except one
		replacement = replacement.replaceAll("   ", " ");
		replacement = replacement.replaceAll("  ", " ");
		return replacement;
	}

	/** aDayInThePark => A Day In The Park. */
	public static String getTitleFromHumpBack(String name) {
		char[] nameArray = name.toCharArray();
		StringBuilder sb = new StringBuilder();
		int lastWordIndex = 0;
		nameArray[0] = String.valueOf(nameArray[0]).toUpperCase().charAt(0);
		for (int i = 0; i < nameArray.length; i++) {
			if (uppercase(nameArray[i])) {
				String namePart = name.substring(lastWordIndex, (i-1));
				sb.append(namePart);
				sb.append(" ");
				lastWordIndex = i;
			} 
		}
		
		if (lastWordIndex < (name.length()-1)) {
			sb.append(name.substring(lastWordIndex, (name.length()-1)));
		}
		return sb.toString().trim();
	}
	
	public static String compressList(@SuppressWarnings("rawtypes") List list) {
		StringBuilder sb = new StringBuilder("");
		if (list != null && list.size() > 0) {
			for (Object o : list) {
				sb.append(o.toString()).append(" ");
			}
		}
		return sb.toString();
	}
}
