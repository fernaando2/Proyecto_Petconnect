package com.example.proyecto_petconnect;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;
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
    private static final int CAMERA_PERMISSION_CODE = 101;
    private static final int CAMERA_REQUEST_CODE = 102;

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

        // Configurar Spinner
        String[] opciones = {"Perdido", "Localizado", "En Acogida"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, opciones);
        spEstado.setAdapter(adapter);

        // Botón Cámara
        findViewById(R.id.btnContenedorFoto).setOnClickListener(v -> pedirPermisosYCamara());

        // Botón Guardar
        findViewById(R.id.btnGuardar).setOnClickListener(v -> {
            String nom = etNombre.getText().toString().trim();
            if (nom.isEmpty()) {
                Toast.makeText(this, "El nombre es obligatorio", Toast.LENGTH_SHORT).show();
                return;
            }
            db.insertarMascota(nom, etEspecie.getText().toString(), etDesc.getText().toString(),
                    spEstado.getSelectedItem().toString(), pathFoto);
            finish();
        });
    }

    private void pedirPermisosYCamara() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, CAMERA_PERMISSION_CODE);
        } else {
            abrirCamara();
        }
    }

    private void abrirCamara() {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getPackageManager()) != null) {
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        } else {
            Toast.makeText(this, "No se encontró app de cámara", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == CAMERA_PERMISSION_CODE && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            abrirCamara();
        } else {
            Toast.makeText(this, "Permiso de cámara denegado", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == CAMERA_REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            imgFoto.setImageBitmap(bmp);
            pathFoto = guardarImagenPrivada(bmp);
        }
    }

    private String guardarImagenPrivada(Bitmap bitmap) {
        File directorio = new File(getExternalFilesDir(null), "mascotas");
        if (!directorio.exists()) directorio.mkdirs();
        String nombreArchivo = "pet_" + UUID.randomUUID().toString() + ".jpg";
        File archivo = new File(directorio, nombreArchivo);
        try (FileOutputStream out = new FileOutputStream(archivo)) {
            bitmap.compress(Bitmap.CompressFormat.JPEG, 90, out);
            return archivo.getAbsolutePath();
        } catch (Exception e) {
            return "";
        }
    }
}