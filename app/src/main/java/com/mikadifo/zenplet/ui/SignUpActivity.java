package com.mikadifo.zenplet.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.service.OwnerService;
import com.mikadifo.zenplet.R;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignUpActivity extends AppCompatActivity {
    public static Owner ownerNew = new Owner();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
    }

    public void registerOwner(View view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallWithToken.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        EditText ownerName = this.findViewById(R.id.edit_name_from_signup);
        EditText ownerEmail = this.findViewById(R.id.edit_email_from_signup);
        EditText ownerPassword = this.findViewById(R.id.edit_password_from_signup);
        EditText ownerPhoneNumber = this.findViewById(R.id.editTextPhone);

        ownerNew.setOwnerName(ownerName.getText().toString());
        ownerNew.setOwnerEmail(ownerEmail.getText().toString());
        ownerNew.setOwnerPassword(ownerPassword.getText().toString());
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
