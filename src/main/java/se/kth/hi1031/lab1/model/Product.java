package se.kth.hi1031.lab1.model;

public class Product {
    private int id;
    private String name;
    private String imageUrl;
    private String description;
    private double price;
    private int quantity;

    public Product(int id, String name, String imageUrl, String description, double price, int quantity) {
        this.id = id;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
        this.quantity = quantity;
    }

    public Product() {

    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
