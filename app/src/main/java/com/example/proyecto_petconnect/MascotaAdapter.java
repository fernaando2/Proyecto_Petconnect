package com.example.proyecto_petconnect;

import android.content.Context;
import android.content.Intent;
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

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Mascota> lista;

    public MascotaAdapter(Context context, ArrayList<Mascota> lista) {
        this.context = context;
        this.lista = lista;
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

        // 1. Cargamos los datos básicos
        holder.nombre.setText(m.getNombre());
        holder.estado.setText(m.getEstado());

        // 2. Cargamos la foto desde el almacenamiento interno
        if (m.getFotoPath() != null && !m.getFotoPath().isEmpty()) {
            File imgFile = new File(m.getFotoPath());
            if (imgFile.exists()) {
                holder.img.setImageBitmap(BitmapFactory.decodeFile(imgFile.getAbsolutePath()));
            }
        } else {
            // Imagen por defecto si no hay foto
            holder.img.setImageResource(android.R.drawable.ic_menu_gallery);
        }

        // 3. Lógica de BORRAR: Solo visible en PerfilActivity
        if (context instanceof PerfilActivity) {
            holder.btnBorrar.setVisibility(View.VISIBLE);
            holder.btnBorrar.setOnClickListener(v -> {
                // Llamamos al método público de PerfilActivity para borrar
                ((PerfilActivity) context).eliminarMascota(m.getId());
            });
        } else {
            // En HomeActivity el botón de borrar no debe existir
            holder.btnBorrar.setVisibility(View.GONE);
        }

        // 4. Lógica de CLIC: Abrir DetalleActivity (Ficha del animal y dueño)
        holder.itemView.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetalleActivity.class);
            intent.putExtra("NOMBRE", m.getNombre());
            intent.putExtra("DESC", m.getDescripcion());
            intent.putExtra("ESTADO", m.getEstado());
            intent.putExtra("FOTO", m.getFotoPath());
            intent.putExtra("USUARIO_ID", m.getUsuarioId()); // Necesario para buscar al dueño
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return lista.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, estado;
        ImageView img;
        View btnBorrar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            // Asegúrate de que estos IDs coincidan con tu fila_mascota.xml
            nombre = itemView.findViewById(R.id.txtNombreFila);
            estado = itemView.findViewById(R.id.txtEstadoFila);
            img = itemView.findViewById(R.id.imgMascotaFila);
            btnBorrar = itemView.findViewById(R.id.btnBorrarFila);
        }
    }
}