package com.example.pmoclase;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;

import java.util.List;

public class GeneralAdapter extends ArrayAdapter<String> {
    private Context context;
    private List<String> items;

    public GeneralAdapter(Context context, List<String> items) {
        super(context, R.layout.item_lista_est, items);
        this.context = context;
        this.items = items;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_lista_est, parent, false);

        TextView txtTitulo = rowView.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = rowView.findViewById(R.id.txtDescripcion);
        ImageButton btnEditar = rowView.findViewById(R.id.btnEditar);
        ImageButton btnEliminar = rowView.findViewById(R.id.btnEliminar);

        String item = items.get(position);

        // Determinar si es persona o cátedra
        if (item.startsWith("PERSONA:")) {
            txtTitulo.setText("PERSONA");
            txtDescripcion.setText(item.substring(8)); // Quitar "PERSONA:" del inicio
        } else if (item.startsWith("CÁTEDRA:")) {
            txtTitulo.setText("CÁTEDRA");
            txtDescripcion.setText(item.substring(8)); // Quitar "CÁTEDRA:" del inicio
        }

        // Ocultar botones en vista general
        btnEditar.setVisibility(View.GONE);
        btnEliminar.setVisibility(View.GONE);

        return rowView;
    }
}