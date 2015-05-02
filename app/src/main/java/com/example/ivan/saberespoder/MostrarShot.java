package com.example.ivan.saberespoder;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.media.Image;
import android.speech.tts.TextToSpeech;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;


public class MostrarShot extends ActionBarActivity {
    TextView tituloS;
    EditText contenidoS;
    Context context = this;
    int posicionShot;
    String[] titulosList;
    String[] contenidosList;
    ImageButton speech;
    TTSManager ttsManager;
    Usuario usuarioIS;
    ImageButton favorite;

    SQLiteDatabase mySqlDB;
    ShotsDB myShotsDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mostrar_shot);
        getSupportActionBar().hide();

        ImageButton toProfile = (ImageButton) findViewById(R.id.imageButton6);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.imageButton7);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView4);
        usuarioIS = getIntent().getParcelableExtra("usuario");
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (usuarioIS == null)
                    i = new Intent(MostrarShot.this, LoginActivity.class);
                else {
                    i = new Intent(MostrarShot.this, ProfileActivity.class);
                    i.putExtra("usuario", usuarioIS);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MostrarShot.this, Settings.class);
                if (usuarioIS != null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(MostrarShot.this, PantallaPrincipal.class);
                if (usuarioIS != null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        //----------------------------Cargar elementos----------------------------
        Intent intent = getIntent();
        titulosList = intent.getStringArrayExtra("listaTitulos");
        contenidosList = intent.getStringArrayExtra("listaContenidos");
        posicionShot = intent.getIntExtra("positionShot",0);

        tituloS = (TextView)findViewById(R.id.titulo_Shot_mostrar);
        contenidoS = (EditText)findViewById(R.id.contenido_Shot_mostrar);

        tituloS.setText(titulosList[posicionShot]);

        contenidoS.setText(contenidosList[posicionShot],TextView.BufferType.EDITABLE);
        contenidoS.setFocusable(false);

        //-----------------------------Desabilitar like--------------------
        if(usuarioIS==null){
            ((ImageButton)findViewById(R.id.btn_like_mostrar)).setVisibility(Button.INVISIBLE);
        }
        else{
            ((ImageButton)findViewById(R.id.btn_like_mostrar)).setVisibility(Button.VISIBLE);
            myShotsDB = new ShotsDB(getApplicationContext());
            mySqlDB = myShotsDB.getReadableDatabase();
            favorite = (ImageButton)findViewById(R.id.btn_like_mostrar);
            boolean liked = myShotsDB.esFavorito(mySqlDB, titulosList[posicionShot],contenidosList[posicionShot],usuarioIS.id+"");
            if(liked){
                int id = getResources().getIdentifier("like", "drawable", getPackageName());
                favorite.setImageResource(id);
            }
            else{
                int id = getResources().getIdentifier("likeoutline", "drawable", getPackageName());
                favorite.setImageResource(id);
            }
        }

        //-------------------------------Detectar Swipe-----------------------------
        findViewById(R.id.contenido_Shot_mostrar).setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeLeft() {
                if(posicionShot!=titulosList.length-1){
                    posicionShot++;
                }
                tituloS.setText(titulosList[posicionShot]);
                contenidoS.setText(contenidosList[posicionShot],TextView.BufferType.EDITABLE);

                if(!(usuarioIS == null)){
                    myShotsDB = new ShotsDB(getApplicationContext());
                    mySqlDB = myShotsDB.getReadableDatabase();

                    boolean liked = myShotsDB.esFavorito(mySqlDB, titulosList[posicionShot],contenidosList[posicionShot],usuarioIS.id+"");
                    //------quitar de favoritos------------
                    if(!liked){
                        favorite.setImageResource(R.drawable.likeoutline);
                    }
                    //-----------agregar a favoritos-----------
                    else{
                        favorite.setImageResource(R.drawable.like);
                    }
                }
            }
            @Override
            public void onSwipeRight() {
                if(posicionShot!=0){
                    posicionShot--;
                }
                tituloS.setText(titulosList[posicionShot]);
                contenidoS.setText(contenidosList[posicionShot],TextView.BufferType.EDITABLE);

                if(!(usuarioIS == null)){
                    myShotsDB = new ShotsDB(getApplicationContext());
                    mySqlDB = myShotsDB.getReadableDatabase();

                    boolean liked = myShotsDB.esFavorito(mySqlDB, titulosList[posicionShot],contenidosList[posicionShot],usuarioIS.id+"");
                    //------quitar de favoritos------------
                    if(!liked){
                        favorite.setImageResource(R.drawable.likeoutline);
                    }
                    //-----------agregar a favoritos-----------
                    else{
                        favorite.setImageResource(R.drawable.like);
                    }
                }

            }
        });
        favorite = (ImageButton)findViewById(R.id.btn_like_mostrar);
        //-------------------------------Realizar Speech------------------------------------
        ttsManager = new TTSManager();
        ttsManager.init(this);
        speech = (ImageButton)findViewById(R.id.btn_speech);
        speech.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                String text = contenidosList[posicionShot];
                ttsManager.initQueue(text);
            }
        });

        //-------------------------------Agregar/Quitar Favorito-------------------------------------
        favorite = (ImageButton)findViewById(R.id.btn_like_mostrar);
        favorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myShotsDB = new ShotsDB(getApplicationContext());
                mySqlDB = myShotsDB.getReadableDatabase();

                boolean liked = myShotsDB.esFavorito(mySqlDB, titulosList[posicionShot],contenidosList[posicionShot],usuarioIS.id+"");
                //------quitar de favoritos------------
                if(!liked){
                    favorite.setImageResource(R.drawable.like);
                    myShotsDB.addShotFavorito(usuarioIS.id+"",titulosList[posicionShot],contenidosList[posicionShot],mySqlDB);
                }
                //-----------agregar a favoritos-----------
                else{
                    favorite.setImageResource(R.drawable.likeoutline);
                    myShotsDB.deleteShotFavorito(usuarioIS.id+"",titulosList[posicionShot],contenidosList[posicionShot],mySqlDB);
                }
            }
        });

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_mostrar_shot, menu);
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

    @Override
    public void onDestroy() {
        super.onDestroy();
        ttsManager.shutDown();
    }


}
