package com.example.product_management;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class ReceivingStocksFragment extends Fragment implements View.OnClickListener{


    public ReceivingStocksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_receiving_stocks, container, false);
        Button button1=layout.findViewById(R.id.receive_button);
        button1.setOnClickListener(this);


        return layout;
    }

    @Override
    public void onClick(View view) {

    }
}