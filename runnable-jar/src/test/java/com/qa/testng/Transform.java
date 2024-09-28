package com.qa.testng;

import org.testng.IAnnotationTransformer;
import org.testng.annotations.ITestAnnotation;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

public class Transform implements IAnnotationTransformer {

    @Override
    public  void transform(ITestAnnotation annotation, Class testClass, Constructor testConstructor, Method testMethod) {

       if(!testMethod.getName().startsWith("get")) {
           annotation.setDataProvider("InputDataProvider");
           annotation.setDataProviderClass(DataSupplier.class);
       }
    }
}
