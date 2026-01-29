package com.example.proyecto_petconnect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import java.io.File;
import java.io.FileOutputStream;
import java.util.UUID;

public class ReporteActivity extends AppCompatActivity {

    private EditText etNombre, etEspecie, etDesc;
    private Spinner spEstado;
    private ImageView imgFoto;
    private String pathFoto = "";
    private DatabaseHelper db;

    // ID temporal para pruebas (En el Hito 3 vendrá del Login)
    private String miUsuarioID = "ID_PRUEBA_1";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        db = new DatabaseHelper(this);
        etNombre = findViewById(R.id.etNombreMascota);
        etEspecie = findViewById(R.id.etEspecie);
        etDesc = findViewById(R.id.etDescripcion);
        spEstado = findViewById(R.id.spinnerEstado);
        imgFoto = findViewById(R.id.imgFotoReporte);

        // Configurar opciones del Spinner
        String[] opciones = {"Perdido", "Localizado", "En Acogida"};
        spEstado.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones));

        // Botón para la Cámara
        findViewById(R.id.btnContenedorFoto).setOnClickListener(v -> comprobarPermisos());

        // Botón Guardar (Aquí estaba el fallo de los 6 argumentos)
        findViewById(R.id.btnGuardar).setOnClickListener(v -> {
            String nom = etNombre.getText().toString().trim();
            if (nom.isEmpty()) {
                Toast.makeText(this, "Ponle un nombre a la mascota", Toast.LENGTH_SHORT).show();
                return;
            }

            // LLAMADA CORREGIDA: Ahora pasamos los 6 datos (incluyendo el ID de usuario)
            db.insertarMascota(
                    nom,
                    etEspecie.getText().toString(),
                    etDesc.getText().toString(),
                    spEstado.getSelectedItem().toString(),
                    pathFoto,
                    miUsuarioID
            );

            Toast.makeText(this, "Reporte guardado con éxito", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    private void comprobarPermisos() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 101);
        } else {
            abrirCamara();
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, 102);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 102 && resultCode == RESULT_OK && data != null) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            imgFoto.setImageBitmap(bmp);
            pathFoto = guardarFotoEnMemoria(bmp);
        }
    }

    private String guardarFotoEnMemoria(Bitmap bitmap) {
        File dir = new File(getExternalFilesDir(null), "mascotas");
        if (!dir.exists()) dir.mkdirs();
        File file = new File(dir, "pet_" + UUID.randomUUID().toString() + ".jpg");
        try (FileOutputStream out = new FileOutputStream(file)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            return file.getAbsolutePath();
        } catch (Exception e) {
            return "";
        }
    }
}