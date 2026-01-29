package com.example.proyecto_petconnect;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNom, etTel, etMail;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new DatabaseHelper(this);
        etNom = findViewById(R.id.etNombreRegistro);
        etTel = findViewById(R.id.etTelefonoRegistro);
        etMail = findViewById(R.id.etEmailRegistro);

        findViewById(R.id.btnFinalizarRegistro).setOnClickListener(v -> {
            String nom = etNom.getText().toString();
            String tel = etTel.getText().toString();
            String mail = etMail.getText().toString();

            if (!nom.isEmpty() && !tel.isEmpty()) {
                // Guardamos en la BD local con un ID único para este móvil
                db.registrarUsuario("MI_ID_LOCAL", nom, tel, mail);
                Toast.makeText(this, "Perfil creado", Toast.LENGTH_SHORT).show();
                finish();
            } else {
                Toast.makeText(this, "Rellena los campos", Toast.LENGTH_SHORT).show();
            }
        });
    }
}