package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView rv;
    private ArrayList<Mascota> misMascotas;
    private String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        db = new DatabaseHelper(this);
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        rv = findViewById(R.id.rvMisMascotas);
        rv.setLayoutManager(new LinearLayoutManager(this));

        cargarInfoUsuario();
        cargarMisPublicaciones();

        // Dentro del onCreate de PerfilActivity.java
        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnCerrarSesion.setOnClickListener(v -> {
            // 1. Creamos el Intent hacia el Login
            Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);

            // 2. Limpiamos el historial de pantallas para que no pueda volver atrás
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);

            // 3. Cerramos la actividad actual
            finish();
        });
    }

    private void cargarInfoUsuario() {
        TextView tvNom = findViewById(R.id.tvPerfilNombre);
        TextView tvCon = findViewById(R.id.tvPerfilContacto);

        Cursor c = db.obtenerUsuario(userEmail);
        if (c != null && c.moveToFirst()) {
            tvNom.setText(c.getString(1)); // Nombre
            tvCon.setText(c.getString(2) + " | " + c.getString(3)); // Tel y Mail
            c.close();
        }
    }

    private void cargarMisPublicaciones() {
        misMascotas = new ArrayList<>();
        Cursor c = db.obtenerMisMascotas(userEmail);
        if (c != null) {
            while (c.moveToNext()) {
                Mascota m = new Mascota(c.getString(1), c.getString(2), c.getString(3),
                        c.getString(4), c.getString(5), c.getString(6));
                m.setId(c.getString(0));
                misMascotas.add(m);
            }
            c.close();
        }
        // Pasamos 'this' para que el adapter sepa que estamos en Perfil y deje borrar
        rv.setAdapter(new MascotaAdapter(this, misMascotas));
    }

    public void eliminarMascota(String id) {
        db.borrarMascota(id);
        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
        cargarMisPublicaciones();
    }
}