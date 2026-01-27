package com.example.proyecto_petconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PetConnect.db";
    private static final String TABLE_NAME = "mascotas";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 2); // Subimos a versión 2 por el nuevo campo
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, ESPECIE TEXT, DESCRIPCION TEXT, ESTADO TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public void insertarMascota(String nombre, String especie, String desc, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE", nombre);
        cv.put("ESPECIE", especie);
        cv.put("DESCRIPCION", desc);
        cv.put("ESTADO", estado);
        db.insert(TABLE_NAME, null, cv);
    }

    public void actualizarMascota(String id, String nombre, String especie, String desc, String estado) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE", nombre);
        cv.put("ESPECIE", especie);
        cv.put("DESCRIPCION", desc);
        cv.put("ESTADO", estado);
        db.update(TABLE_NAME, cv, "ID = ?", new String[]{id});
    }

    public Cursor obtenerTodasLasMascotas() {
        SQLiteDatabase db = this.getReadableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    public void borrarMascota(String id) {
        this.getWritableDatabase().delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}