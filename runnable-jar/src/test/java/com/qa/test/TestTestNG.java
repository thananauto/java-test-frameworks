package com.qa.test;

import com.qa.testng.Operation;
import org.testng.TestNG;
import org.testng.xml.XmlSuite;

import java.util.ArrayList;
import java.util.List;

public class TestTestNG {

    public static void main(String[] args) {
        TestNG testng = new TestNG();
        List<Class<?>> testClasses = new ArrayList<>();
        //BDD UI cases
        testClasses.add(Runner.class);
        //separate testNG test method annotated classes
        testClasses.add(Operation.class);

        testng.setTestClasses(testClasses.toArray(new Class[0]));

        // Enable parallel execution
        testng.setParallel(XmlSuite.ParallelMode.CLASSES);
        testng.setThreadCount(4);

        // Run the tests programmatically
        testng.run();
    }
}
