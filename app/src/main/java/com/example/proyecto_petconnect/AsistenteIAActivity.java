package com.example.proyecto_petconnect;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
// Ya no necesitamos importar AppCompatActivity explícitamente
// import androidx.appcompat.app.AppCompatActivity;
import java.util.HashMap;
import java.util.Map;

// CAMBIO 1: Heredar de BaseActivity
public class AsistenteIAActivity extends BaseActivity {

    private EditText etConsulta;
    private TextView tvRespuesta;
    private ProgressBar progressBar;
    private Map<String, String> baseDeConocimientos;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_asistente_ia);

        // CAMBIO 2: Activar la barra de navegación marcando "IA"
        configurarNavegacion(R.id.nav_ia);

        etConsulta = findViewById(R.id.etConsultaIA);
        tvRespuesta = findViewById(R.id.tvRespuestaIA);
        progressBar = findViewById(R.id.pbCargandoIA);
        Button btnPreguntar = findViewById(R.id.btnPreguntarIA);

        // Llenamos la "memoria" de la IA
        cargarConocimientos();

        // Usamos una expresión lambda para procesar la consulta
        btnPreguntar.setOnClickListener(v -> procesarConsultaLocal());
    }

    private void cargarConocimientos() {
        baseDeConocimientos = new HashMap<>();
        // Categoría: Salud
        baseDeConocimientos.put("vacuna", "Las vacunas esenciales son la polivalente y la de la rabia. Consulta el calendario con tu veterinario.");
        baseDeConocimientos.put("fiebre", "Si notas su nariz seca y caliente, podría tener fiebre. La temperatura normal es de 38-39°C.");
        baseDeConocimientos.put("vomito", "Si ha vomitado una vez, retira comida 12h. Si persiste, acude urgente al veterinario.");
        baseDeConocimientos.put("garrapata", "Retírala con pinzas con cuidado de no dejar la cabeza dentro y desinfecta la zona.");

        // Categoría: Alimentación
        baseDeConocimientos.put("comida", "La mejor dieta depende de la edad y raza. Asegúrate de que el primer ingrediente sea proteína animal.");
        baseDeConocimientos.put("chocolate", "¡CUIDADO! El chocolate es tóxico para perros y gatos. Acude al veterinario de inmediato.");
        baseDeConocimientos.put("agua", "Tu mascota siempre debe tener agua fresca disponible, especialmente en verano.");

        // Categoría: Comportamiento
        baseDeConocimientos.put("ladra", "Los ladridos excesivos pueden ser por ansiedad o aburrimiento. Intenta aumentar sus paseos.");
        baseDeConocimientos.put("muerde", "Si es cachorro, es normal. Usa juguetes mordedores para redirigir su conducta.");

        // Categoría: App PetConnect
        baseDeConocimientos.put("perfil", "En tu perfil puedes ver tus mascotas reportadas y cerrar tu sesión.");
        baseDeConocimientos.put("mapa", "El mapa muestra las ubicaciones de mascotas perdidas y encontradas cerca de ti.");
    }

    private void procesarConsultaLocal() {
        String consulta = etConsulta.getText().toString().toLowerCase().trim();

        if (consulta.isEmpty()) return;

        tvRespuesta.setText("");
        progressBar.setVisibility(View.VISIBLE);

        // Simulamos un retraso de "procesamiento" para que parezca que busca en la nube
        new Handler().postDelayed(() -> {
            progressBar.setVisibility(View.GONE);
            String respuestaEncontrada = "Lo siento, no tengo información específica sobre eso. ¿Puedes intentar con palabras como 'vacunas', 'comida' o 'fiebre'?";

            // Buscamos si alguna palabra clave está en la frase del usuario
            for (String clave : baseDeConocimientos.keySet()) {
                if (consulta.contains(clave)) {
                    respuestaEncontrada = baseDeConocimientos.get(clave);
                    break;
                }
            }
            tvRespuesta.setText(respuestaEncontrada);
        }, 1500); // 1.5 segundos de espera para simular "pensamiento"
    }
}