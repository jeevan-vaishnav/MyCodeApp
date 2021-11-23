package com.example.mycode;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Bundle;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class SignUp extends AppCompatActivity {

    ImageView logoimage_sinup;
    TextView welcome_signup,slogan_name;
    TextInputLayout reg_name,reg_email,reg_phone,reg_pass;

    FirebaseDatabase rootNode;
    private DatabaseReference reference;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener firebaseAuthlistener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        mAuth = FirebaseAuth.getInstance();

        logoimage_sinup = (ImageView)findViewById(R.id.logoimage_sinup);
        welcome_signup = (TextView) findViewById(R.id.welcome_signup);
        slogan_name = (TextView) findViewById(R.id.slogan_name);
        reg_name = (TextInputLayout) findViewById(R.id.name_singup);
        reg_email = (TextInputLayout) findViewById(R.id.email_singup);
        reg_phone = (TextInputLayout) findViewById(R.id.ph_no_singup);
        reg_pass = (TextInputLayout) findViewById(R.id.password_singup);




    }

    public void CallLoginUser(View view) {
        Intent intent = new Intent(SignUp.this,LoginActivity.class);
        Pair[] pairs = new Pair[2];
        pairs[0] = new Pair<View,String>(logoimage_sinup,"logo_image");
        pairs[1] = new Pair<View,String>(welcome_signup,"logo_text");
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
            startActivity(intent, options.toBundle());
            finish();
        }
    }

    private Boolean validateName(){
        String val = reg_name.getEditText().getText().toString();

        if(val.isEmpty()) {
            reg_name.setError("Field connot be empty");
            return false;
        }else {
            reg_name.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }
    /*
    private Boolean validateusername() {
        String val = reg_username.getEditText().getText().toString();
        String noWhiteSpace = "\\A\\w{4,20}\\z";
        if(val.isEmpty()) {
            reg_username.setError("Field connot be empty");
            return false;
        }else  if(!val.matches(noWhiteSpace)){ reg_username.setError("White spcess are not allowed");
            return false;}
        else if (val.length()>=15){
            reg_username.setError("Username to long");
            return false;
        }else {
            reg_username.setError(null);
            return true;
        }
    }

     */
    private Boolean validateemail(){
        String val = reg_email.getEditText().getText().toString();
        String emailPatter = "^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$";
        if(val.isEmpty()) {
            reg_email.setError("Field connot be empty");
            return false;
        }else if(!val.matches(emailPatter)){
            reg_email.setError("Invalid email address");
            return false;
        }else {
            reg_email.setError(null);

            return true;
        }
    }
    private Boolean validatephone(){
        String val = reg_phone.getEditText().getText().toString();

        if(val.isEmpty()) {
            reg_phone.setError("Field connot be empty");
            return false;
        }else {
            reg_phone.setError(null);
            reg_phone.setErrorEnabled(false);
            return true;
        }
    }
    private Boolean validatepassword(){
        String val = reg_pass.getEditText().getText().toString();
        //String password2 = "helloword#123";
        String regex1 = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{8,20}$";
        if(val.isEmpty()) {
            reg_pass.setError("Field connot be empty");
            return false;
        }else  if (!val.matches(regex1)){
            reg_pass.setError("Password is too weak | e.g Helloword#123");
            return false;
        }else {
            reg_pass.setError(null);
            reg_name.setErrorEnabled(false);
            return true;
        }
    }
    public void RegisterUser(View view) {

        if(!validateName() | !validateemail() | !validatephone() | !validatepassword() ){ return; }

        //Get All the value in String
        String name =reg_name.getEditText().getText().toString();
        String email  = reg_email.getEditText().getText().toString();
        String phone = reg_phone.getEditText().getText().toString();
        String password = reg_pass.getEditText().getText().toString();

        mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignUp.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(!task.isSuccessful()){
                    Toast.makeText(SignUp.this,"Authentication  failed",Toast.LENGTH_LONG).show();
                }else {
                    String user_id = mAuth.getCurrentUser().getUid();
                    reference = FirebaseDatabase.getInstance().getReference().child("Users").child(user_id);
                    UserHelperClass helperClass = new UserHelperClass(name,email,phone,password);
                    reference.setValue(helperClass);
                    Toast.makeText(SignUp.this,"Registration is Success",Toast.LENGTH_LONG).show();
                    startActivity(new Intent(SignUp.this,Dashboard.class));
                    finish();
                }
            }
        }) ;





    }
}
