package com.test.automation.web.client;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Map;

import org.json.simple.parser.JSONParser;
import org.springframework.web.client.RestTemplate;

public class RestService {
	private RestTemplate restTemplate;
	private String url;
	
	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Set up connection details.
	 * 
	 * @param username
	 * @param password
	 * @param url
	 */
	public RestService(String username, String password){
		restTemplate = new BasicAuthRestTemplate(username, password);
	}
	
	/**
	 * Send a http post request to the server, in json format.
	 * 
	 * @param request
	 * @return
	 */
	public String post(String request){
		String completeUrl = url;
		return restTemplate.postForObject(completeUrl, request, String.class);
	}

	/**
	 * Send a http get request to the server and retreive the response in json format.
	 * 
	 * @param request
	 * @return
	 */
	public String get(Map<String, ?> urlVariables){
		String completeUrl = url;
		return restTemplate.getForObject(completeUrl, String.class, urlVariables);
	}
	
	/**
	 * Send a http post request to the server, in json format
	 * 
	 * @param request
	 * @param resourcePath
	 * @return
	 */
	public String get(String resourcePath, Map<String, ?> urlVariables){
		String completeUrl = url + "/" + resourcePath;
		return restTemplate.getForObject(completeUrl, String.class, urlVariables);
	}
	
	
	/**
	 * method to read the json file and return as string
	 * @return
	 * @throws Exception 
	 */
	public String readJsonToString(File fileName) throws Exception{
		String jsonString=null;
		JSONParser parser = new JSONParser();
        try {
             jsonString=parser.parse(new InputStreamReader(new FileInputStream(fileName))).toString();
        }catch(Exception e){
        	throw new Exception(e.getMessage());
        }
		return jsonString;
		
	}
	
	private String jsonResponse;

	public String getJsonResponse() {
		return jsonResponse;
	}

	public void setJsonResponse(String jsonResponse) {
		this.jsonResponse = jsonResponse;
	}
}
