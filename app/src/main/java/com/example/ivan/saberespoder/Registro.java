package com.example.ivan.saberespoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


public class Registro extends ActionBarActivity {
    EditText editText, editText2, editText3;
    Context context = this;
    ShotsDB myShotsDB;
    TableData myTableData;
    SQLiteDatabase mySQLiteDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);
        getSupportActionBar().hide();
        TextView tituloR = (TextView) this.findViewById(R.id.textView2);
        editText = (EditText)this.findViewById(R.id.editText);
        editText2 = (EditText)this.findViewById(R.id.editText2);
        editText3 = (EditText)this.findViewById(R.id.editText3);
        Typeface type_estre = Typeface.createFromAsset(getAssets(),"fonts/estre.ttf");
        tituloR.setTypeface(type_estre);
        editText.setTypeface(type_estre);
        editText2.setTypeface(type_estre);
        editText3.setTypeface(type_estre);
        Button btnAceptar = (Button) findViewById(R.id.button3);
        Button btnCancelar  = (Button) findViewById(R.id.button2);
        ImageButton toProfile = (ImageButton) findViewById(R.id.imageButton4);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.imageButton5);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView3);
        btnAceptar.setTypeface(type_estre);
        btnCancelar.setTypeface(type_estre);
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(Registro.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });



        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registro.this,HelpActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });


        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registro.this,PantallaPrincipal.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnCancelar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Registro.this,LoginActivity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
                finish();
            }
        });

        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShotsDB shotsUser = new ShotsDB(context);
                boolean bandera;
                mySQLiteDB = shotsUser.getWritableDatabase();
                Usuario user = shotsUser.addUsuario(editText.getText().toString(),editText2.getText().toString(),editText3.getText().toString(), mySQLiteDB);
                bandera = user != null;
                shotsUser.close();

                if (bandera) {
                    Intent i = new Intent(Registro.this, ProfileActivity.class);
                    i.putExtra("usuario",user);
                    i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(i);
                    finish();
                    Toast.makeText(getApplicationContext(),"Has creado tu usuario exitosamente!",Toast.LENGTH_LONG).show();
                }
                else
                    Toast.makeText(getApplicationContext(), "El usuario y/o correo ya han sido utilizados!", Toast.LENGTH_LONG).show();
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_menu_usuario, menu);
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
