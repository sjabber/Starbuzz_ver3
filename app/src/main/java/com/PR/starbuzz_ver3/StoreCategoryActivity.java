package com.PR.starbuzz_ver3;

import android.app.ListActivity;
import android.database.SQLException;
import android.os.Bundle;
import android.widget.ListView;
import android.view.View;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.CursorAdapter;
import android.widget.Toast;
import android.widget.SimpleCursorAdapter;


public class StoreCategoryActivity extends ListActivity {

    private SQLiteDatabase db;
    private Cursor cursor;

    @Override
    protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ListView listStores = getListView();

        try {
            SQLiteOpenHelper starbuzzDatabaseHelper = new StarbuzzDatabaseHelper(this);
            db = starbuzzDatabaseHelper.getReadableDatabase();

            cursor = db.query("STORE",
                    new String[] {"_id", "NAME"},
                    null, null, null, null, null);
            CursorAdapter listAdapter = new SimpleCursorAdapter(this,
                    android.R.layout.simple_list_item_1,
                    cursor,
                    new String[] {"NAME"},
                    new int[] {android.R.id.text1},
                    0);
            listStores.setAdapter(listAdapter);
        } catch (SQLException e) {
            Toast toast = Toast.makeText(this, "Database unavailable", Toast.LENGTH_SHORT);
            toast.show();
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        cursor.close();
        db.close();
    }

    @Override
    public void onListItemClick(ListView listView,
                                View iteView,
                                int position,
                                long id) {
        Intent intent = new Intent(StoreCategoryActivity.this, StoreActivity.class);
        intent.putExtra(StoreActivity.EXTRA_STORENO, (int) id);
        startActivity(intent);
    }
}
