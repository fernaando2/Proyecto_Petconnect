package com.example.proyecto_petconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {
    public DatabaseHelper(Context context) {
        super(context, "PetConnect.db", null, 5);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE mascotas (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, ESPECIE TEXT, DESCRIPCION TEXT, ESTADO TEXT, FOTO_PATH TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int next) {
        db.execSQL("DROP TABLE IF EXISTS mascotas");
        onCreate(db);
    }

    public void insertarMascota(String n, String e, String d, String s, String p) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE", n); cv.put("ESPECIE", e); cv.put("DESCRIPCION", d); cv.put("ESTADO", s); cv.put("FOTO_PATH", p);
        db.insert("mascotas", null, cv);
    }

    public void borrarMascota(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("mascotas", "ID = ?", new String[]{id});
        db.close();
    }

    public Cursor obtenerMascotasFiltradas(String filtro) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (filtro.equals("Todos")) return db.rawQuery("SELECT * FROM mascotas", null);
        return db.rawQuery("SELECT * FROM mascotas WHERE ESTADO = ?", new String[]{filtro});
    }
}