package com.example.proyecto_petconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class PerfilActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private RecyclerView rv;
    private ArrayList<Mascota> misMascotas;
    private String userEmail;
    private FirebaseAuth mAuth; // Motor de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();
        db = new DatabaseHelper(this);

        // Obtenemos el email
        userEmail = getIntent().getStringExtra("USER_EMAIL");

        rv = findViewById(R.id.rvMisMascotas);
        rv.setLayoutManager(new LinearLayoutManager(this));

        cargarInfoUsuario();
        cargarMisPublicaciones();

        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnCerrarSesion.setOnClickListener(v -> {
            // 1. CERRAR SESIÓN EN FIREBASE (Nube)
            mAuth.signOut();

            // 2. BORRAR PERSISTENCIA LOCAL (Móvil)
            // Sin esto, el LoginActivity te mandará de vuelta al Home al detectar datos
            SharedPreferences prefs = getSharedPreferences("PetConnectPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = prefs.edit();
            editor.clear(); // Borra user_email e isLoggedIn
            editor.apply();

            // 3. IR AL LOGIN Y LIMPIAR EL HISTORIAL
            Intent intent = new Intent(PerfilActivity.this, LoginActivity.class);

            // Estas flags borran todas las pantallas abiertas para que no pueda volver atrás
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);

            startActivity(intent);
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
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
        rv.setAdapter(new MascotaAdapter(this, misMascotas));
    }

    public void eliminarMascota(String id) {
        db.borrarMascota(id);
        Toast.makeText(this, "Eliminado", Toast.LENGTH_SHORT).show();
        cargarMisPublicaciones();
    }
}