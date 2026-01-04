package com.example.dineeasy.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;
import com.example.dineeasy.R;
import com.example.dineeasy.activities.AddDishActivity;
import com.example.dineeasy.database.DishDBHelper;
import com.example.dineeasy.models.Dish;
import java.util.List;
import java.util.Locale;

public class DishAdapter extends RecyclerView.Adapter<DishAdapter.DishViewHolder> {

    private List<Dish> dishList;
    private DishDBHelper dishDBHelper;

    public DishAdapter() {}

    public DishAdapter(List<Dish> dishList) {
        this.dishList = dishList;
    }

    @Override
    public DishViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_dish, parent, false);
        dishDBHelper = new DishDBHelper(parent.getContext());
        return new DishViewHolder(view);
    }

    @Override
    public void onBindViewHolder(DishViewHolder holder, int position) {
        Dish dish = dishList.get(position);
        holder.tvDishName.setText(dish.getDishName());
        holder.tvPrice.setText(String.format(Locale.getDefault(), "$%.2f", dish.getPrice()));
        holder.tvCategory.setText(dish.getCategory());
        holder.tvAvailability.setText(dish.getAvailability());

        if (dish.getAvailability().equals("Available")) {
            holder.tvAvailability.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_green_dark));
        } else {
            holder.tvAvailability.setTextColor(holder.itemView.getContext().getResources().getColor(android.R.color.holo_red_dark));
        }

        holder.ivEdit.setOnClickListener(v -> {
            Intent intent = new Intent(v.getContext(), AddDishActivity.class);
            intent.putExtra("DISH_ID", dish.getId());
            intent.putExtra("DISH_NAME", dish.getDishName());
            intent.putExtra("DISH_PRICE", dish.getPrice());
            intent.putExtra("DISH_CATEGORY", dish.getCategory());
            intent.putExtra("DISH_AVAILABILITY", dish.getAvailability());
            v.getContext().startActivity(intent);
        });

        holder.ivDelete.setOnClickListener(v -> showDeleteDialog(holder.itemView.getContext(), dish.getId(), position));

        holder.ivShare.setOnClickListener(v -> shareDishDetails(v.getContext(), dish));
    }

    private void showDeleteDialog(android.content.Context context, int dishId, int position) {
        new AlertDialog.Builder(context)
                .setTitle("Delete Dish")
                .setMessage("Are you sure you want to delete this dish?")
                .setPositiveButton("Delete", (dialog, which) -> {
                    dishDBHelper.deleteDish(dishId);
                    dishList.remove(position);
                    notifyItemRemoved(position);
                    notifyItemRangeChanged(position, dishList.size());
                    Toast.makeText(context, "Dish deleted", Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void shareDishDetails(android.content.Context context, Dish dish) {
        String shareText = "Check out this dish from DineEasy!\n\n" +
                "🍽 " + dish.getDishName() + "\n" +
                "💰 Price: $" + dish.getPrice() + "\n" +
                "📂 Category: " + dish.getCategory() + "\n" +
                "✅ Status: " + dish.getAvailability() + "\n\n" +
                "From DineEasy Restaurant App";

        Intent shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, "DineEasy Dish: " + dish.getDishName());
        context.startActivity(Intent.createChooser(shareIntent, "Share Dish Details"));
    }

    @Override
    public int getItemCount() {
        return dishList == null ? 0 : dishList.size();
    }

    public void updateData(List<Dish> newDishList) {
        dishList = newDishList;
        notifyDataSetChanged();
    }

    static class DishViewHolder extends RecyclerView.ViewHolder {
        TextView tvDishName;
        TextView tvPrice;
        TextView tvCategory;
        TextView tvAvailability;
        ImageView ivEdit;
        ImageView ivDelete;
        ImageView ivShare;

        DishViewHolder(View itemView) {
            super(itemView);
            tvDishName = itemView.findViewById(R.id.tvDishName);
            tvPrice = itemView.findViewById(R.id.tvPrice);
            tvCategory = itemView.findViewById(R.id.tvCategory);
            tvAvailability = itemView.findViewById(R.id.tvAvailability);
            ivEdit = itemView.findViewById(R.id.ivEdit);
            ivDelete = itemView.findViewById(R.id.ivDelete);
            ivShare = itemView.findViewById(R.id.ivShare);
        }
    }
}