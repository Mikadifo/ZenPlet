package com.mikadifo.zenplet.ui.pets;

import android.app.Dialog;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.provider.MediaStore;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.content.Intent;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;


import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.service.OwnerService;
import com.mikadifo.zenplet.API.service.PetService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

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
    private Owner owner;
    private boolean settedMoreOwners;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private ImageView imageView;

    public NewPet() {
        // Required empty public constructor
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
        imageView = (ImageView)root.findViewById(R.id.imgFoto);
        Button openCameraBtn = root.findViewById(R.id.btnCamara);
        Button openGalleryBtn = root.findViewById(R.id.btnGaleria);
        Button addPet = root.findViewById(R.id.btnViewPetOwners);
        Button btn = root.findViewById(R.id.btnCreatPet);
        EditText name = root.findViewById(R.id.edit_new_name);
        EditText size = root.findViewById(R.id.edit_new_sizze);
        EditText genre = root.findViewById(R.id.edit_new_genre);
        EditText breed = root.findViewById(R.id.edit_new_breed);
        EditText birthdate = root.findViewById(R.id.edit_new_birthdate);


        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallWithToken callWithToken = new CallWithToken();
                Retrofit retrofit = callWithToken.getCallToken();

                pet.setPetName(name.getText().toString());
                pet.setPetSize(size.getText().toString());
                pet.setPetGenre(genre.getText().toString());
                pet.setPetGenre(breed.getText().toString());
                // falta el cumpleaños pet.set
                SignUpActivity.ownerNew.getOwnerPets().add(pet);

                /*PetService petService = retrofit.create(PetService.class);
                Call<Pet> call = petService.savePet(pet);
                call.enqueue(new Callback<Pet>() {
                    @Override
                    public void onResponse(Call<Pet> call, Response<Pet> response) {
                        System.out.println("Se creo la mascota maeee");
                    }

                    @Override
                    public void onFailure(Call<Pet> call, Throwable t) {
                        try {
                            throw t;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });*/
                OwnerService ownerService = retrofit.create(OwnerService.class);
                Call<Owner> callUpdateFirstOwner = ownerService.updateOwner(SignUpActivity.ownerNew.getOwnerId(), SignUpActivity.ownerNew);
                callUpdateFirstOwner.enqueue(new Callback<Owner>() {
                    @Override
                    public void onResponse(Call<Owner> call, Response<Owner> response) {

                        System.out.println("Se creo maeee el primero ");

                    }

                    @Override
                    public void onFailure(Call<Owner> call, Throwable t) {
                        try {
                            throw t;
                        } catch (Throwable throwable) {
                            throwable.printStackTrace();
                        }
                    }
                });
                if (settedMoreOwners){
                    owner.getOwnerPets().add(pet);
                    Call<Owner> callUpdate = ownerService.updateOwner(owner.getOwnerId(), owner);
                    callUpdate.enqueue(new Callback<Owner>() {
                        @Override
                        public void onResponse(Call<Owner> call, Response<Owner> response) {
                            System.out.println("Vale vale vale mae mae mae mae xd arigato");
                        }

                        @Override
                        public void onFailure(Call<Owner> call, Throwable t) {

                        }
                    });
                }


                /*FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction().replace(R.id.nav_host_fragment, new EditPet());
                fragmentTransaction.commit();*/
            }
        });
        openCameraBtn.setOnClickListener(this::cameraAccess);
        openGalleryBtn.setOnClickListener(this::loadImage);
        addPet.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Dialog dialogPersonalizado = new Dialog(getContext());
                dialogPersonalizado.setContentView(R.layout.dialog_email_pets_owner);
                Button btnAlertaEmailOk = dialogPersonalizado.findViewById(R.id.btnAceptarDialog);
                Button btnAlertaEmailCancel = dialogPersonalizado.findViewById(R.id.BtnCancelarDialogo);

                btnAlertaEmailOk.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        EditText onwerEmail = dialogPersonalizado.getCurrentFocus().findViewById(R.id.OwnerEmail);
                        CallWithToken callWithToken = new CallWithToken();
                        Retrofit retrofit = callWithToken.getCallToken();
                        System.out.println(onwerEmail.getText().toString());
                        OwnerService ownerService = retrofit.create(OwnerService.class);
                        Call<Owner> call = ownerService.getOwnerByEmail(onwerEmail.getText().toString());
                        call.enqueue(new Callback<Owner>() {
                            @Override
                            public void onResponse(Call<Owner> call, Response<Owner> response) {
                                owner = response.body();
                                owner.getOwnerPets().add(pet);
                                settedMoreOwners = true;

                            }

                            @Override
                            public void onFailure(Call<Owner> call, Throwable t) {
                                try {
                                    throw t;
                                } catch (Throwable throwable) {
                                    throwable.printStackTrace();
                                }
                            }
                        });


                    }
                });
                dialogPersonalizado.show();
            }
        });
        return root;
    }

    public void cameraAccess(View view){
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            startActivityForResult(intent,1);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 1 && resultCode == -1) {
            Bitmap image = (Bitmap) data.getExtras().get("data");
            int currentBitMapWidth = image.getWidth();
            int currentBitMapHeight = image.getHeight();
            int newWidth = imageView.getWidth();
            int newHeight = (int)
                    Math.floor((double) currentBitMapHeight * (double) newWidth / (double) currentBitMapWidth);
            Bitmap bitmap = Bitmap.createScaledBitmap(image, newWidth, newHeight, true);
            imageView.setImageBitmap(bitmap);
        }
        if(resultCode == -1) {
            Uri path = data.getData();
            imageView.setImageURI(path);
        }
    }
    public void loadImage(View view){
        Intent intent
                = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        startActivityForResult(Intent.createChooser(intent,"Seleccione la aplicacion"), 10);
    }

}