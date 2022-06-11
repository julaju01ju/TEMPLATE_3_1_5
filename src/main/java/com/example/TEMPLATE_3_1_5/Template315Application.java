package com.example.TEMPLATE_3_1_5;

import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.stream.Collectors;


@RestController
public class Template315Application {

	private static final String URL = "http://94.198.50.185:7081/api/users";
	private static RestTemplate restTemplate = new RestTemplate();

	public static void main(String[] args) {
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		HttpEntity<Object> requestEntity = new HttpEntity<>(headers);
		ResponseEntity<List> responseEntity = getAllUsers(requestEntity);
		headers.set("Cookie", responseEntity.getHeaders().get("Set-Cookie").stream().collect(Collectors.joining(";")));


		User newUser = new User();
		newUser.setId(3L);
		newUser.setName("James");
		newUser.setLastName("Brown");
		newUser.setAge((byte) 27);
		HttpEntity<User> entity = new HttpEntity<>(newUser, headers);
		createUser(entity);

		newUser.setName("Thomas");
		newUser.setLastName("Shelby");
		entity = new HttpEntity<>(newUser, headers);
		updateUser(entity);

		deleteUser(entity);


	}

	public static ResponseEntity<List> getAllUsers(HttpEntity<Object> requestEntity) {
		ResponseEntity<List> responseEntity = restTemplate.exchange(URL, HttpMethod.GET, requestEntity, List.class);
		return responseEntity;
	}

	public static void createUser(HttpEntity<User> entity) {
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.POST, entity, String.class);
		System.out.println(responseEntity.getBody());
	}

	public static void updateUser(HttpEntity<User> entity) {
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL, HttpMethod.PUT, entity, String.class);
		System.out.println(responseEntity.getBody());
	}

	public static void deleteUser(HttpEntity<User> entity) {
		ResponseEntity<String> responseEntity = restTemplate.exchange(URL + "/3", HttpMethod.DELETE, entity, String.class);
		System.out.println(responseEntity.getBody());
	}
}
