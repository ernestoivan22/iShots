package com.example.ivan.saberespoder;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;


public class Settings extends ActionBarActivity {

    Usuario usuarioIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        getSupportActionBar().hide();

        ListView listView1 = (ListView) findViewById(R.id.listView2);
        String[] opciones  = {"Ayuda"};

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, opciones);
        listView1.setAdapter(adapter);

        listView1.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                switch (position) {
                    case 0://Ayuda
                        Intent i = new Intent(Settings.this, HelpActivity.class);
                        if (usuarioIS != null)
                            i.putExtra("usuario", usuarioIS);
                        i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(i);
                        break;
                }
            }
        });

        ImageButton toProfile = (ImageButton) findViewById(R.id.profileButton);
        ImageButton btnSettings = (ImageButton) findViewById(R.id.btnSettings);
        ImageView btnLogo = (ImageView) findViewById(R.id.imageView);
        usuarioIS = getIntent().getParcelableExtra("usuario");
        toProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i;
                if (usuarioIS==null)
                    i = new Intent(Settings.this,LoginActivity.class);
                else{
                    i = new Intent(Settings.this,ProfileActivity.class);
                    i.putExtra("usuario", usuarioIS);
                }
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnSettings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this,HelpActivity.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

        btnLogo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Settings.this,PantallaPrincipal.class);
                if (usuarioIS!=null)
                    i.putExtra("usuario", usuarioIS);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                startActivity(i);
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_settings, menu);
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
