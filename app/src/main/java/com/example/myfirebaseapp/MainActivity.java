package com.example.myfirebaseapp;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Set the title
        getSupportActionBar().setTitle("Мониторинг настроения");

        //Open Login activity

//        Button buttonLogin = findViewById(R.id.button_login);
//        //setOnClickListener - помогает на связать listener c определенными атрибутами
//        buttonLogin.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                /*  Намерение (Intent) - это механизм для описания одной операции - выбрать фотографию,
//                    отправить письмо, сделать звонок, запустить браузер и перейти по указанному адресу,
//                    позволяет описать действие, которое необходимо выполнить, и передать его системе    */
//                Intent intent = new Intent(MainActivity.this, LoginActivity.class);
//                startActivity(intent);
//            }
//        });

        //Open Register activity

        Button buttonRegister = findViewById(R.id.button_register);
        //setOnClickListener - помогает на связать listener c определенными атрибутами
        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}