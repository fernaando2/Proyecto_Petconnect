package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MascotaAdapter adapter;
    ArrayList<Mascota> listaMascotas;
    DatabaseHelper db;
    TextView tvVacio;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);
        listaMascotas = new ArrayList<>();
        tvVacio = findViewById(R.id.tvEmptyMessage);

        // Configurar RecyclerView
        recyclerView = findViewById(R.id.recyclerViewMascotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarDatos();

        FloatingActionButton fab = findViewById(R.id.fabAddPet);
        fab.setOnClickListener(v -> startActivity(new Intent(this, ReporteActivity.class)));
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos(); // Para que al volver de registrar aparezca el nuevo perro
    }

    private void cargarDatos() {
        listaMascotas.clear();
        Cursor cursor = db.obtenerTodasLasMascotas();

        if (cursor.getCount() == 0) {
            tvVacio.setVisibility(View.VISIBLE);
        } else {
            tvVacio.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                // El orden de las columnas en tu DatabaseHelper es: 0:ID, 1:NOMBRE, 2:ESPECIE, 3:DESC
                listaMascotas.add(new Mascota(cursor.getString(1), cursor.getString(2), cursor.getString(3)));
            }
        }

        adapter = new MascotaAdapter(this, listaMascotas);
        recyclerView.setAdapter(adapter);
    }
}