package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
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

        recyclerView = findViewById(R.id.recyclerViewMascotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // BOTÓN AGREGAR (FAB Redondo)
        FloatingActionButton fabAdd = findViewById(R.id.fabAddPet);
        fabAdd.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, ReporteActivity.class));
        });

        // BOTÓN VER MAPA (Extended FAB a la izquierda)
        ExtendedFloatingActionButton btnMapa = findViewById(R.id.btnVerMapa);
        btnMapa.setOnClickListener(v -> {
            startActivity(new Intent(HomeActivity.this, MapsActivity.class));
        });

        // Logout con pulsación larga en el título
        tvTitulo.setOnLongClickListener(v -> {
            FirebaseAuth.getInstance().signOut();
            startActivity(new Intent(HomeActivity.this, MainActivity.class));
            finish();
            return true;
        });

        cargarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos(); // Refrescar lista al volver de ReporteActivity
    }

    private void cargarDatos() {
        listaMascotas.clear();
        Cursor cursor = db.obtenerTodasLasMascotas();

        if (cursor != null && cursor.getCount() > 0) {
            tvVacio.setVisibility(View.GONE);
            while (cursor.moveToNext()) {
                // IMPORTANTE: El orden debe ser ID(0), Nombre(1), Especie(2), Desc(3), Estado(4)
                Mascota m = new Mascota(
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4) // <--- Aquí cargamos el Estado
                );
                m.setId(cursor.getString(0));
                listaMascotas.add(m);
            }
        } else {
            tvVacio.setVisibility(View.VISIBLE);
        }

        adapter = new MascotaAdapter(this, listaMascotas);
        recyclerView.setAdapter(adapter);
    }
}