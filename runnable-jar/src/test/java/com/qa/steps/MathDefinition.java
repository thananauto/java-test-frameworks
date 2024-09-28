package com.qa.steps;

import com.qa.utility.Context;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import org.testng.Assert;

public class MathDefinition {

    private Context runTimeDataHandler;

    public MathDefinition(Context runTimeDataHandler) {
        this.runTimeDataHandler = runTimeDataHandler;
    }


    @Given("Enter {string} as {int}")
    public void input(String ip, int number) {
        System.out.println(ip+" --> "+number);
        runTimeDataHandler.setRunTime(ip, number);

    }



    @Then("Perform operation {string} on {string} and {string} with {int}")
    public void perform_operation_on_input1_and_input2_with(String operation,String ip1, String ip2, Integer int1) {
        int ip11 = (int) this.runTimeDataHandler.getRunTime(ip1);
        int ip22 = (int) this.runTimeDataHandler.getRunTime(ip2);
        if(operation.equalsIgnoreCase("add")){
            Assert.assertEquals(ip11+ip22, int1);
        }else if(operation.equalsIgnoreCase("sub")){
            Assert.assertEquals(ip11-ip22, int1);
        }

    }

}
