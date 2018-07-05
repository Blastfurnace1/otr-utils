package com.blastfurnace.otr.constants;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Constants {
	
	public static Long MEGABYTE = (long) (1024*1024);
	
	@Value ("${service.ping.response.ok}")
	public static String PING_SERVICE_RESPONSE = "STATUS OK";
}
