package com.mikadifo.zenplet.ui.pets;

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
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.LostPet;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.service.LostPetService;
import com.mikadifo.zenplet.API.service.PetsFoundService;
import com.mikadifo.zenplet.R;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link EditLostPet#newInstance} factory method to
 * create an instance of this fragment.
 */
public class EditLostPet extends Fragment {
    private String lostPetLocation;
    private Marker marcadorPost;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private LostPet editingLostPet;

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AwesomeValidation awesomeValidation;

    public EditLostPet(LostPet editingLostPet) {
        this.editingLostPet = editingLostPet;
    }

    public EditLostPet() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment EditLostPet.
     */
    // TODO: Rename and change types and number of parameters
    public static EditLostPet newInstance(String param1, String param2) {
        EditLostPet fragment = new EditLostPet();
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
        View root = inflater.inflate(R.layout.fragment_edit_lost_pet, container, false);
        EditText additionalInfo = root.findViewById(R.id.editTextTextMultiLine);
        additionalInfo.setText(this.editingLostPet.getLostPetAdditionalInfo());
        MapView mapView;
        mapView = (MapView) root.findViewById(R.id.mapview);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        String [] location = editingLostPet.getLostPetLocation().split(",");
                        marcadorPost = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(location[1]), Double.parseDouble(location[0])))
                                .title("Your pet"));
                        mapboxMap.addOnMapLongClickListener(new MapboxMap.OnMapLongClickListener() {
                            @Override
                            public boolean onMapLongClick(@NonNull LatLng point) {
                                mapboxMap.removeMarker(marcadorPost);
                                marcadorPost = mapboxMap.addMarker(new MarkerOptions()
                                        .position(point));

                                lostPetLocation = point.getLatitude()+","+point.getLongitude();

                                return true;
                            }
                        });
                    }
                });
            }
        });
        Button buttonSave = root.findViewById(R.id.btnSaveAdditionalInfo);
        Button buttonPetFound = root.findViewById(R.id.btnPetFound);

        buttonSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(additionalInfo.getText().toString().isEmpty()||lostPetLocation.isEmpty()){
                    Toast.makeText(view.getContext(), getContext().getResources().getString(R.string.toast_cannot_be_changed), Toast.LENGTH_LONG).show();
                }else{
                    LostPet lostPetEdit = new LostPet(
                            new Pet(),
                            new Owner(),
                            lostPetLocation,
                            additionalInfo.getText().toString()
                    );
                    CallWithToken callWithToken = new CallWithToken();
                    Retrofit retrofit = callWithToken.getCallToken();
                    LostPetService lostPetService = retrofit.create(LostPetService.class);
                    Call<LostPet> call = lostPetService.updateLostPet(FragmentPets.selectedPet.getPetId(), lostPetEdit);
                    call.enqueue(new Callback<LostPet>() {
                        @Override
                        public void onResponse(Call<LostPet> call, Response<LostPet> response) {
                            System.out.println(response.body());
                            if (response.body().getLostPetAdditionalInfo().equals(lostPetEdit.getLostPetAdditionalInfo())) {
                                Toast.makeText(root.getContext(), getContext().getResources().getString(R.string.toast_Lost_Pet_Updated), Toast.LENGTH_LONG).show();
                                FragmentManager fragmentManager = getFragmentManager();
                                FragmentTransaction fragmentTransaction = fragmentManager
                                        .beginTransaction()
                                        .replace(R.id.nav_host_fragment, new EditPet());
                                fragmentTransaction.commit();
                            } else {
                                Toast.makeText(root.getContext(), getContext().getResources().getString(R.string.toast_Error_updating_lost_petd), Toast.LENGTH_LONG).show();
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

            }
        });
        buttonPetFound.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallWithToken callWithToken = new CallWithToken();
                Retrofit retrofit = callWithToken.getCallToken();
                LostPetService lostPetService = retrofit.create(LostPetService.class);
                Call<Void> call = lostPetService.deleteLostPet(FragmentPets.selectedPet.getPetId());

                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        System.out.println(response.body());
                        if (response.body() == null) {
                            CallWithToken callTokenPetFound = new CallWithToken();
                            Retrofit retrofitPetFound = callTokenPetFound.getCallToken();
                            PetsFoundService petsFoundService = retrofitPetFound.create(PetsFoundService.class);
                            Call<Long> callPetFound = petsFoundService.addPetsFound();
                            callPetFound.enqueue(new Callback<Long>() {
                                @Override
                                public void onResponse(Call<Long> call, Response<Long> response) {
                                    System.out.println(response.body());
                                    Toast.makeText(root.getContext(), getContext().getResources().getString(R.string.toast_We_are_happy_to_help_you_to_find_your_pet), Toast.LENGTH_LONG).show();
                                    editingLostPet = null;
                                    FragmentManager fragmentManager = getFragmentManager();
                                    FragmentTransaction fragmentTransaction = fragmentManager
                                            .beginTransaction()
                                            .replace(R.id.nav_host_fragment, new EditPet());
                                    fragmentTransaction.commit();
                                }

                                @Override
                                public void onFailure(Call<Long> call, Throwable t) {
                                    try {
                                        throw t;
                                    } catch (Throwable throwable) {
                                        throwable.printStackTrace();
                                    }
                                }
                            });
                        }
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
        additionalInfo.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(),R.id.editTextTextMultiLine,"(^[ÁÉÍÓÚA-Za-záéíóú ]{10,300}$)", R.string.invalid_name);
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