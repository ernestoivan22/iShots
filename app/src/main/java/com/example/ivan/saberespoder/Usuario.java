package com.example.ivan.saberespoder;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by César on 28/04/2015.
 */
public class Usuario implements Parcelable{

    int id;
    String nombre;

    public Usuario(int id, String nombre){
        this.id = id;
        this.nombre = nombre;
    }

    public Usuario(Parcel in){
        String[] data = new String[2];
        in.readStringArray(data);
        this.id = Integer.parseInt(data[0]);
        this.nombre = data[1];
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeStringArray(new String[]{
                String.valueOf(this.id), this.nombre
        });
    }

    public static final Parcelable.Creator<Usuario> CREATOR = new ClassLoaderCreator<Usuario>(){

        @Override
        public Usuario createFromParcel(Parcel source, ClassLoader loader) {
            return new Usuario(source);
        }

        @Override
        public Usuario createFromParcel(Parcel source) {
            return new Usuario (source);
        }

        @Override
        public Usuario[] newArray(int size) {
            return new Usuario[size];
        }
    };
}
