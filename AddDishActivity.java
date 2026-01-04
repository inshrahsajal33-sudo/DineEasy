package com.example.dineeasy.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.dineeasy.R;
import com.example.dineeasy.database.DishDBHelper;
import com.example.dineeasy.models.Dish;

public class AddDishActivity extends AppCompatActivity {

    private EditText etDishName;
    private EditText etPrice;
    private Spinner spinnerCategory;
    private Spinner spinnerAvailability;
    private DishDBHelper dishDBHelper;
    private int dishId = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_dish);

        dishDBHelper = new DishDBHelper(this);
        etDishName = findViewById(R.id.etDishName);
        etPrice = findViewById(R.id.etPrice);
        spinnerCategory = findViewById(R.id.spinnerCategory);
        spinnerAvailability = findViewById(R.id.spinnerAvailability);
        Button btnAddDish = findViewById(R.id.btnAddDish);
        Button btnCancel = findViewById(R.id.btnCancel);

        String[] categories = {"Appetizer", "Main Course", "Dessert", "Beverage", "Side Dish"};
        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, categories);
        categoryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCategory.setAdapter(categoryAdapter);

        String[] availability = {"Available", "Out of Stock"};
        ArrayAdapter<String> availabilityAdapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, availability);
        availabilityAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerAvailability.setAdapter(availabilityAdapter);

        if (getIntent().hasExtra("DISH_ID")) {
            dishId = getIntent().getIntExtra("DISH_ID", -1);
            etDishName.setText(getIntent().getStringExtra("DISH_NAME"));
            etPrice.setText(String.valueOf(getIntent().getDoubleExtra("DISH_PRICE", 0.0)));

            String category = getIntent().getStringExtra("DISH_CATEGORY");
            int categoryPosition = categoryAdapter.getPosition(category);
            if (categoryPosition >= 0) spinnerCategory.setSelection(categoryPosition);

            String avail = getIntent().getStringExtra("DISH_AVAILABILITY");
            int availPosition = availabilityAdapter.getPosition(avail);
            if (availPosition >= 0) spinnerAvailability.setSelection(availPosition);

            btnAddDish.setText("Update Dish");
        }

        btnAddDish.setOnClickListener(v -> {
            if (dishId == -1) {
                handleAddDish();
            } else {
                handleUpdateDish();
            }
        });
        btnCancel.setOnClickListener(v -> finish());
    }

    private void handleAddDish() {
        String dishName = etDishName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String availability = spinnerAvailability.getSelectedItem().toString();

        if (dishName.isEmpty()) {
            etDishName.setError("Dish name required");
            return;
        }

        if (price.isEmpty()) {
            etPrice.setError("Price required");
            return;
        }

        double priceValue;
        try {
            priceValue = Double.parseDouble(price);
            if (priceValue <= 0) {
                etPrice.setError("Price must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            etPrice.setError("Invalid price format");
            return;
        }

        Dish dish = new Dish(dishName, priceValue, availability, category);
        long result = dishDBHelper.addDish(dish);

        if (result != -1) {
            Toast.makeText(this, "Dish added successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to add dish", Toast.LENGTH_SHORT).show();
        }
    }

    private void handleUpdateDish() {
        String dishName = etDishName.getText().toString().trim();
        String price = etPrice.getText().toString().trim();
        String category = spinnerCategory.getSelectedItem().toString();
        String availability = spinnerAvailability.getSelectedItem().toString();

        if (dishName.isEmpty()) {
            etDishName.setError("Dish name required");
            return;
        }

        if (price.isEmpty()) {
            etPrice.setError("Price required");
            return;
        }

        double priceValue;
        try {
            priceValue = Double.parseDouble(price);
            if (priceValue <= 0) {
                etPrice.setError("Price must be greater than 0");
                return;
            }
        } catch (NumberFormatException e) {
            etPrice.setError("Invalid price format");
            return;
        }

        Dish dish = new Dish(dishId, dishName, priceValue, availability, category);
        int result = dishDBHelper.updateDish(dish);

        if (result > 0) {
            Toast.makeText(this, "Dish updated successfully", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Failed to update dish", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        dishDBHelper.close();
        super.onDestroy();
    }
}