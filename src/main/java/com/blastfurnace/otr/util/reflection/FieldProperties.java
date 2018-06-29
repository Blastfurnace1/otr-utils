package com.blastfurnace.otr.util.reflection;

import java.lang.reflect.Field;

import lombok.Data;

@Data
public class FieldProperties {

	private Field field;
	
	public String getType() {
		return field.getType().getName();
	};
	
	public String getName() {
		return field.getName();
	};
}
