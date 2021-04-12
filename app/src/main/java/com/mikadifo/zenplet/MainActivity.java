package com.mikadifo.zenplet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.mikadifo.zenplet.API.CallWithToken;
import com.mikadifo.zenplet.API.service.PetsFoundService;
import com.mikadifo.zenplet.nav.BottomNavActivity;
import com.mikadifo.zenplet.ui.LogInActivity;
import com.mikadifo.zenplet.ui.SignUpActivity;
import com.mikadifo.zenplet.ui.pets.PostLostPet;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(CallWithToken.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create()).build();

        PetsFoundService petsFoundService = retrofit.create(PetsFoundService.class);
        Call<Long> call = petsFoundService.getPetsFound();
        call.enqueue(new Callback<Long>() {
            @Override
            public void onResponse(Call<Long> call, Response<Long> response) {
                System.out.println(response.body());
                System.out.println(response.body().longValue());
                TextView petsFound = findViewById(R.id.text_pets_found);
                petsFound.setText(response.body().longValue() + " pets found using our app.");
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

    public void toSignUp(View view) {
        startActivity(
                new Intent(this, SignUpActivity.class)
        );
    }

    public void toLogIn(View view) {
        startActivity(
                new Intent(this, LogInActivity.class)
        );
    }

}
