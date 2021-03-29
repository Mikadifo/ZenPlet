package com.mikadifo.zenplet.API.service;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.R;

import java.util.List;

public class PetAdapter extends ArrayAdapter<Pet> {
    private Context context;
    private List<Pet> pets;

    public PetAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<Pet> objects){
        super(context, resource, objects);
        this.context = context;
        this.pets = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.pets_list, parent, false);

        TextView txtPetName = (TextView) rowView.findViewById(R.id.textViewNamePetsNew);
        TextView txtPetType = (TextView) rowView.findViewById(R.id.textViewTypePetsNew);
        TextView txtPetGenre = (TextView) rowView.findViewById(R.id.textViewGenrePetsNew);

        txtPetName.setText(pets.get(pos).getPetName());
        txtPetType.setText(pets.get(pos).getPetBreed());
        txtPetGenre.setText(pets.get(pos).getPetGenre());

        rowView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //start Activity User Form


            }
        });


    return rowView;
    }
}
