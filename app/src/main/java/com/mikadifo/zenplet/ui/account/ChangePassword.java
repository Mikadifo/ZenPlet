package com.mikadifo.zenplet.ui.account;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

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
import com.mikadifo.zenplet.AES;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.service.OwnerService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.ui.SignUpActivity;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link ChangePassword#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ChangePassword extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    AwesomeValidation awesomeValidation;

    public ChangePassword() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment account.
     */
    // TODO: Rename and change types and number of parameters
    public static ChangePassword newInstance(String param1, String param2) {
        ChangePassword fragment = new ChangePassword();
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
        View root = inflater.inflate(R.layout.fragment_change_password, container, false);
        Button btn = root.findViewById(R.id.btnSaveNewPassword);
        EditText ownerOldPassword = root.findViewById(R.id.edit_old_pwd);
        EditText newPassword = root.findViewById(R.id.edit_new_passwords);
        EditText confirmNewPassword = root.findViewById(R.id.edit_repeat_passwords);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ownerOldPassword.getText().toString().isEmpty() || newPassword.getText().toString().isEmpty() || confirmNewPassword.getText().toString().isEmpty()) {
                    Toast.makeText(v.getContext(), getContext().getResources().getString(R.string.toast_cannot_be_changed), Toast.LENGTH_LONG).show();
                } else {
                    CallWithToken callWithTokentoken = new CallWithToken();
                    Retrofit retrofit = callWithTokentoken.getCallToken();
                    OwnerService ownerService = retrofit.create(OwnerService.class);
                    Call<Owner> call = ownerService.getOwnerById(SignUpActivity.ownerNew.getOwnerId());
                    call.enqueue(new Callback<Owner>() {
                        @Override
                        public void onResponse(Call<Owner> call, Response<Owner> response) {
                            System.out.println(response.body());
                            System.out.println(SignUpActivity.ownerNew);
                            String encryptedOldPassword = AES.encrypt(ownerOldPassword.getText().toString());
                            if (encryptedOldPassword.equals(SignUpActivity.ownerNew.getOwnerPassword())) {
                                if (newPassword.getText().toString().equals(confirmNewPassword.getText().toString())) {
                                    String encryptedNewPassword = AES.encrypt(newPassword.getText().toString());
                                    SignUpActivity.ownerNew.setOwnerPassword(encryptedNewPassword);
                                    Call<Owner> callUpdate = ownerService.updateOwner(SignUpActivity
                                            .ownerNew.getOwnerId(), SignUpActivity.ownerNew);
                                    callUpdate.enqueue(new Callback<Owner>() {
                                        @Override
                                        public void onResponse(Call<Owner> call, Response<Owner> response) {

                                            Toast.makeText(v.getContext(), getContext().getResources().getString(R.string.toast_changed_password_successfully), Toast.LENGTH_LONG).show();
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
                                } else {
                                    Toast.makeText(v.getContext(), getContext().getResources().getString(R.string.toast_new_passwords_do_not_match), Toast.LENGTH_LONG).show();
                                }
                            } else {
                                Toast.makeText(v.getContext(), getContext().getResources().getString(R.string.toast_the_password_is_not_correct), Toast.LENGTH_LONG).show();
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
            }

        });

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        ownerOldPassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.edit_old_pwd, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,40}$", R.string.invalid_password);
                if (!awesomeValidation.validate()) {
                    ownerOldPassword.setError(getContext().getResources().getString(R.string.invalid_password));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        newPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.edit_new_passwords, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,40}$", R.string.invalid_password);
                if (!awesomeValidation.validate()) {
                    newPassword.setError(getContext().getResources().getString(R.string.invalid_password));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        confirmNewPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(getActivity(), R.id.edit_repeat_passwords, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,40}$", R.string.invalid_password);
                if (!awesomeValidation.validate()) {
                    confirmNewPassword.setError(getContext().getResources().getString(R.string.invalid_password));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        return root;
    }

}
