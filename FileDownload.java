package com.Kpravin.SpringRESTExample;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Arrays;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/**
 * 
 * @author Pravin.Kumar
 *
 */
@RestController
@RequestMapping("/api")
public class FileDownload {

	@Autowired
	private HttpServletResponse httpResponse;

	@RequestMapping(value = "/download", method = RequestMethod.GET)
	public ResponseEntity download() throws IOException {

		String txUri = "https://www.ancb.com/.pdf";

		RestTemplate restTemplate = new RestTemplate();
		SimpleClientHttpRequestFactory requestFactory = new SimpleClientHttpRequestFactory();
		// Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("",//
		// 8080)); //provide proxy cong
		// requestFactory.setProxy(proxy);
		requestFactory.setBufferRequestBody(false);
		restTemplate.setRequestFactory(requestFactory);

		HttpHeaders headers = new HttpHeaders();
		headers.setAccept(Arrays.asList(MediaType.APPLICATION_OCTET_STREAM));

		HttpEntity<String> entity = new HttpEntity<String>(headers);

		ResponseEntity<byte[]> response = restTemplate.exchange(txUri, HttpMethod.GET, entity, byte[].class, "1");

		OutputStream outStream = httpResponse.getOutputStream();
		outStream.write(response.getBody());
		return ResponseEntity.ok("File downloaded successfully");
	}

}
