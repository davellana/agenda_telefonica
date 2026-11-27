package com.example.agenda;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ContacteDB helper;
    private SQLiteDatabase db;

    public static ArrayList<Contacte> contactes;
    private ContacteAdapter adapter;
    private ListView lvContactes;
    private TextView txtMissatge;

    public static final int REQUEST_CONTACTE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lvContactes = findViewById(R.id.lvContactes);
        Button btnAfegir = findViewById(R.id.btnAfegir);
        txtMissatge = findViewById(R.id.txtMissatge);

        helper = new ContacteDB(this);
        db = helper.getWritableDatabase();

        contactes = new ArrayList<>();

        adapter = new ContacteAdapter(this, contactes);
        lvContactes.setAdapter(adapter);

        consultar();

        btnAfegir.setOnClickListener(v -> {
            mostrarMissatge("", false);
            Intent intent = new Intent(
                    MainActivity.this,
                    ContacteActivity.class
            );
            intent.putExtra("position", -1);
            startActivityForResult(intent, REQUEST_CONTACTE);
        });

        lvContactes.setOnItemClickListener((parent, view, position, id) -> {
            mostrarMissatge("", false);
            Intent intent = new Intent(
                    MainActivity.this,
                    ContacteActivity.class
            );
            intent.putExtra("position", position);
            startActivityForResult(intent, REQUEST_CONTACTE);
        });
    }

    // CONSULTAR CONTACTES DE LA BASE DE DADES
    private void consultar() {
        contactes.clear();

        Cursor cursor = db.rawQuery("SELECT _id, name, surname, address, number, email, birthDate FROM contactes ORDER BY _id", null);

        if (cursor.moveToFirst()) {
            do {
                Contacte contacte = new Contacte(
                        cursor.getLong(cursor.getColumnIndexOrThrow("_id")),
                        cursor.getString(cursor.getColumnIndexOrThrow("name")),
                        cursor.getString(cursor.getColumnIndexOrThrow("surname")),
                        cursor.getString(cursor.getColumnIndexOrThrow("address")),
                        cursor.getString(cursor.getColumnIndexOrThrow("number")),
                        cursor.getString(cursor.getColumnIndexOrThrow("email")),
                        cursor.getString(cursor.getColumnIndexOrThrow("birthDate"))
                );

                contactes.add(contacte);

            } while (cursor.moveToNext());
        }

        cursor.close();

        adapter.notifyDataSetChanged();
    }

    // MOSTRAR MISSATGES EN EL TEXTVIEW
    public void mostrarMissatge(String missatge, boolean esError) {
        if (txtMissatge == null) return;

        if (missatge == null || missatge.trim().isEmpty()) {
            txtMissatge.setVisibility(View.GONE);
            txtMissatge.setText("");
        } else {
            txtMissatge.setText(missatge);
            txtMissatge.setTextColor(esError ? 0xFF953636 : 0xFF255C68);
            txtMissatge.setVisibility(View.VISIBLE);
        }
    }

    // REBRE RESULTATS DE CONTACTEACTIVITY
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CONTACTE &&
                resultCode == RESULT_OK &&
                data != null) {

            boolean deleted = data.getBooleanExtra("deleted", false);
            int position = data.getIntExtra("position", -1);

            if (deleted) {
                if (position >= 0 && position < contactes.size()) {
                    Contacte contacte = contactes.get(position);
                    int deletedRows = db.delete(
                            "contactes",
                            "_id = ?",
                            new String[]{String.valueOf(contacte.getId())}
                    );
                    if (deletedRows > 0) {
                        mostrarMissatge("Contacte eliminat", false);
                    } else {
                        mostrarMissatge("Error en eliminar el contacte", true);
                    }
                }
            } else {
                String name = data.getStringExtra("name");
                String surname = data.getStringExtra("surname");
                String address = data.getStringExtra("address");
                String number = data.getStringExtra("number");
                String email = data.getStringExtra("email");
                String birthDate = data.getStringExtra("birthDate");

                ContentValues values = new ContentValues();
                values.put("name", name);
                values.put("surname", surname);
                values.put("address", address);
                values.put("number", number);
                values.put("email", email);
                values.put("birthDate", birthDate);

                if (position == -1) {
                    // Nou contacte -> INSERT a la BD
                    long newId = db.insert("contactes", null, values);
                    if (newId != -1) {
                        mostrarMissatge("Contacte afegit correctament", false);
                    } else {
                        mostrarMissatge("Error en desar el contacte a la base de dades", true);
                    }
                } else if (position >= 0 && position < contactes.size()) {
                    // Contacte existent -> UPDATE a la BD
                    Contacte contacte = contactes.get(position);
                    int updatedRows = db.update(
                            "contactes",
                            values,
                            "_id = ?",
                            new String[]{String.valueOf(contacte.getId())}
                    );
                    if (updatedRows > 0) {
                        mostrarMissatge("Contacte actualitzat correctament", false);
                    } else {
                        mostrarMissatge("Error en actualitzar el contacte", true);
                    }
                }
            }

            actualitzarLlista();
        } else {
            mostrarMissatge("", false);
        }
    }

    // ACTUALITZAR LLISTA I RECARREGAR DES DE LA BD
    private void actualitzarLlista() {
        consultar();
    }

    @Override
    protected void onDestroy() {
        if (helper != null) {
            helper.close();
        }

        super.onDestroy();
    }
}
