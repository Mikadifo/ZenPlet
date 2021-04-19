package com.mikadifo.zenplet.ui.vaccines;

import android.app.DatePickerDialog;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.model.PetVaccine;
import com.mikadifo.zenplet.API.model.Vaccine;
import com.mikadifo.zenplet.API.service.PetVaccineService;
import com.mikadifo.zenplet.API.service.VaccineService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewVaccines#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewVaccines extends Fragment {
    private Vaccine vaccine = new Vaccine();
    private PetVaccine petVaccine = new PetVaccine();
    public Pet petForVaccine = new Pet();
    private Pet selectedPet;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AwesomeValidation awesomeValidation;

    public NewVaccines() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewVaccines.
     */
    // TODO: Rename and change types and number of parameters
    public static NewVaccines newInstance(String param1, String param2) {
        NewVaccines fragment = new NewVaccines();
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
        View root = inflater.inflate(R.layout.fragment_new_vaccines, container, false);
        Spinner spinnerVaccinesForPet = root.findViewById(R.id.spinnerVaccinesForPet);
        ArrayAdapter<Pet> arrayAdapter = new ArrayAdapter(root.getContext(), R.layout.support_simple_spinner_dropdown_item, SignUpActivity.ownerNew.getOwnerPets().toArray());
        arrayAdapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinnerVaccinesForPet.setAdapter(arrayAdapter);
        spinnerVaccinesForPet.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                System.out.println(position);
                selectedPet = (Pet) spinnerVaccinesForPet.getItemAtPosition(position);
                System.out.println(selectedPet);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        EditText nameVaccines = root.findViewById(R.id.new_name_vaccines);
        EditText date = root.findViewById(R.id.new_date_vaccine);
        EditText dateNext = root.findViewById(R.id.new_next_vaccines);
        EditText nameDescription = root.findViewById(R.id.new_description_vaccines);
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day, month, year;
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });
        dateNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day, month, year;
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateNext.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });
        Button btn = root.findViewById(R.id.buttonSaveVaccines);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (nameVaccines.getText().toString().isEmpty() || nameDescription.getText().toString().isEmpty() || date.getText().toString().isEmpty() || dateNext.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.toast_you_must_complete_the_fields), Toast.LENGTH_LONG).show();
                }
                CallWithToken callWithToken = new CallWithToken();
                Retrofit retrofit = callWithToken.getCallToken();
                vaccine.setVaccinesName(nameVaccines.getText().toString());
                vaccine.setVaccinesDescription(nameDescription.getText().toString());
                //petVaccine.setPet(FragmentPets.selectedPet);
                VaccineService vaccineService = retrofit.create(VaccineService.class);
                Call<Vaccine> call = vaccineService.saveVaccines(vaccine);
                call.enqueue(new Callback<Vaccine>() {
                    @Override
                    public void onResponse(Call<Vaccine> call, Response<Vaccine> response) {
                        System.out.println(response.body());
                        vaccine = response.body();
                        for (Pet pet : SignUpActivity.ownerNew.getOwnerPets()) {
                            if (pet.getPetId() == selectedPet.getPetId()) {
                                petForVaccine = pet;
                            }
                        }

                        petVaccine.setPetVaccineDate(date.getText().toString());
                        petVaccine.setPetVaccineNext(dateNext.getText().toString());
                        petVaccine.setVaccine(vaccine);
                        petVaccine.setPet(petForVaccine);
                        System.out.println(petVaccine);
                        CallWithToken callWithToken = new CallWithToken();
                        Retrofit retrofit = callWithToken.getCallToken();
                        PetVaccineService petVaccineService = retrofit.create(PetVaccineService.class);
                        Call<PetVaccine> petVaccineCall = petVaccineService.savePetVaccines(petVaccine);
                        petVaccineCall.enqueue(new Callback<PetVaccine>() {
                            @Override
                            public void onResponse(Call<PetVaccine> call, Response<PetVaccine> response) {
                                System.out.println(response.body());
                                petVaccine = response.body();
                                for (Pet pet : SignUpActivity.ownerNew.getOwnerPets()) {
                                    if (pet.getPetId() == response.body().getId().getPetId()) {
                                        pet.getPetVaccines().add(petVaccine);
                                    }
                                }
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.nav_host_fragment, new Vaccines());
                                fragmentTransaction.commit();
                            }

                            @Override
                            public void onFailure(Call<PetVaccine> call, Throwable t) {

                            }
                        });
                    }

                    @Override
                    public void onFailure(Call<Vaccine> call, Throwable t) {

                    }
                });
            }
        });
        //validacion
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        nameVaccines.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.new_name_vaccines, "(^[ÁÉÍÓÚA-Za-záéíóú ]{3,30}$)", R.string.invalid_name);
                if (!awesomeValidation.validate()) {
                    nameVaccines.setError(getContext().getResources().getString(R.string.invalid_name));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        nameDescription.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.new_description_vaccines, "(^[ÁÉÍÓÚA-Za-záéíóú ]{3,300}$)", R.string.invalid_info);
                if (!awesomeValidation.validate()) {
                    nameDescription.setError(getContext().getResources().getString(R.string.invalid_info));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return root;

    }
}