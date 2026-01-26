package com.example.proyecto_petconnect;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class MascotaAdapter extends RecyclerView.Adapter<MascotaAdapter.MyViewHolder> {

    private Context context;
    private ArrayList<Mascota> listaMascotas;
    private DatabaseHelper db;

    public MascotaAdapter(Context context, ArrayList<Mascota> listaMascotas) {
        this.context = context;
        this.listaMascotas = listaMascotas;
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
        Mascota m = listaMascotas.get(position);
        holder.nombre.setText(m.getNombre());
        holder.especie.setText(m.getEspecie());

        // BOTÓN BORRAR (D de CRUD)
        holder.btnBorrar.setOnClickListener(v -> {
            db.getWritableDatabase().delete("mascotas", "NOMBRE = ?", new String[]{m.getNombre()});
            listaMascotas.remove(position);
            notifyItemRemoved(position);
            notifyItemRangeChanged(position, listaMascotas.size());
        });
    }

    @Override
    public int getItemCount() {
        return listaMascotas.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        TextView nombre, especie;
        ImageButton btnBorrar;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            nombre = itemView.findViewById(R.id.txtNombreFila);
            especie = itemView.findViewById(R.id.txtEspecieFila);
            btnBorrar = itemView.findViewById(R.id.btnBorrarFila);
        }
    }
}