package com.mikadifo.zenplet.ui.pets;

import android.content.Intent;
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
import android.widget.Button;
import android.widget.ImageView;

import com.mikadifo.zenplet.R;

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

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    ImageView imageView;
     View root;


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

        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_edit_pet, container, false);
        imageView=(ImageView)root.findViewById(R.id.foto);

        Button btn = root.findViewById(R.id.btnDelete);

        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new FragmentPets());
                fragmentTransaction.commit();
            }
        });


        Button btno = root.findViewById(R.id.btnLost);

            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    FragmentManager fragmentManager = getFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager
                            .beginTransaction()
                            .replace(R.id.nav_host_fragment, new PostLostPet());
                    fragmentTransaction.commit();
                }
            });
        return root;

    }
    public void AbrirCamara(View view){
        //llamar a un recurso desde el intent - recurso para la camara
        Intent intent= new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if(intent.resolveActivity(getActivity().getPackageManager())!=null){
            //manejar el resultado
            startActivityForResult(intent,1);
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        //comprobar si hay respuesta y resultado
        if (requestCode==1&& resultCode==-1){
            //Recibir imagen
           // Bundle bundle=data.getExtras();
            Bitmap image= (Bitmap)data.getExtras().get("data");
            //mostrar imagen en la pantalla
            imageView.setImageBitmap(image);
        }
        if(resultCode==-1){
            Uri path=data.getData();
            imageView.setImageURI(path);
        }

    }
    public void onclick(View view) {
        cargarImagen();
    }
    public void cargarImagen(){
        //Acceder al contenido de la galeria
        Intent intent= new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setType("image/");
        //recibir el resultado de la foto
        startActivityForResult(Intent.createChooser(intent,"Seleccione la aplicacion"), 10);
    }

}