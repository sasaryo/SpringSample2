package com.example.demo.login.domain.service;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class RestClient {
	private static final String URL_POST_MAIL_SEND = "http://localhost:8081/rest/sendMail";

	RestTemplate restTemplate = new RestTemplate();

	public boolean postMailSend(String userId) {
		try {
		 boolean result = restTemplate.getForObject(URL_POST_MAIL_SEND + "/" + userId, Boolean.class);
		 return result;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}
