package com.mikadifo.zenplet.ui.pets;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.camera.CameraPosition;
import com.mapbox.mapboxsdk.camera.CameraUpdateFactory;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.LostPet;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.service.LostPetService;
import com.mikadifo.zenplet.API.service.PetService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;
import com.mikadifo.zenplet.ui.lost_pets_list.LostPetsList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PostLostPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PostLostPet extends Fragment {
    public String lostPetLocation;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    AwesomeValidation awesomeValidation;


    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public PostLostPet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment PostLostPet.
     */
    // TODO: Rename and change types and number of parameters
    public static PostLostPet newInstance(String param1, String param2) {
        PostLostPet fragment = new PostLostPet();
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
        Mapbox.getInstance(getContext().getApplicationContext(),  getString(R.string.mapbox_access_token));

        View root = inflater.inflate(R.layout.fragment_post_lost_pet, container, false);
        MapView mapView;
        mapView = (MapView) root.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {

                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        mapboxMap.addOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
                            @Override
                            public boolean onMapLongClick(@NonNull LatLng point) {
                                System.out.println(point);
                                mapboxMap.addMarker(new MarkerOptions()
                                        .position(point));

                                lostPetLocation = point.getLongitude()+","+point.getLatitude();

                                return true;
                            }
                        });
                    }
                });
            }
        });
        Button btn = root.findViewById(R.id.btnPublishLostPet);
        EditText additionalInfo = root.findViewById(R.id.texteditAdditionalInfo);
                btn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        LostPet lostPet = new LostPet(
                                FragmentPets.selectedPet,
                                SignUpActivity.ownerNew,
                                lostPetLocation,
                                additionalInfo.getText().toString()
                        );
                        CallWithToken callWithToken = new CallWithToken();
                        Retrofit retrofit = callWithToken.getCallToken();
                        LostPetService lostPetService = retrofit.create(LostPetService.class);
                        Call<LostPet> call = lostPetService.saveLostPet(lostPet);
                        call.enqueue(new Callback<LostPet>() {
                            @Override
                            public void onResponse(Call<LostPet> call, Response<LostPet> response) {
                                System.out.println(response.body());
                                if (response.body().getOwner().getOwnerId() == 0) {
                                    System.out.println(getContext().getResources().getString(R.string.toast_An_error_has_been_ocurred_while_saving_lost_pet));
                                    Toast.makeText(root.getContext(), getContext().getResources().getString(R.string.toast_An_error_has_been_ocurred_while_saving_lost_pet), Toast.LENGTH_LONG).show();
                                } else {
                                    Toast.makeText(root.getContext(), getContext().getResources().getString(R.string.toast_The_data_has_been_save_successfully), Toast.LENGTH_LONG).show();
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction()
                                            .replace(R.id.nav_host_fragment, new LostPetsList());
                                    fragmentTransaction.commit();
                                }
                            }

                            @Override
                            public void onFailure(Call<LostPet> call, Throwable t) {
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
        additionalInfo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(),R.id.texteditAdditionalInfo,"(^[ÁÉÍÓÚA-Za-záéíóú ]{10,300}$)", R.string.invalid_info);
                if(!awesomeValidation.validate()){
                    additionalInfo.setError(getContext().getResources().getString(R.string.invalid_info));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        return root;
    }
}