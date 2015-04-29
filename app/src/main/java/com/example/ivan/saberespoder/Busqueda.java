package com.example.ivan.saberespoder;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;


public class Busqueda extends ActionBarActivity {

    ListView myListView;
    SQLiteDatabase mySqlDB;
    ShotsDB myShotsDB;
    Cursor cursor;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        getSupportActionBar().hide();
        myListView = (ListView)findViewById(R.id.listResultados);
        myShotsDB = new ShotsDB(getApplicationContext());
        mySqlDB = myShotsDB.getReadableDatabase();
        cursor = myShotsDB.getShotInfo(mySqlDB);

        if(cursor.moveToFirst())

        myListView = (ListView)findViewById(R.id.listResultados);

        // Get the intent, verify the action and get the query abc
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }

    }

    private void doMySearch(String query) {
        Log.e("QUERRY OBTENIDO", query);
    }


}
