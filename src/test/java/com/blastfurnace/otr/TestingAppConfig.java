package com.blastfurnace.otr;

import static org.assertj.core.api.BDDAssertions.then;

import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port=9001", "test.server="})
public class TestingAppConfig {

	@LocalServerPort
	protected int port;

	@Value("${local.management.port}")
	protected int mgt;

	@Autowired
	protected TestRestTemplate testRestTemplate;
	
	/** local running instance of embedded tomcat visa-vis Spring. */
	protected String testServer = "http://localhost:";
	
	@Test
	/** Make sure the server runs. */
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		@SuppressWarnings("rawtypes")
		ResponseEntity<Map> entity = this.testRestTemplate.getForEntity(
				testServer + this.mgt + "/actuator/info", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
	}

}
	