package com.example.pmoclase;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class EditCatedraActivity extends AppCompatActivity {
    private TextInputEditText etNombreCatedra, etHorario;
    private MaterialButton btnGuardar;
    private DatabaseHelper db;
    private int catedraId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_catedra);

        // Inicializar la base de datos
        db = new DatabaseHelper(this);

        // Inicializar componentes de UI
        etNombreCatedra = findViewById(R.id.etNombreCatedra);
        etHorario = findViewById(R.id.etHorario);
        btnGuardar = findViewById(R.id.btnGuardar);

        // Obtener ID de la cátedra a editar
        catedraId = getIntent().getIntExtra("id", -1);

        if (catedraId != -1) {
            // Cargar datos de la cátedra
            Catedra catedra = db.getCatedra(catedraId);
            if (catedra != null) {
                etNombreCatedra.setText(catedra.getNombre());
                etHorario.setText(catedra.getHorario());
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
        String nombre = etNombreCatedra.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(horario)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Verificar que la cátedra existe
        Catedra catedra = db.getCatedra(catedraId);
        if (catedra == null) {
            Toast.makeText(this, "Error: Cátedra no encontrada", Toast.LENGTH_SHORT).show();
            return;
        }

        // Actualizar datos
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