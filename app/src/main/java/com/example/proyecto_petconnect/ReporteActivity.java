package com.example.proyecto_petconnect;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.widget.*;
import androidx.appcompat.app.AppCompatActivity;
import java.util.Arrays;
import java.util.List;

public class ReporteActivity extends AppCompatActivity {

    private EditText etNombre, etEspecie, etDesc;
    private Spinner spinnerEstado;
    private ImageView imgFoto;
    private DatabaseHelper db;
    private String idMascota = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reporte);

        db = new DatabaseHelper(this);
        etNombre = findViewById(R.id.etNombreMascota);
        etEspecie = findViewById(R.id.etEspecie);
        etDesc = findViewById(R.id.etDescripcion);
        spinnerEstado = findViewById(R.id.spinnerEstado);
        imgFoto = findViewById(R.id.imgFotoReporte);
        Button btnGuardar = findViewById(R.id.btnGuardar);

        // Configurar opciones del Spinner
        List<String> estados = Arrays.asList("Perdido", "Localizado", "En Acogida");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, estados);
        spinnerEstado.setAdapter(adapter);

        // Si es EDICIÓN, cargar datos
        if (getIntent().hasExtra("ID")) {
            idMascota = getIntent().getStringExtra("ID");
            etNombre.setText(getIntent().getStringExtra("NOMBRE"));
            etEspecie.setText(getIntent().getStringExtra("ESPECIE"));
            etDesc.setText(getIntent().getStringExtra("DESC"));
            int pos = estados.indexOf(getIntent().getStringExtra("ESTADO"));
            spinnerEstado.setSelection(pos);
            btnGuardar.setText("ACTUALIZAR");
        }

        findViewById(R.id.btnContenedorFoto).setOnClickListener(v -> {
            Intent i = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            startActivityForResult(i, 1);
        });

        btnGuardar.setOnClickListener(v -> {
            String n = etNombre.getText().toString();
            String e = etEspecie.getText().toString();
            String d = etDesc.getText().toString();
            String s = spinnerEstado.getSelectedItem().toString();

            if (idMascota == null) db.insertarMascota(n, e, d, s);
            else db.actualizarMascota(idMascota, n, e, d, s);

            finish();
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && data != null) {
            Bitmap bmp = (Bitmap) data.getExtras().get("data");
            imgFoto.setImageBitmap(bmp);
        }
    }
}