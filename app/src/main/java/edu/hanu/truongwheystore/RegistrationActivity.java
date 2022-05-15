package edu.hanu.truongwheystore;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;

public class RegistrationActivity extends AppCompatActivity {
    private Button signUpBtn;
    private EditText nameEdt;
    private EditText phoneEdt;
    private EditText emailEdt;
    private EditText passwordEdt;
    private TextView signInTxt;
    private FirebaseAuth mAuth;
    private DatabaseReference mDatabaseRef;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        mDatabaseRef = FirebaseDatabase.getInstance().getReference();
        initView();
        initListener();
    }

    private void initListener() {
        signInTxt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RegistrationActivity.this, LoginActivity.class));
                finishAffinity();
            }
        });

        signUpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });
    }

    private void initView() {
        signUpBtn = findViewById(R.id.signup_btn);
        nameEdt = findViewById(R.id.edt_username);
        emailEdt = findViewById(R.id.edt_email);
        passwordEdt = findViewById(R.id.edt_password);
        signInTxt = findViewById(R.id.signin_register_txt);
        phoneEdt = findViewById(R.id.edt_phoneNo);
    }

    private void registerUser() {
        String name = nameEdt.getText().toString();
        String phoneNumb = phoneEdt.getText().toString();
        String email = emailEdt.getText().toString();
        String password = passwordEdt.getText().toString();

        if (TextUtils.isEmpty(name)) {
            Toast.makeText(this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(email)) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(phoneNumb)) {
            Toast.makeText(this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        }
        if (password.length() < 6) {
            Toast.makeText(this, "Password length must greater than 6 characters", Toast.LENGTH_SHORT).show();
        }
        final ProgressDialog pd = new ProgressDialog(this);
        pd.setMessage("Please wait");
        pd.show();
        mAuth = FirebaseAuth.getInstance();
        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, task -> {
                    if (task.isSuccessful()) {
                        HashMap<String, Object> map = new HashMap<>();
                        map.put("id", mAuth.getCurrentUser().getUid());
                        map.put("Name", name);
                        map.put("Phone number", phoneNumb);
                        map.put("Email", email);
                        mDatabaseRef.child("Users").child(mAuth.getCurrentUser().getUid())
                                .setValue(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {
                                if (task.isSuccessful()) {
                                    pd.dismiss();
                                    Toast.makeText(RegistrationActivity.this, "Register success.",
                                            Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(RegistrationActivity.this, LoginActivity.class);
                                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        });


                    } else {
                        pd.dismiss();
                        Toast.makeText(RegistrationActivity.this, "Register failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }
}