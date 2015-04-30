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
import android.widget.Toast;


public class Busqueda extends ActionBarActivity {

    ListView myListView;
    SQLiteDatabase mySqlDB;
    ShotsDB myShotsDB;
    Cursor cursor;
    ListDataAdapter myListDataAdapter;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        getSupportActionBar().hide();

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

        // Get the intent, verify the action and get the query abc
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
    }

    private void doMySearch(String query) {
        myListView = (ListView)findViewById(R.id.listResultados);
        myListDataAdapter = new ListDataAdapter(getApplicationContext(),R.layout.fila_lista);
        myListView.setAdapter(myListDataAdapter);

        myShotsDB = new ShotsDB(getApplicationContext());
        mySqlDB = myShotsDB.getReadableDatabase();

        cursor = myShotsDB.getSpecificShotInfo(mySqlDB,query);

        if(cursor.moveToFirst()){
            do{
                String titulo, contenido, punteo;
                titulo = cursor.getString(0);
                contenido = cursor.getString(1);
                punteo = cursor.getString(2);
                DataProvider myDataProvider = new DataProvider(titulo,contenido,punteo);
                myListDataAdapter.add(myDataProvider);

            }while(cursor.moveToNext());
        }
        else{
            Toast.makeText(getBaseContext(), "No hubo coincidencias", Toast.LENGTH_LONG).show();
        }

    }


}
