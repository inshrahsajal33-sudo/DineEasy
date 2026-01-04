package com.example.dineeasy.activities;

import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dineeasy.R;
import com.example.dineeasy.adapters.DishAdapter;
import com.example.dineeasy.api.RestaurantApiSimulator;
import com.example.dineeasy.models.Dish;
import java.util.List;

public class DailySpecialsActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private DishAdapter dishAdapter;
    private TextView tvApiKey;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_daily_specials);

        recyclerView = findViewById(R.id.recyclerView);
        tvApiKey = findViewById(R.id.tvApiKey);

        TextView tvBack = findViewById(R.id.tvBack);
        TextView tvRefresh = findViewById(R.id.tvRefresh);

        tvBack.setOnClickListener(v -> finish());
        tvRefresh.setOnClickListener(v -> loadDailySpecials());

        tvApiKey.setText("API Key: " + RestaurantApiSimulator.getApiKey());
        setupRecyclerView();
        loadDailySpecials();
    }

    private void setupRecyclerView() {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        dishAdapter = new DishAdapter();
        recyclerView.setAdapter(dishAdapter);
    }

    private void loadDailySpecials() {
        List<Dish> specials = RestaurantApiSimulator.getDailySpecials();
        dishAdapter.updateData(specials);
    }
}