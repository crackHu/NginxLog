package com.hustack.nl.util;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestTemplate;

@Component
public class DownloadUtils {

	private static RestTemplate restTemplate;

	@SuppressWarnings("static-access")
	public DownloadUtils(RestTemplate restTemplate) {
		this.restTemplate = restTemplate;
	}

	public static File down(HttpMethod method, final URI resourceUri) {

		ResponseExtractor<ResponseEntity<File>> responseExtractor = new ResponseExtractor<ResponseEntity<File>>() {
			@Override
			public ResponseEntity<File> extractData(ClientHttpResponse response) throws IOException {
				String alphanumeric = RandomStringUtils.randomAlphanumeric(6);
				Path tempPath = Files.createTempFile(alphanumeric, null);
				File tempFile = tempPath.toFile();
				OutputStream outputStream = Files.newOutputStream(tempPath);
				InputStream body = response.getBody();
				HttpStatus statusCode = response.getStatusCode();
				FileCopyUtils.copy(body, outputStream);
				return ResponseEntity.status(statusCode).headers(response.getHeaders()).body(tempFile);
			}
		};
		File file = restTemplate.execute(resourceUri, method, null, responseExtractor).getBody();
		return file;
	}
	
	public static File down(final URI resourceUri) {
		return down(HttpMethod.GET, resourceUri);
	}
}
