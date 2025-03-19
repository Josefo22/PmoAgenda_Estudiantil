package com.example.pmoclase;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;

public class MainActivity extends AppCompatActivity {
    private TextInputEditText etNombres, etApellidos, etDocumento, etCorreo;
    private TextInputEditText etNombreCatedra, etHorario;
    private MaterialButton btnGuardarPersona, btnMostrarPersonas, btnGuardarCatedra, btnMostrarCatedras, btnConsultaGeneral;
    private DatabaseHelper db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializar la base de datos
        db = new DatabaseHelper(this);

        // Inicializar los componentes de UI para personas
        etNombres = findViewById(R.id.etNombres);
        etApellidos = findViewById(R.id.etApellidos);
        etDocumento = findViewById(R.id.etDocumento);
        etCorreo = findViewById(R.id.etCorreo);
        btnGuardarPersona = findViewById(R.id.btnGuardarPersona);
        btnMostrarPersonas = findViewById(R.id.btnMostrarPersonas);

        // Inicializar los componentes de UI para cátedras
        etNombreCatedra = findViewById(R.id.etNombreCatedra);
        etHorario = findViewById(R.id.etHorario);
        btnGuardarCatedra = findViewById(R.id.btnGuardarCatedra);
        btnMostrarCatedras = findViewById(R.id.btnMostrarCatedras);

        // Inicializar botón consulta general
        btnConsultaGeneral = findViewById(R.id.btnConsultaGeneral);

        // Configurar eventos para botones de personas
        btnGuardarPersona.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarPersona();
            }
        });

        btnMostrarPersonas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir ListView de personas
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("tipo", "persona");
                startActivity(intent);
            }
        });

        // Botón para ir a editar persona (nuevo)
        btnGuardarPersona.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditPersonaActivity.class);
                startActivity(intent);
                return true;
            }
        });

        // Configurar eventos para botones de cátedras
        btnGuardarCatedra.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarCatedra();
            }
        });

        btnMostrarCatedras.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir ListView de cátedras
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("tipo", "catedra");
                startActivity(intent);
            }
        });

        // Botón para ir a editar cátedra (nuevo)
        btnGuardarCatedra.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                Intent intent = new Intent(MainActivity.this, EditCatedraActivity.class);
                startActivity(intent);
                return true;
            }
        });

        // Configurar evento para botón consulta general
        btnConsultaGeneral.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Abrir ListView de consulta general
                Intent intent = new Intent(MainActivity.this, ListActivity.class);
                intent.putExtra("tipo", "general");
                startActivity(intent);
            }
        });
    }

    private void guardarPersona() {
        // Obtener valores de los campos
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

        // Crear objeto Persona
        Persona persona = new Persona();
        persona.setNombres(nombres);
        persona.setApellidos(apellidos);
        persona.setDocumento(documento);
        persona.setCorreo(correo);

        // Guardar en base de datos
        long id = db.insertPersona(persona);

        if (id > 0) {
            Toast.makeText(this, "Persona guardada exitosamente", Toast.LENGTH_SHORT).show();
            limpiarCamposPersona();
        } else {
            Toast.makeText(this, "Error al guardar persona", Toast.LENGTH_SHORT).show();
        }
    }

    private void guardarCatedra() {
        // Obtener valores de los campos
        String nombre = etNombreCatedra.getText().toString().trim();
        String horario = etHorario.getText().toString().trim();

        // Validar campos
        if (TextUtils.isEmpty(nombre) || TextUtils.isEmpty(horario)) {
            Toast.makeText(this, "Por favor complete todos los campos", Toast.LENGTH_SHORT).show();
            return;
        }

        // Crear objeto Catedra
        Catedra catedra = new Catedra();
        catedra.setNombre(nombre);
        catedra.setHorario(horario);

        // Guardar en base de datos
        long id = db.insertCatedra(catedra);

        if (id > 0) {
            Toast.makeText(this, "Cátedra guardada exitosamente", Toast.LENGTH_SHORT).show();
            limpiarCamposCatedra();
        } else {
            Toast.makeText(this, "Error al guardar cátedra", Toast.LENGTH_SHORT).show();
        }
    }

    private void limpiarCamposPersona() {
        etNombres.setText("");
        etApellidos.setText("");
        etDocumento.setText("");
        etCorreo.setText("");
        etNombres.requestFocus();
    }

    private void limpiarCamposCatedra() {
        etNombreCatedra.setText("");
        etHorario.setText("");
        etNombreCatedra.requestFocus();
    }
}