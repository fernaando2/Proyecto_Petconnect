package com.example.proyecto_petconnect;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {
    private EditText etEmail, etPass;
    private ProgressBar progressBar;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // Inicializamos Firebase ANTES de cualquier lógica
        mAuth = FirebaseAuth.getInstance();

        // 1. QUITAMOS LA COMPROBACIÓN AUTOMÁTICA
        // (Como querías que SIEMPRE pida login, no ponemos el redireccionamiento aquí)

        setContentView(R.layout.activity_login);

        // 2. VINCULACIÓN DE VISTAS
        etEmail = findViewById(R.id.etEmailLogin);
        etPass = findViewById(R.id.etPassLogin);
        Button btnEntrar = findViewById(R.id.btnEntrar);
        Button btnRegistrar = findViewById(R.id.btnIrRegistro);

        // El ProgressBar es opcional, si no lo tienes en el XML no romperá la app
        progressBar = findViewById(R.id.progressBarLogin);

        // 3. LÓGICA DEL BOTÓN ENTRAR
        btnEntrar.setOnClickListener(v -> {
            String email = etEmail.getText().toString().trim();
            String pass = etPass.getText().toString().trim();

            if (email.isEmpty() || pass.isEmpty()) {
                Toast.makeText(this, "Completa los campos", Toast.LENGTH_SHORT).show();
                return;
            }

            // Mostramos carga si el ID existe
            if (progressBar != null) {
                progressBar.setVisibility(View.VISIBLE);
            }

            // Intento de Login en Firebase
            mAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener(this, task -> {
                        if (progressBar != null) {
                            progressBar.setVisibility(View.GONE);
                        }

                        if (task.isSuccessful()) {
                            // Login correcto
                            irAlHome(email);
                        } else {
                            // Si falla (contraseña mal, sin internet, etc.)
                            String error = task.getException() != null ? task.getException().getMessage() : "Error desconocido";
                            Toast.makeText(LoginActivity.this, "Fallo: " + error, Toast.LENGTH_LONG).show();
                        }
                    });
        });

        btnRegistrar.setOnClickListener(v -> {
            startActivity(new Intent(LoginActivity.this, RegistroActivity.class));
        });
    }

    private void irAlHome(String email) {
        Intent intent = new Intent(this, HomeActivity.class);
        intent.putExtra("USER_EMAIL", email);
        startActivity(intent);
        finish();
    }
}