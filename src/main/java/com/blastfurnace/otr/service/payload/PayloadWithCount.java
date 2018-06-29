package com.blastfurnace.otr.service.payload;

import lombok.Data;

@Data
/** return the results with a count of either total records or 
 * The total number of results for the query (eg for pagination)
 * @author jim_8
 *
 * @param <T>
 */
public class PayloadWithCount<T> {
	
	private T payload;
	
	private Long resultsCount = 1l;

	public PayloadWithCount(T payload, Long resultsCount) {
		this.payload = payload;
		this.resultsCount = resultsCount;
	}

}


