package com.example.proyecto_petconnect;

import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {

    private DatabaseHelper db;
    private RecyclerView rv;
    private MascotaAdapter adapter;
    private ArrayList<Mascota> misMascotas;
    private TextView tvNombre, tvContacto;

    // Este ID debe ser el mismo que usas al registrarte y al subir mascotas
    private String miID = "MI_ID_LOCAL";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        db = new DatabaseHelper(this);
        rv = findViewById(R.id.rvMisMascotas);
        tvNombre = findViewById(R.id.tvPerfilNombre);
        tvContacto = findViewById(R.id.tvPerfilContacto);

        rv.setLayoutManager(new LinearLayoutManager(this));

        cargarDatosUsuario();
        cargarMisPublicaciones();
    }

    private void cargarDatosUsuario() {
        Cursor c = db.obtenerUsuario(miID);
        if (c != null && c.moveToFirst()) {
            // Suponiendo columnas: 1:Nombre, 2:Teléfono, 3:Email
            tvNombre.setText(c.getString(1));
            String contacto = c.getString(2) + " | " + c.getString(3);
            tvContacto.setText(contacto);
            c.close();
        } else {
            tvNombre.setText("Usuario no registrado");
            tvContacto.setText("Por favor, completa tu perfil");
        }
    }

    private void cargarMisPublicaciones() {
        misMascotas = new ArrayList<>();
        // Este método en DatabaseHelper debe filtrar por USUARIO_ID
        Cursor c = db.obtenerMisMascotas(miID);

        if (c != null) {
            while (c.moveToNext()) {
                // Mapeo de columnas: 0:ID, 1:Nombre, 2:Especie, 3:Desc, 4:Estado, 5:Foto, 6:UID
                Mascota m = new Mascota(
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6)
                );
                m.setId(c.getString(0));
                misMascotas.add(m);
            }
            c.close();
        }

        adapter = new MascotaAdapter(this, misMascotas);
        rv.setAdapter(adapter);
    }

    // MÉTODO VITAL: El adaptador llama a este método para borrar
    public void eliminarMascota(String idMascota) {
        db.borrarMascota(idMascota);
        Toast.makeText(this, "Publicación eliminada", Toast.LENGTH_SHORT).show();
        // Recargamos la lista para que desaparezca visualmente
        cargarMisPublicaciones();
    }
}