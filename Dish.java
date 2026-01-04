package com.example.dineeasy.models;

public class Dish {
    private int id;
    private String dishName;
    private double price;
    private String availability;
    private String category;

    public Dish() {}

    public Dish(String dishName, double price, String availability, String category) {
        this.dishName = dishName;
        this.price = price;
        this.availability = availability;
        this.category = category;
    }

    public Dish(int id, String dishName, double price, String availability, String category) {
        this.id = id;
        this.dishName = dishName;
        this.price = price;
        this.availability = availability;
        this.category = category;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getDishName() { return dishName; }
    public void setDishName(String dishName) { this.dishName = dishName; }
    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
    public String getAvailability() { return availability; }
    public void setAvailability(String availability) { this.availability = availability; }
    public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
}