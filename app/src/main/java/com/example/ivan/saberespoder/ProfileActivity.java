package com.example.ivan.saberespoder;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Typeface;
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

import org.w3c.dom.Text;


public class ProfileActivity extends ActionBarActivity {

    SQLiteDatabase mySQLiteDB;
    Usuario usuarioIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);
        getSupportActionBar().hide();
        ListView listView1 = (ListView) findViewById(R.id.listView);
        String[] opciones  = {"Crear shot","Mis shots","Mis favoritos","Cerrar sesión"};
        Typeface type_estre = Typeface.createFromAsset(getAssets(),"fonts/geo_1.ttf");
        TextView txt7 = (TextView) findViewById(R.id.textView7);
        txt7.setTypeface(type_estre);
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
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,HelpActivity.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(ProfileActivity.this,PantallaPrincipal.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
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
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
                    case 1://Mis shots
                        Intent ii = new Intent(ProfileActivity.this,Busqueda.class);
                        if (usuarioIS!=null)
                            ii.putExtra("usuario", usuarioIS);
                        ii.putExtra("llamada_de",0); //para saber que desplegar
                        ii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(ii);
                        break;
                    case 2://Mis favoritos
                        Intent iiii = new Intent(ProfileActivity.this,Busqueda.class);
                        if (usuarioIS!=null)
                            iiii.putExtra("usuario", usuarioIS);
                        iiii.putExtra("llamada_de",1); //para saber que desplegar
                        iiii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iiii);
                        break;
                    case 3://Cerrar sesion
                        ShotsDB myShotsDB = new ShotsDB(getApplicationContext());
                        mySQLiteDB = myShotsDB.getWritableDatabase();
                        myShotsDB.cerrarSesion(mySQLiteDB, usuarioIS);
                        mySQLiteDB.close();
                        Intent iiiii = new Intent(ProfileActivity.this, LoginActivity.class);
                        iiiii.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(iiiii);
                        finish();
                        break;
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(ProfileActivity.this,PantallaPrincipal.class);
        if (usuarioIS!=null)
            i.putExtra("usuario", usuarioIS);
        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(i);
        finish();
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
