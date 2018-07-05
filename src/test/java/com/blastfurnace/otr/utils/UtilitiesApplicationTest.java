/*
 * Copyright 2012-2014 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.blastfurnace.otr.utils;

import org.junit.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.HashMap;
import java.util.Map;

import com.blastfurnace.otr.AppConfigTest;
import com.blastfurnace.otr.api.response.GenericResponse;
import com.blastfurnace.otr.constants.Constants;
import com.blastfurnace.otr.service.request.QueryData;


/**
 * Integration Tests for Audio Services
 *
 * @author Jim Blackson
 */
public class UtilitiesApplicationTest extends AppConfigTest {

	private static final Logger log = LoggerFactory.getLogger(UtilitiesApplicationTest.class);  
	

	/** Test the Generic Rest Response object. */
	private void testGenericRestResponse() {
		log.info(String.valueOf(Constants.MEGABYTE));
		GenericResponse<Long> resp = new GenericResponse<Long>();
		resp.setPayload(Constants.MEGABYTE);

		then (Constants.MEGABYTE == resp.getPayload());
	} 
	
	/** Test the Ping Servlet. */
	private void testPingServlet() {
		ResponseEntity<String> entity = this.getTestRestTemplate().getForEntity(
				"http://localhost:" + this.getPort() + "/ping", String.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		HttpHeaders httpHeaders = this.getTestRestTemplate()
				  .headForHeaders("http://localhost:" + this.getPort() + "/ping");
		
		then(httpHeaders.getContentType()
				  .includes(MediaType.TEXT_HTML));
		
		String response = this.getTestRestTemplate().getForObject(
				"http://localhost:" + this.getPort() + "/ping", String.class);

		then(response).isNotNull();
		then(response.equals(Constants.PING_SERVICE_RESPONSE));
		log.info(response);
	}
	
	/** Test the Long Servlet. */
	private void testLogServlet() {
		ResponseEntity<String> entity = this.getTestRestTemplate().getForEntity(
				"http://localhost:" + this.getPort() + "/log", String.class);

		then(entity).isNotNull();
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		HttpHeaders httpHeaders = this.getTestRestTemplate()
				  .headForHeaders("http://localhost:" + this.getPort() + "/log");
		
		then(httpHeaders.getContentType()
				  .includes(MediaType.TEXT_PLAIN));
		then(httpHeaders.getContentDisposition().getFilename()).isNotNull();
		
		log.info(httpHeaders.getContentDisposition().getFilename());
	}
	
	private Map<String, String> buildQueryTestData() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("page", "5");
		map.put("size", "25");
		map.put("sort", "Twinkles");
		map.put("sortAscending", "1");
		map.put("joinAnd", "0");
		map.put("rankles", "rankled");
		map.put("wrinkles", "wrinkled");
		return map;
	}
	
	/** Test the query Object. */
	private void testQueryData() {
		QueryData query = new QueryData();
		log.info("Default QueryData properties");
		log.info(query.toString());
		
		Map<String, String> map = buildQueryTestData();
		QueryData queryNew = new QueryData(map);
		log.info("QueryData with set properties");
		log.info(queryNew.toString());
		then(queryNew.hasProperty("rankles"));
		then(queryNew.hasProperty("wrinkles"));
	}
	
	@Test
	/** Test Utilities Objects. */
	public void shouldPerformActions() throws Exception {
		log.info("Running Utils tests");
		
		testGenericRestResponse();
		testPingServlet();
		testLogServlet();
		testQueryData();
		
		log.info("Utils tests Complete");
		
	}
}
