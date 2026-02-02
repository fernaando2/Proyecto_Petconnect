package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity {

    private String userEmail;
    private DatabaseHelper db;
    private RecyclerView rv;
    private Spinner spinnerFiltro;
    private ArrayList<Mascota> listaMascotas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        db = new DatabaseHelper(this);

        // 1. Obtener email del usuario
        userEmail = getIntent().getStringExtra("USER_EMAIL");
        if (userEmail == null) userEmail = "invitado@mail.com";

        // 2. Vincular RecyclerView
        rv = findViewById(R.id.rvMascotasHome);
        rv.setLayoutManager(new LinearLayoutManager(this));

        // 3. Configurar Spinner de Filtro
        spinnerFiltro = findViewById(R.id.spinnerFiltroHome);
        String[] opciones = {"Todos", "Perdido", "Localizado", "En Adopción"};
        ArrayAdapter<String> adapterF = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapterF.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerFiltro.setAdapter(adapterF);

        spinnerFiltro.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                cargarMascotas(opciones[position]);
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {}
        });

        // 4. CONFIGURAR BOTTOM NAVIGATION (Reemplaza a los botones antiguos)
        com.google.android.material.bottomnavigation.BottomNavigationView bottomNav = findViewById(R.id.bottom_navigation);

        bottomNav.setOnNavigationItemSelectedListener(item -> {
            int id = item.getItemId();

            if (id == R.id.nav_mapa) {
                startActivity(new Intent(this, MapsActivity.class));
//            } else if (id == R.id.nav_chat) {
//                Intent intentChat = new Intent(this, ComunidadActivity.class);
//                intentChat.putExtra("USER_EMAIL", userEmail);
//                startActivity(intentChat);
            } else if (id == R.id.nav_ia) {
                startActivity(new Intent(this, AsistenteIAActivity.class));
            } else if (id == R.id.nav_perfil) {
                Intent intentPerfil = new Intent(this, PerfilActivity.class);
                intentPerfil.putExtra("USER_EMAIL", userEmail);
                startActivity(intentPerfil);
            }
            return true;
        });

        // 5. BOTÓN AGREGAR (Mantenemos el FAB para reportar)
        findViewById(R.id.fabAddPet).setOnClickListener(v -> {
            Intent intent = new Intent(this, ReporteActivity.class);
            intent.putExtra("USER_EMAIL", userEmail);
            startActivity(intent);
        });
    }

    // Recargar la lista cada vez que volvemos a la pantalla
    @Override
    protected void onResume() {
        super.onResume();
        if (spinnerFiltro != null) {
            cargarMascotas(spinnerFiltro.getSelectedItem().toString());
        }
    }

    private void cargarMascotas(String filtro) {
        listaMascotas = new ArrayList<>();
        Cursor c = db.obtenerMascotasFiltradas(filtro);

        if (c != null) {
            while (c.moveToNext()) {
                // El orden debe ser: Nombre(1), Especie(2), Desc(3), Estado(4), Foto(5), UID(6)
                Mascota m = new Mascota(
                        c.getString(1),
                        c.getString(2),
                        c.getString(3),
                        c.getString(4),
                        c.getString(5),
                        c.getString(6)
                );
                m.setId(c.getString(0)); // ID autoincremental es la columna 0
                listaMascotas.add(m);
            }
            c.close();
        }

        // Vincular el adaptador
        MascotaAdapter adapter = new MascotaAdapter(this, listaMascotas);
        rv.setAdapter(adapter);
    }
}