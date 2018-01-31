package br.com.rhinosistemas.util;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Calendar;
import java.util.Date;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpSession;

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

public class Util {

	public static Date convertStringToDate(String data) {

		try {
			return new SimpleDateFormat("yyyy-MM-dd").parse(data);
		} catch (ParseException e) {
			e.printStackTrace();
		}

		return null;
	}
	public static String formataData(String data) {
		
		try {
			DateFormat formato = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.mmm");
			DateFormat formatoRetorno = new SimpleDateFormat("MM-dd");
			Date date = formato.parse(data);
			
			return formatoRetorno.format(date);
			
		} catch (ParseException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	public static Date stringToDateSemHoras(Date data) {
		Calendar dataSemHora = Calendar.getInstance();
		dataSemHora.setTime(data);
		dataSemHora.set(Calendar.HOUR, 0);
		dataSemHora.set(Calendar.MINUTE, 0);
		dataSemHora.set(Calendar.SECOND, 0);
		dataSemHora.set(Calendar.MILLISECOND, 0);
		
		return dataSemHora.getTime();
	}
	
	public static Date stringToDateFinalDia(Date data) {
        Calendar dataSemHora = Calendar.getInstance();
        dataSemHora.setTime(data);
        dataSemHora.set(Calendar.HOUR_OF_DAY, 23);
        dataSemHora.set(Calendar.MINUTE, 59);
        dataSemHora.set(Calendar.SECOND, 59);
        dataSemHora.set(Calendar.MILLISECOND, 59);
        
        return dataSemHora.getTime();
    }

	public static String realizarChamadaRest(HttpSession session, String parametrosQuery) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {

		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = createHeadersWithAuthentication(session);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity<?> requestEntity = new HttpEntity(headers);

		String transactionUrl = "https://jira.ci.gsnet.corp/rest/api/2/search?jql="+parametrosQuery;
		ResponseEntity<String> rateResponse = restTemplate.exchange(transactionUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {
		});


		return rateResponse.getBody();
	}

	public static String realizarChamadaAgile(HttpSession session, String parametrosQuery) throws KeyManagementException, NoSuchAlgorithmException, KeyStoreException {
		RestTemplate restTemplate = getRestTemplate();
		HttpHeaders headers = createHeadersWithAuthentication(session);
		
		@SuppressWarnings({ "unchecked", "rawtypes" })
		HttpEntity<?> requestEntity = new HttpEntity(headers);

		String transactionUrl = "https://jira.ci.gsnet.corp/rest/agile/1.0/"+parametrosQuery;
		ResponseEntity<String> rateResponse = restTemplate.exchange(transactionUrl, HttpMethod.GET, requestEntity, new ParameterizedTypeReference<String>() {
		});
		
		
		return rateResponse.getBody();
	}

	public static HttpHeaders createHeadersWithAuthentication(HttpSession session) {
		// Usuario usuario = (Usuario) session.getAttribute("usuario");

		// String plainCreds = usuario.getUsuario() + ":" + usuario.getPassword();
		String plainCreds = "xb188164" + ":" + "Gilson001";
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
