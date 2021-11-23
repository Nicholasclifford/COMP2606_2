package com.example.product_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class TopLevelActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //if the screen is large e.g.(tablet) this must load to the left of the screen
        //while the output view loads to the right

        Button receive_button=findViewById(R.id.toprecievebutton);
        receive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TopLevelActivity.this,sublevelActivity.class);
                intent.putExtra(sublevelActivity.choice,true);
                startActivity(intent);
            }
        });
        Button ordering_button=findViewById(R.id.topsendbutton);
        ordering_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(TopLevelActivity.this,sublevelActivity.class);
                intent.putExtra(sublevelActivity.choice,false);
                startActivity(intent);
            }
        });

    }
}