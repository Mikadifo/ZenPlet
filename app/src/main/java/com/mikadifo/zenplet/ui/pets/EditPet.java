package com.mikadifo.zenplet.ui.pets;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.LostPet;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.model.PetVaccine;
import com.mikadifo.zenplet.API.service.LostPetService;
import com.mikadifo.zenplet.API.service.PetService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditPet extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AwesomeValidation awesomeValidation;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imageView;

    public EditPet() {

        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditPet.
     */
    // TODO: Rename and change types and number of parameters
    public static EditPet newInstance(String param1, String param2) {
        EditPet fragment = new EditPet();
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
        View root = inflater.inflate(R.layout.fragment_edit_pet, container, false);
        imageView = root.findViewById(R.id.foto);
        EditText name = root.findViewById(R.id.edit_name);
        Spinner spinnerSizeEditPet = root.findViewById(R.id.spinnerSizeEditPet);
        RadioButton radioButtonGenreEditPetMale = root.findViewById(R.id.ratioMaleEditPet);
        RadioButton radioButtonGenreEditPetFemale = root.findViewById(R.id.ratioFemaleEditPet);
        EditText breed = root.findViewById(R.id.edit_breed);
        EditText birthdate = root.findViewById(R.id.edit_birthdate);

        birthdate.setOnClickListener(view -> {
            int day, month, year;
            Calendar calendar = Calendar.getInstance();
            day = calendar.get(Calendar.DAY_OF_MONTH);
            month = calendar.get(Calendar.MONTH);
            year = calendar.get(Calendar.YEAR);

            DatePickerDialog datePickerDialog = new DatePickerDialog(getContext(), (viewDate, dateYear, dateMonth, dayOfMonth)
                    -> {
                String birthdateS = year + "-" + (month + 1) + "-" + dayOfMonth;
                Date date = null;
                try {
                    date = new SimpleDateFormat("yyyy-MM-dd").parse(birthdateS);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
                Calendar fechaNacimiento = Calendar.getInstance();
                Calendar fechaActual = Calendar.getInstance();
                fechaNacimiento.setTime(date);
                int yearCalculated = fechaActual.get(Calendar.YEAR) - fechaNacimiento.get(Calendar.YEAR);
                int monthCalculated = fechaActual.get(Calendar.MONTH) - fechaNacimiento.get(Calendar.MONTH);
                int dayCalculated = fechaActual.get(Calendar.DAY_OF_MONTH) - fechaNacimiento.get(Calendar.DAY_OF_MONTH);
                if (monthCalculated < 0 || (monthCalculated == 0 && dayCalculated < 0)) {
                    yearCalculated--;

                }
                if (yearCalculated > 50 || yearCalculated < 0) {
                    Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.invalid_date),
                            Toast.LENGTH_LONG).show();
                } else if (yearCalculated == 0) {
                    if (monthCalculated == 0) {
                        if (dayCalculated < 0) {
                            Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.invalid_date),
                                    Toast.LENGTH_LONG).show();
                        } else {
                            birthdate.setText(birthdateS);
                        }
                    } else {
                        birthdate.setText(birthdateS);
                    }
                } else {
                    birthdate.setText(birthdateS);
                }
            }, year, month, day);

            datePickerDialog.show();
        });

        byte[] decodedString = Base64.decode(FragmentPets.selectedPet.getPetImage().split(",")[1], Base64.DEFAULT);
        Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
        imageView.setImageBitmap(decodedByte);

        name.setText(FragmentPets.selectedPet.getPetName());
        String[] arraySpinner = root.getContext().getResources().getStringArray(R.array.Size);
        int itemList = Arrays.asList(arraySpinner).indexOf(FragmentPets.selectedPet.getPetSize());
        spinnerSizeEditPet.setSelection(itemList);
        breed.setText(FragmentPets.selectedPet.getPetBreed());
        if (FragmentPets.selectedPet.getPetGenre().equals("Male")) {
            radioButtonGenreEditPetMale.setChecked(true);
        } else radioButtonGenreEditPetFemale.setChecked(true);
        birthdate.setText(FragmentPets.selectedPet.getPetBirthdate());

        CallWithToken callWithToken = new CallWithToken();
        Retrofit retrofit = callWithToken.getCallToken();
        PetService petService = retrofit.create(PetService.class);

        Button btnSave = root.findViewById(R.id.btnSaveEditPet);
        btnSave.setOnClickListener(view -> {
            if (imageView.getDrawable() == null || !awesomeValidation.validate()||name.getText().toString().isEmpty()||breed.getText().toString().isEmpty()) {
                Toast.makeText(view.getContext(),
                        getContext().getResources().getString(R.string.toast_you_must_complete_the_fields),
                        Toast.LENGTH_LONG).show();
            } else {
                Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                byte[] imageInByte = {};
                try {
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                    imageInByte = baos.toByteArray();
                    baos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                String fotoEnBase64 = "data:image/jpeg;base64," + Base64.encodeToString(imageInByte, Base64.NO_WRAP);

                Set<Pet> ownerPets = new HashSet<>(SignUpActivity.ownerNew.getOwnerPets());
                List<PetVaccine> petVaccinesList = new ArrayList<>(FragmentPets.selectedPet.getPetVaccines());
                SignUpActivity.ownerNew.setOwnerPets(null);
                FragmentPets.selectedPet.setPetName(name.getText().toString());
                FragmentPets.selectedPet.setPetOwner(SignUpActivity.ownerNew);
                FragmentPets.selectedPet.setPetSize(spinnerSizeEditPet.getSelectedItem().toString());
                FragmentPets.selectedPet.setPetBreed(breed.getText().toString());
                FragmentPets.selectedPet.setPetGenre(getContext().getResources().getString(R.string.stringMaleRatio));
                if (radioButtonGenreEditPetMale.isChecked())
                    FragmentPets.selectedPet.setPetGenre(getContext().getResources().getString(R.string.stringMaleRatio));
                else
                    FragmentPets.selectedPet.setPetGenre(getContext().getResources().getString(R.string.stringFemaleRatio));
                FragmentPets.selectedPet.setPetBirthdate(birthdate.getText().toString());
                FragmentPets.selectedPet.setPetImage(fotoEnBase64);
                FragmentPets.selectedPet.setPetVaccines(null);
                Call<Pet> callupdate = petService.updatePet(FragmentPets.selectedPet.getPetId(), FragmentPets.selectedPet);
                callupdate.enqueue(new Callback<Pet>() {
                    @Override
                    public void onResponse(Call<Pet> call, Response<Pet> response) {
                        FragmentPets.selectedPet = response.body();
                        FragmentPets.selectedPet.setPetVaccines(petVaccinesList);
                        for (Pet pet : ownerPets) {
                            if (pet.getPetId() == FragmentPets.selectedPet.getPetId()) {
                                pet.setPetVaccines(petVaccinesList);
                            }
                        }
                        SignUpActivity.ownerNew.setOwnerPets(ownerPets);
                        Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.toast_The_data_has_been_save_successfully), Toast.LENGTH_LONG).show();
                        FragmentManager fragmentManager = getFragmentManager();
                        FragmentTransaction fragmentTransaction = fragmentManager
                                .beginTransaction()
                                .replace(R.id.nav_host_fragment, new FragmentPets());
                        fragmentTransaction.commit();
                        Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.toast_updated_pet), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFailure(Call<Pet> call, Throwable t) {
                        try {
                            throw t;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
            }
        });

        Button openCameraBtn = root.findViewById(R.id.btnAbrirCamara);
        Button openGalleryBtn = root.findViewById(R.id.btnAbrirGaleria);
        openGalleryBtn.setOnClickListener(this::loadImage);
        openCameraBtn.setOnClickListener(this::openCamera);
        Button btnlo = root.findViewById(R.id.btnLost);
        Button btnd = root.findViewById(R.id.btnDelete);

        btnlo.setOnClickListener(view -> {
            CallWithToken callWithToken1 = new CallWithToken();
            Retrofit retrofit1 = callWithToken1.getCallToken();
            LostPetService lostPetService = retrofit1.create(LostPetService.class);
            Call<LostPet> call = lostPetService.getLostPetByPetId(FragmentPets.selectedPet.getPetId());
            call.enqueue(new Callback<LostPet>() {
                @Override
                public void onResponse(Call<LostPet> call, Response<LostPet> response) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, new EditLostPet(response.body()));
                    fragmentTransaction.commit();
                }

                @Override
                public void onFailure(Call<LostPet> call, Throwable t) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, new PostLostPet());
                    fragmentTransaction.commit();
                }
            });
        });

        btnd.setOnClickListener(view -> {
            CallWithToken callWithToken12 = new CallWithToken();
            Retrofit retrofit12 = callWithToken12.getCallToken();
            PetService petService1 = retrofit12.create(PetService.class);
            AlertDialog.Builder dialogo1 = new AlertDialog.Builder(getContext());
            dialogo1.setTitle(getContext().getResources().getString(R.string.dialog_Important));
            dialogo1.setMessage(
                    getContext().getResources().getString(R.string.dialog_Are_you_sure_to_remove_this_pet));
            dialogo1.setCancelable(false);
            dialogo1.setPositiveButton(getContext().getResources().getString(R.string.option_yes),
                    (dialogo11, id) -> {
                        Call<Void> call = petService1.deletePet(FragmentPets.selectedPet.getPetId());
                        call.enqueue(new Callback<Void>() {
                            @Override
                            public void onResponse(Call<Void> call, Response<Void> response) {
                                SignUpActivity.ownerNew.getOwnerPets().remove(FragmentPets.selectedPet);
                                dialogo11.dismiss();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                                        .replace(R.id.nav_host_fragment, new FragmentPets());
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

                    }).setNegativeButton("No", null).show();
        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.edit_name, "(^[√Å√â√ç√ì√öA-Za-z√°√©√≠√≥√∫ ]{3,30}$)",
                        R.string.invalid_name);
                if (!awesomeValidation.validate()) {
                    name.setError(getContext().getResources().getString(R.string.invalid_name));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        breed.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.edit_breed, "(^[√Å√â√ç√ì√öA-Za-z√°√©√≠√≥√∫ ]{3,30}$)",
                        R.string.invalid_name);
                if (!awesomeValidation.validate()) {
                    breed.setError(getContext().getResources().getString(R.string.invalid_name));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return root;
    }

    public void openCamera(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    public void loadImage(View view) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, getContext().getResources().getString(R.string.toast_Select_the_app)), 10);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int currentBitMapWidth = image.getWidth();
            int currentBitMapHeight = image.getHeight();
            int newWidth = imageView.getWidth();
            int newHeight = (int) Math
                    .floor((double) currentBitMapHeight * (double) newWidth / (double) currentBitMapWidth);
            Bitmap bitmap = Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
            imageView.setImageBitmap(image);
        }
        if (resultCode == -1) {
            Uri path = data.getData();
            imageView.setImageURI(path);
        }

    }

}
