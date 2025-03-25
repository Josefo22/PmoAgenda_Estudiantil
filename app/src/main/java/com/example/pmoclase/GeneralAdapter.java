package com.example.pmoclase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;
import java.util.List;

public class GeneralAdapter extends RecyclerView.Adapter<GeneralAdapter.ViewHolder> {
    private Context context;
    private List<Object> originalItems;
    private List<Object> items;

    public GeneralAdapter(Context context, List<Object> items) {
        this.context = context;
        this.originalItems = new ArrayList<>(items);  // Guardar lista original
        this.items = items;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_general, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Object item = items.get(position);
        
        if (item instanceof Persona) {
            Persona persona = (Persona) item;
            holder.tvTitulo.setText("PERSONA: " + persona.getNombres() + " " + persona.getApellidos());
            holder.tvDescripcion.setText("Documento: " + persona.getDocumento() + "\nCorreo: " + persona.getCorreo());
        } else if (item instanceof Catedra) {
            Catedra catedra = (Catedra) item;
            holder.tvTitulo.setText("CÁTEDRA: " + catedra.getNombre());
            holder.tvDescripcion.setText("Horario: " + catedra.getHorario());
        }
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvTitulo;
        TextView tvDescripcion;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitulo = itemView.findViewById(R.id.tvTitulo);
            tvDescripcion = itemView.findViewById(R.id.tvDescripcion);
        }
    }
    
    // Método para filtrar resultados
    public void filter(String query) {
        items.clear();
        
        if (query.isEmpty()) {
            // Si la consulta está vacía, mostrar todos los elementos
            items.addAll(originalItems);
        } else {
            // Filtrar por la consulta
            String lowerCaseQuery = query.toLowerCase();
            
            for (Object item : originalItems) {
                if (item instanceof Persona) {
                    Persona persona = (Persona) item;
                    if (persona.getNombres().toLowerCase().contains(lowerCaseQuery) ||
                        persona.getApellidos().toLowerCase().contains(lowerCaseQuery) ||
                        persona.getDocumento().toLowerCase().contains(lowerCaseQuery) ||
                        persona.getCorreo().toLowerCase().contains(lowerCaseQuery)) {
                        items.add(persona);
                    }
                } else if (item instanceof Catedra) {
                    Catedra catedra = (Catedra) item;
                    if (catedra.getNombre().toLowerCase().contains(lowerCaseQuery) ||
                        catedra.getHorario().toLowerCase().contains(lowerCaseQuery)) {
                        items.add(catedra);
                    }
                }
            }
        }
        
        notifyDataSetChanged();
    }
    
    // Método para actualizar datos
    public void updateData(List<Object> newItems) {
        this.originalItems = new ArrayList<>(newItems);
        this.items.clear();
        this.items.addAll(newItems);
        notifyDataSetChanged();
    }
}