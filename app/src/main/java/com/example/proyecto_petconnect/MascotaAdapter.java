package com.example.proyecto_petconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Mascota> lista;
    private DatabaseHelper db;

    public MascotaAdapter(Context context, ArrayList<Mascota> lista) {
        this.context = context;
        this.lista = lista;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(context).inflate(R.layout.fila_mascota, parent, false);
        return new MyViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Mascota m = lista.get(position);
        holder.nombre.setText(m.getNombre());
        holder.especie.setText(m.getEspecie());
        holder.estado.setText(m.getEstado().toUpperCase());

        // Cambio de color según estado
        if (m.getEstado().equalsIgnoreCase("Perdido")) {
            holder.estado.setTextColor(Color.RED);
        } else if (m.getEstado().equalsIgnoreCase("Localizado")) {
            holder.estado.setTextColor(Color.parseColor("#2E7D32")); // Verde oscuro
        } else {
            holder.estado.setTextColor(Color.BLUE);
        }

        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, ReporteActivity.class);
            intent.putExtra("ID", m.getId());
            intent.putExtra("NOMBRE", m.getNombre());
            intent.putExtra("ESPECIE", m.getEspecie());
            intent.putExtra("DESC", m.getDescripcion());
            intent.putExtra("ESTADO", m.getEstado());
            context.startActivity(intent);
        });

        holder.btnBorrar.setOnClickListener(v -> {
            db.borrarMascota(m.getId());
            lista.remove(position);
            notifyItemRemoved(position);
        });
    }

    @Override
    public int getItemCount() { return lista.size(); }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, especie, estado;
        ImageView imgMascota;
        ImageButton btnBorrar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreFila);
            especie = itemView.findViewById(R.id.txtEspecieFila);
            estado = itemView.findViewById(R.id.txtEstadoFila);
            imgMascota = itemView.findViewById(R.id.imgMascotaFila);
            btnBorrar = itemView.findViewById(R.id.btnBorrarFila);
        }
    }
}