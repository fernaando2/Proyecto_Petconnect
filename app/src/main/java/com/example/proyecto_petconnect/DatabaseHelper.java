package com.example.proyecto_petconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "PetConnect.db";
    private static final int DATABASE_VERSION = 1;

    public static final String TABLE_NAME = "mascotas";
    public static final String COL_ID = "ID";
    public static final String COL_NOMBRE = "NOMBRE";
    public static final String COL_ESPECIE = "ESPECIE";
    public static final String COL_DESCRIPCION = "DESCRIPCION";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE " + TABLE_NAME + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, ESPECIE TEXT, DESCRIPCION TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    // CREATE
    public boolean insertarMascota(String nombre, String especie, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, nombre);
        contentValues.put(COL_ESPECIE, especie);
        contentValues.put(COL_DESCRIPCION, desc);
        long result = db.insert(TABLE_NAME, null, contentValues);
        return result != -1;
    }

    // READ
    public Cursor obtenerTodasLasMascotas() {
        SQLiteDatabase db = this.getWritableDatabase();
        return db.rawQuery("SELECT * FROM " + TABLE_NAME, null);
    }

    // UPDATE
    public boolean actualizarMascota(String id, String nombre, String especie, String desc) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COL_NOMBRE, nombre);
        contentValues.put(COL_ESPECIE, especie);
        contentValues.put(COL_DESCRIPCION, desc);
        db.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id});
        return true;
    }

    // DELETE (Borrar todo para el ejemplo)
    public void borrarMascota(String id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, "ID = ?", new String[]{id});
    }
}