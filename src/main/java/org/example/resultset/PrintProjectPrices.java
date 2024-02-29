package org.example.resultset;

public class PrintProjectPrices {
    private String name;
    private Double price;

    public PrintProjectPrices(String name, Double price) {
        this.name = name;
        this.price = price;
    }
    @Override
    public String toString() {
        return  "----> PrintProjectPrices ( name : '" + name + '\'' + ", price : " + price + " )";
    }
}
