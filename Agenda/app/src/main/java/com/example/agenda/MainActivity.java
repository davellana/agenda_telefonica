
package com.example.agenda;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static ArrayList<Contacte> contactes;
    private ContacteAdapter adapter;
    private ListView lvContactes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        lvContactes = findViewById(R.id.lvContactes);
        Button btnAfegir = findViewById(R.id.btnAfegir);

        contactes = new ArrayList<>();

        adapter = new ContacteAdapter(this, contactes);
        lvContactes.setAdapter(adapter);

        btnAfegir.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ContacteActivity.class);
            startActivityForResult(intent, 1);

        });

        lvContactes.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(MainActivity.this, ContacteActivity.class);
            intent.putExtra("position", position);
            startActivityForResult(intent, 1);

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {

            int position = data.getIntExtra("position", -1);

            if (data.getBooleanExtra("deleted", false)) {

                if (position >= 0 && position < contactes.size()) {
                    contactes.remove(position);
                }

            } else if (position == -1) {

                Contacte nouContacte = new Contacte(
                        data.getStringExtra("name"),
                        data.getStringExtra("surname"),
                        data.getStringExtra("address"),
                        data.getStringExtra("number"),
                        data.getStringExtra("email"),
                        data.getStringExtra("birthDate")
                );

                contactes.add(nouContacte);

            } else if (position >= 0 && position < contactes.size()) {

                Contacte contacte = contactes.get(position);

                contacte.setName(data.getStringExtra("name"));
                contacte.setSurname(data.getStringExtra("surname"));
                contacte.setAddress(data.getStringExtra("address"));
                contacte.setNumber(data.getStringExtra("number"));
                contacte.setEmail(data.getStringExtra("email"));
                contacte.setBirthDate(data.getStringExtra("birthDate"));

            }

            adapter.notifyDataSetChanged();
        }
    }
}