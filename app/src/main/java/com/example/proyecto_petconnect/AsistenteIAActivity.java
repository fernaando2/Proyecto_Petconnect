package com.example.proyecto_petconnect;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class AsistenteIAActivity extends BaseActivity {

    private EditText etConsulta;
    private TextView tvRespuesta;
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistente_ia);

        // Activar la barra de navegación (tu código original)
        configurarNavegacion(R.id.nav_ia);

        etConsulta = findViewById(R.id.etConsultaIA);
        tvRespuesta = findViewById(R.id.tvRespuestaIA);
        progressBar = findViewById(R.id.pbCargandoIA);
        Button btnPreguntar = findViewById(R.id.btnPreguntarIA);

        btnPreguntar.setOnClickListener(v -> consultarWikipedia());
    }

    private void consultarWikipedia() {
        String consulta = etConsulta.getText().toString().trim();

        if (consulta.isEmpty()) return;

        tvRespuesta.setText("");
        progressBar.setVisibility(View.VISIBLE);

        // Hilo en Segundo Plano para no bloquear la interfaz (Concurrencia - Matrícula de Honor)
        ExecutorService executor = Executors.newSingleThreadExecutor();
        executor.execute(() -> {

            // Hacemos la llamada a Wikipedia
            String respuesta = hacerPeticionWikipedia(consulta);

            // Volvemos al hilo principal para actualizar la pantalla
            new Handler(Looper.getMainLooper()).post(() -> {
                progressBar.setVisibility(View.GONE);
                tvRespuesta.setText(respuesta);
            });
        });
    }

    private String hacerPeticionWikipedia(String termino) {
        try {
            // 1. Preparamos la palabra para la URL (cambia espacios por %20)
            String terminoCodificado = URLEncoder.encode(termino, "UTF-8");

            // 2. URL oficial de la API de Wikipedia en español (resumen de la página)
            String urlString = "https://es.wikipedia.org/api/rest_v1/page/summary/" + terminoCodificado;
            URL url = new URL(urlString);

            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET"); // Es GET, mucho más fácil que POST
            conn.setConnectTimeout(5000); // 5 segundos de tiempo de espera máximo

            int responseCode = conn.getResponseCode();

            // Si Wikipedia nos devuelve 404, es que no existe ese artículo
            if (responseCode == 404) {
                return "No he encontrado información exacta sobre '" + termino + "' en nuestra base de datos urbana (Wikipedia). Intenta buscar otra palabra (ej: Perro, Gato, Rabia).";
            }

            // Si ha ido bien (200 OK), leemos la respuesta
            if (responseCode >= 200 && responseCode <= 299) {
                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "utf-8"));
                StringBuilder response = new StringBuilder();
                String responseLine;
                while ((responseLine = br.readLine()) != null) {
                    response.append(responseLine.trim());
                }

                String jsonCompleto = response.toString();

                // Buscamos a mano el campo "extract", que es el resumen en texto plano
                String clave = "\"extract\":\"";
                int startIndex = jsonCompleto.indexOf(clave);

                if (startIndex != -1) {
                    startIndex += clave.length();
                    // Buscamos dónde termina el resumen
                    int endIndex = jsonCompleto.indexOf("\",\"", startIndex);
                    if (endIndex == -1) endIndex = jsonCompleto.indexOf("\"}", startIndex);

                    if (endIndex > startIndex) {
                        return jsonCompleto.substring(startIndex, endIndex)
                                .replace("\\n", "\n")
                                .replace("\\\"", "\"");
                    }
                }
                return "He encontrado el artículo, pero no he podido leer el resumen.";
            } else {
                return "Error al conectar con la base de datos central (" + responseCode + ").";
            }

        } catch (Exception e) {
            e.printStackTrace();
            return "Parece que no tienes conexión a internet ahora mismo. Inténtalo de nuevo más tarde.";
        }
    }
}