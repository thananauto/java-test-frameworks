package com.qa.utility;

import com.qa.page.objects.SauceHome;
import lombok.Getter;
import lombok.Setter;
import org.openqa.selenium.WebDriver;

import java.util.HashMap;

@Getter
@Setter
public  class Context extends Base {
    private static HashMap<String, Object> runTime= new HashMap<>();
    private WebDriver driver;
    private RunnerConfig config;
    private  SauceHome home;




    public void setRunTime(String str, Object obj){
        runTime.put(str, obj);
    }

    public Object getRunTime(String str){
        return runTime.getOrDefault(str, null);
    }


    public void setSauceHome(Context reusableLibrary) {
        this.home = new SauceHome(reusableLibrary);
    }
}
