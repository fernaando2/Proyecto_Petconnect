package com.example.proyecto_petconnect;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class RegistroActivity extends AppCompatActivity {
    private EditText etNom, etTel, etMail, etPass;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registro);

        db = new DatabaseHelper(this);
        etNom = findViewById(R.id.etNombreRegistro);
        etTel = findViewById(R.id.etTelefonoRegistro);
        etMail = findViewById(R.id.etEmailRegistro);
        etPass = findViewById(R.id.etPassRegistro);
        Button btnFin = findViewById(R.id.btnFinalizarRegistro);

        btnFin.setOnClickListener(v -> {
            String nom = etNom.getText().toString().trim();
            String tel = etTel.getText().toString().trim();
            String mail = etMail.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (nom.isEmpty() || mail.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa los datos", Toast.LENGTH_SHORT).show();
            } else {
                // GUARDAMOS LOS 6 ARGUMENTOS
                db.registrarUsuario(mail, nom, tel, mail, pass, "defecto");

                // PASAMOS EL EMAIL AL HOME PARA EVITAR EL CIERRE
                Intent i = new Intent(this, HomeActivity.class);
                i.putExtra("USER_EMAIL", mail);
                startActivity(i);
                finish();
            }
        });
    }
}