package com.example.pmoclase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditPersonaActivity extends AppCompatActivity {
    private TextInputEditText etNombres, etApellidos, etDocumento, etCorreo;
    private MaterialButton btnGuardar;
    private DatabaseHelper db;
    private int personaId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_persona);

        // Inicializar la base de datos
        db = new DatabaseHelper(this);

        // Inicializar componentes de UI
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etDocumento = findViewById(R.id.etDocumento);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Obtener ID de la persona a editar
        personaId = getIntent().getIntExtra("id", -1);

        if (personaId != -1) {
            // Cargar datos de la persona
            Persona persona = db.getPersona(personaId);
            if (persona != null) {
                etNombres.setText(persona.getNombres());
                etApellidos.setText(persona.getApellidos());
                etDocumento.setText(persona.getDocumento());
                etCorreo.setText(persona.getCorreo());
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
        String nombres = etNombres.getText().toString().trim();
        String apellidos = etApellidos.getText().toString().trim();
        String documento = etDocumento.getText().toString().trim();
        String correo = etCorreo.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(nombres) || TextUtils.isEmpty(apellidos) || 
            TextUtils.isEmpty(documento) || TextUtils.isEmpty(correo)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que la persona existe
        Persona persona = db.getPersona(personaId);
        if (persona == null) {
            Toast.makeText(this, "Error: Persona no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar datos
        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setDocumento(documento);
        persona.setCorreo(correo);

        int result = db.updatePersona(persona);
        if (result > 0) {
            Toast.makeText(this, "Persona actualizada exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar persona", Toast.LENGTH_SHORT).show();
        }
    }
}