package com.mikadifo.zenplet.API.service;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.LayoutRes;
import androidx.annotation.NonNull;

import com.mikadifo.zenplet.API.model.LostPet;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.R;

import java.util.List;

public class LosPetAdapter extends ArrayAdapter<LostPet> {
    private Context context;
    private List<LostPet> lostPets;


    public LosPetAdapter(@NonNull Context context, @LayoutRes int resource, @NonNull List<LostPet> objects){
        super(context, resource, objects);
        this.context = context;
        this.lostPets = objects;
    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.list_lost_pets, parent, false);

        TextView txtPetName = (TextView) rowView.findViewById(R.id.ViewNameLostPets);
        TextView txtAddInfo = (TextView) rowView.findViewById(R.id.ViewDescriptionLostPet);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.ImagenLostPet);

        txtPetName.setText(lostPets.get(pos).getPet().getPetName());
        txtAddInfo.setText(lostPets.get(pos).getLostPetAdditionalInfo());
        byte[] decodedString = Base64.decode(lostPets.get(pos).getPet().getPetImage().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);


        return rowView;
    }
}
