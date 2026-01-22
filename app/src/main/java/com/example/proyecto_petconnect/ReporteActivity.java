package com.example.proyecto_petconnect;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReporteActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        EditText etNombre = findViewById(R.id.etNombreMascota);
        EditText etEspecie = findViewById(R.id.etEspecie);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = etNombre.getText().toString();
                String especie = etEspecie.getText().toString();

                if (!especie.isEmpty()) {
                    // Aquí creamos el objeto mascota
                    Mascota nuevaMascota = new Mascota(nombre, especie, "Pendiente", "Alta");

                    Toast.makeText(ReporteActivity.this, "¡Mascota " + nombre + " reportada!", Toast.LENGTH_LONG).show();
                    finish(); // Cierra esta pantalla y vuelve a la Home
                } else {
                    Toast.makeText(ReporteActivity.this, "Por favor, indica la especie", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}