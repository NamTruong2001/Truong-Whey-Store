package edu.hanu.truongwheystore.Model;

import java.io.Serializable;

public class Category implements Serializable {
    private String name;
    private String description;
    private String img_url;
    private String docRef;

    public Category() {}

    public Category(String name, String description, String img_url) {
        this.name = name;
        this.description = description;
        this.img_url = img_url;
    }

    public String getDocRef() {
        return docRef;
    }

    public void setDocRef(String docRef) {
        this.docRef = docRef;
    }

    public String getName() {
        return name;
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
        return "Category{" +
                "name='" + name + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
