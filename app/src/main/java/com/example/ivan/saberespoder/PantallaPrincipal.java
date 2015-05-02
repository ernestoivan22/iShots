package com.example.ivan.saberespoder;

import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
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
    Usuario usuarioIS;
    ListView myListView;
    Context context = this;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pantalla_principal);
        getSupportActionBar().hide();
        ImageButton toProfile = (ImageButton) findViewById(R.id.profileButton);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView);
        usuarioIS = getIntent().getParcelableExtra("usuario");
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (usuarioIS==null)
                    i = new Intent(PantallaPrincipal.this,LoginActivity.class);
                else{
                    i = new Intent(PantallaPrincipal.this,ProfileActivity.class);
                    i.putExtra("usuario", usuarioIS);
                }
                startActivity(i);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PantallaPrincipal.this,Settings.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                startActivity(i);
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(PantallaPrincipal.this,PantallaPrincipal.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                startActivity(i);
            }
        });

        Button btn = (Button)findViewById(R.id.btn_buscar);
        btn.setOnClickListener(new View.OnClickListener(){
            @Override
        public void onClick(View arg0){
                onSearchRequested();
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
        if (usuarioIS==null)
            usuarioIS = myShotsDB.getUsuarioIS(sqLiteDatabase);
        sqLiteDatabase.close();

        //Detectar taps
        myListView = (ListView)findViewById(R.id.shots_recientes);
        myListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                String tituloObt = ((DataProvider)parent.getItemAtPosition(position)).getTitle();
                String contenidoObt = ((DataProvider)parent.getItemAtPosition(position)).getTitle();
                Intent intent = new Intent(context,MostrarShot.class);
                String[] listaTitulos = new String[parent.getCount()];
                String[] listaContenidos = new String[parent.getCount()];

                for(int i=0; i<parent.getCount();i++){
                    listaTitulos[i] = ((DataProvider)parent.getItemAtPosition(i)).getTitle();
                }
                for(int i=0; i<parent.getCount();i++){
                    listaContenidos[i] = ((DataProvider)parent.getItemAtPosition(i)).getContent();
                }
                //Toast.makeText(getBaseContext(), listaContenidos[position], Toast.LENGTH_LONG).show();
                intent.putExtra("listaTitulos",listaTitulos);
                intent.putExtra("listaContenidos", listaContenidos);
                intent.putExtra("positionShot", position);
                if (usuarioIS!=null)
                    intent.putExtra("usuario", usuarioIS);
                startActivity(intent);

            }

        });

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
