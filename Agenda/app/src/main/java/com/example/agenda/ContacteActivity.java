package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class ContacteActivity extends AppCompatActivity {

    private EditText txtNom, txtCognoms, txtAdreca;
    private EditText txtNum, txtEmail, txtData;

    private Button btnConfirmar, btnCancelar, btnEliminar;

    private int position = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacte);

        txtNom = findViewById(R.id.txtNom);
        txtCognoms = findViewById(R.id.txtNCognoms);
        txtAdreca = findViewById(R.id.txtAdreca);
        txtNum = findViewById(R.id.txtNum);
        txtEmail = findViewById(R.id.txtEmail);
        txtData = findViewById(R.id.txtData);

        btnConfirmar = findViewById(R.id.btnConfirmar);
        btnCancelar = findViewById(R.id.btnCancelar);
        btnEliminar = findViewById(R.id.btn_eliminar);

        position = getIntent().getIntExtra("position", -1);

        if (position == -1) {
            btnEliminar.setVisibility(View.GONE);
        } else {
            Contacte contacte = MainActivity.contactes.get(position);

            txtNom.setText(contacte.getName());
            txtCognoms.setText(contacte.getSurname());
            txtAdreca.setText(contacte.getAddress());
            txtNum.setText(contacte.getNumber());
            txtEmail.setText(contacte.getEmail());
            txtData.setText(contacte.getBirthDate());
        }

        // Botó de confirmar.
        btnConfirmar.setOnClickListener(v -> {

            String nom = txtNom.getText().toString().trim();
            String cognoms = txtCognoms.getText().toString().trim();
            String adreca = txtAdreca.getText().toString().trim();
            String numero = txtNum.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String data = txtData.getText().toString().trim();

            if (nom.isEmpty()) {
                txtNom.setError("El nom és obligatori");
                txtNom.requestFocus();
                return;
            }

            if (numero.isEmpty()) {
                txtNum.setError("El telèfon és obligatori");
                txtNum.requestFocus();
                return;
            }

            Intent resultat = new Intent();

            resultat.putExtra("position", position);
            resultat.putExtra("name", nom);
            resultat.putExtra("surname", cognoms);
            resultat.putExtra("address", adreca);
            resultat.putExtra("number", numero);
            resultat.putExtra("email", email);
            resultat.putExtra("birthDate", data);

            setResult(RESULT_OK, resultat);
            finish();
        });

        btnCancelar.setOnClickListener(v -> {
            setResult(RESULT_CANCELED);
            finish();
        });

        btnEliminar.setOnClickListener(v -> {

            Intent resultat = new Intent();

            resultat.putExtra("position", position);
            resultat.putExtra("deleted", true);

            setResult(RESULT_OK, resultat);
            finish();
        });
    }
}