package com.example.javi_.practicaandroid;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;


public class DBPref extends DBHelper {
    private static DBPref mInstance = null;
    public static enum Categoria {
        HISTORIA('A'),
        geografia('B'),
        literatura('C'),
        arte('D'),
        deportes('E'),
        ciencia('F'),
        astronomia('H'),
        TECNOLOGIA('G'),
        OTROS('Z');
        public final char C;
         Categoria(char c) {
            this.C = c;
            }
        }
    public static enum Dificultad {
        FACIL('e'),
        MEDIA('m'),
        DIFICIL('h');

        public final char D;
         Dificultad(char d) {
            this.D = d;
            }
    }
    private DBPref(Context contexto) {
        super(contexto);
    }

    public static DBPref getInstance(Context contexto){
        if(mInstance == null) {
            mInstance = new DBPref(contexto);
        }
        return mInstance;
    }



    public Cursor getPreguntas(Categoria c, Dificultad d, int limit) {
         return this.db.rawQuery("SELECT `pregunta`, `respuesta_correcta`, `respuesta_incorrecta_1`, `respuesta_incorrecta_2`," +
                         "`respuesta_incorrecta_3`,`pregunta_tipo` FROM `preguntas` WHERE Categoria = ? AND Dificultad = ?" + "ORDER BY RANDOM() LIMIT ?",
        new String[]{String.valueOf(c.C), String.valueOf(d.D), String.valueOf(limit)});
        }
    //...
}