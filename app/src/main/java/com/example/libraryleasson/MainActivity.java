package com.example.libraryleasson;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.permissions.CurrencyManager;
import com.google.android.material.button.MaterialButton;

public class MainActivity extends AppCompatActivity {

    private MaterialButton main_BTM_open;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);


        main_BTM_open = findViewById(R.id.main_BTM_open);
        main_BTM_open.setOnClickListener(v -> openDialog());


    }

    private void openDialog() {
        CurrencyManager.openCurrencyDialog(this, new CurrencyManager.CallBack_Currency() {
            @Override
            public void selected(String name, String Code, String symbol) {
                main_BTM_open.setText(symbol);
            }
        });
    }
}