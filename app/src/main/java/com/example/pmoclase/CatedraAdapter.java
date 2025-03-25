package com.example.pmoclase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
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

public class CatedraAdapter extends RecyclerView.Adapter<CatedraAdapter.ViewHolder> {
    private Context context;
    private List<Catedra> catedras;
    private DatabaseHelper db;

    public CatedraAdapter(Context context, List<Catedra> catedras) {
        this.context = context;
        this.catedras = catedras;
        this.db = new DatabaseHelper(context);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_lista_cat, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Catedra catedra = catedras.get(position);

        holder.tvNombreCatedra.setText(catedra.getNombre());
        holder.tvHorario.setText("Horario: " + catedra.getHorario());

        holder.btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, EditCatedraActivity.class);
            intent.putExtra("id", catedra.getId());
            context.startActivity(intent);
        });

        holder.btnEliminar.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(context);
            builder.setTitle("Confirmar eliminación");
            builder.setMessage("¿Está seguro de eliminar esta cátedra?");
            builder.setPositiveButton("Sí", (dialog, which) -> {
                int position1 = holder.getAdapterPosition();
                if (position1 != RecyclerView.NO_POSITION) {
                    db.deleteCatedra(catedra.getId());
                    catedras.remove(position1);
                    notifyItemRemoved(position1);
                    Toast.makeText(context, "Cátedra eliminada", Toast.LENGTH_SHORT).show();
                }
            });
            builder.setNegativeButton("No", (dialog, which) -> dialog.dismiss());
            builder.create().show();
        });
    }

    @Override
    public int getItemCount() {
        return catedras.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvNombreCatedra;
        TextView tvHorario;
        MaterialButton btnEditar;
        MaterialButton btnEliminar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvNombreCatedra = itemView.findViewById(R.id.tvNombreCatedra);
            tvHorario = itemView.findViewById(R.id.tvHorario);
            btnEditar = itemView.findViewById(R.id.btnEditar);
            btnEliminar = itemView.findViewById(R.id.btnEliminar);
        }
    }
}