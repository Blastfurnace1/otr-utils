package com.blastfurnace.otr.util.reflection;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ObjectData<T> {
	
	    // An object of type T is declared
	
	    private T data;
	    public ObjectData(T data) {  
	    	this.data = data;
	    
	    }  // constructor
	    public T getObject()  { return this.data; }

		public Map<String,Object> addValues(String[] columns, List<FieldProperties> df) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
			Map<String,Object> map = new HashMap<String, Object>();
			
			for (FieldProperties fp : df) {
				if (Utils.isAReturnField(columns, fp.getName())) {
					Field f = data.getClass().getDeclaredField(fp.getName());
			    	f.setAccessible(true);
			    	Object value =  f.get(data);
			    	map.put(fp.getName(), value);
				}
			}
			return map;
		}
}

