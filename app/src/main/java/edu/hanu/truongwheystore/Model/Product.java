package edu.hanu.truongwheystore.Model;

public class Product {
    private String name;
    private String description;
    private String img_url;
    private int price;

    public Product() {}
    public Product(String name, String description, String img_url, int price) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImg_url() {
        return img_url;
    }

    public void setImg_url(String img_url) {
        this.img_url = img_url;
    }
}
