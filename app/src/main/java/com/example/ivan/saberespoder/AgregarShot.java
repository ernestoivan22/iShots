package com.example.ivan.saberespoder;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class AgregarShot extends ActionBarActivity {
    EditText ShotTitle, ShotContent;
    Context context = this;
    ShotsDB myShotsDB;
    SQLiteDatabase sqLiteDatabase;
    Usuario usuarioIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //holi
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_agregar_shot);
        ShotTitle = (EditText)findViewById(R.id.shot_titulo);
        ShotContent = (EditText)findViewById(R.id.shot_contenido);

        ImageButton toProfile = (ImageButton) findViewById(R.id.imageButton);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.imageButton15);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView7);
        usuarioIS = getIntent().getParcelableExtra("usuario");
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (usuarioIS == null)
                    i = new Intent(AgregarShot.this, LoginActivity.class);
                else {
                    i = new Intent(AgregarShot.this, ProfileActivity.class);
                    i.putExtra("usuario", usuarioIS);
                }
                startActivity(i);
                finish();
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgregarShot.this,Settings.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                startActivity(i);
                finish();
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(AgregarShot.this,PantallaPrincipal.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                startActivity(i);
                finish();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_agregar_shot, menu);
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

    public void addShot(View view){
        String titulo = ShotTitle.getText().toString();
        String contenido = ShotContent.getText().toString();
        myShotsDB =  new ShotsDB(context);
        sqLiteDatabase = myShotsDB.getWritableDatabase();
        myShotsDB.addShotInfo(titulo,contenido,0,null,sqLiteDatabase);
        Toast.makeText(getBaseContext(),"Shot guardado", Toast.LENGTH_LONG).show();
        myShotsDB.close();
    }
}
