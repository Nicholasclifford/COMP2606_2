package com.example.product_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;

public class sublevelActivity extends AppCompatActivity {

    public static String choice;
    public boolean option;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sublevel);

        Intent intent =getIntent();
        option=intent.getExtras().getBoolean(choice);


        if(option) {

            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.sublevel_frag, new ReceivingStocksFragment());
            ft.commit();
        }
        else
        {
            FragmentManager fragmentManager=getSupportFragmentManager();
            FragmentTransaction ft=fragmentManager.beginTransaction();
            ft.replace(R.id.sublevel_frag, new OrderingStocksFragment());
            ft.commit();
        }

    }
}