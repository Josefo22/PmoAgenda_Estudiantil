package com.example.pmoclase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import java.util.List;

public class PersonaAdapter extends RecyclerView.Adapter<PersonaAdapter.ViewHolder> {
    private Context context;
    private List<Persona> personas;
    private DatabaseHelper db;

    public PersonaAdapter(Context context, List<Persona> personas) {
        this.context = context;
        this.personas = personas;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lista_est, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Persona persona = personas.get(position);

        holder.tvNombre.setText(persona.getNombres() + " " + persona.getApellidos());
        holder.tvDocumento.setText("Documento: " + persona.getDocumento());
        holder.tvCorreo.setText("Correo: " + persona.getCorreo());

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditPersonaActivity.class);
            intent.putExtra("id", persona.getId());
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmar eliminación");
            builder.setMessage("¿Está seguro de eliminar esta persona?");
            builder.setPositiveButton("Sí", (dialog, which) -> {
                int position1 = holder.getAdapterPosition();
                if (position1 != RecyclerView.NO_POSITION) {
                    db.deletePersona(persona.getId());
                    personas.remove(position1);
                    notifyItemRemoved(position1);
                    Toast.makeText(context, "Persona eliminada", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return personas.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombre;
        TextView tvDocumento;
        TextView tvCorreo;
        MaterialButton btnEditar;
        MaterialButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombre = itemView.findViewById(R.id.tvNombre);
            tvDocumento = itemView.findViewById(R.id.tvDocumento);
            tvCorreo = itemView.findViewById(R.id.tvCorreo);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}