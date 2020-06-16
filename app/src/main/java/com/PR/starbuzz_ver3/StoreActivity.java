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

import com.example.starbuzz2.R;


public class StoreActivity extends Activity {

    public static final String EXTRA_STORENO = "storeNo";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store);

        //get the store from the intent
        int storeNo = (Integer)getIntent().getExtras().get(EXTRA_STORENO);

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            SQLiteDatabase db = starbuzzDatabaseHelper.getReadableDatabase();
            Cursor cursor = db.query("STORE",
                    new String[] {"NAME", "ADDRESS", "IMAGE_RESOURCE_ID"},
                    "_id = ?",
                    new String[] {Integer.toString(storeNo)},
                    null, null, null);

            if (cursor.moveToFirst()) {
                String nameText = cursor.getString(0);
                String addressText = cursor.getString(1);
                int photoId = cursor.getInt(2);

                //populate the store image
                ImageView photo = (ImageView)findViewById(R.id.photo);
                photo.setImageResource(photoId);
                photo.setContentDescription(nameText);

                //populate the store name
                TextView name = (TextView)findViewById(R.id.name);
                name.setText(nameText);

                //populate the store address
                TextView address = (TextView)findViewById(R.id.address);
                address.setText(addressText);
            }
            cursor.close();
            db.close();
        } catch (SQLiteException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }
}
