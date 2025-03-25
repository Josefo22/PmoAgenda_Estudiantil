package com.example.pmoclase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DatabaseHelper extends SQLiteOpenHelper {
    // Database Version
    private static final int DATABASE_VERSION = 1;

    // Database Name
    private static final String DATABASE_NAME = "universidad_db";

    // Table Names
    private static final String TABLE_PERSONAS = "personas";
    private static final String TABLE_CATEDRAS = "catedras";

    // Common column names
    private static final String KEY_ID = "id";

    // PERSONAS Table - column names
    private static final String KEY_NOMBRES = "nombres";
    private static final String KEY_APELLIDOS = "apellidos";
    private static final String KEY_DOCUMENTO = "documento";
    private static final String KEY_CORREO = "correo";

    // CATEDRAS Table - column names
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_HORARIO = "horario";

    // Table Create Statements
    // Personas table create statement
    private static final String CREATE_TABLE_PERSONAS = "CREATE TABLE "
            + TABLE_PERSONAS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NOMBRES + " TEXT," + KEY_APELLIDOS + " TEXT,"
            + KEY_DOCUMENTO + " TEXT," + KEY_CORREO + " TEXT" + ")";

    // Catedras table create statement
    private static final String CREATE_TABLE_CATEDRAS = "CREATE TABLE "
            + TABLE_CATEDRAS + "(" + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
            + KEY_NOMBRE + " TEXT," + KEY_HORARIO + " TEXT" + ")";

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // creating required tables
        db.execSQL(CREATE_TABLE_PERSONAS);
        db.execSQL(CREATE_TABLE_CATEDRAS);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // on upgrade drop older tables
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PERSONAS);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CATEDRAS);

        // create new tables
        onCreate(db);
    }

    // CRUD operations for Persona

    // Adding new persona
    public long insertPersona(Persona persona) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRES, persona.getNombres());
        values.put(KEY_APELLIDOS, persona.getApellidos());
        values.put(KEY_DOCUMENTO, persona.getDocumento());
        values.put(KEY_CORREO, persona.getCorreo());

        // insert row
        long id = db.insert(TABLE_PERSONAS, null, values);
        db.close();

        return id;
    }

    // Getting single persona
    public Persona getPersona(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_PERSONAS, null, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Persona persona = new Persona();
            // Populate persona object safely
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nombresIndex = cursor.getColumnIndex(KEY_NOMBRES);
            int apellidosIndex = cursor.getColumnIndex(KEY_APELLIDOS);
            int documentoIndex = cursor.getColumnIndex(KEY_DOCUMENTO);
            int correoIndex = cursor.getColumnIndex(KEY_CORREO);
            
            if (idIndex != -1) {
                persona.setId(cursor.getInt(idIndex));
            }
            if (nombresIndex != -1) {
                persona.setNombres(cursor.getString(nombresIndex));
            }
            if (apellidosIndex != -1) {
                persona.setApellidos(cursor.getString(apellidosIndex));
            }
            if (documentoIndex != -1) {
                persona.setDocumento(cursor.getString(documentoIndex));
            }
            if (correoIndex != -1) {
                persona.setCorreo(cursor.getString(correoIndex));
            }
            
            cursor.close();
            return persona;
        }
        
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    // Get all personas
    public List<Persona> getAllPersonas() {
        List<Persona> personas = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_PERSONAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            // Get column indices once
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nombresIndex = cursor.getColumnIndex(KEY_NOMBRES);
            int apellidosIndex = cursor.getColumnIndex(KEY_APELLIDOS);
            int documentoIndex = cursor.getColumnIndex(KEY_DOCUMENTO);
            int correoIndex = cursor.getColumnIndex(KEY_CORREO);
            
            do {
                Persona persona = new Persona();
                if (idIndex != -1) {
                    persona.setId(cursor.getInt(idIndex));
                }
                if (nombresIndex != -1) {
                    persona.setNombres(cursor.getString(nombresIndex));
                }
                if (apellidosIndex != -1) {
                    persona.setApellidos(cursor.getString(apellidosIndex));
                }
                if (documentoIndex != -1) {
                    persona.setDocumento(cursor.getString(documentoIndex));
                }
                if (correoIndex != -1) {
                    persona.setCorreo(cursor.getString(correoIndex));
                }

                personas.add(persona);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return personas;
    }

    // Update single persona
    public int updatePersona(Persona persona) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRES, persona.getNombres());
        values.put(KEY_APELLIDOS, persona.getApellidos());
        values.put(KEY_DOCUMENTO, persona.getDocumento());
        values.put(KEY_CORREO, persona.getCorreo());

        // updating row
        return db.update(TABLE_PERSONAS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(persona.getId())});
    }

    // Delete single persona
    public void deletePersona(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_PERSONAS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }

    // CRUD operations for Catedra

    // Adding new catedra
    public long insertCatedra(Catedra catedra) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, catedra.getNombre());
        values.put(KEY_HORARIO, catedra.getHorario());

        // insert row
        long id = db.insert(TABLE_CATEDRAS, null, values);
        db.close();

        return id;
    }

    // Getting single catedra
    public Catedra getCatedra(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_CATEDRAS, null, KEY_ID + "=?",
                new String[]{String.valueOf(id)}, null, null, null, null);

        if (cursor != null && cursor.moveToFirst()) {
            Catedra catedra = new Catedra();
            // Populate catedra object safely
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nombreIndex = cursor.getColumnIndex(KEY_NOMBRE);
            int horarioIndex = cursor.getColumnIndex(KEY_HORARIO);
            
            if (idIndex != -1) {
                catedra.setId(cursor.getInt(idIndex));
            }
            if (nombreIndex != -1) {
                catedra.setNombre(cursor.getString(nombreIndex));
            }
            if (horarioIndex != -1) {
                catedra.setHorario(cursor.getString(horarioIndex));
            }
            
            cursor.close();
            return catedra;
        }
        
        if (cursor != null) {
            cursor.close();
        }
        return null;
    }

    // Get all catedras
    public List<Catedra> getAllCatedras() {
        List<Catedra> catedras = new ArrayList<>();
        String selectQuery = "SELECT * FROM " + TABLE_CATEDRAS;

        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        // looping through all rows and adding to list
        if (cursor.moveToFirst()) {
            // Get column indices once
            int idIndex = cursor.getColumnIndex(KEY_ID);
            int nombreIndex = cursor.getColumnIndex(KEY_NOMBRE);
            int horarioIndex = cursor.getColumnIndex(KEY_HORARIO);
            
            do {
                Catedra catedra = new Catedra();
                if (idIndex != -1) {
                    catedra.setId(cursor.getInt(idIndex));
                }
                if (nombreIndex != -1) {
                    catedra.setNombre(cursor.getString(nombreIndex));
                }
                if (horarioIndex != -1) {
                    catedra.setHorario(cursor.getString(horarioIndex));
                }

                catedras.add(catedra);
            } while (cursor.moveToNext());
        }

        cursor.close();
        return catedras;
    }

    // Update single catedra
    public int updateCatedra(Catedra catedra) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(KEY_NOMBRE, catedra.getNombre());
        values.put(KEY_HORARIO, catedra.getHorario());

        // updating row
        return db.update(TABLE_CATEDRAS, values, KEY_ID + " = ?",
                new String[]{String.valueOf(catedra.getId())});
    }

    // Delete single catedra
    public void deleteCatedra(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CATEDRAS, KEY_ID + " = ?",
                new String[]{String.valueOf(id)});
        db.close();
    }
}