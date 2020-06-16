package com.PR.starbuzz_ver3;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.starbuzz2.R;


class StarbuzzDatabaseHelper extends SQLiteOpenHelper{
    private static final String DB_NAME = "starbuzz"; // the name of our database
    private static final int DB_VERSION = 2; // the version of the database

    StarbuzzDatabaseHelper(Context context){
        super(context, DB_NAME, null, DB_VERSION);
    }
    @Override
    public void onCreate(SQLiteDatabase db){
        updateMyDatabase(db, 0, DB_VERSION);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        updateMyDatabase(db, oldVersion, newVersion);
    }


    private void updateMyDatabase(SQLiteDatabase db, int oldVersion, int newVersion) {
        if (oldVersion < 1) {
            db.execSQL("CREATE TABLE DRINK (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertDrink(db, "Latte", "Espresso and steamed milk", R.drawable.latte);
            insertDrink(db, "Cappuccino", "Espresso, hot milk and steamed-milk foam",R.drawable.cappuccino);
            insertDrink(db, "Filter", "Our best drip coffee", R.drawable.filter);
            insertDrink(db, "Americano", "Espresso roasted colombian bean and fresh water", R.drawable.americano);
            insertDrink(db, "MilkTea", "Milk with black tea", R.drawable.milktea);
            //food
            db.execSQL ("CREATE TABLE FOOD (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "DESCRIPTION TEXT, "
                    + "IMAGE_RESOURCE_ID INTERGER);");
            insertFood(db, "Cheesecake", "Sweet New York Cheesecake", R.drawable.cheesecake);
            insertFood(db, "Tiramisu", "Sweet cake with coffee", R.drawable.tiramisu);
            insertFood(db, "Bagel", "Good bagels for breakfast", R.drawable.bagel);

            db.execSQL("CREATE TABLE STORE (_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                    + "NAME TEXT, "
                    + "ADDRESS TEXT, "
                    + "IMAGE_RESOURCE_ID INTEGER);");
            insertStore(db, "안성점", "경기도 안성시 중앙로", R.drawable.shop_anseong);
            insertStore(db, "일산점", "경기도 일산동구 정발산동", R.drawable.shop_ilsan);
            insertStore(db, "평택점", "경기도 평택시 평택로", R.drawable.shop_p);
        }
        if (oldVersion < 2) {
            db.execSQL("ALTER TABLE DRINK ADD COLUMN FAVORITE NUMERIC;");
            db.execSQL("ALTER TABLE FOOD ADD COLUMN FAVORITE NUMERIC;");
            db.execSQL("ALTER TABLE STORE ADD COLUMN FAVORITE NUMERIC");
        }
    }
    private static void insertDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId) {
        ContentValues drinkValues = new ContentValues();
        drinkValues.put("NAME", name);
        drinkValues.put("DESCRIPTION", description);
        drinkValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("DRINK", null, drinkValues);
    }

    private static void insertFood(SQLiteDatabase db, String name,
                                   String description, int resourceId) {
        ContentValues FoodValues = new ContentValues();
        FoodValues.put("NAME", name);
        FoodValues.put("DESCRIPTION", description);
        FoodValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("FOOD", null, FoodValues);
    }

    private static void insertStore(SQLiteDatabase db, String name,
                                    String address, int resourceId) {
        ContentValues storeValues = new ContentValues();
        storeValues.put("NAME", name);
        storeValues.put("ADDRESS", address);
        storeValues.put("IMAGE_RESOURCE_ID", resourceId);
        db.insert("STORE", null, storeValues);
    }

    private static void UpdateDrink(SQLiteDatabase db, String name,
                                    String description, int resourceId) {
        ContentValues drinkValues1 = new ContentValues();
        drinkValues1.put("NAME", name);
        drinkValues1.put("DESCRIPTION", description);
        drinkValues1.put("IMAGE_RESOURCE_ID", resourceId);
        db.update("DRINK", drinkValues1, "name=?", new String[]{name});
    }

    private static void UpdateFood(SQLiteDatabase db, String name,
                                   String description, int resourceId) {
        ContentValues Foodvalues1 = new ContentValues();
        Foodvalues1.put("NAME", name);
        Foodvalues1.put("DESCRIPTION", description);
        Foodvalues1.put("IMAGE_RESOURCE_ID", resourceId);
        db.update("FOOD", Foodvalues1, "name=?", new String[] {name});
    }
}