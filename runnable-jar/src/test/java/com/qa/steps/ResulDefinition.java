package com.qa.steps;

import com.qa.utility.Context;
import io.cucumber.java.en.When;

public class ResulDefinition {

    private Context runTimeDataHandler;

    public ResulDefinition(Context runTimeDataHandler) {
        this.runTimeDataHandler = runTimeDataHandler;
    }

    @When("Add the two number")
    public void add_the_two_number() {
        System.out.println( runTimeDataHandler.getRunTime("hello"));

    }


}
