package com.example.dineeasy.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import com.example.dineeasy.models.Dish;
import java.util.ArrayList;
import java.util.List;

public class DishDBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "DineEasy.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_DISH = "dish";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_DISH_NAME = "dishName";
    private static final String COLUMN_PRICE = "price";
    private static final String COLUMN_AVAILABILITY = "availability";
    private static final String COLUMN_CATEGORY = "category";

    public DishDBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_DISH_TABLE = "CREATE TABLE " + TABLE_DISH + "("
                + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + COLUMN_DISH_NAME + " TEXT,"
                + COLUMN_PRICE + " REAL,"
                + COLUMN_AVAILABILITY + " TEXT,"
                + COLUMN_CATEGORY + " TEXT)";
        db.execSQL(CREATE_DISH_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_DISH);
        onCreate(db);
    }

    public long addDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISH_NAME, dish.getDishName());
        values.put(COLUMN_PRICE, dish.getPrice());
        values.put(COLUMN_AVAILABILITY, dish.getAvailability());
        values.put(COLUMN_CATEGORY, dish.getCategory());
        return db.insert(TABLE_DISH, null, values);
    }

    public List<Dish> getAllDishes() {
        List<Dish> dishList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_DISH, null);

        if (cursor.moveToFirst()) {
            do {
                Dish dish = new Dish();
                dish.setId(cursor.getInt(0));
                dish.setDishName(cursor.getString(1));
                dish.setPrice(cursor.getDouble(2));
                dish.setAvailability(cursor.getString(3));
                dish.setCategory(cursor.getString(4));
                dishList.add(dish);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return dishList;
    }

    public int updateDish(Dish dish) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COLUMN_DISH_NAME, dish.getDishName());
        values.put(COLUMN_PRICE, dish.getPrice());
        values.put(COLUMN_AVAILABILITY, dish.getAvailability());
        values.put(COLUMN_CATEGORY, dish.getCategory());
        return db.update(TABLE_DISH, values, COLUMN_ID + " = ?", new String[]{String.valueOf(dish.getId())});
    }

    public void deleteDish(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_DISH, COLUMN_ID + " = ?", new String[]{String.valueOf(id)});
    }

    public Dish getDishById(int id) {
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.query(TABLE_DISH, null, COLUMN_ID + " = ?", new String[]{String.valueOf(id)}, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Dish dish = new Dish();
            dish.setId(cursor.getInt(0));
            dish.setDishName(cursor.getString(1));
            dish.setPrice(cursor.getDouble(2));
            dish.setAvailability(cursor.getString(3));
            dish.setCategory(cursor.getString(4));
            cursor.close();
            return dish;
        }
        if (cursor != null) cursor.close();
        return null;
    }
}