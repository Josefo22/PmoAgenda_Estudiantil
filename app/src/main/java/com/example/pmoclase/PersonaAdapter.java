package com.example.pmoclase;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class PersonaAdapter extends ArrayAdapter<Persona> {
    private Context context;
    private List<Persona> personas;
    private DatabaseHelper db;

    public PersonaAdapter(Context context, List<Persona> personas) {
        super(context, R.layout.item_lista_est, personas);
        this.context = context;
        this.personas = personas;
        this.db = new DatabaseHelper(context);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.item_lista_est, parent, false);

        TextView txtTitulo = rowView.findViewById(R.id.txtTitulo);
        TextView txtDescripcion = rowView.findViewById(R.id.txtDescripcion);
        ImageButton btnEditar = rowView.findViewById(R.id.btnEditar);
        ImageButton btnEliminar = rowView.findViewById(R.id.btnEliminar);

        final Persona persona = personas.get(position);

        // Establecer datos en las vistas
        txtTitulo.setText(persona.getNombres() + " " + persona.getApellidos());
        txtDescripcion.setText("Documento: " + persona.getDocumento() + "\nCorreo: " + persona.getCorreo());

        // Configurar botón editar
        btnEditar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditPersonaActivity.class);
                intent.putExtra("id", persona.getId());
                context.startActivity(intent);
            }
        });

        // Configurar botón eliminar
        btnEliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmar eliminación");
                builder.setMessage("¿Está seguro de eliminar esta persona?");
                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        db.deletePersona(persona.getId());
                        personas.remove(position);
                        notifyDataSetChanged();
                        Toast.makeText(context, "Persona eliminada", Toast.LENGTH_SHORT).show();
                    }
                });
                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create().show();
            }
        });

        return rowView;
    }
}