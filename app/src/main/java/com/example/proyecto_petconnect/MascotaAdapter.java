package com.example.proyecto_petconnect;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.io.File;
import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.ViewHolder> {

    private ArrayList<Mascota> listaMascotas;
    private Context context;

    public MascotaAdapter(Context context, ArrayList<Mascota> listaMascotas) {
        this.context = context;
        this.listaMascotas = listaMascotas;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        // Inflamos el diseño de la "tarjeta" de cada mascota
        View view = LayoutInflater.from(context).inflate(R.layout.fila_mascota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mascota m = listaMascotas.get(position);

        holder.tvNombre.setText(m.getNombre());
        holder.tvEstado.setText(m.getEstado());
        holder.tvEspecie.setText(m.getEspecie());

        // CARGAR LA FOTO REAL
        if (m.getFotoPath() != null && !m.getFotoPath().equals("sin_foto")) {
            File imgFile = new File(m.getFotoPath());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgMascota.setImageBitmap(myBitmap);
            } else {
                holder.imgMascota.setImageResource(R.drawable.perfil_placeholder);
            }
        } else {
            holder.imgMascota.setImageResource(R.drawable.perfil_placeholder);
        }

        // LÓGICA DE BORRADO (Solo si estamos en PerfilActivity)
        if (context instanceof PerfilActivity) {
            holder.btnBorrar.setVisibility(View.VISIBLE);
            holder.btnBorrar.setOnClickListener(v -> {
                ((PerfilActivity) context).eliminarMascota(m.getId());
            });
        } else {
            holder.btnBorrar.setVisibility(View.GONE);
        }

        // CLICK PARA VER DETALLES
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleActivity.class);
            intent.putExtra("MASCOTA", m); // Mascota debe ser Serializable
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMascota, btnBorrar;
        TextView tvNombre, tvEstado, tvEspecie;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            imgMascota = itemView.findViewById(R.id.imgMascotaFila);
            tvNombre = itemView.findViewById(R.id.tvNombreFila);
            tvEstado = itemView.findViewById(R.id.tvEstadoFila);
            tvEspecie = itemView.findViewById(R.id.tvEspecieFila);
            btnBorrar = itemView.findViewById(R.id.btnBorrarMascota);
        }
    }
}