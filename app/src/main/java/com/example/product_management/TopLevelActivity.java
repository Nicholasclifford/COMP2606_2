package com.example.product_management;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if the screen is large e.g.(tablet) this must load to the left of the screen
        //while the output view loads to the right

        FragmentContainerView frag_tablet = findViewById(R.id.tabletmainview);
        if (frag_tablet == null) { // in phone view
            Log.v("phone test","this tab is in the phone view ");
            Button receive_button = findViewById(R.id.toprecievebutton);
            receive_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TopLevelActivity.this, sublevelActivity.class);
                    intent.putExtra(sublevelActivity.choice, true);
                    startActivity(intent);

                }
            });
            Button ordering_button = findViewById(R.id.topsendbutton);
            ordering_button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(TopLevelActivity.this, sublevelActivity.class);
                    intent.putExtra(sublevelActivity.choice, false);
                    startActivity(intent);

                }
            });
        } else {
            Log.v("phone test","this tab is in the tablet view ");
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction ft = fragmentManager.beginTransaction();
            ft.replace(R.id.tabletmainview, new Topfragment());
            ft.commit();


        }
    }
}