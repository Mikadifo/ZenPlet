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

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
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
        TextView txtPetAge = (TextView) rowView.findViewById(R.id.textViewAgePetsNew);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.imgPet);
        Date date=null;
        try {
            date = new SimpleDateFormat("yyyy-mm-dd").parse(pets.get(pos).getPetBirthdate());
        } catch (ParseException e) {
            System.out.println("Error:"+e);
        }
        Calendar fechaNacimiento = Calendar.getInstance();
        Calendar fechaActual = Calendar.getInstance();
        fechaNacimiento.setTime(date);
        int year = fechaActual.get(Calendar.YEAR)- fechaNacimiento.get(Calendar.YEAR);
        int mes =fechaActual.get(Calendar.MONTH)- fechaNacimiento.get(Calendar.MONTH);
        int dia = fechaActual.get(Calendar.DATE)- fechaNacimiento.get(Calendar.DATE);
        if(mes<0 || (mes==0 && dia<0)){
            year--;

        }
        txtPetAge.setText(Integer.toString(year)+" Year "+Integer.toString(mes)+" Month ");
        txtPetName.setText(pets.get(pos).getPetName());
        txtPetType.setText(pets.get(pos).getPetBreed());
        txtPetGenre.setText(pets.get(pos).getPetGenre());

        byte[] decodedString = Base64.decode(pets.get(pos).getPetImage().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);


    return rowView;
    }


}
