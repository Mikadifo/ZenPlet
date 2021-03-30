package com.mikadifo.zenplet;

import androidx.appcompat.app.AppCompatActivity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.mikadifo.zenplet.nav.BottomNavActivity;
import com.mikadifo.zenplet.ui.LogInActivity;
import com.mikadifo.zenplet.ui.SignUpActivity;
import com.mikadifo.zenplet.ui.pets.PostLostPet;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        setTheme(R.style.Theme_AppCompat_NoActionBar);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
