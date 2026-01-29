package com.example.proyecto_petconnect;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper {

    public DatabaseHelper(Context context) {
        super(context, "PetConnect.db", null, 26); // Incrementamos versión
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Usuario: ID, Nombre, Teléfono, Email (4 columnas)
        db.execSQL("CREATE TABLE usuarios (ID TEXT PRIMARY KEY, NOMBRE TEXT, TELEFONO TEXT, EMAIL TEXT)");

        // Mascota: 7 columnas totales (incluyendo el USUARIO_ID)
        db.execSQL("CREATE TABLE mascotas (ID INTEGER PRIMARY KEY AUTOINCREMENT, NOMBRE TEXT, ESPECIE TEXT, " +
                "DESCRIPCION TEXT, ESTADO TEXT, FOTO_PATH TEXT, USUARIO_ID TEXT)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int old, int next) {
        db.execSQL("DROP TABLE IF EXISTS usuarios");
        db.execSQL("DROP TABLE IF EXISTS mascotas");
        onCreate(db);
    }

    // Registro de Usuario con 4 argumentos reales
    public void registrarUsuario(String id, String nom, String tel, String mail) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("ID", id);
        cv.put("NOMBRE", nom);
        cv.put("TELEFONO", tel);
        cv.put("EMAIL", mail);
        db.insert("usuarios", null, cv);
    }

    // Inserción de Mascota con los 6 argumentos requeridos
    public void insertarMascota(String n, String e, String d, String s, String p, String uid) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put("NOMBRE", n);
        cv.put("ESPECIE", e);
        cv.put("DESCRIPCION", d);
        cv.put("ESTADO", s);
        cv.put("FOTO_PATH", p);
        cv.put("USUARIO_ID", uid);
        db.insert("mascotas", null, cv);
    }

    public Cursor obtenerUsuario(String id) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM usuarios WHERE ID = ?", new String[]{id});
    }

    public Cursor obtenerMisMascotas(String uid) {
        return this.getReadableDatabase().rawQuery("SELECT * FROM mascotas WHERE USUARIO_ID = ?", new String[]{uid});
    }

    public Cursor obtenerMascotasFiltradas(String filtro) {
        SQLiteDatabase db = this.getReadableDatabase();
        if (filtro.equals("Todos")) return db.rawQuery("SELECT * FROM mascotas", null);
        return db.rawQuery("SELECT * FROM mascotas WHERE ESTADO = ?", new String[]{filtro});
    }

    /**
     * Borra una mascota de la base de datos usando su ID único.
     * @param id El ID de la mascota que se desea eliminar.
     */
    public void borrarMascota(String id) {
        // Abrimos la base de datos en modo escritura
        SQLiteDatabase db = this.getWritableDatabase();

        // Ejecutamos el borrado filtrando por la columna ID
        // Usamos '?' por seguridad para evitar inyecciones SQL
        db.delete("mascotas", "ID = ?", new String[]{id});

        // Cerramos la conexión para liberar memoria
        db.close();
    }
}