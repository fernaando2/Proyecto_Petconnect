package com.example.proyecto_petconnect;

import android.content.Intent;
import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class BaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // Este método lo llamaremos desde todas tus pantallas
    protected void configurarNavegacion(int idItemActual) {
        BottomNavigationView nav = findViewById(R.id.bottom_navigation);

        if (nav == null) return; // Protección por si se te olvida poner el include en el XML

        // 1. Marca visualmente el botón de la pantalla actual
        nav.setSelectedItemId(idItemActual);

        // 2. Escucha los clics
        nav.setOnItemSelectedListener(item -> {
            int id = item.getItemId();

            // Si pulsas el botón de la pantalla donde ya estás, no hace nada
            if (id == idItemActual) return true;

            Intent intent = null;

            if (id == R.id.nav_mapa) {
                intent = new Intent(this, MapsActivity.class);
            } else if (id == R.id.nav_chat) {
                intent = new Intent(this, ComunidadActivity.class);
            } else if (id == R.id.nav_ia) {
                // Asegúrate de que tu actividad se llame así
                intent = new Intent(this, AsistenteIAActivity.class);
            } else if (id == R.id.nav_perfil) {
                intent = new Intent(this, PerfilActivity.class);
            }

            if (intent != null) {
                startActivity(intent);
                // TRUCO PRO: Elimina la animación de cambio para que parezca fijo
                overridePendingTransition(0, 0);
                finish(); // Cierra la anterior para no acumular pantallas
                return true;
            }
            return false;
        });
    }
}