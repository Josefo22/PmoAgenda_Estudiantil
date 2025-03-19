package com.example.pmoclase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class EditCatedraActivity extends AppCompatActivity {
    private EditText etDocumento, etNombre, etApellido;
    private Button btnGuardar;
    private TextView tvTitle;
    private DatabaseHelper db;
    private int catedraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_catedra);

        // Inicializar la base de datos
        db = new DatabaseHelper(this);

        // Inicializar componentes de UI
        tvTitle = findViewById(R.id.tvTitle);
        etDocumento = findViewById(R.id.etDocumento);
        etNombre = findViewById(R.id.etNombre);
        etApellido = findViewById(R.id.etApellido);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Cambiar título
        tvTitle.setText("Editar Cátedra");

        // Obtener ID de la cátedra a editar
        catedraId = getIntent().getIntExtra("id", -1);

        if (catedraId != -1) {
            // Cargar datos de la cátedra
            Catedra catedra = db.getCatedra(catedraId);
            if (catedra != null) {
                etDocumento.setText(String.valueOf(catedra.getId()));
                etNombre.setText(catedra.getNombre());
                etApellido.setText(catedra.getHorario());
            }
        }

        btnGuardar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCatedra();
            }
        });
    }

    private void guardarCatedra() {
        String nombre = etNombre.getText().toString().trim();
        String horario = etApellido.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(horario)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        Catedra catedra = db.getCatedra(catedraId);
        catedra.setNombre(nombre);
        catedra.setHorario(horario);

        int result = db.updateCatedra(catedra);
        if (result > 0) {
            Toast.makeText(this, "Cátedra actualizada exitosamente", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            Toast.makeText(this, "Error al actualizar cátedra", Toast.LENGTH_SHORT).show();
        }
    }
}