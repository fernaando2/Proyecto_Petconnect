package com.example.proyecto_petconnect;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class HomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_home);

        // Ajuste de márgenes para el diseño moderno
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.home), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // 1. Buscamos el botón flotante naranja
        FloatingActionButton fabAdd = findViewById(R.id.fabAnadir);

        // 2. Programamos el clic
        fabAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Por ahora solo mostramos un mensaje para probar que funciona
                Toast.makeText(HomeActivity.this, "Abriendo formulario de reporte...", Toast.LENGTH_SHORT).show();

                // Nota: Aquí en el siguiente paso pondremos el Intent para ir a la pantalla de Registro
            }
        });
    }
}