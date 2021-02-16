package com.project.primenumbersservice.application;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;

import java.net.URL;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class PrimeNumbersServiceApplicationTest {

	@LocalServerPort
	private int port;

	private URL base;

	@Autowired
	private TestRestTemplate template;

	@Test
	public void shouldReturnExpectedResponseWithValidInput() throws Exception {
		this.base = new URL ("http://localhost:" + port + "/primes/10");
		String expectedResponse = "{\"Initial\":10,\"Primes\":[2,3,5,7]}";
		ResponseEntity<String> response = template.getForEntity(base.toString(),
				String.class);
		assertThat(response.getBody()).isEqualTo(expectedResponse);
	}

	@Test
	public void shouldThrowErrorWithInvalidInput() throws Exception {
		this.base = new URL ("http://localhost:" + port + "/primes/");
		ResponseEntity<String> response = template.getForEntity(base.toString(),
				String.class);
		assertThat(response.getBody()).contains("\"error\":\"Not Found\"");
	}
}