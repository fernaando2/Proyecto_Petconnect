package com.example.proyecto_petconnect;

import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        Mascota m = (Mascota) getIntent().getSerializableExtra("MASCOTA");

        if (m != null) {
            ((TextView) findViewById(R.id.tvDetalleNombre)).setText(m.getNombre());
            ((TextView) findViewById(R.id.tvDetalleEspecie)).setText(m.getEspecie());
            ((TextView) findViewById(R.id.tvDetalleDescripcion)).setText(m.getDescripcion());
            ((TextView) findViewById(R.id.tvDetalleEstado)).setText(m.getEstado());

            ImageView img = findViewById(R.id.imgDetalleMascota);
            if (m.getFotoPath() != null && !m.getFotoPath().equals("sin_foto")) {
                img.setImageBitmap(BitmapFactory.decodeFile(m.getFotoPath()));
            }
        }
    }
}