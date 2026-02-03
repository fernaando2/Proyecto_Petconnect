package com.example.proyecto_petconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import java.util.List;

public class MensajeAdapter extends ArrayAdapter<Mensaje> {
    public MensajeAdapter(Context context, List<Mensaje> objetos) {
        super(context, 0, objetos);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Mensaje mensaje = getItem(position);

        // Usamos un diseño sencillo de Android para no complicar el XML
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(android.R.layout.simple_list_item_2, parent, false);
        }

        TextView tvUsuario = convertView.findViewById(android.R.id.text1);
        TextView tvTexto = convertView.findViewById(android.R.id.text2);

        if (mensaje != null) {
            tvUsuario.setText(mensaje.usuario); // Arriba el email
            tvTexto.setText(mensaje.texto);     // Abajo el mensaje
        }
        return convertView;
    }
}