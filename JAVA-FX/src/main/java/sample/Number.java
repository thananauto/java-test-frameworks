package sample;

import javafx.beans.property.*;


public class Number {
    public Double getNumber() {
        return number.get();
    }

    public void setNumber(Double number) {
        this.numberProperty().setValue(number);
    }


    public final DoubleProperty numberProperty(){

        if(number == null){
            number = new SimpleDoubleProperty(0);
        }
        return number;
    }
    private DoubleProperty number;
}
