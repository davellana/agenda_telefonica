
package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
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

            btnEliminar.setVisibility(android.view.View.GONE);

        } else {

            // Carreguem les dades del contacte.
            // Les dades s'envien des de MainActivity mitjançant un ArrayList estàtic.
            Contacte contacte = MainActivity.contactes.get(position);
            txtNom.setText(contacte.getName());
            txtCognoms.setText(contacte.getSurname());
            txtAdreca.setText(contacte.getAddress());
            txtNum.setText(contacte.getNumber());
            txtEmail.setText(contacte.getEmail());
            txtData.setText(contacte.getBirthDate());

        }

        btnConfirmar.setOnClickListener(v -> {

            String nom = txtNom.getText().toString().trim();
            String cognoms = txtCognoms.getText().toString().trim();
            String adreca = txtAdreca.getText().toString().trim();
            String numero = txtNum.getText().toString().trim();
            String email = txtEmail.getText().toString().trim();
            String data = txtData.getText().toString().trim();

            if (nom.isEmpty() || numero.isEmpty()) {
                txtNom.setError("El nom és obligatori");
                return;
            }

            Intent result = new Intent();

            result.putExtra("position", position);
            result.putExtra("name", nom);
            result.putExtra("surname", cognoms);
            result.putExtra("address", adreca);
            result.putExtra("number", numero);
            result.putExtra("email", email);
            result.putExtra("birthDate", data);

            setResult(RESULT_OK, result);
            finish();

        });

        btnCancelar.setOnClickListener(v -> finish());

        btnEliminar.setOnClickListener(v -> {

            Intent result = new Intent();
            result.putExtra("position", position);
            result.putExtra("deleted", true);

            setResult(RESULT_OK, result);
            finish();

        });
    }
}