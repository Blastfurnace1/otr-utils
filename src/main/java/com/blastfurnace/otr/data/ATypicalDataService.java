package com.blastfurnace.otr.data;

import com.blastfurnace.otr.rest.request.QueryData;

public interface ATypicalDataService<T> {

	public Long getResultsCount(QueryData qry);

	public Iterable<T> query(QueryData qry);

	public T get(Long id);
	
	public T save(T audio);
	
	public void delete(Long id);

}