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
    private static final String CREATE_QUERRY7 = "CREATE TABLE "+TableData.shotsPunteos.TABLE_NAME+" ("+TableData.shotsPunteos.ID_SHOT+" INT, "+TableData.shotsPunteos.ID_USUARIO+" INT, "+TableData.shotsPunteos.PUNTEO_SHOT+" INT);";
    //private static final String CREATE_USER = "INSERT INTO "+TableData.UserInfo.TABLE_NAME+" VALUES (1, \'admin\', \'admin@admin.com\', \'pass\')";
    private static final String CREATE_SHOTS = "INSERT INTO "+TableData.ShotInfo.TABLE_NAME+" VALUES (\'Islas de perros\', \'Las Islas Canarias le deben su nombre a los canes y no a los canarios.\', 0, -1,  1)";
    private static final String CREATE_SHOTS2 = "INSERT INTO "+TableData.ShotInfo.TABLE_NAME+" VALUES (\'Cosas del diablo\', \'El opuesto de símbolo es diávolo.\', 0, -1,  2)";
    private static final String CREATE_SHOTS3 = "INSERT INTO "+TableData.ShotInfo.TABLE_NAME+" VALUES (\'La Aventura Musical de Jojo\', \'Casi la totalidad de personajes de la aventura bizarra de Jojo poseen nombres de bandas, artistas y canciones.\', 0, -1,  3)";
    private static final String CREATE_SHOTS4 = "INSERT INTO "+TableData.ShotInfo.TABLE_NAME+" VALUES (\'May the 4th be with you\', \'El 4 de mayo es el día de las guerras de las galaxias.\', 0, -1,  4)";
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
        db.execSQL(CREATE_QUERRY7);
        db.execSQL(CREATE_SHOTS);
        db.execSQL(CREATE_SHOTS2);
        db.execSQL(CREATE_SHOTS3);
        db.execSQL(CREATE_SHOTS4);
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

    public void addShotPunteo(String userId, String tituloShot, String contenidoShot, String punteo, SQLiteDatabase db){
        String shotId = getId_Shot(db, tituloShot,contenidoShot);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.shotsPunteos.ID_SHOT, shotId);
        contentValues.put(TableData.shotsPunteos.ID_USUARIO, userId);
        contentValues.put(TableData.shotsPunteos.PUNTEO_SHOT, punteo);
        db.insert(TableData.shotsPunteos.TABLE_NAME, null, contentValues);
        Log.e("DATABASE OPERATIONS", "One row inserted in. SHOTS_PUNTEOS");
    }

    public void upDateShotPunteo(String userId, String tituloShot, String contenidoShot, String punteo, SQLiteDatabase db){
        String shotId = getId_Shot(db, tituloShot,contenidoShot);
        ContentValues contentValues = new ContentValues();
        contentValues.put(TableData.shotsPunteos.PUNTEO_SHOT, punteo);
        String selection = TableData.shotsPunteos.ID_SHOT+" = ? AND "+TableData.shotsPunteos.ID_USUARIO+" = ?";
        String[] selection_args = {shotId, userId};
        db.update(TableData.shotsPunteos.TABLE_NAME, contentValues,selection,selection_args);
    }

    public void controlShotPunteo(String userId, String tituloShot, String contenidoShot, String punteo, SQLiteDatabase db){
        String shotId = getId_Shot(db, tituloShot,contenidoShot);
        String[] projections = {TableData.shotsPunteos.PUNTEO_SHOT};
        String selection = TableData.shotsPunteos.ID_SHOT+" = ? AND "+TableData.shotsPunteos.ID_USUARIO+" = ?";
        String[] selection_args = {shotId, userId};
        Cursor cursor = db.query(TableData.shotsPunteos.TABLE_NAME,projections,selection,selection_args,null,null,null);
        if(cursor.moveToFirst()){
            upDateShotPunteo(userId,tituloShot, contenidoShot, punteo, db);
        }
        else{
            addShotPunteo(userId, tituloShot, contenidoShot, punteo, db);
        }
    }

    public float obtenerShotPunteoPromedio(String tituloShot, String contenidoShot, SQLiteDatabase db){
        String shotId = getId_Shot(db, tituloShot,contenidoShot);
        Cursor cursor;
        String[] projections = {"AVG("+TableData.shotsPunteos.PUNTEO_SHOT+")"};
        String selection = TableData.shotsPunteos.ID_SHOT +" = ? ";
        String[] selection_args = {shotId};

        cursor = db.query(TableData.shotsPunteos.TABLE_NAME, projections, selection, selection_args, null, null, null);
        if(cursor.moveToFirst()){
            float promedio;
            promedio = cursor.getFloat(0);

            //guardarlo en shotInfo
            ContentValues contentValues = new ContentValues();
            contentValues.put(TableData.ShotInfo.PUNTEO, promedio);
            selection = TableData.ShotInfo.ID_SHOT+" = ? ";
            String[] selection_args2 = {shotId};
            db.update(TableData.ShotInfo.TABLE_NAME, contentValues,selection,selection_args2);


            return promedio;
        }
        else{
            return 0;
        }

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
        String argumentosFinal = "";
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
            argumentosFinal = argumentos.substring(0, (argumentos.length()-2));
            Log.e("RESULTADO IDS", argumentosFinal);

        }
        else{
            return cursor;
        }

        //----------------------------------Obtener los shots--------------------------------------
        String[] projections2 = {TableData.ShotInfo.TITULO, TableData.ShotInfo.CONTENIDO,
                TableData.ShotInfo.PUNTEO};
        String selection2 = TableData.ShotInfo.ID_SHOT+" in("+argumentosFinal+")";

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
                TableData.UserInfo.ID_USUARIO};

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
                TableData.UserInfo.ID_USUARIO};

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
            contentValues.put(String.valueOf(TableData.SesionActiva.id_user), cursor.getCount()+1);
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
            Usuario user = new Usuario(cursor.getCount()+1,nombre);
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
