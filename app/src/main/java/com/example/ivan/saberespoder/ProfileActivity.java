package com.example.ivan.saberespoder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;


public class ProfileActivity extends ActionBarActivity {

    SQLiteDatabase mySQLiteDB;
    Usuario usuarioIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        ListView listView1 = (ListView) findViewById(R.id.listView);
        String[] opciones  = {"Crear shot","Mis shots","Calificar shots","Mis favoritos","Cerrar sesión"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, opciones);
        listView1.setAdapter(adapter);

        ImageButton toProfile = (ImageButton) findViewById(R.id.toProfile);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.Conf);
        ImageView btnLogo = (ImageView) findViewById(R.id.iShots);

        usuarioIS = getIntent().getParcelableExtra("usuario");

        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (usuarioIS==null)
                    i = new Intent(ProfileActivity.this,LoginActivity.class);
                else{
                    i = new Intent(ProfileActivity.this,ProfileActivity.class);
                    i.putExtra("usuario", usuarioIS);
                }
                startActivity(i);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,Settings.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                startActivity(i);
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,PantallaPrincipal.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                startActivity(i);
            }
        });

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                switch(position){
                    case 0://Agregar shots
                        Intent i = new Intent(ProfileActivity.this,AgregarShot.class);
                        if (usuarioIS!=null)
                            i.putExtra("usuario", usuarioIS);
                        startActivity(i);
                        break;
                    case 1://Mis shots
                        break;
                    case 2://Calificar shots
                        break;
                    case 3://Mis favoritos
                        break;
                    case 4://Cerrar sesion
                        ShotsDB myShotsDB = new ShotsDB(getApplicationContext());
                        mySQLiteDB = myShotsDB.getWritableDatabase();
                        myShotsDB.cerrarSesion(mySQLiteDB,usuarioIS);
                        mySQLiteDB.close();
                        startActivity(new Intent(ProfileActivity.this, LoginActivity.class));
                        finish();
                        break;
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
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
