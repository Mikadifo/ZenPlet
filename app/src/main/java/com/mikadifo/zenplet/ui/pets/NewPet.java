package com.mikadifo.zenplet.ui.pets;

import android.app.DatePickerDialog;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

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
import android.content.Intent;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Spinner;
import android.widget.Toast;


import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.service.PetService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Calendar;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link NewPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class NewPet extends Fragment {
    private Pet pet = new Pet();

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AwesomeValidation awesomeValidation;

    private ImageView imageView;

    public NewPet() {
        pet = new Pet();
        // Required emptgnUpActivity.ownerNepublic constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment NewPet.
     */
    // TODO: Rename and change types and number of parameters
    public static NewPet newInstance(String param1, String param2) {

        NewPet fragment = new NewPet();
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
        View root = inflater.inflate(R.layout.fragment_new_pet, container, false);
        imageView = (ImageView) root.findViewById(R.id.imgFoto);
        Button openCameraBtn = root.findViewById(R.id.btnCamara);
        Button openGalleryBtn = root.findViewById(R.id.btnGaleria);
        Button btn = root.findViewById(R.id.btnCreatPet);
        EditText name = root.findViewById(R.id.edit_new_name);
        Spinner spinnerSizeNewPet = root.findViewById(R.id.spinnerSizeNewPet);
        RadioButton radioButtonGenreNewPetMale = root.findViewById(R.id.ratioMale);
        EditText breed = root.findViewById(R.id.edit_new_breed);
        EditText birthdate = root.findViewById(R.id.edit_new_birthdate);
        birthdate.setOnClickListener(new View.OnClickListener() {
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
                        birthdate.setText(year + "-" + (month + 1) + "-" + dayOfMonth);
                    }
                }, day, month, year);
                datePickerDialog.show();
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (imageView.getDrawable() == null || !awesomeValidation.validate()) {
                    Toast.makeText(view.getContext(),
                            getContext().getResources().getString(R.string.toast_you_must_complete_the_fields),
                            Toast.LENGTH_LONG).show();
                } else {
                    byte[] imageInByte = {};
                    try {
                        Bitmap bitmap = ((BitmapDrawable) imageView.getDrawable()).getBitmap();
                        ByteArrayOutputStream baos = new ByteArrayOutputStream();
                        bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
                        imageInByte = baos.toByteArray();
                        baos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    String fotoEnBase64 = "data:image/jpeg;base64," + Base64.encodeToString(imageInByte, Base64.NO_WRAP);
                    CallWithToken callWithToken = new CallWithToken();
                    Retrofit retrofit = callWithToken.getCallToken();
                    pet.setPetName(name.getText().toString());
                    pet.setPetSize(spinnerSizeNewPet.getSelectedItem().toString());
                    pet.setPetSize(spinnerSizeNewPet.getSelectedItem().toString());
                    if (radioButtonGenreNewPetMale.isChecked())
                        pet.setPetGenre("Male");
                    else
                        pet.setPetGenre("Female");
                    pet.setPetBreed(breed.getText().toString());
                    pet.setPetBirthdate(birthdate.getText().toString());
                    pet.setPetImage(fotoEnBase64);
                    Set<Pet> ownerPets = SignUpActivity.ownerNew.getOwnerPets();
                    SignUpActivity.ownerNew.setOwnerPets(null);
                    pet.setPetOwner(SignUpActivity.ownerNew);
                    PetService petService = retrofit.create(PetService.class);
                    Call<Pet> call = petService.savePet(pet);
                    call.enqueue(new Callback<Pet>() {
                        @Override
                        public void onResponse(Call<Pet> call, Response<Pet> response) {
                            pet = response.body();
                            SignUpActivity.ownerNew.setOwnerPets(ownerPets);
                            SignUpActivity.ownerNew.getOwnerPets().add(pet);
                            FragmentManager fragmentManager = getFragmentManager();
                            FragmentTransaction fragmentTransaction = fragmentManager
                                    .beginTransaction()
                                    .replace(R.id.nav_host_fragment, new FragmentPets());
                            fragmentTransaction.commit();

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
            }


        });
        //validacion
        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        name.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.edit_new_name, "(^[ÁÉÍÓÚA-Za-záéíóú ]{3,30}$)", R.string.invalid_name);
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
                awesomeValidation.addValidation(getActivity(), R.id.edit_new_breed, "(^[ÁÉÍÓÚA-Za-záéíóú ]{3,30}$)", R.string.invalid_name);
                if (!awesomeValidation.validate()) {
                    breed.setError(getContext().getResources().getString(R.string.invalid_name));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        openCameraBtn.setOnClickListener(this::cameraAccess);
        openGalleryBtn.setOnClickListener(this::loadImage);
        return root;
    }

    public void cameraAccess(View view) {
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (intent.resolveActivity(getActivity().getPackageManager()) != null) {
            startActivityForResult(intent, 1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        System.out.println(requestCode);
        if (requestCode == 1 && resultCode == -1) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int currentBitMapWidth = image.getWidth();
            int currentBitMapHeight = image.getHeight();
            int newWidth = image.getWidth() / 4;
            int newHeight = image.getHeight() / 4;
//            int newWidth = imageView.getWidth();
//            int newHeight = (int)
//                    Math.floor((double) currentBitMapHeight * (double) newWidth / (double) currentBitMapWidth);
            Bitmap bitmap = Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
            imageView.setImageBitmap(bitmap);
        }
        if (resultCode == -1) {
            Uri path = data.getData();
            imageView.setImageURI(path);
        }
    }

    public void loadImage(View view) {
        Intent intent
                = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent, getContext().getResources().getString(R.string.toast_Select_the_app)), 10);
    }


}
