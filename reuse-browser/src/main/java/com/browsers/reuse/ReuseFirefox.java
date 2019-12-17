package com.browsers.reuse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Collections;
import java.util.Properties;

import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.Command;
import org.openqa.selenium.remote.CommandExecutor;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.HttpCommandExecutor;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.remote.Response;
import org.openqa.selenium.remote.SessionId;
import org.openqa.selenium.remote.http.W3CHttpCommandCodec;
import org.openqa.selenium.remote.http.W3CHttpResponseCodec;

public class ReuseFirefox {
	
	
	public static RemoteWebDriver createDriverFromSession(final String sessionId, URL command_executor){
	    CommandExecutor executor = new HttpCommandExecutor(command_executor) {

	    @Override
	    public Response execute(Command command) throws IOException {
	        Response response = null;
	        if (command.getName() == "newSession") {
	            response = new Response();
	            response.setSessionId(sessionId.toString());
	            response.setStatus(0);
	            response.setValue(Collections.<String, String>emptyMap());

	            try {
	                Field commandCodec = null;
	                commandCodec = this.getClass().getSuperclass().getDeclaredField("commandCodec");
	                commandCodec.setAccessible(true);
	                commandCodec.set(this, new W3CHttpCommandCodec());

	                Field responseCodec = null;
	                responseCodec = this.getClass().getSuperclass().getDeclaredField("responseCodec");
	                responseCodec.setAccessible(true);
	                responseCodec.set(this, new W3CHttpResponseCodec());
	            } catch (NoSuchFieldException e) {
	                e.printStackTrace();
	            } catch (IllegalAccessException e) {
	                e.printStackTrace();
	            }

	        } else {
	            response = super.execute(command);
	        }
	        return response;
	    }
	    };

	    return new RemoteWebDriver(executor, new DesiredCapabilities());
	}
	
	public static void savePropertiesFile(Properties prop){
		 try {
				prop.store(new FileOutputStream(System.getProperty("user.dir")+"\\prop.properties"), null);
			} catch (Exception e) {
			}
	}
	
	public static Properties loadProperties(){
		
		File file=new File((System.getProperty("user.dir")+"\\prop.properties"));
	    Properties prop=new Properties();
	    
	    if(file.exists()){
	    	//file.delete();
			 try {
				prop.load(new FileInputStream(file));
			} catch (Exception e) {
				System.out.println("File is not loaded");
				
			}
			 }
	    return prop;
		
	}

	public static void main(String[] args) throws MalformedURLException {

		System.setProperty("webdriver.gecko.driver", "geckodriver.exe");

		Properties prop = loadProperties();
		String blnFlag = prop.getProperty("browserFlag");
		if (blnFlag.equals("true")) {
			FirefoxDriver driver = new FirefoxDriver();
			HttpCommandExecutor executor = (HttpCommandExecutor) driver
					.getCommandExecutor();
			URL url = executor.getAddressOfRemoteServer();
			SessionId session_id = driver.getSessionId();
			// setting in properties file
			prop.setProperty("sessionId", session_id.toString());
			prop.setProperty("url", url.toString());

			System.out
					.println("Double check the 'url' and 'session id' have get saved in properties file, before start the re-use session");

			// set the browserFlag to false
			prop.setProperty("browserFlag", "false");

			// save the properties file
			savePropertiesFile(prop);
			return;

		}

		// use the below snippet for next browser launching session
		String sessionID = prop.getProperty("sessionId");
		String strUrl = prop.getProperty("url");

		URL url = new URL(strUrl);

		RemoteWebDriver driver = createDriverFromSession(sessionID, url);
		driver.get("http://tarunlalwani.com/post/reusing-existing-browser-session-selenium-java/");


		/**
		 * write code below to playaround with the elements
		 */

		System.out.println(driver.getTitle());
		// System.out.println(driver.findElement(By.id("txtUserID")).isDisplayed());

	}


}
