package com.example.proyecto_petconnect;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

import com.google.ai.client.generativeai.GenerativeModel;
import com.google.ai.client.generativeai.java.GenerativeModelFutures;
import com.google.ai.client.generativeai.type.Content;
import com.google.ai.client.generativeai.type.GenerateContentResponse;
import com.google.common.util.concurrent.FutureCallback;
import com.google.common.util.concurrent.Futures;
import com.google.common.util.concurrent.ListenableFuture;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class AsistenteIAActivity extends AppCompatActivity {

    private EditText etConsulta;
    private TextView tvRespuesta;
    private ProgressBar progressBar;
    private GenerativeModelFutures model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistente_ia);

        etConsulta = findViewById(R.id.etConsultaIA);
        tvRespuesta = findViewById(R.id.tvRespuestaIA);
        progressBar = findViewById(R.id.pbCargandoIA);
        Button btnPreguntar = findViewById(R.id.btnPreguntarIA);

        // CONFIGURACIÓN DE GEMINI CON TU KEY
        GenerativeModel gm = new GenerativeModel("gemini-1.5-flash", "AIzaSyDUFRcV0iJ7fMxO3UGwqgKstyUPBAuxXQ4");
        model = GenerativeModelFutures.from(gm);

        btnPreguntar.setOnClickListener(v -> preguntarALaIA());
    }

    private void preguntarALaIA() {
        String textoUsuario = etConsulta.getText().toString().trim();

        if (textoUsuario.isEmpty()) {
            Toast.makeText(this, "Escribe una duda primero", Toast.LENGTH_SHORT).show();
            return;
        }

        // Instrucción de contexto para que actúe como experto
        String prompt = "Actúa como un experto veterinario y asistente de la app PetConnect. " +
                "Responde de forma clara y profesional a esta consulta: " + textoUsuario;

        tvRespuesta.setText("Buscando información...");
        progressBar.setVisibility(View.VISIBLE);

        Content content = new Content.Builder().addText(prompt).build();
        Executor executor = Executors.newSingleThreadExecutor();
        ListenableFuture<GenerateContentResponse> response = model.generateContent(content);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            Futures.addCallback(response, new FutureCallback<GenerateContentResponse>() {
                @Override
                public void onSuccess(GenerateContentResponse result) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        tvRespuesta.setText(result.getText());
                    });
                }

                @Override
                public void onFailure(Throwable t) {
                    runOnUiThread(() -> {
                        progressBar.setVisibility(View.GONE);
                        tvRespuesta.setText("Error al conectar con la IA. Revisa tu conexión.");
                    });
                }
            }, this.getMainExecutor());
        }
    }
}