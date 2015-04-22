package com.example.ivan.saberespoder;

import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.util.List;

/**
 * Created by Ivan on 21/04/2015.
 */
public class ShotsDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SHOTS.DB";
    private static final int DATABASE_VERSION =1;
    private static final String CREATE_QUERRY =
            "CREATE TABLE "+TableData.ShotInfo.TABLE_NAME+" ("+TableData.ShotInfo.TITULO+"TEXT, "+TableData.ShotInfo.PUNTEO+"TEXT );"+
            "CREATE TABLE "+TableData.Etiquetas.TABLE_NAME+" ("+TableData.Etiquetas.ETIQUETA+" TEXT );"+
            "CREATE TABLE "+TableData.EtiquetasRelacion.TABLE_NAME+" ("+TableData.EtiquetasRelacion.SHOT_ID+" INT,"+TableData.EtiquetasRelacion.ETIQUETA_ID+" INT);"+
            "CREATE TABLE "+TableData.UserInfo.TABLE_NAME+" ("+TableData.UserInfo.NOMBRE_USUARIO+" TEXT,"+TableData.UserInfo.CORREO+" TEXT,"+ TableData.UserInfo.PASS_USUARIO+" TEXT);";
    public ShotsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.e("DATABASE OPERATIONS", "Database created / opened...");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERRY);
        Log.e("DATABASE OPERATIONS", "Tables Created...");
    }

    public void addShotInfo(String titulo, String contenido, int punteo, List<String> etiquetas, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.ShotInfo.TITULO, titulo);
        contentValues.put(TableData.ShotInfo.CONTENIDO, contenido);
        contentValues.put(TableData.ShotInfo.PUNTEO, punteo);
        db.insert(TableData.ShotInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted in ");

        for(int i = 0; i<etiquetas.size();i++){

        }
    }

    public void addEtiquetas(String etiqueta){

    }

    public void addUsuario(String nombre, String password, String correo, SQLiteDatabase db){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.UserInfo.NOMBRE_USUARIO, nombre);
        contentValues.put(TableData.UserInfo.CORREO, correo);
        contentValues.put(TableData.UserInfo.PASS_USUARIO, password);
        db.insert(TableData.ShotInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted in ");
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
