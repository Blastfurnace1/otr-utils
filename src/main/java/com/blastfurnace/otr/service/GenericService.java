package com.blastfurnace.otr.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.blastfurnace.otr.data.ATypicalDataService;
import com.blastfurnace.otr.rest.request.QueryData;
import com.blastfurnace.otr.service.payload.PayloadWithCount;
import com.blastfurnace.otr.service.response.GenericResponse;
import com.blastfurnace.otr.util.reflection.FieldProperties;
import com.blastfurnace.otr.util.reflection.ObjectData;
import com.blastfurnace.otr.util.reflection.Utils;

/** Generic Services to be instantiated by type, deal with the common 
 * methods of getting data and checking the status of the operation. 
 * Delegates calls to the A Typical Data Service
 * @author jim_8
 *
 * @param <T>
 */
public class GenericService<T> {
	
	private List<FieldProperties> fieldDefinitions;
	
	public GenericService(List<FieldProperties> fieldDefinitions) {
		super();
		this.fieldDefinitions = fieldDefinitions;
	}

	public GenericResponse<List<Map<String,Object>>> query(QueryData qry, ATypicalDataService<T> service) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		GenericResponse<List<Map<String,Object>>> response = new GenericResponse<List<Map<String,Object>>>(list);
		try {
			String[] columns = Utils.getFields(qry);

			Iterable<T> props = service.query(qry);

			if (props == null) {
				response.setStatus(10l);
				response.setMessage("No Results found");
			} else {
				for (T afp : props) {
					ObjectData<T> obj = new ObjectData<T>(afp);
					Map<String,Object> map = null;
					try {
						map = obj.addValues(columns, fieldDefinitions);
					} catch (Exception  e) {
						response.setErrorOccured(true);
						response.addError(e.getMessage());
					}
					list.add(map);
				}

				if (list.isEmpty() && response.getErrorOccured() == false) {
					response.setStatus(10l);
					response.setMessage("No Results found");
				}

				if (response.getErrorOccured()) {
					response.setStatus(-20l);
					response.setMessage("unable to get complete data");
				}
				response.setPayload(list);
			}
		} catch (Exception e) {
			response.setStatus(-10l);
			response.setMessage("An Error Occurred - unable to get results");
			response.setErrorOccured(true);
			response.addError(e.getMessage());
		}
		return response;
	}

	public GenericResponse<PayloadWithCount<List<Map<String,Object>>>> queryWithCount(QueryData qry, ATypicalDataService<T> service) {
		List<Map<String,Object>> list = new ArrayList<Map<String,Object>>();
		PayloadWithCount<List<Map<String,Object>>> payload = new PayloadWithCount<List<Map<String,Object>>>(list,0l);
		GenericResponse<PayloadWithCount<List<Map<String,Object>>>> response = new GenericResponse<PayloadWithCount<List<Map<String,Object>>>>(payload);
		try {
			String[] columns = Utils.getFields(qry);

			Iterable<T> props = service.query(qry);

			if (props == null) {
				response.setStatus(10l);
				response.setMessage("No Results found");
			} else {
				for (T afp : props) {
					ObjectData<T> obj = new ObjectData<T>(afp);
					Map<String,Object> map = null;
					try {
						map = obj.addValues(columns, fieldDefinitions);
					} catch (Exception  e) {
						response.setErrorOccured(true);
						response.addError(e.getMessage());
					}
					list.add(map);
				}

				if (list.isEmpty() && response.getErrorOccured() == false) {
					response.setStatus(10l);
					response.setMessage("No Results found");
				}

				if (response.getErrorOccured()) {
					response.setStatus(-20l);
					response.setMessage("unable to get complete data");
				}
				response.getPayload().setPayload(list);
			}
			
			GenericResponse<Long> count = getResultsCount(qry, service);
			if (count.getErrorOccured() || count.getStatus() != 0) {
				response.updateGenericResponse(count);
				response.getPayload().setResultsCount(-1l);
			} else {
				response.getPayload().setResultsCount(count.getPayload());
			}
			 
		} catch (Exception e) {
			response.setStatus(-10l);
			response.setMessage("An Error Occurred - unable to get results");
			response.setErrorOccured(true);
			response.addError(e.getMessage());
		}
		return response;
	}
	
	public GenericResponse<T> get(Long id, ATypicalDataService<T> service) {
		GenericResponse<T> response = new GenericResponse<T>(null);
		try {
			T data = service.get(id);
			response.setPayload(data);

			if (data == null) {
				response.setStatus(10l);
				response.setMessage("No Results found");
			}
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatus(-10l);
			response.setMessage("An Error Occurred - unable to get a result");
			response.setErrorOccured(true);
			response.addError(e.getMessage());
		}

		return response;
	}

	
	public GenericResponse<Long> getResultsCount(QueryData qry, ATypicalDataService<T> service) {
		GenericResponse<Long> response = new GenericResponse<Long>(null);
		try {
			Long count = service.getResultsCount(qry);
			response.setPayload(count);
			if (count == null) {
				response.setStatus(-30l);
				response.setMessage("Unable to save Record");
			}
		} catch (Exception e) {
			response.setStatus(-10l);
			response.setMessage("An Error Occurred - unable to get a result count");
			response.setErrorOccured(true);
			response.addError(e.getMessage());
		}

		return response;
	}

	public GenericResponse<String> delete(Long id, ATypicalDataService<T> service) {

		GenericResponse<String> response = new GenericResponse<String>("");
		try {
			service.delete(id);
		} catch (Exception e) {
			response.setStatus(-10l);
			response.setMessage("An Error Occurred - unable to delete record");
			response.setErrorOccured(true);
			response.addError(e.getMessage());
		}

		return response;
	}

	public GenericResponse<T> save(T data, ATypicalDataService<T> service) {
		 
		GenericResponse<T> response = new GenericResponse<T>(null);
		if (data == null) {
			response.setStatus(-50l);
			response.setMessage("Unable to save Record - nothing to save");
			return response;
		}
		try {
			T newData = service.save(data);
			response.setPayload(newData);
			if (newData == null) {
				response.setStatus(-50l);
				response.setMessage("Unable to save Record");
			}
		} catch (Exception e) {
			response.setStatus(-10l);
			response.setMessage("An Error Occurred - unable to get a result");
			response.setErrorOccured(true);
			response.addError(e.getMessage());
		}

		return response;
	}
}
