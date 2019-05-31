package com.example.user.pochoclips.views.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.Toast;
import com.example.user.pochoclips.R;
import com.example.user.pochoclips.models.POJO.User;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.auth.IdpResponse;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;
import java.util.List;


public class ActivityLogin extends AppCompatActivity {


    private static final int MY_REQUEST_CODE = 7117;
    List<AuthUI.IdpConfig> providers;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        //init provider
        providers = Arrays.asList(
                //new AuthUI.IdpConfig.EmailBuilder().build(),
                //new AuthUI.IdpConfig.PhoneBuilder().build(),
                new AuthUI.IdpConfig.FacebookBuilder().build()
                //new AuthUI.IdpConfig.GoogleBuilder().build()
        );

        showSignInOptiosn();

    }

    private void showSignInOptiosn() {
        startActivityForResult(
                AuthUI.getInstance().createSignInIntentBuilder()
                        .setAvailableProviders(providers)
                        .setTheme(R.style.MyTheme)
                        .build(),MY_REQUEST_CODE

        );
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == MY_REQUEST_CODE){
            IdpResponse response = IdpResponse.fromResultIntent(data);
            if (resultCode == RESULT_OK){
                //get user
                FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
                //show email on Toast
                Toast.makeText(ActivityLogin.this, ""+user.getEmail(), Toast.LENGTH_SHORT).show();


                guardarUsuario(user);

                Intent intent = new Intent(ActivityLogin.this, ActivityInicio.class);
                startActivity(intent);
                finish();
            }
            else {
                Toast.makeText(ActivityLogin.this, ""+response.getError().getMessage(), Toast.LENGTH_SHORT).show();
            }
        }
    }


    private void guardarUsuario(FirebaseUser firebaseUser) {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        User user = new User();
        user.setPuntajeRecomendador(0);
        user.setuId(firebaseUser.getUid());
        user.setEmail(firebaseUser.getEmail());
        user.setName(firebaseUser.getDisplayName());
        user.setPhotoPorfile(firebaseUser.getPhotoUrl().toString());
        DatabaseReference databaseReference = firebaseDatabase.getReference("TodosLosUsuarios").child(firebaseUser.getUid());
        DatabaseReference databaseReference1 = firebaseDatabase.getReference("User").child(firebaseUser.getUid()).child("DatosDelUsuario");
        databaseReference.setValue(user);
        databaseReference1.setValue(user);
        finish();
    }
}

