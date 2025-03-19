package com.example.pmoclase;

import android.os.Bundle;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private ListView listView;
    private TextView tvTitle;
    private DatabaseHelper db;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Inicializar la base de datos
        db = new DatabaseHelper(this);

        // Inicializar componentes de UI
        listView = findViewById(R.id.listView);
        tvTitle = findViewById(R.id.tvTitle);

        // Obtener el tipo de lista a mostrar
        tipo = getIntent().getStringExtra("tipo");

        if (tipo != null) {
            switch (tipo) {
                case "persona":
                    tvTitle.setText("Lista de Personas");
                    cargarPersonas();
                    break;
                case "catedra":
                    tvTitle.setText("Lista de Cátedras");
                    cargarCatedras();
                    break;
                case "general":
                    tvTitle.setText("Consulta General");
                    cargarConsultaGeneral();
                    break;
            }
        }
    }

    private void cargarPersonas() {
        List<Persona> personasList = db.getAllPersonas();
        if (personasList.size() > 0) {
            PersonaAdapter adapter = new PersonaAdapter(this, personasList);
            listView.setAdapter(adapter);
        }
    }

    private void cargarCatedras() {
        List<Catedra> catedrasList = db.getAllCatedras();
        if (catedrasList.size() > 0) {
            CatedraAdapter adapter = new CatedraAdapter(this, catedrasList);
            listView.setAdapter(adapter);
        }
    }

    private void cargarConsultaGeneral() {
        List<String> generalList = new ArrayList<>();

        // Añadir personas
        List<Persona> personasList = db.getAllPersonas();
        for (Persona persona : personasList) {
            generalList.add("PERSONA:\n" + persona.toString());
        }

        // Añadir cátedras
        List<Catedra> catedrasList = db.getAllCatedras();
        for (Catedra catedra : catedrasList) {
            generalList.add("CÁTEDRA:\n" + catedra.toString());
        }

        // Usar un adaptador genérico
        GeneralAdapter adapter = new GeneralAdapter(this, generalList);
        listView.setAdapter(adapter);
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar lista cuando se regrese a esta actividad
        if (tipo != null) {
            switch (tipo) {
                case "persona":
                    cargarPersonas();
                    break;
                case "catedra":
                    cargarCatedras();
                    break;
                case "general":
                    cargarConsultaGeneral();
                    break;
            }
        }
    }
}