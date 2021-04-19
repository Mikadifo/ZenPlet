package com.mikadifo.zenplet.ui.pets;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.service.PetAdapter;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FragmentPets#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FragmentPets extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    public static Pet selectedPet;
    private View root;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public FragmentPets() {

    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment FragmentPets.
     */
    public static FragmentPets newInstance(String param1, String param2) {
        FragmentPets fragment = new FragmentPets();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_pets, container, false);
        ListView listView =root.findViewById(R.id.list_pets);
        Button btn = root.findViewById(R.id.butAddPet);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new NewPet());
                fragmentTransaction.commit();
            }
        });
        if (SignUpActivity.ownerNew.getOwnerPets() != null){
            for (Pet pet: SignUpActivity.ownerNew.getOwnerPets()){
                List<Pet> petList = new ArrayList<Pet>(SignUpActivity.ownerNew.getOwnerPets());
                listView.setAdapter(new PetAdapter(getContext(), R.layout.pets_list, petList));
                //llamar desde la lista de pets al fragment EditPet
                listView.setOnItemClickListener(petListener);
            }
        }
        return root;

    }

    private AdapterView.OnItemClickListener petListener= (adapterView, view, position, id)->{
        //retornar objeto seleecionado
        selectedPet = (Pet)adapterView.getItemAtPosition(position);
        System.out.println("Este es cuadno selecciona"+selectedPet);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new EditPet());
        fragmentTransaction.commit();
    };
}