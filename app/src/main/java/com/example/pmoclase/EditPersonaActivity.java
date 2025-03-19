package com.example.pmoclase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditPersonaActivity extends AppCompatActivity {
    private EditText etDocumento, etNombre, etApellido;
    private Button btnGuardar;
    private TextView tvTitle;
    private DatabaseHelper db;
    private int personaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_persona);

        // Inicializar la base de datos
        db = new DatabaseHelper(this);

        // Inicializar componentes de UI
        tvTitle = findViewById(R.id.tvTitle);
        etDocumento = findViewById(R.id.etDocumento);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Cambiar título
        tvTitle.setText("Editar Persona");

        // Obtener ID de la persona a editar
        personaId = getIntent().getIntExtra("id", -1);

        if (personaId != -1) {
            // Cargar datos de la persona
            Persona persona = db.getPersona(personaId);
            if (persona != null) {
                etDocumento.setText(persona.getDocumento());
                etNombre.setText(persona.getNombres());
                etApellido.setText(persona.getApellidos());
            }
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPersona();
            }
        });
    }

    private void guardarPersona() {
        String documento = etDocumento.getText().toString().trim();
        String nombres = etNombre.getText().toString().trim();
        String apellidos = etApellido.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(documento) || TextUtils.isEmpty(nombres) || TextUtils.isEmpty(apellidos)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Persona persona = db.getPersona(personaId);
        persona.setDocumento(documento);
        persona.setNombres(nombres);
        persona.setApellidos(apellidos);

        int result = db.updatePersona(persona);
        if (result > 0) {
            Toast.makeText(this, "Persona actualizada exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar persona", Toast.LENGTH_SHORT).show();
        }
    }
}