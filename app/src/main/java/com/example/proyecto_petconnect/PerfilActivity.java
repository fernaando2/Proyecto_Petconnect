package com.example.proyecto_petconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
// IMPORTANTE: Ya no necesitamos importar AppCompatActivity porque lo tiene BaseActivity
// import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

// CAMBIO 1: Heredamos de BaseActivity para tener la lógica del menú
public class PerfilActivity extends BaseActivity {

    private DatabaseHelper db;
    private RecyclerView rv;
    private ArrayList<Mascota> misMascotas;
    private String userEmail;
    private FirebaseAuth mAuth; // Motor de Firebase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perfil);

        // CAMBIO 2: Activamos el menú y marcamos el icono de "Perfil"
        configurarNavegacion(R.id.nav_perfil);

        // --- El resto de tu código sigue igual ---

        // Inicializamos Firebase
        mAuth = FirebaseAuth.getInstance();
        db = new DatabaseHelper(this);

        // Obtenemos el email
        if (getIntent().hasExtra("USER_EMAIL")) {
            userEmail = getIntent().getStringExtra("USER_EMAIL");
        } else {
            // Si por alguna razón no llega el extra, lo sacamos de Firebase
            if (mAuth.getCurrentUser() != null) {
                userEmail = mAuth.getCurrentUser().getEmail();
            }
        }

        rv = findViewById(R.id.rvMisMascotas);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // Verificamos que tengamos usuario antes de cargar datos
        if (userEmail != null) {
            cargarInfoUsuario();
            cargarMisPublicaciones();
        }

        Button btnCerrarSesion = findViewById(R.id.btnCerrarSesion);

        btnCerrarSesion.setOnClickListener(v -> {
            // 1. CERRAR SESIÓN EN FIREBASE (Nube)
            mAuth.signOut();

            // 2. BORRAR PERSISTENCIA LOCAL (Móvil)
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

            // IMPORTANTE: Quitamos la animación al salir para que sea limpio
            overridePendingTransition(0, 0);
            finish();
        });
    }

    private void cargarInfoUsuario() {
        TextView tvNom = findViewById(R.id.tvPerfilNombre);
        TextView tvCon = findViewById(R.id.tvPerfilContacto);

        // Aseguramos que el email no sea nulo antes de buscar en DB
        if (userEmail == null) return;

        Cursor c = db.obtenerUsuario(userEmail);
        if (c != null && c.moveToFirst()) {
            tvNom.setText(c.getString(1)); // Nombre
            tvCon.setText(c.getString(2) + " | " + c.getString(3)); // Tel y Mail
            c.close();
        }
    }

    private void cargarMisPublicaciones() {
        if (userEmail == null) return;

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