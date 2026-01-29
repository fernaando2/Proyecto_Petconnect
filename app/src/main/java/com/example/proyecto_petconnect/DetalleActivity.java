package com.example.proyecto_petconnect;

import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class DetalleActivity extends AppCompatActivity {
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalle);

        db = new DatabaseHelper(this);

        ImageView img = findViewById(R.id.imgDetalle);
        TextView txtNombre = findViewById(R.id.txtDetalleNombre);
        TextView txtInfo = findViewById(R.id.txtDetalleInfo);
        TextView txtDueño = findViewById(R.id.txtNombreDueño);
        TextView txtTel = findViewById(R.id.txtTelDueño);
        TextView txtMail = findViewById(R.id.txtMailDueño);

        // Datos de la mascota
        txtNombre.setText(getIntent().getStringExtra("NOMBRE"));
        txtInfo.setText(getIntent().getStringExtra("DESC"));
        String path = getIntent().getStringExtra("FOTO");
        if (path != null) img.setImageBitmap(BitmapFactory.decodeFile(path));

        // Buscar datos del dueño por ID
        String uid = getIntent().getStringExtra("USUARIO_ID");
        Cursor c = db.obtenerUsuario(uid);
        if (c.moveToFirst()) {
            txtDueño.setText(c.getString(1)); // Nombre
            txtTel.setText("Teléfono: " + c.getString(2));
            txtMail.setText("Email: " + c.getString(3));
        }
        c.close();
    }
}