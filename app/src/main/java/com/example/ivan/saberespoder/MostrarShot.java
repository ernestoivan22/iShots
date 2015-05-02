package com.example.ivan.saberespoder;

import android.content.Context;
import android.content.Intent;
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

        //-------------------------------Detectar Swipe-----------------------------
        findViewById(R.id.contenido_Shot_mostrar).setOnTouchListener(new OnSwipeTouchListener(context) {
            @Override
            public void onSwipeLeft() {
                if(posicionShot!=titulosList.length-1){
                    posicionShot++;
                }
                tituloS.setText(titulosList[posicionShot]);
                contenidoS.setText(contenidosList[posicionShot],TextView.BufferType.EDITABLE);
            }
            @Override
            public void onSwipeRight() {
                if(posicionShot!=0){
                    posicionShot--;
                }
                tituloS.setText(titulosList[posicionShot]);
                contenidoS.setText(contenidosList[posicionShot],TextView.BufferType.EDITABLE);
            }
        });

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
