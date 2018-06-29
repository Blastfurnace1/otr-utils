package com.blastfurnace.otr.util.reflection;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

import com.blastfurnace.otr.rest.request.QueryData;

public class Utils {
	public static List<FieldProperties> getFieldNames(Object obj) {
		List<FieldProperties> fields = new ArrayList<>();
		Field[] allFields = obj.getClass().getDeclaredFields();
		for (Field field : allFields) {
			FieldProperties fp = new FieldProperties();
			fp.setField(field);
			fields.add(fp);
		}
		return fields;
	}
	
	public static String[] getFields(QueryData queryColumns) {
	    	String[] values = null;
	    	if (queryColumns.hasProperty("requestColumns")) {
	    		String value = queryColumns.getStringValue("requestColumns");
	    		values = value.split(";");
	    	}
	    	
	    	return values;
	    }
	public static boolean isAReturnField(String[] returnColumns, String columnName) {
		// No column name - no
		if (columnName == null || columnName.length() < 1) {
			return false;
		}
		
		// Column definitions - no
		if (columnName.equalsIgnoreCase("fieldDefinitions")) {
			return false;
		}
		// Not filtered - yes
		if (returnColumns == null || returnColumns.length < 1) {
			return true;
		}
		// column found - yes
		for (int i=0; i < returnColumns.length; i++) {
			if (returnColumns[i].equalsIgnoreCase(columnName)) {
				return true;
			}
		}
		return false;
		
	}
}
