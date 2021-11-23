package com.example.product_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentContainerView;
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

        FragmentContainerView frag_tablet = (FragmentContainerView) findViewById(R.id.tabletmainview);
        FragmentManager fragmentManager=getSupportFragmentManager();
        FragmentTransaction ft=fragmentManager.beginTransaction();
        if (frag_tablet == null)
        {
            if(option) {

                ft.replace(R.id.sublevel_frag, new ReceivingStocksFragment());
            }
            else
            {
                ft.replace(R.id.sublevel_frag, new OrderingStocksFragment());
            }

        }
        else
        {
            if(option) {

                ft.replace(R.id.tabletmainview, new ReceivingStocksFragment());
            }
            else
            {
                ft.replace(R.id.tabletmainview, new OrderingStocksFragment());
            }

        }
        ft.commit();


    }
}