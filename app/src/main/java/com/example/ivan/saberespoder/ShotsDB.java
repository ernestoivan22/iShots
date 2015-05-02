package com.example.ivan.saberespoder;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

/**
 * Created by Ivan on 21/04/2015.
 */
public class ShotsDB extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "SHOTS.DB";
    private static final int DATABASE_VERSION =1;
    private static final String CREATE_QUERRY1 = "CREATE TABLE "+TableData.ShotInfo.TABLE_NAME+" ("+TableData.ShotInfo.TITULO+" TEXT, "+TableData.ShotInfo.CONTENIDO+" TEXT, "+TableData.ShotInfo.PUNTEO+" TEXT, "+TableData.ShotInfo.ID_USUARIO+" INT, "+TableData.ShotInfo.ID_SHOT+" INTEGER PRIMARY KEY);";
    private static final String CREATE_QUERRY2 = "CREATE TABLE "+TableData.Etiquetas.TABLE_NAME+" ("+TableData.Etiquetas.ETIQUETA+" TEXT );";
    private static final String CREATE_QUERRY3 =  "CREATE TABLE "+TableData.EtiquetasRelacion.TABLE_NAME+" ("+TableData.EtiquetasRelacion.SHOT_ID+" INT, "+TableData.EtiquetasRelacion.ETIQUETA_ID+" INT);";
    private static final String CREATE_QUERRY4 = "CREATE TABLE "+TableData.UserInfo.TABLE_NAME+" ("+TableData.UserInfo.ID_USUARIO+" INTEGER PRIMARY KEY, "+TableData.UserInfo.NOMBRE_USUARIO+" TEXT, "+TableData.UserInfo.CORREO+" TEXT, "+ TableData.UserInfo.PASS_USUARIO+" TEXT);";
    private static final String CREATE_QUERRY5 = "CREATE TABLE "+TableData.SesionActiva.TABLE_NAME+" ("+TableData.SesionActiva.id+" INT, "+TableData.SesionActiva.id_user+" INT, "+TableData.SesionActiva.username+" TEXT, "+ TableData.SesionActiva.enSesion+" INT);";
    private static final String CREATE_QUERRY6 = "CREATE TABLE "+TableData.shotsFavoritos.TABLE_NAME+" ("+TableData.shotsFavoritos.ID_SHOT+" INT, "+TableData.shotsFavoritos.ID_USUARIO+" INT);";

    public ShotsDB(Context context){
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
            Log.e("DATABASE OPERATIONS", "Database created / opened...");
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_QUERRY1);
        db.execSQL(CREATE_QUERRY2);
        db.execSQL(CREATE_QUERRY3);
        db.execSQL(CREATE_QUERRY4);
        db.execSQL(CREATE_QUERRY5);
        db.execSQL(CREATE_QUERRY6);
        Log.e("DATABASE OPERATIONS", "Tables Created...");
    }

    public void addShotInfo(String titulo, String contenido, int punteo, List<String> etiquetas, SQLiteDatabase db, Usuario user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.ShotInfo.TITULO, titulo);
        contentValues.put(TableData.ShotInfo.CONTENIDO, contenido);
        contentValues.put(TableData.ShotInfo.PUNTEO, punteo);
        contentValues.put(TableData.ShotInfo.ID_USUARIO, user.id);
        db.insert(TableData.ShotInfo.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted in. ID:"+user.id);
    }

    public void addShotFavorito(String userId, String tituloShot, String contenidoShot, SQLiteDatabase db){
        String shotId = getId_Shot(db, tituloShot,contenidoShot);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.shotsFavoritos.ID_SHOT, shotId);
        contentValues.put(TableData.shotsFavoritos.ID_USUARIO, userId);
        db.insert(TableData.shotsFavoritos.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted in. SHOTS_FAVORITOS");
    }

    public void deleteShotFavorito(String userId, String tituloShot, String contenidoShot, SQLiteDatabase db){
        String shotId = getId_Shot(db, tituloShot,contenidoShot);
        String selection = TableData.shotsFavoritos.ID_SHOT +" = ? AND "+TableData.shotsFavoritos.ID_USUARIO+" = ?";
        String[] selection_args = {shotId, userId};
        db.delete(TableData.shotsFavoritos.TABLE_NAME,selection,selection_args);
    }

    public Cursor getMyShots(SQLiteDatabase db, Usuario user){
        Cursor cursor;
        String[] projections = {TableData.ShotInfo.TITULO, TableData.ShotInfo.CONTENIDO,
                TableData.ShotInfo.PUNTEO};
        String selection = TableData.ShotInfo.ID_USUARIO+" = ?";
        String[] selection_args = {String.valueOf(user.id)};
        cursor = db.query(TableData.ShotInfo.TABLE_NAME, projections, selection, selection_args, null, null, null);
        return cursor;
    }

    public Cursor getShotInfo(SQLiteDatabase db){
        try{
            Cursor cursor;
            String[] projections = {TableData.ShotInfo.TITULO, TableData.ShotInfo.CONTENIDO,
                    TableData.ShotInfo.PUNTEO};
            cursor = db.query(TableData.ShotInfo.TABLE_NAME, projections, null, null, null, null, null);
            return cursor;
        }
        catch(Exception e){

            Log.e("DATABASE OPERATIONS", "Excepcion SELECT ");
            return null;
        }

    }

    public Cursor getSpecificShotInfo(SQLiteDatabase db, String toSearch){
        Cursor cursor;
        String[] projections = {TableData.ShotInfo.TITULO, TableData.ShotInfo.CONTENIDO,
                TableData.ShotInfo.PUNTEO};
        String selection = TableData.ShotInfo.CONTENIDO +" LIKE ? OR "+TableData.ShotInfo.TITULO+" LIKE ?";
        String[] selection_args = {"%"+toSearch+"%","%"+toSearch+"%"};

        cursor = db.query(TableData.ShotInfo.TABLE_NAME, projections, selection, selection_args, null, null, null);
        return cursor;
    }

    public boolean esFavorito(SQLiteDatabase db, String shot_titulo, String shot_contenido, String user_id){
        Cursor cursor;
        boolean bandera = false;
        String[] projections = {TableData.shotsFavoritos.ID_SHOT};
        String selection = TableData.shotsFavoritos.ID_USUARIO+" = ?";
        String[] selection_args = {user_id};

        String shot_id = getId_Shot(db,shot_titulo,shot_contenido);

        cursor = db.query(TableData.shotsFavoritos.TABLE_NAME, projections, selection, selection_args, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String shot;
                shot = cursor.getString(0);
                if(shot.equals(shot_id)){
                    bandera = true;
                }

            }while(cursor.moveToNext());
        }
        return bandera;
    }

    public String getId_Shot(SQLiteDatabase db, String titulo_shot, String contenido_shot){
        Cursor cursor;
        String[] projections = {TableData.ShotInfo.ID_SHOT};
        String selection = TableData.ShotInfo.CONTENIDO +" = ? AND "+TableData.ShotInfo.TITULO+" = ?";
        String[] selection_args = {contenido_shot,titulo_shot};

        cursor = db.query(TableData.ShotInfo.TABLE_NAME, projections, selection, selection_args, null, null, null);

        if(cursor.moveToFirst()){
            do{
                String id;
                id = cursor.getString(0);
                return id;

            }while(cursor.moveToNext());

        }
        Log.e("BUSCANDO ID", "No se encontro");
        return "0";

    }

    public Cursor getFavoritos(SQLiteDatabase db, String user_id){
        Cursor cursor;
        Cursor cursor2;
        String argumentos = "";

        //------------------------Obtener los ids de los shots------------------------------
        String[] projections = {TableData.shotsFavoritos.ID_SHOT};

        String selection = TableData.shotsFavoritos.ID_USUARIO+" = ?";
        String[] selection_args = {user_id};

        cursor = db.query(TableData.shotsFavoritos.TABLE_NAME, projections, selection, selection_args, null, null, null);
        if(cursor.moveToFirst()){
            do{
                String shot;
                shot = cursor.getString(0);
                argumentos += shot+", ";

            }while(cursor.moveToNext());
            argumentos = argumentos.substring(0, argumentos.length()-4);
            Log.e("RESULTADO IDS", argumentos);

        }
        else{
            return cursor;
        }

        //----------------------------------Obtener los shots--------------------------------------
        String[] projections2 = {TableData.ShotInfo.TITULO, TableData.ShotInfo.CONTENIDO,
                TableData.ShotInfo.PUNTEO};
        String selection2 = user_id+" in("+argumentos+")";

        cursor2 = db.query(TableData.ShotInfo.TABLE_NAME, projections2, selection2, null, null, null, null);
        return cursor2;

    }

    public void addEtiquetas(String etiqueta){

    }

    public void cerrarSesion(SQLiteDatabase db, Usuario user){
        ContentValues contentValues = new ContentValues();
        contentValues.put(String.valueOf(TableData.SesionActiva.id), 1);
        contentValues.put(String.valueOf(TableData.SesionActiva.id_user), user.id);
        contentValues.put(TableData.SesionActiva.username, user.nombre);
        contentValues.put(String.valueOf(TableData.SesionActiva.enSesion), 0);
        db.update(TableData.SesionActiva.TABLE_NAME,contentValues,null,null);
    }

    public Usuario getUsuarioIS(SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {TableData.SesionActiva.id_user,
                TableData.SesionActiva.username,
                TableData.SesionActiva.enSesion};
        String nombreUsuario;
        cursor = db.query(TableData.SesionActiva.TABLE_NAME, projections, null, null, null, null, null);
        int idUsuario,enSesion = 0;
        Usuario user = null;
        if(cursor.moveToFirst()) {
            idUsuario = cursor.getInt(0);
            nombreUsuario = cursor.getString(1);
            enSesion = cursor.getInt(2);
            user = new Usuario(idUsuario,nombreUsuario);
        }
        if (enSesion==1)
            return user;
        else
            return null;
    }

    public Usuario iniciarSesion(String correo, String password, SQLiteDatabase db){

        Cursor cursor;
        String[] projections = {TableData.UserInfo.CORREO,
                TableData.UserInfo.PASS_USUARIO,
                TableData.UserInfo.NOMBRE_USUARIO,
                String.valueOf(TableData.UserInfo.ID_USUARIO)};

        String passwordUsuario, correoUsuario, nombreUsuario="";
        int id_usuario=0;

        cursor = db.query(TableData.UserInfo.TABLE_NAME, projections,null,null,null,null,null);

        boolean banderaExiste = false;
        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    correoUsuario = cursor.getString(0);
                    passwordUsuario = cursor.getString(1);
                    nombreUsuario = cursor.getString(2);
                    id_usuario = cursor.getInt(3);
                    if (correoUsuario.equals(correo) && passwordUsuario.equals(password))
                        banderaExiste = true;
                }while(cursor.moveToNext() && !banderaExiste);
            }

        }

        if (!banderaExiste)
            return null;
        else {
            ContentValues contentValues = new ContentValues();
            contentValues.put(String.valueOf(TableData.SesionActiva.id), 1);
            contentValues.put(String.valueOf(TableData.SesionActiva.id_user), id_usuario);
            contentValues.put(TableData.SesionActiva.username, nombreUsuario);
            contentValues.put(String.valueOf(TableData.SesionActiva.enSesion), 1);

            String selection = TableData.SesionActiva.id+" = ?";
            String[] selection_args = {"1"};
            db.update(TableData.SesionActiva.TABLE_NAME,contentValues,selection,selection_args);

            return new Usuario(id_usuario, nombreUsuario);
        }
    }

    public Usuario addUsuario(String nombre, String correo, String password, SQLiteDatabase db){
        Cursor cursor;
        String[] projections = {TableData.UserInfo.NOMBRE_USUARIO, TableData.UserInfo.CORREO,
                TableData.UserInfo.PASS_USUARIO};

        cursor = db.query(TableData.UserInfo.TABLE_NAME, projections,null,null,null,null,null);
        boolean tablaVacia;
        tablaVacia = cursor.getCount() == 0;
        boolean banderaExiste = false;

        if(cursor!=null){
            if(cursor.moveToFirst()){
                do{
                    String nombreUsuario, correoUsuario;
                    nombreUsuario = cursor.getString(0);
                    correoUsuario = cursor.getString(1);
                    if (nombreUsuario.equals(nombre) || correoUsuario.equals(correo))
                        banderaExiste = true;
                }while(cursor.moveToNext());
            }

        }

        if (!banderaExiste){
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableData.UserInfo.NOMBRE_USUARIO, nombre);
            contentValues.put(TableData.UserInfo.CORREO, correo);
            contentValues.put(TableData.UserInfo.PASS_USUARIO, password);
            db.insert(TableData.UserInfo.TABLE_NAME, null, contentValues);
            Log.e("DATABASE OPERATIONS", "One row inserted in ");

            contentValues = new ContentValues();
            contentValues.put(String.valueOf(TableData.SesionActiva.id), 1);
            contentValues.put(String.valueOf(TableData.SesionActiva.id_user), cursor.getCount());
            contentValues.put(TableData.SesionActiva.username, nombre);
            contentValues.put(String.valueOf(TableData.SesionActiva.enSesion), 1);
            if (tablaVacia){
                db.insert(TableData.SesionActiva.TABLE_NAME, null, contentValues);
            }
            else{
                String selection = TableData.SesionActiva.id+" = ?";
                String[] selection_args = {"1"};
                db.update(TableData.SesionActiva.TABLE_NAME,contentValues,selection,selection_args);
            }
            Usuario user = new Usuario(cursor.getCount(),nombre);
            return user;
        }
        else {
            return null;
        }
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
