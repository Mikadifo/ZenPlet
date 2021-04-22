package com.mikadifo.zenplet.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.mikadifo.zenplet.AES;
import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.service.OwnerService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.nav.BottomNavActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LogInActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);
    }

    public void getLogin(View view) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallWithToken.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        EditText login = this.findViewById(R.id.edit_name_from_login);
        EditText password = this.findViewById(R.id.edit_password_from_login);

        String encryptedPassword = AES.encrypt(password.getText().toString());
        System.out.println(encryptedPassword);

        OwnerService ownerService = retrofit.create(OwnerService.class);
        Call<Owner> call = ownerService.getLogin(login.getText().toString(), encryptedPassword);
        call.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                System.out.println(response.body());

                if (response.body().getOwnerId() != 0) {
                    CallWithToken.token = response.body().getToken();
                    SignUpActivity.ownerNew = response.body();
                    startActivity(
                            new Intent(LogInActivity.this, BottomNavActivity.class)
                    );
                } else {
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(LogInActivity.this);
                    dialogo1.setTitle(getApplicationContext().getResources().getString(R.string.dialog_Important));
                    dialogo1.setMessage(getApplicationContext().getResources().getString(R.string.dialog_This_user_is_not_registered));
                    dialogo1.setIcon(R.drawable.ic_logo);
                    dialogo1.show();
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
