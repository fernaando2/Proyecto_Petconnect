package com.example.proyecto_petconnect;

import android.content.Intent;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {

    private DatabaseHelper db; // Declaramos la base de datos
    private Mascota m; // La mascota que recibimos

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        // Inicializamos el helper de la base de datos
        db = new DatabaseHelper(this);

        // Recuperamos el objeto Mascota enviado por el Intent
        m = (Mascota) getIntent().getSerializableExtra("MASCOTA");

        if (m != null) {
            // Rellenamos los campos de texto
            ((TextView) findViewById(R.id.tvDetalleNombre)).setText(m.getNombre());
            ((TextView) findViewById(R.id.tvDetalleEspecie)).setText(m.getEspecie());
            ((TextView) findViewById(R.id.tvDetalleDescripcion)).setText(m.getDescripcion());
            ((TextView) findViewById(R.id.tvDetalleEstado)).setText(m.getEstado());

            // Cargamos la imagen si existe
            ImageView img = findViewById(R.id.imgDetalleMascota);
            if (m.getFotoPath() != null && !m.getFotoPath().equals("sin_foto")) {
                img.setImageBitmap(BitmapFactory.decodeFile(m.getFotoPath()));
            }

            // Configuración del botón de contacto
            findViewById(R.id.btnContactar).setOnClickListener(v -> {
                // Buscamos el teléfono del autor usando su ID (email)
                String telefono = db.obtenerTelefonoUsuario(m.getUsuarioId());

                if (telefono != null && !telefono.isEmpty()) {
                    String mensaje = "Hola, he visto tu publicación de " + m.getNombre() + " en PetConnect y me gustaría ayudar.";

                    try {
                        // Intentamos abrir WhatsApp
                        String url = "https://api.whatsapp.com/send?phone=" + telefono + "&text=" + Uri.encode(mensaje);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        intent.setData(Uri.parse(url));
                        startActivity(intent);
                    } catch (Exception e) {
                        // Si WhatsApp no está instalado, abrimos el marcador de teléfono
                        Intent intent = new Intent(Intent.ACTION_DIAL);
                        intent.setData(Uri.parse("tel:" + telefono));
                        startActivity(intent);
                    }
                } else {
                    Toast.makeText(this, "El autor no tiene un teléfono registrado.", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }
}