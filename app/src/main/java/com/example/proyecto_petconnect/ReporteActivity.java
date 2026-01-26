package com.example.proyecto_petconnect;

import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReporteActivity extends AppCompatActivity {

    DatabaseHelper miBD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        miBD = new DatabaseHelper(this);
        EditText etNombre = findViewById(R.id.etNombreMascota);
        EditText etEspecie = findViewById(R.id.etEspecie);
        EditText etDesc = findViewById(R.id.etDescripcion);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        btnGuardar.setOnClickListener(v -> {
            String nombre = etNombre.getText().toString();
            String especie = etEspecie.getText().toString();
            String desc = etDesc.getText().toString();

            if (nombre.isEmpty() || especie.isEmpty()) {
                Toast.makeText(this, "Rellena nombre y especie", Toast.LENGTH_SHORT).show();
                return;
            }

            // BUSCAR SI EXISTE PARA ACTUALIZAR (UPDATE) O CREAR (CREATE)
            Cursor cursor = miBD.obtenerTodasLasMascotas();
            boolean existe = false;
            String idEncontrado = "";

            while (cursor.moveToNext()) {
                if (cursor.getString(1).equalsIgnoreCase(nombre)) {
                    existe = true;
                    idEncontrado = cursor.getString(0);
                    break;
                }
            }

            if (existe) {
                miBD.actualizarMascota(idEncontrado, nombre, especie, desc);
                Toast.makeText(this, "Reporte actualizado", Toast.LENGTH_SHORT).show();
            } else {
                miBD.insertarMascota(nombre, especie, desc);
                Toast.makeText(this, "Reporte creado", Toast.LENGTH_SHORT).show();
            }
            finish(); // Volver a Home
        });
    }
}