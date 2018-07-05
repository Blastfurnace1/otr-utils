package com.blastfurnace.otr.service.request;

import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import lombok.Data;

@Data
/** Class to encapsulate arguments for a query based request. */
public class QueryData {
	
	private static final Logger log = LoggerFactory.getLogger(QueryData.class);
	
	private static final String MAP_ENTRY_PRINT_SEPARATOR = "  ";

	/** The page that you are requesting results for. */
	private Integer page = 1;
	
	/** The result set size expected for the page. */
	private Integer size = 20;

	/** How the results are to be sorted. */
	private String sort = "title";
	
	/** Sort the results ascending. */
	private boolean sortAscending = false;
	
	/** Join the query params by an and or an or. */
	private boolean joinAnd = true;
	
	/** A map of the params for a query.
	 * Any value here is related to a sql query param
	 * The key is the object property name
	 * the key-value is the comparison value
	 */
	private Map<String, String> queryParams;
	
	public QueryData() {}
	
	/** Set the Query Properties from values in the Map. */
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
	
	/** Does the Object property exist in the map. */
	public boolean hasProperty(String columnName) {
		// No Map
		if (queryParams == null) {
			return false;
		}
		
		// No value
		String value = queryParams.get(columnName);
		if (value == null || value.isEmpty()) {
			return false;
		}
		
		// has key but no value
		if (value == null || value.isEmpty()) {
			return false;
		}
		
		return true;
	}
	
	/** get the value as a String. */
	public String getStringValue(String columnName) {
		if (hasProperty(columnName) == false) { 
			return null;
		}
		return queryParams.get(columnName);
	}
	
	/** get the value as an Integer. */
	public Integer getIntValue(String columnName) {
		if (hasProperty(columnName) == false) {
			return null;
		}
		try {
			return new Integer(queryParams.get(columnName));
		} catch (Exception e) {
			log.error("Integer: Unable to Covert value " + queryParams.get(columnName) + " for " + columnName);
		}
		return null;
	}
	
	/** get the value as a Long. */
	public Long getLongValue(String columnName) {
		if (hasProperty(columnName) == false) {
			return null;
		}
		try {
			return new Long(queryParams.get(columnName));
		} catch (Exception e) {
			log.error("Long: Unable to Covert value " + queryParams.get(columnName) + " for " + columnName);
		}
		return null;
	}
	
	/** get the value as a Float. */
	public Float getFloatValue(String columnName) {
		if (hasProperty(columnName) == false) {
			return null;
		}
		try {
			return new Float(queryParams.get(columnName));
		} catch (Exception e) {
			log.error("Float: Unable to Covert value " + queryParams.get(columnName) + " for " + columnName);
		}
		return null;
	}
	
	/** get the value as a Double. */
	public Double getDoubleValue(String columnName) {
		if (hasProperty(columnName) == false) {
			return null;
		}
		try {
			return new Double(queryParams.get(columnName));
		} catch (Exception e) {
			log.error("Double: Unable to Covert value " + queryParams.get(columnName) + " for " + columnName);
		}
		return null;
	}
	
	/** For pagination: set the start and size of the next set of results. */
	public Integer getOffset() {
		// values not defined
		if (page == null || size == null) {
			log.error("Page " + page + " or Size " + size + " is not defined for pagination returning a size of 20 and setting page to 1 if it is not defined");
			if (page == null) {
				page = new Integer(1);
			}
			if (size == null) {
				size = new Integer(20);
			}
		}
		
		if ((page-1) < 1 ) {
			return size; 
		}
		
		return (page -1) * size;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("page: ").append(page).append(MAP_ENTRY_PRINT_SEPARATOR);
		sb.append("size: ").append(size).append(MAP_ENTRY_PRINT_SEPARATOR);
		sb.append("sort: ").append(sort).append(MAP_ENTRY_PRINT_SEPARATOR);
		sb.append("sortAscending: ").append(sortAscending).append(MAP_ENTRY_PRINT_SEPARATOR);
		sb.append("joinAnd: ").append(joinAnd).append(MAP_ENTRY_PRINT_SEPARATOR);
		if (queryParams != null) {
			sb.append(MAP_ENTRY_PRINT_SEPARATOR).append("Map Values: ").append(MAP_ENTRY_PRINT_SEPARATOR);
			Set<String> keys = queryParams.keySet();
			for (String key : keys) {
				String value = queryParams.get(key);
				sb.append(key).append(": ").append(value).append(MAP_ENTRY_PRINT_SEPARATOR);
			}
		}
		
		return sb.toString();
	}
}
