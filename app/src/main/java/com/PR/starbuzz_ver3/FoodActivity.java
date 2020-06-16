package com.PR.starbuzz_ver3;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;
import android.view.View;
import android.widget.CheckBox;
import android.content.ContentValues;

import com.example.starbuzz2.R;


public class FoodActivity extends Activity {
    public static final String EXTRA_FOODNO = "foodNo";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_food);
//Get the food from the intent
        int foodNo = (Integer)getIntent().getExtras().get(EXTRA_FOODNO);
//Create a cursor
        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            Cursor cursor = db.query ("FOOD",
                    new String[] {"NAME", "DESCRIPTION", "IMAGE_RESOURCE_ID", "FAVORITE"},
                    "_id = ?",
                    new String[] {Integer.toString(foodNo)},
                    null, null,null);
//Move to the first record in the Cursor
            if (cursor.moveToFirst()) {
//Get the food details from the cursor
                String nameText = cursor.getString(0);
                String descriptionText = cursor.getString(1);
                int photoId = cursor.getInt(2);
                boolean isFavorite = (cursor.getInt(3) == 1);
                //Populate the food name
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);
//Populate the food description
                TextView description = (TextView)findViewById(R.id.description);
                description.setText(descriptionText);
//Populate the food image
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);
//Populate the favorite checkbox
                CheckBox favorite = (CheckBox)findViewById(R.id.favorite2);
                favorite.setChecked(isFavorite);
            };
            cursor.close();
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
    //Update the database when the checkbox is clicked
    public void onFavoriteClicked(View view){
        int foodNo = (Integer)getIntent().getExtras().get("foodNo");
        CheckBox favorite = (CheckBox)findViewById(R.id.favorite2);
        ContentValues foodValues = new ContentValues();
        foodValues.put("FAVORITE", favorite.isChecked());
        SQLiteOpenHelper starbuzzDatabaseHelper =
                new StarbuzzDatabaseHelper(FoodActivity.this);
        try {
            SQLiteDatabase db = starbuzzDatabaseHelper.getWritableDatabase();
            db.update("FOOD", foodValues,
                    "_id = ?", new String[] {Integer.toString(foodNo)});
            db.close();
        } catch(SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
