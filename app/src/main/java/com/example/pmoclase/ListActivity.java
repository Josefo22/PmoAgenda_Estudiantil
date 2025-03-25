package com.example.pmoclase;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private TextView tvTitulo;
    private LinearLayout searchLayout;
    private TextInputEditText etSearch;
    private MaterialButton btnSearch;
    private DatabaseHelper db;
    private GeneralAdapter generalAdapter;
    private String tipo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);

        // Inicializar componentes
        recyclerView = findViewById(R.id.recyclerView);
        tvTitulo = findViewById(R.id.tvTitulo);
        searchLayout = findViewById(R.id.searchLayout);
        etSearch = findViewById(R.id.etSearch);
        btnSearch = findViewById(R.id.btnSearch);
        db = new DatabaseHelper(this);

        // Configurar RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Obtener tipo de lista a mostrar
        tipo = getIntent().getStringExtra("tipo");
        if (tipo != null) {
            switch (tipo) {
                case "persona":
                    tvTitulo.setText("Lista de Personas");
                    List<Persona> personas = db.getAllPersonas();
                    PersonaAdapter personaAdapter = new PersonaAdapter(this, personas);
                    recyclerView.setAdapter(personaAdapter);
                    break;
                case "catedra":
                    tvTitulo.setText("Lista de Cátedras");
                    List<Catedra> catedras = db.getAllCatedras();
                    CatedraAdapter catedraAdapter = new CatedraAdapter(this, catedras);
                    recyclerView.setAdapter(catedraAdapter);
                    break;
                case "general":
                    tvTitulo.setText("Consulta General");
                    // Combinar datos de personas y cátedras en una lista de objetos
                    List<Object> items = new ArrayList<>();
                    items.addAll(db.getAllPersonas());
                    items.addAll(db.getAllCatedras());
                    generalAdapter = new GeneralAdapter(this, items);
                    recyclerView.setAdapter(generalAdapter);
                    
                    // Mostrar el layout de búsqueda solo para consulta general
                    searchLayout.setVisibility(View.VISIBLE);
                    setupSearch();
                    break;
            }
        }
    }
    
    private void setupSearch() {
        // Configurar evento de clic del botón buscar
        btnSearch.setOnClickListener(v -> {
            String query = etSearch.getText().toString().trim();
            if (generalAdapter != null) {
                generalAdapter.filter(query);
            }
        });
        
        // Configurar evento de texto cambiado para búsqueda en tiempo real
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (generalAdapter != null) {
                    generalAdapter.filter(s.toString());
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        // Actualizar la lista cuando se regrese a esta actividad
        if (tipo != null) {
            switch (tipo) {
                case "persona":
                    List<Persona> personas = db.getAllPersonas();
                    PersonaAdapter personaAdapter = new PersonaAdapter(this, personas);
                    recyclerView.setAdapter(personaAdapter);
                    break;
                case "catedra":
                    List<Catedra> catedras = db.getAllCatedras();
                    CatedraAdapter catedraAdapter = new CatedraAdapter(this, catedras);
                    recyclerView.setAdapter(catedraAdapter);
                    break;
                case "general":
                    List<Object> items = new ArrayList<>();
                    items.addAll(db.getAllPersonas());
                    items.addAll(db.getAllCatedras());
                    if (generalAdapter != null) {
                        generalAdapter.updateData(items);
                        
                        // Mantener la búsqueda actual
                        String currentQuery = etSearch.getText().toString().trim();
                        if (!currentQuery.isEmpty()) {
                            generalAdapter.filter(currentQuery);
                        }
                    } else {
                        generalAdapter = new GeneralAdapter(this, items);
                        recyclerView.setAdapter(generalAdapter);
                    }
                    break;
            }
        }
    }
}