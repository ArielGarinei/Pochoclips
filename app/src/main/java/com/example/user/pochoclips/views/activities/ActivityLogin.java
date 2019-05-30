package com.example.user.pochoclips.views.activities;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.user.pochoclips.R;
import com.example.user.pochoclips.models.POJO.User;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.Arrays;


public class ActivityLogin extends AppCompatActivity {

    /*private TextInputLayout inputLayoutEmail;
    private TextInputLayout inputLayoutContraseña;
    private EditText editTextEmail;
    private EditText editTextContraseña;*/
    private Button buttonComencemos;
    private ImageView imageViewGoogleLogin;
    private ImageView imageViewFacebookLogin;
    private ProgressBar progressBar;
    private TextView textViewCargando;

    private LoginButton loginButtonFacebook;
    private SignInButton loginButtonGoogle;

    private static final String EMAIL = "email";
    private static final String PUBLIC_PROFILE = "public_profile";

    private CallbackManager callbackManager;
    private GoogleApiClient googleApiClient;
    private FirebaseAuth firebaseAuth;
    private FirebaseAuth.AuthStateListener authStateListener;
    private GoogleSignInClient mGoogleSignInClient;
    private static final Integer RC_SIGN_IN = 123;

    @Override
    protected void onStart() {
        super.onStart();
        firebaseAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        firebaseAuth.removeAuthStateListener(authStateListener);
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_login);
        callbackManager = CallbackManager.Factory.create();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();



        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);


        /*inputLayoutEmail = findViewById(R.id.editTextUsuarioOEmail);
        inputLayoutEmail.setError("Ingresar un Email Valido");
        inputLayoutContraseña = findViewById(R.id.editTextContraseña);
        editTextEmail = findViewById(R.id.editTextUsuarioOEmailDesign);
        editTextContraseña = findViewById(R.id.editTextContraseñaDesign);*/

        buttonComencemos = findViewById(R.id.buttonComencemos);
        loginButtonGoogle = findViewById(R.id.login_button_google);
        progressBar = findViewById(R.id.progressBar_ActivityLogin);
        textViewCargando = findViewById(R.id.textViewCargando_ActivityLogin);

        buttonComencemos.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                intentAInicioActivity();
            }
        });

        firebaseAuth = FirebaseAuth.getInstance();
        authStateListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser usuarioActual = firebaseAuth.getCurrentUser();
                if (usuarioActual != null) {
                    intentAInicioActivity();
                }
            }
        };

        loginButtonFacebook = findViewById(R.id.login_button_facebook);
        loginButtonFacebook.setReadPermissions(Arrays.asList(EMAIL, PUBLIC_PROFILE));
        loginButtonFacebook.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                // App code
                Toast.makeText(ActivityLogin.this, "Logeo Exitoso", Toast.LENGTH_SHORT).show();
                handleFacebookAccessToken(loginResult.getAccessToken());

            }
            @Override
            public void onCancel() {
                // App code
                Toast.makeText(ActivityLogin.this, "Logeo Cancelado", Toast.LENGTH_SHORT).show();
            }
            @Override
            public void onError(FacebookException exception) {
                // App code
                Toast.makeText(ActivityLogin.this, "Logeo Erroneo", Toast.LENGTH_SHORT).show();
            }
        });


        loginButtonGoogle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                signIn();
            }
        });

        //TODO: esto es para que no te deje entrar al login si ya estas logeado.
/*       AccessToken accessToken = AccessToken.getCurrentAccessToken();
       boolean isLoggedIn = accessToken != null && !accessToken.isExpired();
       if (isLoggedIn){
           Intent intent = new Intent(this,ActivityInicio.class);
           startActivity(intent);
       }*/
    }

    public void intentAInicioActivity() {
        Intent intent = new Intent(ActivityLogin.this, ActivityInicio.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN){
            // MAnejo Google
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                Toast.makeText(ActivityLogin.this, "Ocurrio un error en el LogIn", Toast.LENGTH_LONG).show();
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            FirebaseUser user =FirebaseAuth.getInstance().getCurrentUser();
                            user.getPhotoUrl();
                            guardarUsuario(user);
                            intentAInicioActivity();
                        } else {
                            Toast.makeText(ActivityLogin.this, "Ocurrio un error en el LogIn", Toast.LENGTH_LONG).show();
                        }
                    }
                });
    }

    private void handleFacebookAccessToken(AccessToken token) {
        progressBar.setVisibility(View.VISIBLE);
        textViewCargando.setVisibility(View.VISIBLE);
        loginButtonFacebook.setVisibility(View.GONE);
        loginButtonGoogle.setVisibility(View.GONE);
        /*inputLayoutContraseña.setVisibility(View.GONE);
        inputLayoutEmail.setVisibility(View.GONE);*/
        buttonComencemos.setVisibility(View.GONE);

        final FirebaseAuth firebaseAuth;
        firebaseAuth = FirebaseAuth.getInstance();

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(Task<AuthResult> task) {
                        if (!task.isSuccessful()) {
                            Toast.makeText(ActivityLogin.this, "Ocurrio un Error en el Login", Toast.LENGTH_LONG).show();
                        } else {
                            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                            guardarUsuario(firebaseUser);
                            progressBar.setVisibility(View.GONE);
                            textViewCargando.setVisibility(View.GONE);
                            loginButtonFacebook.setVisibility(View.VISIBLE);
                            loginButtonGoogle.setVisibility(View.VISIBLE);
                            /*inputLayoutContraseña.setVisibility(View.VISIBLE);
                            inputLayoutEmail.setVisibility(View.VISIBLE);*/
                            buttonComencemos.setVisibility(View.VISIBLE);
                        }
                    }
                });
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
