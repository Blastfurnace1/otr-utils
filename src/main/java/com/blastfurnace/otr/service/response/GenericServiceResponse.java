package com.blastfurnace.otr.service.response;

import java.util.ArrayList;
import java.util.List;

import com.blastfurnace.otr.api.response.GenericResponse;

import lombok.Data;

@Data
/** return the results as payload and any information about the results of the
 * service request (errors, success, ...)
 * @author jim_8
 *
 * @param <T>
 */
public class GenericServiceResponse<T> extends GenericResponse<T> {
	
	/** Very basic and a bit repetitive. More for looking at the error messages. */
	private Boolean errorOccured = false;
	
	/** actual error messages - good for debugging - bad for customers. */
	private List<String> errors = new ArrayList<String>();
	
	/** add an error message. */
	public void addError(String err) {
		errors.add(err);
	}
	
	public GenericServiceResponse () {
		super();
	}
	
	public GenericServiceResponse (T payload) {
		super(payload);
	}
	
	public void updateGenericResponse(GenericServiceResponse<T> response) {
		// update the status only if its not in an error state
		if (this.getStatus() == 0) {
			if (response.getStatus() != 0) {
				this.setStatus(response.getStatus());
				this.setMessage(response.getMessage());
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
