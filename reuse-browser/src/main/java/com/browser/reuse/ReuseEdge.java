package com.browser.reuse;


import org.openqa.selenium.By;
import org.openqa.selenium.Point;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.remote.*;
import org.openqa.selenium.remote.codec.w3c.W3CHttpCommandCodec;
import org.openqa.selenium.remote.codec.w3c.W3CHttpResponseCodec;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public class InternetExplorer {


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

    public static void main(String [] args) throws MalformedURLException, Exception {

        Properties prop=loadProperties();
        String blnFlag=prop.getProperty("browserFlag");
        if(blnFlag.equals("true")){
            EdgeDriver driver = new EdgeDriver();
            HttpCommandExecutor executor = (HttpCommandExecutor) driver.getCommandExecutor();
            URL url = executor.getAddressOfRemoteServer();
            SessionId session_id = driver.getSessionId();
            //setting in properties file
            prop.setProperty("sessionId", session_id.toString());
            prop.setProperty("url", url.toString());

            System.out.println("Double check the 'url' and 'session id' have get saved in properties file, before start the re-use session");

            //set the browserFlag to false
            prop.setProperty("browserFlag", "false");

            //save the properties file
            savePropertiesFile(prop);
            return;

        }

        //use the below snippet for next browser launching session
        String sessionID=prop.getProperty("sessionId");
        String strUrl=prop.getProperty("url");

        URL url= new URL(strUrl);


        RemoteWebDriver driver = createDriverFromSession(sessionID, url);

        /**
         * write code below to playaround with the elements
         */

       WebElement logo = null;//driver.findElement(By.cssSelector(".login_logo"));
        WebElement username = null;//driver.findElement(By.cssSelector("#user-name"));
        WebElement password = null;//driver.findElement(By.cssSelector("#password"));
        WebElement login = null;//driver.findElement(By.cssSelector("#login-button"));
        List<WebElement> lstEle = driver.findElements(By.xpath("//button[contains(.,'Add to cart')]"));

        //collect all x and y co-ordinates
        List<Point> lstPoints= lstEle.stream().map(WebElement::getLocation).collect(Collectors.toList());

        // find the parent element
        WebElement parent = driver.findElement(By.xpath("//div[contains(.,'sleeved and bottom') and @class='inventory_item_desc']"));
        Point parentCordinates = parent.getLocation();
        System.out.println("All cordinates" + lstPoints);
        System.out.println("Parent cordinate" + parentCordinates);

        Map<Integer, Point> lstRow = new HashMap<>();

        //iterate the for loop and find nearest x and y coordinates
        int x = 0; int y =0;
        int index = 0;
        for(int i = 0; i<lstPoints.size(); i++){
            int yDiff = lstPoints.get(i).getY() - parentCordinates.getY();
            //ignore the negative integer
           if(yDiff < 1 )
                continue;


            if(y == 0 ) {
                y = yDiff;
                lstRow.put(i, lstPoints.get(i));
                continue;
            }

            if(y >= yDiff){
                y = yDiff;
                lstRow.put(i, lstPoints.get(i));
               // index = i;
            }

        }

        //lstEle.get(index).click();
        System.out.println(lstRow.size());

        //traverse over the row side

        for(Map.Entry<Integer, Point> eachItem :  lstRow.entrySet()){
            int xDiff = eachItem.getValue().getX() - parentCordinates.getX();
            if(x == 0){
                x = xDiff;
                index = eachItem.getKey();
                continue;
            }
            /*if(xDiff < 1){
                continue;
            }*/

            if(Math.abs(xDiff)  < Math.abs(x)){
                index = eachItem.getKey();
            }

        }

        System.out.println(index);
        lstEle.get(index).click();

if(false) {

    int mainCordinates = password.getLocation().getY();
    int temp = Math.abs(lstEle.get(0).getLocation().getY() - mainCordinates);
     index = 0;

    for (int i = 1; i < lstEle.size(); i++) {
        int Ycord = lstEle.get(i).getLocation().getY();
        int diff = Math.abs(Ycord - mainCordinates);
        if (diff < temp) {
            temp = diff;
            index = i;
        }

    }


    lstEle.get(index).sendKeys("standard use");

}

        System.out.println(driver.getTitle());





    }



}