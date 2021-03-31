package com.mikadifo.zenplet.API.service;

import android.app.Activity;
import android.app.Fragment;
import android.content.Context;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.pets.EditPet;
import com.mikadifo.zenplet.ui.pets.FragmentPets;

import com.mikadifo.zenplet.ui.pets.PostLostPet;

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
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgPet);

        txtPetName.setText(pets.get(pos).getPetName());
        txtPetType.setText(pets.get(pos).getPetBreed());
        txtPetGenre.setText(pets.get(pos).getPetGenre());
        byte[] decodedString = Base64.decode(pets.get(pos).getPetImage(), Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);


    return rowView;
    }


}
