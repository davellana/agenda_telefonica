
package com.example.agenda;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class ContacteAdapter extends ArrayAdapter<Contacte> {

    private Activity context;
    private ArrayList<Contacte> contactes;

    public ContacteAdapter(Activity context, ArrayList<Contacte> contactes) {
        super(context, R.layout.item_contacte, contactes);
        this.context = context;
        this.contactes = contactes;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_contacte, parent, false);
        }

        Contacte contacte = contactes.get(position);

        TextView txtNombre = convertView.findViewById(R.id.txtNombre);
        ImageButton btnEditar = convertView.findViewById(R.id.btn_editar);
        ImageButton btnLlamar = convertView.findViewById(R.id.btn_llamar);
        ImageButton btnEmail = convertView.findViewById(R.id.btn_email);

        txtNombre.setText(contacte.getName() + " " + contacte.getSurname() + "\n" + contacte.getNumber());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContacteActivity.class);
            intent.putExtra("position", position);
            context.startActivityForResult(intent, 1);

        });

        btnLlamar.setOnClickListener(v -> {
            if (contacte.getNumber() == null || contacte.getNumber().trim().isEmpty()) {
                Toast.makeText(context, "Aquest contacte no té telèfon", Toast.LENGTH_SHORT).show();
                return;
            }

            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contacte.getNumber()));
            context.startActivity(intent);

        });

        btnEmail.setOnClickListener(v -> {
            if (contacte.getEmail() == null || contacte.getEmail().trim().isEmpty()) {
                Toast.makeText(context, "Aquest contacte no té correu electrònic", Toast.LENGTH_SHORT).show();
                return;
            }
            Intent intent = new Intent(Intent.ACTION_SENDTO);
            intent.setData(Uri.parse("mailto:" + contacte.getEmail().trim()));
            context.startActivity(intent);
        });

        return convertView;
    }
}
