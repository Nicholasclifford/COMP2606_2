package com.example.product_management;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class Topfragment extends Fragment implements View.OnClickListener{


    public Topfragment() {
        // Required empty public constructor
    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_topfragment, container, false);

        Button receive_button=layout.findViewById(R.id.toprecievebutton);
        receive_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                Intent intent=new Intent(getActivity(),sublevelActivity.class);
                intent.putExtra(sublevelActivity.choice,true);
                startActivity(intent);
            }
        });
        Button ordering_button=layout.findViewById(R.id.topsendbutton);
        ordering_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getActivity(),sublevelActivity.class);
                intent.putExtra(sublevelActivity.choice,false);
                startActivity(intent);
            }
        });



        return layout;
    }

    @Override
    public void onClick(View view) {

    }
}