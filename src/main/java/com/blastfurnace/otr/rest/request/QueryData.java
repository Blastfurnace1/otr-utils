package com.blastfurnace.otr.rest.request;

import java.util.Map;

import lombok.Data;

@Data
public class QueryData {

	private Integer page = 1;
	
	private Integer size = 20;

	private String sort = "title";
	
	private boolean sortAscending = false;
	
	private boolean joinAnd = true;
	
	private Map<String, String> queryParams;
	
	public boolean hasProperty(String columnName) {
		String value = queryParams.get(columnName);
		if (value == null || value.isEmpty()) {
			return false;
		}
		
		if (value == null || value.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	public String getStringValue(String columnName) {
		if (hasProperty(columnName) == false) { 
			return null;
		}
		return queryParams.get(columnName);
	}
	
	public Integer getIntValue(String columnName) {
		if (hasProperty(columnName) == false) {
			return null;
		}
		return new Integer(queryParams.get(columnName));
	}
	
	public Long getLongValue(String columnName) {
		if (hasProperty(columnName) == false) {
			return null;
		}
		return new Long(queryParams.get(columnName));
	}
	
	
	public Float getFloatValue(String columnName) {
		if (hasProperty(columnName) == false) {
			return null;
		}
		return new Float(queryParams.get(columnName));
	}
	
	public QueryData(Map<String, String> queryParameters) {

    	this.queryParams = queryParameters;
    	
    	if (hasProperty("page")) {
    		setPage(new Integer(queryParams.get("page")));
    	}
    	if (hasProperty("size")) {
    	    setSize(new Integer(queryParams.get("size")));
    	}
    	if (hasProperty("sort")) {
    	    setSort(queryParams.get("sort"));
    	}
    	if (hasProperty("sortASC")) {
    		setSortAscending(new Boolean(queryParams.get("sortASC")));
    	}
    	
	}

	public Integer getOffset() {
		return (page -1) * size;
	}
}
