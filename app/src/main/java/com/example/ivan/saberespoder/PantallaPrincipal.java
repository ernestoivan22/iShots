package com.example.ivan.saberespoder;

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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;


public class PantallaPrincipal extends ActionBarActivity{
    ListView listView;
    ShotsDB myShotsDB;
    SQLiteDatabase sqLiteDatabase;
    Cursor cursor;
    ListDataAdapter myListDataAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        getSupportActionBar().hide();
        ImageButton toProfile = (ImageButton) findViewById(R.id.profileButton);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //attemptLogin();
                startActivity(new Intent(PantallaPrincipal.this,LoginActivity.class));
            }
        });



        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PantallaPrincipal.this,Settings.class));
            }
        });


        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PantallaPrincipal.this,PantallaPrincipal.class));
            }
        });
        listView = (ListView)findViewById(R.id.shots_recientes);
        myListDataAdapter = new ListDataAdapter(getApplicationContext(),R.layout.fila_lista);
        listView.setAdapter(myListDataAdapter);
        myShotsDB = new ShotsDB(getApplicationContext());


        sqLiteDatabase = myShotsDB.getReadableDatabase();



        cursor = myShotsDB.getShotInfo(sqLiteDatabase);
        if(cursor!=null){
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

        }




    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_pantalla_principal, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
