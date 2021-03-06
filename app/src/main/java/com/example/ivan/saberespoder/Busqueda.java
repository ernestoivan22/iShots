package com.example.ivan.saberespoder;

import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class Busqueda extends ActionBarActivity {

    ListView myListView;
    SQLiteDatabase mySqlDB;
    ShotsDB myShotsDB;
    Cursor cursor;
    ListDataAdapter myListDataAdapter;
    Context context = this;
    Usuario usuarioIS;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_busqueda);
        getSupportActionBar().hide();
        Typeface type_estre = Typeface.createFromAsset(getAssets(),"fonts/geo_1.ttf");
        ImageButton toProfile = (ImageButton) findViewById(R.id.imageButton2);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.imageButton3);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView2);
        TextView txtResultados = (TextView) findViewById(R.id.textView5);
        txtResultados.setTypeface(type_estre);
        usuarioIS = getIntent().getParcelableExtra("usuario");
        myShotsDB = new ShotsDB(getApplicationContext());
        mySqlDB = myShotsDB.getReadableDatabase();
        if (usuarioIS==null)
            usuarioIS = myShotsDB.getUsuarioIS(mySqlDB);
        mySqlDB.close();
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (usuarioIS==null)
                    i = new Intent(Busqueda.this,LoginActivity.class);
                else{
                    i = new Intent(Busqueda.this,ProfileActivity.class);
                    i.putExtra("usuario", usuarioIS);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Busqueda.this,HelpActivity.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Busqueda.this,PantallaPrincipal.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        // Get the intent, verify the action and get the query abc
        Intent intent = getIntent();
        if (Intent.ACTION_SEARCH.equals(intent.getAction()) || RecognizerIntent.ACTION_RECOGNIZE_SPEECH.equals(intent.getAction())) {
            String query = intent.getStringExtra(SearchManager.QUERY);
            doMySearch(query);
        }
        else{
            if(intent.getIntExtra("llamada_de",0)==0){
                searchMyShots();
            }
            else if(intent.getIntExtra("llamada_de",0)==1){
                searchMyFavoriteShots();
            }

        }

        //Detectar taps
        myListView = (ListView)findViewById(R.id.listResultados);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String tituloObt = ((DataProvider) parent.getItemAtPosition(position)).getTitle();
                String contenidoObt = ((DataProvider) parent.getItemAtPosition(position)).getTitle();
                Intent intent = new Intent(context, MostrarShot.class);
                String[] listaTitulos = new String[parent.getCount()];
                String[] listaContenidos = new String[parent.getCount()];

                for (int i = 0; i < parent.getCount(); i++) {
                    listaTitulos[i] = ((DataProvider) parent.getItemAtPosition(i)).getTitle();
                }
                for (int i = 0; i < parent.getCount(); i++) {
                    listaContenidos[i] = ((DataProvider) parent.getItemAtPosition(i)).getContent();
                }
                //Toast.makeText(getBaseContext(), listaContenidos[position], Toast.LENGTH_LONG).show();
                intent.putExtra("listaTitulos", listaTitulos);
                intent.putExtra("listaContenidos", listaContenidos);
                intent.putExtra("positionShot", position);
                if (usuarioIS != null)
                    intent.putExtra("usuario", usuarioIS);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(intent);

            }

        });

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
                String titulo, contenido, contenidoExtra;
                float punteo;
                titulo = cursor.getString(0);
                contenido = cursor.getString(1);
                punteo = cursor.getFloat(2);
                if (contenido.length()<=50)
                    contenidoExtra = contenido;
                else
                    contenidoExtra = contenido.substring(0,50)+"...";
                DataProvider myDataProvider = new DataProvider(titulo,contenido,punteo,contenidoExtra);
                myListDataAdapter.add(myDataProvider);

            }while(cursor.moveToNext());
        }
        else{
            Toast.makeText(getBaseContext(), "No hubo coincidencias.", Toast.LENGTH_LONG).show();
        }
        myShotsDB.close();
    }

    private void searchMyShots() {
        myListView = (ListView)findViewById(R.id.listResultados);
        myListDataAdapter = new ListDataAdapter(getApplicationContext(),R.layout.fila_lista);
        myListView.setAdapter(myListDataAdapter);

        myShotsDB = new ShotsDB(getApplicationContext());
        mySqlDB = myShotsDB.getReadableDatabase();

        cursor = myShotsDB.getMyShots(mySqlDB, usuarioIS);

        if(cursor.moveToFirst()){
            do{
                String titulo, contenido, contenidoExtra;
                float punteo;
                titulo = cursor.getString(0);
                contenido = cursor.getString(1);
                punteo = cursor.getFloat(2);
                if (contenido.length()<=50)
                    contenidoExtra = contenido;
                else
                    contenidoExtra = contenido.substring(0,50)+"...";
                DataProvider myDataProvider = new DataProvider(titulo,contenido,punteo,contenidoExtra);
                myListDataAdapter.add(myDataProvider);
            }while(cursor.moveToNext());
        }
        else{
            Toast.makeText(getBaseContext(), "Aún no ha creado shots", Toast.LENGTH_LONG).show();
        }
        myShotsDB.close();
    }

    private void searchMyFavoriteShots() {
        myListView = (ListView)findViewById(R.id.listResultados);
        myListDataAdapter = new ListDataAdapter(getApplicationContext(),R.layout.fila_lista);
        myListView.setAdapter(myListDataAdapter);

        myShotsDB = new ShotsDB(getApplicationContext());
        mySqlDB = myShotsDB.getReadableDatabase();

        cursor = myShotsDB.getFavoritos(mySqlDB, usuarioIS.id + "");

        if(cursor.moveToFirst()){
            do{
                String titulo, contenido, contenidoExtra;
                float punteo;
                titulo = cursor.getString(0);
                contenido = cursor.getString(1);
                punteo = cursor.getFloat(2);
                if (contenido.length()<=50)
                    contenidoExtra = contenido;
                else
                    contenidoExtra = contenido.substring(0,50)+"...";
                DataProvider myDataProvider = new DataProvider(titulo,contenido,punteo,contenidoExtra);
                myListDataAdapter.add(myDataProvider);

            }while(cursor.moveToNext());
        }
        else{
            Toast.makeText(getBaseContext(), "No ha agregado shots como favoritos", Toast.LENGTH_LONG).show();
        }
        myShotsDB.close();
    }
}
