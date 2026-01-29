package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private MascotaAdapter adapter;
    private ArrayList<Mascota> listaMascotas;
    private DatabaseHelper db;
    private TextView tvVacio;
    private String filtroActual = "Todos";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);
        listaMascotas = new ArrayList<>();
        tvVacio = findViewById(R.id.tvEmptyMessage);
        recyclerView = findViewById(R.id.recyclerViewMascotas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        ChipGroup chipGroup = findViewById(R.id.chipGroupFilters);
        chipGroup.setOnCheckedChangeListener((group, checkedId) -> {
            if (checkedId == R.id.chipPerdidos) filtroActual = "Perdido";
            else if (checkedId == R.id.chipLocalizados) filtroActual = "Localizado";
            else if (checkedId == R.id.chipAcogida) filtroActual = "En Acogida";
            else filtroActual = "Todos";
            cargarDatos();
        });

        findViewById(R.id.btnVerMapa).setOnClickListener(v -> startActivity(new Intent(this, MapsActivity.class)));
        findViewById(R.id.fabAddPet).setOnClickListener(v -> startActivity(new Intent(this, ReporteActivity.class)));

        cargarDatos();
    }

    @Override
    protected void onResume() {
        super.onResume();
        cargarDatos();
    }

    private void cargarDatos() {
        listaMascotas.clear();
        Cursor c = db.obtenerMascotasFiltradas(filtroActual);
        if (c != null && c.getCount() > 0) {
            tvVacio.setVisibility(View.GONE);
            while (c.moveToNext()) {
                Mascota m = new Mascota(c.getString(1), c.getString(2), c.getString(3), c.getString(4), c.getString(5));
                m.setId(c.getString(0));
                listaMascotas.add(m);
            }
        } else {
            tvVacio.setVisibility(View.VISIBLE);
        }
        adapter = new MascotaAdapter(this, listaMascotas);
        recyclerView.setAdapter(adapter);
    }
}