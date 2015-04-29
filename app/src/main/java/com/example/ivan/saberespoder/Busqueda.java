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
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
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

        ImageButton toProfile = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.imageButton3);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView2);

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                startActivity(new Intent(Busqueda.this,LoginActivity.class));
            }
        });



        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Busqueda.this,Settings.class));
            }
        });


        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Busqueda.this,PantallaPrincipal.class));
            }
        });

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
