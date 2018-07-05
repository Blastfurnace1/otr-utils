package com.blastfurnace.otr.api.response;

import org.springframework.beans.factory.annotation.Value;

import lombok.Data;

@Data
/** return the results as payload and any information about the results of the
 * request (errors, success, ...)
 * @author jim_8
 *
 * @param <T>
 */
public class GenericResponse<T> {
	
	private T payload;
	
	/** version of the interface. */
	@Value("${app.version}")
	private Long version;
	
	/** status of the request: negative nums mean an error occured; 0 means ok; > 0 is positive messages. */
	private Long status = 0l;
	
	/** More information about the result of the request - think customer messages. */
	private String message = "Result Ok";
	
	public GenericResponse () {}
	
	public GenericResponse (T payload) {
		this.payload = payload;
	}
	
}
