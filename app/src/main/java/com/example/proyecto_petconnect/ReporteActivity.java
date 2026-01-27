package com.example.proyecto_petconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class ReporteActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private EditText etNombre, etEspecie, etDesc;
    private ImageView imgFoto;
    private FrameLayout btnFotoContenedor;
    private DatabaseHelper miBD;
    private String idMascota = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        miBD = new DatabaseHelper(this);
        etNombre = findViewById(R.id.etNombreMascota);
        etEspecie = findViewById(R.id.etEspecie);
        etDesc = findViewById(R.id.etDescripcion);
        imgFoto = findViewById(R.id.imgFotoReporte);
        btnFotoContenedor = findViewById(R.id.btnContenedorFoto);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // MODO EDICIÓN: Cargar datos si existen
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            idMascota = extras.getString("ID");
            etNombre.setText(extras.getString("NOMBRE"));
            etEspecie.setText(extras.getString("ESPECIE"));
            etDesc.setText(extras.getString("DESC"));
            btnGuardar.setText("ACTUALIZAR REPORTE");
        }

        // ACCIÓN DE LA CÁMARA (Al pulsar la imagen o la camarita)
        btnFotoContenedor.setOnClickListener(v -> {
            Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            try {
                startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            } catch (Exception e) {
                Toast.makeText(this, "Error al abrir la cámara", Toast.LENGTH_SHORT).show();
            }
        });

        btnGuardar.setOnClickListener(v -> {
            String n = etNombre.getText().toString();
            String e = etEspecie.getText().toString();
            String d = etDesc.getText().toString();

            if (idMascota == null) {
                miBD.insertarMascota(n, e, d);
                Toast.makeText(this, "Mascota registrada", Toast.LENGTH_SHORT).show();
            } else {
                miBD.actualizarMascota(idMascota, n, e, d);
                Toast.makeText(this, "Mascota actualizada", Toast.LENGTH_SHORT).show();
            }
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            Bundle extras = data.getExtras();
            Bitmap imageBitmap = (Bitmap) extras.get("data");
            imgFoto.setImageBitmap(imageBitmap);
        }
    }
}