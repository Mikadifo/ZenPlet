package com.mikadifo.zenplet.ui.lost_pets_list;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.util.Base64;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.mapbox.mapboxsdk.Mapbox;
import com.mapbox.mapboxsdk.annotations.Marker;
import com.mapbox.mapboxsdk.annotations.MarkerOptions;
import com.mapbox.mapboxsdk.geometry.LatLng;
import com.mapbox.mapboxsdk.maps.MapView;
import com.mapbox.mapboxsdk.maps.MapboxMap;
import com.mapbox.mapboxsdk.maps.OnMapReadyCallback;
import com.mapbox.mapboxsdk.maps.Style;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.pets.FragmentPets;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostPetInfo#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostPetInfo extends Fragment {
    private String lostPetInfoLocation;
    private Marker marcadorLostPetInfo;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LostPetInfo() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostPetInfo.
     */
    // TODO: Rename and change types and number of parameters
    public static LostPetInfo newInstance(String param1, String param2) {
        LostPetInfo fragment = new LostPetInfo();
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
        View root = inflater.inflate(R.layout.fragment_lost_pet_info, container, false);
        TextView nameLostPetInfo = root.findViewById(R.id.lostPetNameInfo);
        TextView infoLostPetInfo = root.findViewById(R.id.lostPetInfo);
        ImageView imageLostPetInfo = root.findViewById(R.id.lostPetImageInfo);
        Button btnBack = root.findViewById(R.id.btnBackInfo);
        btnBack.setOnClickListener(butBackListener);
        nameLostPetInfo.setText(LostPetsList.selectedLostPet.getPet().getPetName());
        infoLostPetInfo.setText(LostPetsList.selectedLostPet.getLostPetAdditionalInfo());
        if (FragmentPets.selectedPet!=null){
            byte[] decodedString = Base64.decode(FragmentPets.selectedPet.getPetImage(), Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageLostPetInfo.setImageBitmap(decodedByte);
        }
        MapView mapView;
        mapView = (MapView) root.findViewById(R.id.mapViewInfo);
        mapView.onCreate(savedInstanceState);
        mapView.getMapAsync(new OnMapReadyCallback() {
            @Override
            public void onMapReady(@NonNull MapboxMap mapboxMap) {
                mapboxMap.setStyle(Style.MAPBOX_STREETS, new Style.OnStyleLoaded() {
                    @Override
                    public void onStyleLoaded(@NonNull Style style) {
                        String [] location = LostPetsList.selectedLostPet.getLostPetLocation().split(",");
                        marcadorLostPetInfo = mapboxMap.addMarker(new MarkerOptions()
                                .position(new LatLng(Double.parseDouble(location[0]), Double.parseDouble(location[1])))
                                .title("Your pet"));
                    }
                });
            }
        });

        return root;

    }

    private View.OnClickListener butBackListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            FragmentManager fragmentManager = getFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager
                    .beginTransaction()
                    .replace(R.id.nav_host_fragment, new LostPetsList());
            fragmentTransaction.commit();
        }
    };
}