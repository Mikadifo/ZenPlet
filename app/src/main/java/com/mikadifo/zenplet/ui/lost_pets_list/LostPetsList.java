package com.mikadifo.zenplet.ui.lost_pets_list;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.LostPet;
import com.mikadifo.zenplet.API.model.Pet;
import com.mikadifo.zenplet.API.model.PetVaccine;
import com.mikadifo.zenplet.API.service.LosPetAdapter;
import com.mikadifo.zenplet.API.service.LostPetService;
import com.mikadifo.zenplet.API.service.PetAdapter;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;
import com.mikadifo.zenplet.ui.pets.EditLostPet;
import com.mikadifo.zenplet.ui.pets.EditPet;
import com.mikadifo.zenplet.ui.vaccines.EditVaccines;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link LostPetsList#newInstance} factory method to
 * create an instance of this fragment.
 */
public class LostPetsList extends Fragment {
    public static LostPet selectedLostPet;
    private  LostPet lostPet;

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    public LostPetsList() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment LostPetsList.
     */
    // TODO: Rename and change types and number of parameters
    public static LostPetsList newInstance(String param1, String param2) {
        LostPetsList fragment = new LostPetsList();
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
        View root = inflater.inflate(R.layout.fragment_lost_pets_list, container, false);
        ListView listView =root.findViewById(R.id.list_lost_pets);
        CallWithToken callWithToken = new CallWithToken();
        Retrofit retrofit = callWithToken.getCallToken();
        LostPetService lostPetService = retrofit.create(LostPetService.class);
        Call<List<LostPet>> call = lostPetService.getLostPets();
        call.enqueue(new Callback<List<LostPet>>() {
            @Override
            public void onResponse(Call<List<LostPet>> call, Response<List<LostPet>> response) {
                if (response.body() != null){
                    for (LostPet lostPet: response.body()){
                        List<LostPet> lostPetList = new ArrayList<LostPet>(response.body());
                        listView.setAdapter(new LosPetAdapter(root.getContext(), R.layout.list_lost_pets, lostPetList));
                    }
                    listView.setOnItemClickListener(lostPetlistener);
                }
            }

            @Override
            public void onFailure(Call<List<LostPet>> call, Throwable t) {
                try {
                    throw t;
                } catch (Throwable throwable) {
                    throwable.printStackTrace();
                }
            }
        });

        return root;
    }

    private AdapterView.OnItemClickListener lostPetlistener = (adapterView, view, position, id) -> {
        selectedLostPet = (LostPet) adapterView.getItemAtPosition(position);
        FragmentManager fragmentManager = getFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager
                .beginTransaction()
                .replace(R.id.nav_host_fragment, new LostPetInfo());
        fragmentTransaction.commit();
    };


}
