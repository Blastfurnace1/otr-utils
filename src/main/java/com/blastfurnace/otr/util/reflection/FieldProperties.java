package com.blastfurnace.otr.util.reflection;

import java.lang.reflect.Field;

import lombok.Data;

@Data
/** Wrapper class around an objects defined field to expose only the needed field properties. */
public class FieldProperties {

	private Field field;
	
	/** get the fully packaged object type. */
	public String getType() {
		if (field != null) {
			return field.getType().getName();
		}
		
		return "";
	};
	
	/** get the field name. */
	public String getName() {
		if (field != null) {
			return field.getName();
		}
		return "";
	};
	
}
