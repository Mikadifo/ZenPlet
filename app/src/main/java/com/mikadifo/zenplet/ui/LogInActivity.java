package com.mikadifo.zenplet.ui;

import androidx.appcompat.app.AppCompatActivity;

import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.model.Owner;
import com.mikadifo.zenplet.API.service.OwnerService;
import com.mikadifo.zenplet.R;
import com.mikadifo.zenplet.nav.BottomNavActivity;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
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


    public void getLogin(View view){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallWithToken.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        EditText login = this.findViewById(R.id.edit_name_from_login);
        EditText password = this.findViewById(R.id.edit_password_from_login);
        OwnerService ownerService = retrofit.create(OwnerService.class);
        Call<Owner> call = ownerService.getLogin(login.getText().toString(), password.getText().toString());
        call.enqueue(new Callback<Owner>() {
            @Override
            public void onResponse(Call<Owner> call, Response<Owner> response) {
                if (response.body().getOwnerId()!=0){
                    CallWithToken.token = response.body().getToken();
                    System.out.println("este dice nulo?"+SignUpActivity.ownerNew.getOwnerPets());
                    SignUpActivity.ownerNew = response.body();
                    startActivity(
                            new Intent(LogInActivity.this, BottomNavActivity.class)
                    );
                }else{
                    AlertDialog.Builder dialogo1 = new AlertDialog.Builder(LogInActivity.this);
                    dialogo1.setTitle(getApplicationContext().getResources().getString(R.string.dialog_Important));
                    dialogo1.setMessage(getApplicationContext().getResources().getString(R.string.dialog_This_user_is_not_registered));
                    dialogo1.setIcon(R.drawable.ic_logo);
                    dialogo1.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Toast.makeText(getApplicationContext(),"Ok", Toast.LENGTH_SHORT).show();
                        }
                    });
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
