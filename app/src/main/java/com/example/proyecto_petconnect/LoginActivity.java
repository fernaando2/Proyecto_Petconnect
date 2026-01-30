package com.example.proyecto_petconnect;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPass;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        db = new DatabaseHelper(this);
        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etPassLogin);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnRegistrar = findViewById(R.id.btnIrRegistro);

        btnEntrar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            Cursor cursor = db.login(email, pass);
            if (cursor != null && cursor.moveToFirst()) {
                Intent intent = new Intent(this, HomeActivity.class);
                intent.putExtra("USER_EMAIL", email);
                startActivity(intent);
                finish();
                cursor.close();
            } else {
                Toast.makeText(this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
            }
        });

        btnRegistrar.setOnClickListener(v -> startActivity(new Intent(this, RegistroActivity.class)));
    }
}