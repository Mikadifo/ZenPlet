package com.mikadifo.zenplet.ui.vaccines;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Vaccine;
import com.mikadifo.zenplet.API.service.PetService;
import com.mikadifo.zenplet.API.service.VaccineService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.pets.FragmentPets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditVaccines#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditVaccines extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public EditVaccines() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditVaccines.
     */
    // TODO: Rename and change types and number of parameters
    public static EditVaccines newInstance(String param1, String param2) {
        EditVaccines fragment = new EditVaccines();
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
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_vaccines, container, false);
        Button btn = root.findViewById(R.id.deleteVaccine);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new Vaccines());
                fragmentTransaction.commit();
            }
        });
        EditText name = root.findViewById(R.id.edit_name_vaccines);
        EditText date = root.findViewById(R.id.edit_date_vaccine);
        EditText next = root.findViewById(R.id.edit_next_vaccines);
        EditText description= root.findViewById(R.id.edit_description_vaccines);
        Button btnEdit = root.findViewById(R.id.saveVaccine);
        //llamada a token, retrofit y pestService
        CallWithToken callWithToken= new CallWithToken();
        Retrofit retrofit = callWithToken.getCallToken();
        VaccineService vaccineService = retrofit.create(VaccineService.class);
        //Editar la vacuna
        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //FragmentPets.selectedPet.setNa(name.getText().toString());
               // FragmentPets.selectedPet.setDate(date.getText().toString());
                // FragmentPets.selectedPet.setNext(next.getText().toString());
                // FragmentPets.selectedPet.setDescription(description.getText().toString());
                //llamada al metodo del servicio
               /** Call<Vaccine> callupdate = vaccineService.updateVaccine(FragmentPets.selectedPet.getPetId(), FragmentPets.selectedPet);
                callupdate.enqueue(new Callback<Vaccine>() {
                    @Override
                    public void onResponse(Call<Vaccine> call, Response<Vaccine> response) {
                        Toast.makeText(v.getContext(), "The data has been updated successfully", Toast.LENGTH_LONG).show();
                        System.out.println(response.body());
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new FragmentPets());
                        fragmentTransaction.commit();
                        Toast.makeText(v.getContext(), "Updated vaccine", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Vaccine> call, Throwable t) {
                        try {
                            throw t;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });**/
            }
        });

        return root;

    }
}