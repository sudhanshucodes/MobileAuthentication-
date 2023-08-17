package com.example.authentication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.authentication.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    TextView alreadyHaveAccount,tvShow,tvShow1;
    EditText userPass,userPass1;
    EditText inputEmail,inputPassword,inputConfirmPassword;
    TextView btnRegister;
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    ProgressDialog progressDialog;
    FirebaseAuth mAuth;
    FirebaseUser mUser;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);  //For Full Screen
        alreadyHaveAccount=findViewById(R.id.alreadyHaveAccount);
        tvShow=findViewById(R.id.txt_show);
        userPass=findViewById(R.id.editTextTextPassword1);

        inputEmail=findViewById(R.id.editTextTextEmailAddress);
        inputPassword=findViewById(R.id.editTextTextPassword1);
        inputConfirmPassword=findViewById(R.id.inputConfirmPassword);
        btnRegister=findViewById(R.id.btnRegister);

        progressDialog = new ProgressDialog(this);

        mAuth=FirebaseAuth.getInstance();
        mUser=mAuth.getCurrentUser();




        tvShow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPass.getInputType()==144){           //144 means hide and 129 means show
                    userPass.setInputType(129);
                    tvShow.setText("Hide");
                }else{
                    userPass.setInputType(144);
                    tvShow.setText("Show");
                }
                userPass.setSelection(userPass.getText().length());
            }
        });

        tvShow1=findViewById(R.id.txt_show1);
        userPass1=findViewById(R.id.inputConfirmPassword);


        tvShow1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(userPass1.getInputType()==144){           //144 means hide and 129 means show
                    userPass1.setInputType(129);
                    tvShow1.setText("Hide");
                }else{
                    userPass1.setInputType(144);
                    tvShow1.setText("Show");
                }
                userPass1.setSelection(userPass1.getText().length());
            }
        });
        alreadyHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegisterActivity.this,LoginActivity1.class));

            }
        });


        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PerforAuth();
            }
        });
    }

    private void PerforAuth() {
        String email=inputEmail.getText().toString();
        String password=inputPassword.getText().toString();
        String confirmPassword=inputConfirmPassword.getText().toString();


        if(!email.matches(emailPattern)){
            inputEmail.setError("Enter Context Email");
        }else if(password.isEmpty() || password.length()<6){
            inputPassword.setError("Enter Proper Password");
        }else if(!password.equals(confirmPassword)){
            inputConfirmPassword.setError("Password Not Match Both field");
        }else{
            progressDialog.setMessage("Please Wait while Registration...");
            progressDialog.setTitle("Registration");
            progressDialog.setCanceledOnTouchOutside(false);
            progressDialog.show();
            mAuth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                   if(task.isSuccessful()){
                       progressDialog.dismiss();
                       sendUserToNextActivity();
                       Toast.makeText(RegisterActivity.this, "Registration Successful", Toast.LENGTH_SHORT).show();
                   }else{
                       progressDialog.dismiss();
                       Toast.makeText(RegisterActivity.this, ""+task.getException(), Toast.LENGTH_SHORT).show();
                   }
                }
            });


        }

    }

    private void sendUserToNextActivity() {
      Intent intent=new Intent(RegisterActivity.this,HomeActivity.class);
      intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK|Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(intent);
    }
}