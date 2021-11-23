package com.example.mycode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class LoginActivity extends AppCompatActivity {
    private  static int SPLASH_SCREEN2 = 5000;
    Button callSingUp;
    ImageView logoimage_login;
    TextView logoname_login;
    TextInputLayout user_email,password;

    private FirebaseAuth mAuthFire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);

        mAuthFire = FirebaseAuth.getInstance();
        logoimage_login = (ImageView)findViewById(R.id.logoimage_login);
        logoname_login = (TextView) findViewById(R.id.logoname_login);
        user_email = (TextInputLayout) findViewById(R.id.user_email);
        password = (TextInputLayout) findViewById(R.id.password);
        // if  user already logged then activity redirect in dashbaord
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        if(user!=null){
            Intent intent = new Intent(LoginActivity.this,Dashboard.class);
            startActivity(intent);
            finish();
        }


    }

    public void singup_screen(View view) {
        Intent intent = new Intent(LoginActivity.this,SignUp.class);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(logoimage_login,"logo_image");
        pairs[1] = new Pair<View,String>(logoname_login,"logo_text");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(LoginActivity.this,pairs);
            startActivity(intent,options.toBundle());
            finish();}
    }


    private Boolean validateemail(){
        String val = user_email.getEditText().getText().toString();

        if(val.isEmpty()) {
            user_email.setError("Field connot be empty");
            return false;
        }else {
            user_email.setError(null);

            return true;
        }
    }

    private Boolean validatepassword(){
        String val = password.getEditText().getText().toString();
        if(val.isEmpty()) {
            password.setError("Field connot be empty");
            return false;
        }else {
            password.setError(null);
            password.setErrorEnabled(false);
            return true;
        }
    }



    public void UserLogin(View view) {

        if(!validateemail() | !validatepassword() ){ return; }


        String email = user_email.getEditText().getText().toString();
        String pass = password.getEditText().getText().toString();

        mAuthFire.signInWithEmailAndPassword(email,pass).addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                    if (task.isSuccessful()){
                        Toast.makeText(LoginActivity.this, "Login Successfully", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(LoginActivity.this,Dashboard.class));
                        finish();
                    }else{
                        Toast.makeText(LoginActivity.this, "Invalid User and Password", Toast.LENGTH_SHORT).show();
                    }
            }
        });
    }

}