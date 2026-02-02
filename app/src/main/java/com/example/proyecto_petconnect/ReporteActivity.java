package com.example.proyecto_petconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import java.io.File;
import java.io.FileOutputStream;

public class ReporteActivity extends AppCompatActivity {
    private DatabaseHelper db;
    private EditText etNom, etEsp, etDes;
    private Spinner spEstado;
    private ImageView imgPreview;
    private String pathFoto = "sin_foto";
    private Mascota mascotaAEditar; // La movemos aquí para que sea accesible en toda la clase

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        db = new DatabaseHelper(this);
        etNom = findViewById(R.id.etNombreMascota);
        etEsp = findViewById(R.id.etEspecie);
        etDes = findViewById(R.id.etDescripcion);
        spEstado = findViewById(R.id.spinnerEstado);
        imgPreview = findViewById(R.id.imgPreviewReporte);
        Button btnGuardar = findViewById(R.id.btnGuardar);
        Button btnCamara = findViewById(R.id.btnAbrirCamara);

        // 1. Configurar Spinner
        String[] opciones = {"Perdido", "Localizado", "En Adopción"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);

        // 2. Comprobar si venimos de "Editar"
        mascotaAEditar = (Mascota) getIntent().getSerializableExtra("MASCOTA_EDITAR");

        if (mascotaAEditar != null) {
            etNom.setText(mascotaAEditar.getNombre());
            etEsp.setText(mascotaAEditar.getEspecie());
            etDes.setText(mascotaAEditar.getDescripcion());
            pathFoto = mascotaAEditar.getFotoPath(); // Mantenemos la foto anterior si no hace una nueva

            // Cargar preview de la foto actual si existe
            if (pathFoto != null && !pathFoto.equals("sin_foto")) {
                imgPreview.setImageBitmap(BitmapFactory.decodeFile(pathFoto));
            }

            // Seleccionar el estado correcto en el Spinner
            int position = adapter.getPosition(mascotaAEditar.getEstado());
            spEstado.setSelection(position);

            btnGuardar.setText("ACTUALIZAR REPORTE");
        }

        // 3. Lógica de la Cámara
        btnCamara.setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 101);
        });

        // 4. Lógica ÚNICA para Guardar o Actualizar
        btnGuardar.setOnClickListener(v -> {
            String nombre = etNom.getText().toString().trim();
            String especie = etEsp.getText().toString().trim();
            String desc = etDes.getText().toString().trim();
            String estado = spEstado.getSelectedItem().toString();
            String mail = getIntent().getStringExtra("USER_EMAIL");

            if (nombre.isEmpty() || especie.isEmpty()) {
                Toast.makeText(this, "Por favor, rellena los campos básicos", Toast.LENGTH_SHORT).show();
                return;
            }

            if (mascotaAEditar == null) {
                // INSERTAR NUEVA
                db.insertarMascota(nombre, especie, desc, estado, pathFoto, mail);
                Toast.makeText(this, "Mascota publicada con éxito", Toast.LENGTH_SHORT).show();
            } else {
                // ACTUALIZAR EXISTENTE
                db.actualizarMascota(mascotaAEditar.getId(), nombre, especie, desc, estado, pathFoto);
                Toast.makeText(this, "Publicación actualizada", Toast.LENGTH_SHORT).show();
            }

            finish(); // Volver atrás
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Bitmap bundle = (Bitmap) data.getExtras().get("data");
            imgPreview.setImageBitmap(bundle);
            pathFoto = guardarImagen(bundle);
        }
    }

    private String guardarImagen(Bitmap bitmap) {
        File directorio = getExternalFilesDir(null);
        File archivo = new File(directorio, "pet_" + System.currentTimeMillis() + ".jpg");
        try {
            FileOutputStream out = new FileOutputStream(archivo);
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            out.flush();
            out.close();
            return archivo.getAbsolutePath();
        } catch (Exception e) {
            e.printStackTrace();
            return "sin_foto";
        }
    }
}