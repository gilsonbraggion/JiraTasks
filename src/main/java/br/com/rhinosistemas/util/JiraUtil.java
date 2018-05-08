package br.com.rhinosistemas.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.Base64;

import javax.net.ssl.SSLContext;

import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

import br.com.rhinosistemas.model.Usuario;

public class JiraUtil {

	public static String realizarChamadaRest(Usuario usuario, String parametrosQuery) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = createHeadersWithAuthentication(usuario);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity<?> requestEntity = new HttpEntity(headers);

		String transactionUrl = "https://jira.ci.gsnet.corp/rest/api/2/search?jql="+parametrosQuery;
		ResponseEntity<String> rateResponse = restTemplate.exchange(transactionUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {
		});


		return rateResponse.getBody();
	}

	public static String realizarChamadaAgile(Usuario usuario, String parametrosQuery) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = createHeadersWithAuthentication(usuario);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity<?> requestEntity = new HttpEntity(headers);

		String transactionUrl = "https://jira.ci.gsnet.corp/rest/agile/1.0/"+parametrosQuery;
		ResponseEntity<String> rateResponse = restTemplate.exchange(transactionUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {
		});
		
		
		return rateResponse.getBody();
	}

	public static HttpHeaders createHeadersWithAuthentication(Usuario usuario) {
		String plainCreds = usuario.getUsuario() + ":" + usuario.getPassword();
		byte[] base64CredsBytes = Base64.getEncoder().encode(plainCreds.getBytes());
		String base64Creds = new String(base64CredsBytes);

		HttpHeaders headers = new HttpHeaders();
		headers.add("Authorization", "Basic " + base64Creds);

		return headers;
	}
	
	public static RestTemplate getRestTemplate() throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		TrustStrategy acceptingTrustStrategy = (X509Certificate[] chain, String authType) -> true;
		SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();

		SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext);

		CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();

		HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();

		requestFactory.setHttpClient(httpClient);
		RestTemplate restTemplate = new RestTemplate(requestFactory);

		restTemplate.getMessageConverters().add(new MappingJackson2HttpMessageConverter());
		
		return restTemplate;
	}

}
