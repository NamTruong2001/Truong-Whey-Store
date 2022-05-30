package edu.hanu.truongwheystore.Model;

import java.io.Serializable;

public class Product implements Serializable {
    private String id;
    private String name;
    private String description;
    private String img_url;
    private int price;
    //private Category category;
    private boolean homeType;

    public Product() {}
    public Product(String name, String description, String img_url, int price) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
        this.price = price;
        //this.category = category;
    }

   /* public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public boolean isHomeType() {
        return homeType;
    }

    public void setHomeType(boolean homeType) {
        this.homeType = homeType;
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

    @Override
    public String toString() {
        return "Product{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", img_url='" + img_url + '\'' +
                ", price=" + price +
                ", homeType=" + homeType +
                '}';
    }
}
