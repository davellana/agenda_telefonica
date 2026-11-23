
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
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_contacte, parent, false);
        }

        Contacte contacte = contactes.get(position);

        TextView txtNombre = convertView.findViewById(R.id.txtNombre);
        ImageButton btnEditar = convertView.findViewById(R.id.btn_editar);
        ImageButton btnLlamar = convertView.findViewById(R.id.btn_llamar);

        txtNombre.setText(contacte.getName() + " " + contacte.getSurname() + "\n" + contacte.getNumber());

        btnEditar.setOnClickListener(v -> {
            Intent intent = new Intent(context, ContacteActivity.class);
            intent.putExtra("position", position);
            context.startActivityForResult(intent, 1);

        });

        btnLlamar.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL);
            intent.setData(Uri.parse("tel:" + contacte.getNumber()));
            context.startActivity(intent);

        });

        return convertView;
    }
}