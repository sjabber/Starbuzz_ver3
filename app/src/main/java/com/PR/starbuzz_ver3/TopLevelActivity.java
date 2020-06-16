package com.PR.starbuzz_ver3;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.AdapterView;
import android.widget.ListView;
import android.view.View;
import android.database.Cursor;
import android.database.sqlite.SQLiteOpenHelper;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteDatabase;
import android.widget.SimpleCursorAdapter;
import android.widget.CursorAdapter;
import android.widget.Toast;

import com.example.starbuzz2.R;

public class TopLevelActivity extends Activity {
    private SQLiteDatabase db;
    private Cursor favoritesCursor1;
    private Cursor favoritesCursor2;
    private Cursor favoritesCursor3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_top_level);

//Create an OnItemClickListener for the Options ListView
        AdapterView.OnItemClickListener itemClickListener =
                new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> listView,
                                            View v,
                                            int position,
                                            long id) {
                        if (position == 0) {
                            Intent intent = new Intent(TopLevelActivity.this,
                                    DrinkCategoryActivity.class);
                            startActivity(intent);
                        }
                        else if (position == 1) {
                            Intent intent = new Intent(TopLevelActivity.this,
                                    FoodCategoryActivity.class);
                            startActivity(intent);
                        }
                        else if (position == 2) {
                            Intent intent = new Intent(TopLevelActivity.this, StoreCategoryActivity.class);
                            startActivity(intent);
                        }
                    }
                };
//Add the listener to the Options ListView
        ListView listView = (ListView) findViewById(R.id.list_options);
        listView.setOnItemClickListener(itemClickListener);

//Populate the list_favorites ListView from a cursor
        ListView listFavorites = (ListView)findViewById(R.id.list_favorites);
        ListView listFavorites1 = (ListView)findViewById(R.id.list_favorites1);
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoritesCursor1 = db.query("DRINK",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter =
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1,
                            favoritesCursor1,
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listFavorites.setAdapter(favoriteAdapter);
        }
        catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable drinkcateogry", Toast.LENGTH_SHORT);
            toast.show();
        }
        //Navigate to DrinkActivity if a drink is clicked
        listFavorites.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id)
            {
                Intent intent = new Intent(TopLevelActivity.this, DrinkActivity.class);
                intent.putExtra(DrinkActivity.EXTRA_DRINKNO, (int)id);
                startActivity(intent);
            }
        });

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();
            favoritesCursor2 = db.query("FOOD",
                    new String[]{"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            CursorAdapter favoriteAdapter2 =
                    new SimpleCursorAdapter(TopLevelActivity.this,
                            android.R.layout.simple_list_item_1,
                            favoritesCursor2,
                            new String[]{"NAME"},
                            new int[]{android.R.id.text1}, 0);
            listFavorites1.setAdapter(favoriteAdapter2);
        }
        catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable foodcategory", Toast.LENGTH_SHORT);
            toast.show();
        }
        listFavorites1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> listView, View v, int position, long id) {
                Intent intent1 = new Intent(TopLevelActivity.this, FoodActivity.class);
                intent1.putExtra(FoodActivity.EXTRA_FOODNO, (int)id);
                startActivity(intent1);
            }
        });
    }

    public void onRestart() {
        super.onRestart();
        try{
            StarbuzzDatabaseHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();

            Cursor newCursor = db.query("DRINK",
                    new String[] { "_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            ListView listFavorites = (ListView)findViewById(R.id.list_favorites);
            CursorAdapter adapter = (CursorAdapter) listFavorites.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor1 = newCursor;
/*
            db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor newCursor1 = db.query("FOOD",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
                    */
            newCursor = db.query("FOOD",
                    new String[] {"_id", "NAME"},
                    "FAVORITE = 1",
                    null, null, null, null);
            ListView listFavorites1 = (ListView)findViewById(R.id.list_favorites1);
            adapter = (CursorAdapter) listFavorites1.getAdapter();
            adapter.changeCursor(newCursor);
            favoritesCursor2 = newCursor;


        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable restart", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    //Close the cursor and database in the onDestroy() method
    @Override
    public void onDestroy(){
        super.onDestroy();
        favoritesCursor1.close();
        favoritesCursor2.close();
        db.close();
    }
}