package com.example.ivan.saberespoder;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.RatingBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Ivan on 28/04/2015.
 */
public class ListDataAdapter extends ArrayAdapter {
    //holi
    List list = new ArrayList();
    public ListDataAdapter(Context context, int resource){
        super(context,resource);
    }
    static class LayoutHandler{
        TextView TITULO, CONTEXTO;
        RatingBar PUNTEO;

    }
    @Override
    public void add(Object object){
        super.add(object);
        list.add(object);
    }

    @Override
    public int getCount(){
        return list.size();
    }

    @Override
    public Object getItem(int indice){
        return list.get(indice);
    }

    @Override
    public View getView(int indice, View convertView, ViewGroup parent){
        View fila = convertView;
        LayoutHandler myLayoutHandler;
        if(fila == null){
            LayoutInflater myLayoutInflater = (LayoutInflater)this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            fila = myLayoutInflater.inflate(R.layout.fila_lista,parent,false);
            myLayoutHandler = new LayoutHandler();
            myLayoutHandler.TITULO = (TextView)fila.findViewById(R.id.formato_fila_titulo);
            myLayoutHandler.CONTEXTO = (TextView)fila.findViewById(R.id.formato_fila_contenido);
            myLayoutHandler.PUNTEO = (RatingBar)fila.findViewById(R.id.ratingBar2);
            fila.setTag(myLayoutHandler);
        }
        else{
            myLayoutHandler = (LayoutHandler)fila.getTag();
            DataProvider myDataProvider = (DataProvider)this.getItem(indice);

        }
        DataProvider myDataProvider = (DataProvider)this.getItem(indice);
        myLayoutHandler.TITULO.setText(myDataProvider.getTitle());
        myLayoutHandler.CONTEXTO.setText(myDataProvider.getContent());
        myLayoutHandler.PUNTEO.setRating(myDataProvider.getRating());

        return fila;
    }


}