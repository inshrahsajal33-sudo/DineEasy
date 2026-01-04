package com.example.dineeasy.api;

import com.example.dineeasy.models.Dish;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class RestaurantApiSimulator {

    private static final String API_KEY = "dineeasy_api_2024_hadiqa_secure_key";
    private static final Random random = new Random();

    private static final String[][] DAILY_SPECIALS_DATA = {
            {"Chef's Special Pasta", "A delicious pasta with secret sauce", "Main Course"},
            {"Grilled Salmon", "Fresh salmon with herbs and lemon", "Main Course"},
            {"Vegetable Lasagna", "Layered pasta with fresh vegetables", "Main Course"},
            {"Beef Burger Deluxe", "Premium beef with special toppings", "Main Course"},
            {"Chicken Tikka Masala", "Spicy chicken in creamy sauce", "Main Course"},
            {"Mushroom Risotto", "Creamy rice with wild mushrooms", "Main Course"},
            {"Garlic Bread Basket", "Freshly baked with garlic butter", "Appetizer"},
            {"Caesar Salad", "Classic salad with Caesar dressing", "Appetizer"},
            {"Chocolate Lava Cake", "Warm cake with molten chocolate center", "Dessert"},
            {"New York Cheesecake", "Creamy cheesecake with berry sauce", "Dessert"},
            {"Fresh Lemonade", "Homemade lemonade with mint", "Beverage"},
            {"Cappuccino", "Freshly brewed Italian coffee", "Beverage"}
    };

    public static String getApiKey() {
        return API_KEY;
    }

    public static List<Dish> getDailySpecials() {
        List<Dish> specials = new ArrayList<>();
        int count = 5 + random.nextInt(3);

        for (int i = 0; i < count && i < DAILY_SPECIALS_DATA.length; i++) {
            String[] dishData = DAILY_SPECIALS_DATA[i];
            String dishName = dishData[0];
            double price = 8.99 + (random.nextDouble() * 15.0);
            price = Math.round(price * 100.0) / 100.0;
            String category = dishData[2];
            String availability = random.nextBoolean() ? "Available" : "Limited Stock";

            Dish dish = new Dish(dishName, price, availability, category);
            specials.add(dish);
        }

        return specials;
    }

    public static boolean isApiKeyValid(String key) {
        return API_KEY.equals(key);
    }

    public static Dish getRandomSpecial() {
        int index = random.nextInt(DAILY_SPECIALS_DATA.length);
        String[] dishData = DAILY_SPECIALS_DATA[index];
        String dishName = dishData[0];
        double price = 9.99 + (random.nextDouble() * 12.0);
        price = Math.round(price * 100.0) / 100.0;
        String category = dishData[2];
        String availability = random.nextBoolean() ? "Available" : "Out of Stock";

        return new Dish(dishName, price, availability, category);
    }

    public static List<Dish> getAllSpecials() {
        List<Dish> allSpecials = new ArrayList<>();

        for (String[] dishData : DAILY_SPECIALS_DATA) {
            String dishName = dishData[0];
            double price = 7.99 + (random.nextDouble() * 18.0);
            price = Math.round(price * 100.0) / 100.0;
            String category = dishData[2];
            String availability = random.nextBoolean() ? "Available" : "Limited";

            Dish dish = new Dish(dishName, price, availability, category);
            allSpecials.add(dish);
        }

        return allSpecials;
    }
}