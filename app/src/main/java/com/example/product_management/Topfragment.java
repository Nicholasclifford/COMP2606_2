package com.example.product_management;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.HashMap;


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

              /*  Intent intent=new Intent(getActivity(),sublevelActivity.class);
                intent.putExtra(sublevelActivity.choice,true);
                startActivity(intent);*/

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.tabletmainview, new ReceivingStocksFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });
        Button ordering_button=layout.findViewById(R.id.topsendbutton);
        ordering_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               /*
                Intent intent=new Intent(getActivity(),sublevelActivity.class);
                intent.putExtra(sublevelActivity.choice,false);
                startActivity(intent);*/

                FragmentManager fragmentManager = getParentFragmentManager();
                FragmentTransaction ft = fragmentManager.beginTransaction();
                ft.replace(R.id.tabletmainview, new OrderingStocksFragment());
                ft.addToBackStack(null);
                ft.commit();
            }
        });

        Button sync_database=layout.findViewById(R.id.sync_database_button);
        sync_database.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                SQLiteOpenHelper database= new ProductManagementDatabaseHelper(getContext());
                SQLiteDatabase db=database.getReadableDatabase();
                Cursor cursor= db.rawQuery("Select * from Product where Dirtybit=true ",null);
                HashMap<String,String> hashMap=new HashMap<>();
                ArrayList<HashMap> sync_data= new ArrayList<>();

                if(cursor.moveToFirst())
                {
                    while(!cursor.isAfterLast())
                    {
                        hashMap.put("_id",cursor.getString(0));
                        hashMap.put("Name",cursor.getString(1));
                        hashMap.put("StockOnHand",cursor.getString(2));
                        hashMap.put("StockInTransit",cursor.getString(3));
                        hashMap.put("Price",cursor.getString(4));
                        hashMap.put("ReorderQuantity",cursor.getString(5));
                        hashMap.put("ReorderAmount",cursor.getString(6));

                        sync_data.add(hashMap);
                    }

                }

                Toast.makeText(getContext(),"database synced",Toast.LENGTH_SHORT).show();
                cursor.close();
                db.close();
            }
        });


        return layout;
    }

    @Override
    public void onClick(View view) {

    }
}