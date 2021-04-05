package com.mikadifo.zenplet.ui.account;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.service.OwnerService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.nav.BottomNavActivity;
import com.mikadifo.zenplet.ui.LogInActivity;
import com.mikadifo.zenplet.ui.SignUpActivity;
import com.mikadifo.zenplet.ui.pets.FragmentPets;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link Account1#newInstance} factory method to
 * create an instance of this fragment.
 */
public class Account1 extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


    public Account1() {


    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment Account1.
     */
    // TODO: Rename and change types and number of parameters
    public static Account1 newInstance(String param1, String param2) {
        Account1 fragment = new Account1();
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
        View root = inflater.inflate(R.layout.fragment_account1, container, false);
        EditText username = root.findViewById(R.id.edit_username);
        EditText email = root.findViewById(R.id.edit_email);
        EditText phone = root.findViewById(R.id.edit_phone);
       //
        username.setText( SignUpActivity.ownerNew.getOwnerName());
        email.setText(SignUpActivity.ownerNew.getOwnerEmail());
        phone.setText(SignUpActivity.ownerNew.getOwnerPhoneNumber());
        Button btns = root.findViewById(R.id.btnSave);
        Button btnChangePassword = root.findViewById(R.id.btnChangePassword3);
        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FragmentManager fragmentManager = getFragmentManager();
                FragmentTransaction fragmentTransaction = fragmentManager
                        .beginTransaction()
                        .replace(R.id.nav_host_fragment, new ChangePassword());
                fragmentTransaction.commit();
            }
        });
        btns.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                CallWithToken callWithToken= new CallWithToken();
                Retrofit retrofit = callWithToken.getCallToken();
                OwnerService ownerService = retrofit.create(OwnerService.class);
                Call<Owner> call = ownerService.getOwnerById(SignUpActivity.ownerNew.getOwnerId());
                call.enqueue(new Callback<Owner>() {
                    @Override
                    public void onResponse(Call<Owner> call, Response<Owner> response) {
                        SignUpActivity.ownerNew = response.body();

                        if(SignUpActivity.ownerNew.getOwnerName().equals(username.getText().toString())&&
                                SignUpActivity.ownerNew.getOwnerEmail().equals(email.getText().toString())&&
                                SignUpActivity.ownerNew.getOwnerPhoneNumber().equals(phone.getText().toString())
                        ){
                            Toast.makeText(view.getContext(), "The data is the same and cannot be changed", Toast.LENGTH_LONG).show();

                           }else {
                            SignUpActivity.ownerNew.setOwnerName(username.getText().toString());
                            SignUpActivity.ownerNew.setOwnerEmail(email.getText().toString());
                            SignUpActivity.ownerNew.setOwnerPhoneNumber(phone.getText().toString());
                            Call<Owner> callupdate = ownerService.updateOwner(SignUpActivity.ownerNew.getOwnerId(),SignUpActivity.ownerNew);
                            callupdate.enqueue(new Callback<Owner>() {
                                @Override
                                public void onResponse(Call<Owner> call, Response<Owner> response) {
                                    Toast.makeText(view.getContext(), "Successfully saved changes", Toast.LENGTH_LONG).show();
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

        return root;
    }


}