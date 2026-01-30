package com.example.proyecto_petconnect;

import android.content.Intent;
import android.graphics.Bitmap;
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

        // Configurar Spinner
        String[] opciones = {"Perdido", "Localizado", "En Adopción"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, opciones);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spEstado.setAdapter(adapter);

        findViewById(R.id.btnAbrirCamara).setOnClickListener(v -> {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(intent, 101);
        });

        findViewById(R.id.btnGuardar).setOnClickListener(v -> {
            String mail = getIntent().getStringExtra("USER_EMAIL");
            db.insertarMascota(etNom.getText().toString(), etEsp.getText().toString(),
                    etDes.getText().toString(), spEstado.getSelectedItem().toString(), pathFoto, mail);
            Toast.makeText(this, "Mascota publicada", Toast.LENGTH_SHORT).show();
            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            Bitmap bundle = (Bitmap) data.getExtras().get("data");
            imgPreview.setImageBitmap(bundle);
            // Guardamos la foto en el móvil y obtenemos la ruta real
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