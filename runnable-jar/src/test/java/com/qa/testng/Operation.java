package com.qa.testng;


import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Listeners(TestLog.class)
public class Operation {
    Locale locale = Locale.getDefault();
    DateFormat dateFormat = DateFormat.getTimeInstance(DateFormat.DEFAULT, locale);

    @Test(dataProvider = "Exceldata", dataProviderClass = DataSupplier.class, groups = {"asdasd"})
    public void getExcel(List<Person> lst){
        lst.forEach(System.out::println);
    }

    @Test(dataProvider = "getNames", dataProviderClass = DataSupplier.class)
    public void getNames(List<String> lst){
        System.out.println(lst);

    }
    @Test(dataProvider = "getNamesMap", dataProviderClass = DataSupplier.class)
    public void getNamesMap(Map<String, String> map){

        map.entrySet().forEach(e-> System.out.println(e.getKey()+" --> "+e.getValue()));

    }


    @Test(dataProvider = "InputDataProvider", dataProviderClass = DataSupplier.class)

    public void add(Integer a1, Integer a2){
        System.out.println("Add");
        String date = dateFormat.format(new Date());
        System.out.println(date);
        System.out.println(a1+a2);
    }

    @Test(dataProvider = "InputDataProvider", dataProviderClass = DataSupplier.class)
    public void sub(Integer a1, Integer a2){
        System.out.println("sub");
        String date = dateFormat.format(new Date());
        System.out.println(date);
        System.out.println(a1- a2);
    }
    @Test(dataProvider = "InputDataProvider", dataProviderClass = DataSupplier.class)
    public void mul(Integer a1, Integer a2){
        System.out.println("mul");
        String date = dateFormat.format(new Date());
        System.out.println(date);
        System.out.println(a1*a2);
    }
    @Test(dataProvider = "InputDataProvider", dataProviderClass = DataSupplier.class)
    public void div(Integer a1, Integer a2){
        System.out.println("div");

        String date = dateFormat.format(new Date());
        System.out.println(date);
        System.out.println(a1/a2);

    }
}
