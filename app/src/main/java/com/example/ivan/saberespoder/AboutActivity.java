package com.example.ivan.saberespoder;

import android.media.Image;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;


public class AboutActivity extends ActionBarActivity {

    Usuario usuarioIS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        getSupportActionBar().hide();
        TextView text1 = (TextView) findViewById(R.id.textView4);
        TextView text2 = (TextView) findViewById(R.id.textView8);
        ImageView imagen = (ImageView) findViewById(R.id.imageView6);
        usuarioIS = getIntent().getParcelableExtra("usuario");
        int opcion =  getIntent().getIntExtra("opcion",-1);
        switch(opcion){
            case 0:
                imagen.setImageResource(R.drawable.logopeq);
                text1.setText("Version 0.5");
                text2.setText("Intelligence Shots 2015");
                break;
            case 1:
                imagen.setImageResource(R.drawable.reconocer_voz);
                text1.setText("");
                text2.setText("");
                break;
            case 2:
                imagen.setImageResource(R.drawable.escuchar_shot);
                text1.setText("");
                text2.setText("");
                break;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_about, menu);
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
