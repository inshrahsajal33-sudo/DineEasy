package com.example.dineeasy.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dineeasy.R;
import com.example.dineeasy.adapters.DishAdapter;
import com.example.dineeasy.database.DishDBHelper;
import com.example.dineeasy.models.Dish;
import java.util.List;

public class ManageDishesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DishAdapter dishAdapter;
    private DishDBHelper dishDBHelper;
    private TextView tvNoDishes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manage_dish);

        dishDBHelper = new DishDBHelper(this);
        recyclerView = findViewById(R.id.recyclerView);
        tvNoDishes = findViewById(R.id.tvNoDishes);

        TextView tvBack = findViewById(R.id.tvBack);
        TextView tvAddNew = findViewById(R.id.tvAddNew);

        tvBack.setOnClickListener(v -> finish());
        tvAddNew.setOnClickListener(v -> {
            Intent intent = new Intent(ManageDishesActivity.this, AddDishActivity.class);
            startActivity(intent);
        });

        setupRecyclerView();
        loadDishes();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dishAdapter = new DishAdapter();
        recyclerView.setAdapter(dishAdapter);
    }

    private void loadDishes() {
        List<Dish> dishList = dishDBHelper.getAllDishes();
        if (dishList.isEmpty()) {
            recyclerView.setVisibility(View.GONE);
            tvNoDishes.setVisibility(View.VISIBLE);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            tvNoDishes.setVisibility(View.GONE);
            dishAdapter.updateData(dishList);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        loadDishes();
    }

    @Override
    protected void onDestroy() {
        dishDBHelper.close();
        super.onDestroy();
    }
}