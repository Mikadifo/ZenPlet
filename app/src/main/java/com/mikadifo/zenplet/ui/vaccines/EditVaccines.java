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
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.model.PetVaccine;
import com.mikadifo.zenplet.API.model.PetVaccinesId;
import com.mikadifo.zenplet.API.model.Vaccine;
import com.mikadifo.zenplet.API.service.PetVaccineService;
import com.mikadifo.zenplet.API.service.VaccineService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

import java.text.SimpleDateFormat;
import java.util.Calendar;

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
    private Pet selectedPet;
    private PetVaccine petVaccine = new PetVaccine();
    private Pet petForVaccine = new Pet();
    private Pet petVaccineSelected = new Pet();
    private Vaccine vaccineToUpdate;
    AwesomeValidation awesomeValidation;

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
        TextView txtPetFor = root.findViewById(R.id.VaccineePetLabel);
        EditText nameVaccines = root.findViewById(R.id.edit_name_vaccines);
        EditText date = root.findViewById(R.id.edit_date_vaccine);
        EditText dateNext = root.findViewById(R.id.edit_next_vaccines);
        EditText nameDescription = root.findViewById(R.id.edit_description_vaccines);

        date.setText(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        dateNext.setText(new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime()));
        date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int day, month, year;
                Calendar calendar = Calendar.getInstance();
                day = calendar.get(Calendar.DAY_OF_MONTH);
                month = calendar.get(Calendar.MONTH);
                year = calendar.get(Calendar.YEAR);

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                date.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);
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

                DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                                dateNext.setText(dayOfMonth + "/" + (month + 1) + "/" + year);
                            }
                        }, year, month, day);
                datePickerDialog.show();
            }
        });
        for (Pet pet : SignUpActivity.ownerNew.getOwnerPets()) {
            if (pet.getPetId() == Vaccines.selectedPetVaccine.getId().getPetId()) {
                petVaccineSelected = pet;
            }
        }

        //mostrar los datos para poder editar
        nameVaccines.setText(Vaccines.selectedPetVaccine.getVaccine().getVaccinesName());
        nameDescription.setText(Vaccines.selectedPetVaccine.getVaccine().getVaccinesDescription());
        date.setText(Vaccines.selectedPetVaccine.getPetVaccineDate());
        dateNext.setText(Vaccines.selectedPetVaccine.getPetVaccineNext());
        txtPetFor.setText(petVaccineSelected.getPetName());

        Button btn = root.findViewById(R.id.saveVaccine);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!awesomeValidation.validate()||nameDescription.getText().toString().isEmpty()||nameVaccines.getText().toString().isEmpty()) {
                    Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.toast_you_must_complete_the_fields), Toast.LENGTH_LONG).show();
                } else {
                    CallWithToken callWithToken = new CallWithToken();
                    Retrofit retrofit = callWithToken.getCallToken();
                    vaccineToUpdate = Vaccines.selectedPetVaccine.getVaccine();
                    vaccineToUpdate.setVaccinesName(nameVaccines.getText().toString());
                    vaccineToUpdate.setVaccinesDescription(nameDescription.getText().toString());
                    VaccineService vaccineService = retrofit.create(VaccineService.class);
                    Call<Vaccine> call = vaccineService.updateVaccine(vaccineToUpdate.getVaccinesId(), vaccineToUpdate);
                    call.enqueue(new Callback<Vaccine>() {
                        @Override
                        public void onResponse(Call<Vaccine> call, Response<Vaccine> response) {
                            vaccineToUpdate = response.body();
                            petVaccine.setId(new PetVaccinesId(petVaccineSelected.getPetId(), 0L));
                            petVaccine.setPetVaccineDate(date.getText().toString());
                            petVaccine.setPetVaccineNext(dateNext.getText().toString());
                            petVaccine.setVaccine(vaccineToUpdate);

                            CallWithToken callWithToken = new CallWithToken();
                            Retrofit retrofit = callWithToken.getCallToken();
                            PetVaccineService petVaccineService = retrofit.create(PetVaccineService.class);
                            Call<PetVaccine> petVaccineCall
                                    = petVaccineService.updatePetVaccines(
                                    petVaccine.getId().getPetId(),
                                    petVaccine.getVaccine().
                                            getVaccinesId(),
                                    petVaccine
                            );
                            petVaccineCall.enqueue(new Callback<PetVaccine>() {
                                @Override
                                public void onResponse(Call<PetVaccine> call, Response<PetVaccine> response) {
                                    petVaccine = response.body();

                                    PetVaccine beforePetVaccine = new PetVaccine();
                                    for (Pet pet : SignUpActivity.ownerNew.getOwnerPets()) {
                                        if (pet.getPetId() == response.body().getId().getPetId()) {
                                            for (PetVaccine currentPetVaccine : pet.getPetVaccines()) {
                                                if (currentPetVaccine.getVaccine().getVaccinesId() == response.body().getId().getVaccineId()) {
                                                    beforePetVaccine = currentPetVaccine;
                                                }
                                            }
                                            pet.getPetVaccines().remove(beforePetVaccine);
                                            pet.getPetVaccines().add(response.body());
                                        }
                                    }

                                    Toast.makeText(view.getContext(),
                                            getContext().getResources().getString(R.string.toast_The_vaccine_has_been_updated_successfully),
                                            Toast.LENGTH_LONG).show();
                                    //llevar al fragmento vaccines
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction()
                                            .replace(R.id.nav_host_fragment, new Vaccines());
                                    fragmentTransaction.commit();
                                }

                                @Override
                                public void onFailure(Call<PetVaccine> call, Throwable t) {
                                    try {
                                        throw t;
                                    } catch (Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                }
                            });
                        }

                        @Override
                        public void onFailure(Call<Vaccine> call, Throwable t) {
                            try {
                                throw t;
                            } catch (Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        }
                    });
                }
            }
        });


        Button btnde = root.findViewById(R.id.deleteVaccine);
        btnde.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallWithToken callWithToken = new CallWithToken();
                Retrofit retrofit = callWithToken.getCallToken();
                VaccineService vaccineService = retrofit.create(VaccineService.class);
                Call<Void> vaccineCall = vaccineService.deleteVaccine(Vaccines.selectedPetVaccine.getVaccine().getVaccinesId());
                vaccineCall.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call,
                                           Response<Void> response) {
                        petVaccineSelected.getPetVaccines().remove(Vaccines.selectedPetVaccine);
                        for (Pet pet : SignUpActivity.ownerNew.getOwnerPets()) {
                            if (pet.getPetId() == petForVaccine.getPetId()) {
                                pet.setPetVaccines(petForVaccine.getPetVaccines());
                            }
                        }
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new Vaccines());
                        fragmentTransaction.commit();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        try {
                            throw t;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
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
                awesomeValidation.addValidation(getActivity(), R.id.edit_name_vaccines, "(^[ÁÉÍÓÚA-Za-záéíóú ]{3,30}$)", R.string.invalid_name);
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
                awesomeValidation.addValidation(getActivity(), R.id.edit_description_vaccines, "(^[ÁÉÍÓÚA-Za-záéíóú ]{3,250}$)", R.string.invalid_info);
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