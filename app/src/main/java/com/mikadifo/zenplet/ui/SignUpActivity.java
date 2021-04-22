package com.mikadifo.zenplet.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.mikadifo.zenplet.AES;
import com.basgeekball.awesomevalidation.AwesomeValidation;
import com.basgeekball.awesomevalidation.ValidationStyle;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.service.OwnerService;
import com.mikadifo.zenplet.R;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Patterns;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    AwesomeValidation awesomeValidation;
    public static Owner ownerNew = new Owner();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        EditText ownerName = this.findViewById(R.id.edit_name_from_signup);
        EditText ownerEmail = this.findViewById(R.id.edit_email_from_signup);
        EditText ownerPassword = this.findViewById(R.id.edit_password_from_signup);
        EditText ownerPhoneNumber = this.findViewById(R.id.editTextPhone);

        awesomeValidation = new AwesomeValidation(ValidationStyle.BASIC);
        ownerName.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(SignUpActivity.this, R.id.edit_name_from_signup, "(^\\w{3,20}$)", R.string.invalid_username);
                if (!awesomeValidation.validate()) {
                    ownerName.setError(getBaseContext().getResources().getString(R.string.invalid_username));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ownerEmail.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(SignUpActivity.this, R.id.edit_email_from_signup, Patterns.EMAIL_ADDRESS, R.string.invalid_email);
                if (!awesomeValidation.validate()) {
                    ownerEmail.setError(getBaseContext().getResources().getString(R.string.invalid_email));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ownerPassword.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(SignUpActivity.this, R.id.edit_password_from_signup, "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[a-zA-Z]).{8,40}$", R.string.invalid_password);
                if (!awesomeValidation.validate()) {
                    ownerPassword.setError(getBaseContext().getResources().getString(R.string.invalid_password));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        ownerPhoneNumber.addTextChangedListener(new TextWatcher() {

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                awesomeValidation.addValidation(SignUpActivity.this, R.id.editTextPhone, Patterns.PHONE, R.string.invalid_login);

                if (!awesomeValidation.validate()) {
                    ownerPhoneNumber.setError(getBaseContext().getResources().getString(R.string.invalid_phone));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

    public void registerOwner(View view) {
        if (!awesomeValidation.validate()) {
            Toast.makeText(view.getContext(),
                    getBaseContext().getResources().getString(R.string.toast_you_must_complete_the_fields),
                    Toast.LENGTH_LONG).show();
        } else {
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl(CallWithToken.BASE_URL)
                    .addConverterFactory(GsonConverterFactory.create()).build();

            EditText ownerName = this.findViewById(R.id.edit_name_from_signup);
            EditText ownerEmail = this.findViewById(R.id.edit_email_from_signup);
            EditText ownerPassword = this.findViewById(R.id.edit_password_from_signup);
            EditText ownerPhoneNumber = this.findViewById(R.id.editTextPhone);

            ownerNew.setOwnerName(ownerName.getText().toString());
            ownerNew.setOwnerEmail(ownerEmail.getText().toString());
            ownerNew.setOwnerPassword(AES.encrypt(ownerPassword.getText().toString()));
            ownerNew.setOwnerPhoneNumber(ownerPhoneNumber.getText().toString());

            OwnerService ownerService = retrofit.create(OwnerService.class);
            Call<Owner> call = ownerService.saveOwner(ownerNew);
            call.enqueue(new Callback<Owner>() {
                @Override
                public void onResponse(Call<Owner> call, Response<Owner> response) {
                    startActivity(
                            new Intent(SignUpActivity.this, LogInActivity.class)
                    );
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
}
