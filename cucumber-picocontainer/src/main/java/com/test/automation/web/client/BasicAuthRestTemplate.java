package com.test.automation.web.client;

import java.io.IOException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.Base64;
import java.util.Collections;
import java.util.List;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.http.client.InterceptingClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class BasicAuthRestTemplate extends RestTemplate{
	  private  class InvalidCertificateHostVerifier
	    implements HostnameVerifier
	  {
	    public boolean verify(String paramString, SSLSession paramSSLSession)
	    {
	      return true;
	    }
	  }
	  
	  private  class InvalidCertificateTrustManager
	    implements X509TrustManager
	  {
	    public X509Certificate[] getAcceptedIssuers()
	    {
	      return null;
	    }
	    
	    public void checkServerTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
	      throws CertificateException
	    {}
	    
	    public void checkClientTrusted(X509Certificate[] paramArrayOfX509Certificate, String paramString)
	      throws CertificateException
	    {}
	  }
	  
	  public void sslContext()
	  {
	    try
	    {
	      SSLContext localSSLContext = SSLContext.getInstance("TLS");
	      localSSLContext.init(null, new TrustManager[] { new InvalidCertificateTrustManager() }, null);
	      SSLContext.setDefault(localSSLContext);
	    }
	    catch (Exception localException)
	    {
	      throw new RuntimeException(localException);
	    }
	  }
	  
	  public BasicAuthRestTemplate(String paramString1, String paramString2)
	  {
	    addAuthentication(paramString1, paramString2);
	    
	    HttpsURLConnection.setDefaultHostnameVerifier(new InvalidCertificateHostVerifier());
	  }
	  
	  @SuppressWarnings("null")
	private void addAuthentication(String paramString1, String paramString2)
	  {
	    if (paramString1 == null && paramString1.length()==0) {
	      return;
	    }
	    //adding the SSL context
	    sslContext();
	    List localList = Collections.singletonList(new BasicAuthorizationInterceptor(paramString1, paramString2));
	    setRequestFactory(new InterceptingClientHttpRequestFactory(getRequestFactory(), localList));
	  }
	  
	  private  class BasicAuthorizationInterceptor
	    implements ClientHttpRequestInterceptor
	  {
	    private final String username;
	    private final String password;
	    
	    public BasicAuthorizationInterceptor(String paramString1, String paramString2)
	    {
	      this.username = paramString1;
	      this.password = (paramString2 == null ? "" : paramString2);
	    }
	    
	    public ClientHttpResponse intercept(HttpRequest paramHttpRequest, byte[] paramArrayOfByte, ClientHttpRequestExecution paramClientHttpRequestExecution)
	      throws IOException
	    {
	      byte[] arrayOfByte = Base64.getEncoder().encode((this.username + ":" + this.password).getBytes());
	      paramHttpRequest.getHeaders().add("Authorization", "Basic " + new String(arrayOfByte));
	      return paramClientHttpRequestExecution.execute(paramHttpRequest, paramArrayOfByte);
	    }
	  }
	}