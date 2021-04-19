package com.mikadifo.zenplet.ui.vaccines;

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
import com.mikadifo.zenplet.API.model.PetVaccine;
import com.mikadifo.zenplet.API.service.VaccinesAdapter;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Vaccines#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Vaccines extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public static PetVaccine selectedPetVaccine;
    private View root;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Vaccines.
     */
    // TODO: Rename and change types and number of parameters
    public static Vaccines newInstance(String param1, String param2) {
        Vaccines fragment = new Vaccines();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    public Vaccines() {
        // Required empty public constructor
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
        // Inflate the layout for this fragment

        root = inflater.inflate(R.layout.fragment_vaccines, container, false);
        List<PetVaccine> petVaccineslist = new ArrayList<>();
        for (Pet pet : SignUpActivity.ownerNew.getOwnerPets()) {
            if (pet.getPetVaccines() != null) {
                petVaccineslist.addAll(pet.getPetVaccines());
            }
        }

        if (!petVaccineslist.isEmpty()) {
            ListView list = root.findViewById(R.id.ListVaccines);
            list.setAdapter(new VaccinesAdapter(root.getContext(), R.layout.vaccines_list, petVaccineslist));
            list.setOnItemClickListener(vaccineListener);
        }

        Button btn = root.findViewById(R.id.butNewVaccines);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new NewVaccines());
                fragmentTransaction.commit();
            }
        });
        return root;
    }

    private AdapterView.OnItemClickListener vaccineListener = (adapterView, view, position, id) -> {
        selectedPetVaccine = (PetVaccine) adapterView.getItemAtPosition(position);
        System.out.println("Este es cuando selecciona vaccine" + selectedPetVaccine);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new EditVaccines());
        fragmentTransaction.commit();
    };


}