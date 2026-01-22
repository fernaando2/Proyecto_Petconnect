package com.example.proyecto_petconnect;

import android.content.Intent; // Importante para cambiar de pantalla
import android.os.Bundle;
import android.view.View;
import android.widget.Button; // Importante para el botón

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Ajuste de márgenes para que no se pise con la barra de estado del móvil
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        // --- AQUÍ EMPIEZA NUESTRO CÓDIGO ---

        // 1. Buscamos el botón por el ID que pusimos en el XML (btnLogin)
        Button btnEntrar = findViewById(R.id.btnLogin);

        // 2. Programamos qué pasa cuando el usuario hace clic
        btnEntrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // 3. Creamos el Intent para ir de esta pantalla (MainActivity) a la otra (HomeActivity)
                Intent intent = new Intent(MainActivity.this, HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}