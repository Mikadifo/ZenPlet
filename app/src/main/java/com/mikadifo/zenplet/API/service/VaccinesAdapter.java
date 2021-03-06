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

import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.model.PetVaccine;
import com.mikadifo.zenplet.API.model.Vaccine;
import com.mikadifo.zenplet.R;

import java.util.List;


public class VaccinesAdapter extends ArrayAdapter<PetVaccine> {

    private Context context;
    private List<PetVaccine> petVaccines;


    public VaccinesAdapter(@NonNull Context context, @LayoutRes int resource,  @NonNull List<PetVaccine> objects){
        super(context, resource, objects);
        this.context = context;
        this.petVaccines = objects;

    }

    @Override
    public View getView(final int pos, View convertView, ViewGroup parent){
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        View rowView = inflater.inflate(R.layout.vaccines_list, parent, false);

        TextView txtNameV = (TextView) rowView.findViewById(R.id.nameText);
        TextView txtDate = (TextView) rowView.findViewById(R.id.dateText);
        TextView txtNext = (TextView) rowView.findViewById(R.id.nextText);
        TextView txtDescription = (TextView) rowView.findViewById(R.id.descText);

        txtNameV.setText(petVaccines.get(pos).getVaccine().getVaccinesName());
        txtDate.setText(petVaccines.get(pos).getPetVaccineDate());
        txtNext.setText(petVaccines.get(pos).getPetVaccineNext());
        txtDescription.setText(petVaccines.get(pos).getVaccine().getVaccinesDescription());

        return rowView;
    }


}
