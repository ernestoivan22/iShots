package com.example.ivan.saberespoder;

import android.provider.BaseColumns;

/**
 * Created by Ivan on 21/04/2015.
 */
public class TableData {
    public TableData(){

    }

    public static abstract class ShotInfo implements BaseColumns{
        public static final String TITULO = "Titulo";
        public static final String CONTENIDO = "Contenido";
        public static final String PUNTEO = "punteo";

        public static final String TABLE_NAME = "Shot_Info";


    }

    public static abstract class EtiquetasRelacion implements BaseColumns{
        public static final String ETIQUETA_ID = "Etiqueta_Id";
        public static final String SHOT_ID = "Shot_Id";
        public static final String TABLE_NAME = "Relacion_Etiquetas";
    }

    public static abstract class Etiquetas implements BaseColumns{
        public static final String ETIQUETA = "Etiqueta";
        public static final String TABLE_NAME = "Etiquetas";
    }

    public static abstract class UserInfo implements BaseColumns{
        public static int ID_USUARIO = 1;
        public static final String NOMBRE_USUARIO = "Nombre_Usuario";
        public static final String PASS_USUARIO = "Password";
        public static final String CORREO = "Correo";
        public static final String TABLE_NAME = "User_Info";
    }
}
