package com.example.proyecto_petconnect;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
// Ya no hace falta importar AppCompatActivity explícitamente porque BaseActivity lo hace
// import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

// CAMBIO 1: Heredar de BaseActivity
public class ComunidadActivity extends BaseActivity {

    private EditText etMensaje;
    private ListView lvChat;
    private MensajeAdapter adapter;
    private ArrayList<Mensaje> listaMensajes;
    private DatabaseReference dbRef;

    private String emailUsuario;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comunidad);

        // CAMBIO 2: Activar la barra de navegación marcando "Chat"
        configurarNavegacion(R.id.nav_chat);

        // --- El resto de tu lógica sigue igual ---

        // 1. Inicializar Firebase y obtener el email del usuario logueado
        dbRef = FirebaseDatabase.getInstance().getReference("chat_comunitario");

        // Esto saca el email de la sesión activa de Firebase
        if (FirebaseAuth.getInstance().getCurrentUser() != null) {
            emailUsuario = FirebaseAuth.getInstance().getCurrentUser().getEmail();
        } else {
            emailUsuario = "Usuario Anónimo";
        }

        // 2. Vincular vistas con tus IDs
        etMensaje = findViewById(R.id.etMensajeChat);
        lvChat = findViewById(R.id.lvChatComunidad);
        Button btnEnviar = findViewById(R.id.btnEnviarChat);

        // 3. Configurar Lista
        listaMensajes = new ArrayList<>();
        adapter = new MensajeAdapter(this, listaMensajes);
        lvChat.setAdapter(adapter);

        // 4. Botón Enviar con los 3 parámetros que pide tu Mensaje.java
        btnEnviar.setOnClickListener(v -> {
            String texto = etMensaje.getText().toString().trim();
            if (!texto.isEmpty()) {
                // PASAMOS: usuario, texto y el tiempo actual
                Mensaje m = new Mensaje(emailUsuario, texto, System.currentTimeMillis());

                dbRef.push().setValue(m);
                etMensaje.setText("");
            }
        });

        // 5. Escuchar mensajes nuevos (Multihilo)
        dbRef.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {
                Mensaje nuevoMensaje = snapshot.getValue(Mensaje.class);
                if (nuevoMensaje != null) {
                    runOnUiThread(() -> {
                        listaMensajes.add(nuevoMensaje);
                        adapter.notifyDataSetChanged();
                        lvChat.setSelection(listaMensajes.size() - 1);
                    });
                }
            }
            @Override public void onChildChanged(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onChildRemoved(@NonNull DataSnapshot snapshot) {}
            @Override public void onChildMoved(@NonNull DataSnapshot snapshot, @Nullable String previousChildName) {}
            @Override public void onCancelled(@NonNull DatabaseError error) {}
        });
    }
}