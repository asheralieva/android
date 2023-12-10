package com.example.myfirebaseapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private EditText editTextRegisterFullName, editTextRegisterEmail, editTextRegisterMobile, editTextRegisterDoB,
            editTextRegisterPwd, editTextRegisterConfirmPwd;
    private ProgressBar progressBar;
    private RadioGroup radioGroupRegisterGender;
    private RadioButton radioButtonRegisterGenderSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        
        getSupportActionBar().setTitle("Register");

        Toast.makeText(RegisterActivity.this, "You cen register now", Toast.LENGTH_SHORT).show();

        /**View - занимает прямоугольную область на экране  отвечает за рисование и обработку.
        * View - базовый класс виджетов, которые используются для создания интерактивных компонентов UI(кнопок, текстовых полей)
        * findViewById - это метод, который находит по присвоенному ему id. Также возвращает экземпляр View, который затем преобразуется в целевой класс*/

        progressBar = findViewById(R.id.progressBar);
        editTextRegisterFullName = findViewById(R.id.edittext_register_fullName);
        editTextRegisterEmail = findViewById(R.id.edittext_register_email);
        editTextRegisterMobile = findViewById(R.id.edittext_register_mobile);
        editTextRegisterDoB = findViewById(R.id.edittext_register_dob);
        editTextRegisterPwd = findViewById(R.id.edittext_register_password);
        editTextRegisterConfirmPwd = findViewById(R.id.edittext_register_confirm_password);

        //clearCheck() - для очистки всех отмеченных переключателей при запуске или возобновлении активности
        //RadioButton for gender
        radioGroupRegisterGender = findViewById(R.id.radio_group_register_gender);
        radioGroupRegisterGender.clearCheck();

        /**Создали кнопку зарегаться, создали экземпляр OnClickListener* и переопределили метод OnClick
         *Назначили OnClickListener нашей кнопке с помощью  в наших фрагментах/действиях метода onCreate.
         *Когда юзер нажимает кнопку, вызывается функция onClick назначенного OnClickListener.*/

        Button buttonRegister = findViewById(R.id.button_register);
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int selectedGenderId = radioGroupRegisterGender.getCheckedRadioButtonId();
                radioGroupRegisterGender = findViewById(selectedGenderId);

                //Obtain the entered data
                String textFullName = editTextRegisterFullName.getText().toString();
                String textEmail = editTextRegisterEmail.getText().toString();
                String textMobile = editTextRegisterMobile.getText().toString();
                String textDoB = editTextRegisterDoB.getText().toString();
                String textPwd = editTextRegisterPwd.getText().toString();
                String textConfirmPwd = editTextRegisterConfirmPwd.getText().toString();
                String textGender; //Невозможно получить значение, прежде чем проверять, выбрана ли какая-либо кнопка или нет.

                if (TextUtils.isEmpty(textFullName)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your full name", Toast.LENGTH_LONG).show();
                    editTextRegisterFullName.setError("Full Name is required");
                    editTextRegisterFullName.requestFocus();
                }else if (TextUtils.isEmpty(textEmail)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Email is required");
                    editTextRegisterEmail.requestFocus();
                }else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your email", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Valid email is required");
                    editTextRegisterEmail.requestFocus();
                }else if (TextUtils.isEmpty(textDoB)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your date of birth", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Date of Birth is required");
                    editTextRegisterEmail.requestFocus();
                }else if (radioGroupRegisterGender.getCheckedRadioButtonId() == -1) {
                    Toast.makeText(RegisterActivity.this, "Please select your gender", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Gender is required");
                    editTextRegisterEmail.requestFocus();
                }else if (TextUtils.isEmpty(textMobile)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your mobile no.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Mobile No. is required");
                    editTextRegisterEmail.requestFocus();
                }else if (textMobile.length() != 10) {
                    Toast.makeText(RegisterActivity.this, "Please re-enter your mobile no.", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Mobile no. should be 10 digits");
                    editTextRegisterEmail.requestFocus();
                }else if (TextUtils.isEmpty(textPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please enter your password", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Password is required");
                    editTextRegisterEmail.requestFocus();
                }else if (textPwd.length() < 6) {
                    Toast.makeText(RegisterActivity.this, "Password should be at least 6 digits", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Password is weak");
                    editTextRegisterEmail.requestFocus();
                }else if (TextUtils.isEmpty(textConfirmPwd)) {
                    Toast.makeText(RegisterActivity.this, "Please confirm your password", Toast.LENGTH_LONG).show();
                    editTextRegisterEmail.setError("Password Confirmation is required");
                    editTextRegisterEmail.requestFocus();
                    //Clear the entered passwords
                    editTextRegisterPwd.clearComposingText();
                    editTextRegisterConfirmPwd.clearComposingText();
                }else {
                    textGender = radioButtonRegisterGenderSelected.getText().toString();
                    progressBar.setVisibility(View.VISIBLE);
                    registerUser(textFullName, textEmail, textGender, textDoB, textConfirmPwd, textMobile, textPwd);

                }

            }
        });


    }
    //Register user using the credentials given
    private void registerUser(String textFullName, String textEmail, String textGender, String textDoB, String textConfirmPwd, String textMobile, String textPwd) {
        FirebaseAuth auth = FirebaseAuth.getInstance();
        auth.createUserWithEmailAndPassword(textEmail, textPwd).addOnCompleteListener(RegisterActivity.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
               if (task.isSuccessful()){
                   Toast.makeText(RegisterActivity.this, "User registered successfully", Toast.LENGTH_LONG).show();
                   FirebaseUser firebaseUser = auth.getCurrentUser();

                   //Send verification
                   firebaseUser.sendEmailVerification();

//                   //Open user profile after successful registration
//                   /*FLAG_ACTIVITY_CLEAR_TOP чаще всего используется вместе с FLAG_ACTIVITY_NEW_TASK.
//                   При совместном использовании эти флаги определяют местонахождение существующего действия в другой задаче
//                   и помещают его в положение, в котором оно может реагировать на намерение.*/
//                   Intent intent = new Intent(RegisterActivity.this, UserProfileActivity.class);
//                   //To Prevent User from returning back to Register Activity on pressing back Button after registration
//                   intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
//                   startActivity(intent);
//                   finish(); // to close register activity
               }
            }
        });
    }
}