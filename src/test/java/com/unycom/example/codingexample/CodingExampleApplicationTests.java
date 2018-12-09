package com.unycom.example.codingexample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment= SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CodingExampleApplicationTests {


	@LocalServerPort
	private int port;

	private final String host = "http://localhost";

	TestRestTemplate restTemplate = new TestRestTemplate();
	private String getUrl(String path) {
		return String.format("%s:%d/%s", host, port, path);
	}

	@Test
	public void testGetListOfCustomers() {
		ResponseEntity<String> response = restTemplate.getForEntity(getUrl("/api/v1/customers"), String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("Haydn"));
		assertTrue(response.getBody().contains("Mozart"));
		assertTrue(response.getBody().contains("Beethoven"));
	}

	@Test
	public void testGetCustomer() {
		ResponseEntity<String> response = restTemplate.getForEntity(getUrl("/api/v1/customers/0001"), String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("Haydn"));
	}

	@Test
	public void testGetUnknownCustomer() {
		ResponseEntity<?> response = restTemplate.getForEntity(getUrl("/api/v1/customers/XXX-UNKNOWN"), String.class);
		assertEquals(404, response.getStatusCodeValue());
	}

	@Test
	public void testGetListOfOrders() {
		ResponseEntity<String> response = restTemplate.getForEntity(getUrl("/api/v1/orders"), String.class);
		assertEquals(200, response.getStatusCodeValue());
		assertTrue(response.getBody().contains("violin")); // the store without violins can't pass this test
	}

	@Test
	public void testGetOrder() {
		ResponseEntity<String> response = restTemplate.getForEntity(getUrl("/api/v1/orders/1"), String.class);
		assertEquals(200, response.getStatusCodeValue());
	}

	@Test
	public void testGetUnknownOrder() {
		ResponseEntity<?> response = restTemplate.getForEntity(getUrl("/api/v1/orders/1000001"), String.class);
		assertEquals(404, response.getStatusCodeValue());
	}
}
