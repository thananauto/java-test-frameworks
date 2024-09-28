package com.qa.testng;

import com.creditdatamw.zerocell.annotation.Column;
import com.creditdatamw.zerocell.annotation.RowNumber;
import com.creditdatamw.zerocell.annotation.ZerocellReaderBuilder;
import lombok.Getter;


@Getter
public class Person {
    @RowNumber
    private int rowNumber;

    @Column(name = "Name", index = 0)
    private String name;
    @Column(name = "Source", index = 1)
    private String source;
    @Column(name = "Destination", index = 2)
    private String destination;

    @Column(name = "Hours", index = 3)
    private double hrs;


    @Override
    public String toString() {
        return "Person{" +
                "rowNumber=" + rowNumber +
                ", name='" + name + '\'' +
                ", source='" + source + '\'' +
                ", destination='" + destination + '\'' +
                ", hrs=" + hrs +
                '}';
    }
}
