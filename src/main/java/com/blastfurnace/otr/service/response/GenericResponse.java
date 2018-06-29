package com.blastfurnace.otr.service.response;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;

@Data
/** return the results as payload and any information about the results of the
 * service request (errors, success, ...)
 * @author jim_8
 *
 * @param <T>
 */
public class GenericResponse<T> {
	
	private T payload;
	
	/** version of the interface. */
	private Long version = 1l;
	
	/** status of the request: negative nums mean an error occured; 0 means ok; > 0 is positive messages. */
	private Long status = 0l;
	
	/** More information about the result of the request - think customer messages. */
	private String message = "Result Ok";
	
	/** Very basic and a bit repetitive. More for looking at the error messages. */
	private Boolean errorOccured = false;
	
	/** actual error messages - good for debugging - bad for customers. */
	private List<String> errors = new ArrayList<String>();
	
	/** add an error message. */
	public void addError(String err) {
		errors.add(err);
	}
	
	public GenericResponse (T payload) {
		this.payload = payload;
	}
	
	@SuppressWarnings("rawtypes")
	public void updateGenericResponse(GenericResponse response) {
		// update the status only if its not in an error state
		if (this.status== 0) {
			if (response.getStatus() != 0) {
				this.status = response.getStatus();
				this.message = response.getMessage();
			}
		}
		
		if (response.getErrorOccured()) {
			this.errorOccured = true;
			List<String> errors = response.getErrors();
			for (String error : errors) {
				this.addError(error);
			}
		}
	}
}
