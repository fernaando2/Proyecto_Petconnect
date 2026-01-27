package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MascotaAdapter adapter;
    private ArrayList<Mascota> listaMascotas;
    private DatabaseHelper db;
    private TextView tvVacio, tvTitulo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);
        listaMascotas = new ArrayList<>();
        tvVacio = findViewById(R.id.tvEmptyMessage);
        tvTitulo = findViewById(R.id.tvTitle);

        // Configurar Lista
        recyclerView = findViewById(R.id.recyclerViewMascotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarDatos();

        // Botón añadir
        FloatingActionButton fab = findViewById(R.id.fabAddPet);
        fab.setOnClickListener(v -> startActivity(new Intent(this, ReporteActivity.class)));

        // CERRAR SESIÓN: Si dejas pulsado el título, sales de la app
        tvTitulo.setOnLongClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            Toast.makeText(this, "Sesión cerrada", Toast.LENGTH_SHORT).show();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
            return true;
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    private void cargarDatos() {
        listaMascotas.clear();
        Cursor cursor = db.obtenerTodasLasMascotas();

        if (cursor != null && cursor.getCount() > 0) {
            tvVacio.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                listaMascotas.add(new Mascota(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3)));
            }
        } else {
            tvVacio.setVisibility(View.VISIBLE);
        }

        adapter = new MascotaAdapter(this, listaMascotas);
        recyclerView.setAdapter(adapter);
    }
}