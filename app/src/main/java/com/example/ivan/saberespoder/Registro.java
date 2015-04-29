package com.example.ivan.saberespoder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
        editText = (EditText)this.findViewById(R.id.editText);
        editText2 = (EditText)this.findViewById(R.id.editText2);
        editText3 = (EditText)this.findViewById(R.id.editText3);
        Button btnAceptar = (Button) findViewById(R.id.button3);
        btnAceptar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ShotsDB shotsUser = new ShotsDB(context);
                boolean bandera;
                mySQLiteDB = shotsUser.getWritableDatabase();
                bandera = shotsUser.addUsuario(editText.getText().toString(),editText2.getText().toString(),editText3.getText().toString(), mySQLiteDB);
                shotsUser.close();

                if (bandera) {
                    startActivity(new Intent(Registro.this, PantallaPrincipal.class));
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

/*    public void addUser(View view){
        String nombre = editText.getText().toString();
        String correo = editText2.getText().toString();
        String password = editText3.getText().toString();

        myShotsDB= new ShotsDB(context);
        mySQLiteDB = myShotsDB
                .getWritableDatabase();
        myShotsDB.addUsuario(nombre,correo,password, mySQLiteDB,this);
        Toast.makeText(getBaseContext(), "Data Saved", Toast.LENGTH_LONG).show();
        myShotsDB.close();

    }*/

}
