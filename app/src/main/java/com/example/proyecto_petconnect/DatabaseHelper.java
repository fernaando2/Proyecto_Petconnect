package com.example.proyecto_petconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "PetConnect.db", null, 120);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("CREATE TABLE usuarios (ID TEXT PRIMARY KEY, NOMBRE TEXT, TELEFONO TEXT, EMAIL TEXT, PASSWORD TEXT, FOTO_PERFIL TEXT)");
        db.execSQL("CREATE TABLE mascotas (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, ESPECIE TEXT, DESCRIPCION TEXT, ESTADO TEXT, FOTO_PATH TEXT, USUARIO_ID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int next) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS mascotas");
        onCreate(db);
    }

    public void registrarUsuario(String id, String nom, String tel, String mail, String pass, String foto) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID", id); cv.put("NOMBRE", nom); cv.put("TELEFONO", tel);
        cv.put("EMAIL", mail); cv.put("PASSWORD", pass); cv.put("FOTO_PERFIL", foto);
        db.insert("usuarios", null, cv);
    }

    public Cursor login(String mail, String pass) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM usuarios WHERE EMAIL = ? AND PASSWORD = ?", new String[]{mail, pass});
    }

    public Cursor obtenerUsuario(String email) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM usuarios WHERE EMAIL = ?", new String[]{email});
    }

    public void insertarMascota(String n, String e, String d, String s, String p, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE", n); cv.put("ESPECIE", e); cv.put("DESCRIPCION", d);
        cv.put("ESTADO", s); cv.put("FOTO_PATH", p); cv.put("USUARIO_ID", uid);
        db.insert("mascotas", null, cv);
    }

    public Cursor obtenerMascotasFiltradas(String filtro) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (filtro.equals("Todos")) return db.rawQuery("SELECT * FROM mascotas", null);
        return db.rawQuery("SELECT * FROM mascotas WHERE ESTADO = ?", new String[]{filtro});
    }

    public Cursor obtenerMisMascotas(String uid) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM mascotas WHERE USUARIO_ID = ?", new String[]{uid});
    }

    public void borrarMascota(String id) {
        this.getWritableDatabase().delete("mascotas", "ID = ?", new String[]{id});
    }
}