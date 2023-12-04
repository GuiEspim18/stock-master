package br.com.stockMaster;

public class Product {

    private String name;
    private int price;
    private String description;

    public Product () {

    }

    public Product (String name, int price, String descriptio) {
        this.name = name;
        this.price = price;
        this.description = descriptio;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
