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
        View view = LayoutInflater.from(context).inflate(R.layout.fila_mascota, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Mascota m = listaMascotas.get(position);

        holder.tvNombre.setText(m.getNombre());
        holder.tvEstado.setText(m.getEstado());

        // Manejo de la foto
        if (m.getFotoPath() != null && !m.getFotoPath().equals("sin_foto")) {
            File imgFile = new File(m.getFotoPath());
            if (imgFile.exists()) {
                Bitmap myBitmap = BitmapFactory.decodeFile(imgFile.getAbsolutePath());
                holder.imgMascota.setImageBitmap(myBitmap);
            } else {
                holder.imgMascota.setImageResource(android.R.drawable.ic_menu_gallery);
            }
        } else {
            holder.imgMascota.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // LÓGICA DE PERFIL (EDITAR Y BORRAR)
        if (context instanceof PerfilActivity) {
            holder.btnBorrar.setVisibility(View.VISIBLE);
            holder.btnEditar.setVisibility(View.VISIBLE);

            // Acción Editar
            holder.btnEditar.setOnClickListener(v -> {
                Intent intent = new Intent(context, ReporteActivity.class);
                intent.putExtra("MASCOTA_EDITAR", m);
                intent.putExtra("USER_EMAIL", m.getUsuarioId());
                context.startActivity(intent);
            });

            // Acción Borrar
            holder.btnBorrar.setOnClickListener(v -> {
                ((PerfilActivity) context).eliminarMascota(m.getId());
            });
        } else {
            // Si no estamos en el perfil, ocultamos ambos botones
            holder.btnBorrar.setVisibility(View.GONE);
            holder.btnEditar.setVisibility(View.GONE);
        }

        // Click para ver detalles
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleActivity.class);
            intent.putExtra("MASCOTA", m);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        ImageView imgMascota, btnBorrar, btnEditar;
        TextView tvNombre, tvEstado;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            // Estos IDs deben coincidir con el XML "fila_mascota.xml"
            imgMascota = itemView.findViewById(R.id.imgMascotaFila);
            tvNombre = itemView.findViewById(R.id.tvNombreFila);
            tvEstado = itemView.findViewById(R.id.tvEstadoFila);
            btnBorrar = itemView.findViewById(R.id.btnBorrar);
            btnEditar = itemView.findViewById(R.id.btnEditar);
        }
    }
}