package com.qa.testng;

import com.creditdatamw.zerocell.Reader;
import lombok.SneakyThrows;
import org.apache.tools.ant.util.FileUtils;
import org.openqa.selenium.bidi.Input;
import org.testng.annotations.DataProvider;

import java.io.File;
import java.io.InputStream;
import java.lang.reflect.Method;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.*;

public class DataSupplier {

    @DataProvider(name = "InputDataProvider")
    public Object[][] getTestData(Method method){
        System.out.println(method.getName());
        return new Object[][] {
                {7, 8},
                {9, 5},
                {3, 5}
        };
    }

    @DataProvider( name = "getNames")
    public Object[][] getNames(){
        List<String> lstStr = Arrays.asList("Hello", "Thanan");
        return new Object[][]{new Object[]{ lstStr }};
    }

    @DataProvider( name = "getNamesMap")
    public Object[][] getNamesInMap(){
        HashMap<String, String> map = new HashMap<>();
        map.put("Name", "Thanan");
        map.put("Vikram", "Kamal");
        return new Object[][]{new Object[]{ map }};
    }

    @DataProvider(name = "Exceldata")
    public Object[][] getExcelData(){
      /*  Optional.of(new File(DataSupplier.class.getClassLoader().getResource("Destination.xlsx").getFile()))
                .orElse(new File(DataSupplier.class.getClassLoader().getResourceAsStream("Destination.xlsx")))
*/
       // File file = new File(DataSupplier.class.getClassLoader().getResource("Destination.xlsx").getFile());
        InputStream is = DataSupplier.class.getClassLoader().getResourceAsStream("Destination.xlsx");
        File file1 = inputStreamToFile(is, "Destination_temp.xlsx");
        List<Person> lst = Reader.of(Person.class)
                .from(file1)
                .sheet("Sheet1")
                .list();
        return new Object[][]{ new Object[]{lst}};
    }

    @SneakyThrows
    public  File inputStreamToFile(InputStream inputStream, String fileName)  {
        // Create a file object
        File file = new File(fileName);
        if(file.exists())
            file.delete();

        // Use Files.copy with Java 8 streams
         try (InputStream is = inputStream) {
             Files.copy(inputStream, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
       }

        return file;
    }



}
