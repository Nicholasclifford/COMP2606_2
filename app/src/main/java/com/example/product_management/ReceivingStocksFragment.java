package com.example.product_management;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.view.ContentInfo;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class ReceivingStocksFragment extends Fragment implements View.OnClickListener{

    private SQLiteDatabase db_spinner;
    private Cursor cursor_spinner;

    public ReceivingStocksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(savedInstanceState==null)
        {
            OutputView databaseoutput= new OutputView();
            FragmentTransaction ft =getChildFragmentManager().beginTransaction();
            ft.add(R.id.databaseshow,databaseoutput);
            ft.addToBackStack(null);
            ft.commit();
        }


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout=inflater.inflate(R.layout.fragment_receiving_stocks, container, false);
        Button button1=layout.findViewById(R.id.receive_button);
        Spinner list=layout.findViewById(R.id.spinner_receive);
        EditText stock=layout.findViewById(R.id.quantity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String product=String.valueOf(list.getSelectedItem());
                int amount= Integer.parseInt(String.valueOf(stock.getText()));
                int position=list.getSelectedItemPosition()+1;

                try{
                    SQLiteOpenHelper database =new ProductManagementDatabaseHelper(getContext());
                    SQLiteDatabase db= database.getReadableDatabase();
                    Cursor cursor=db.query("Product",new String[]{"_id","Name","StockOnHand","StockInTransit"},
                            "_id = ?",new String[]{Integer.toString(position)},null,null,null);

                    cursor.moveToFirst();
                    int old_on_vals=cursor.getInt(2);
                    int old_in_vals=cursor.getInt(3);

                    ContentValues update_value=new ContentValues();
                    update_value.put("StockInTransit",old_in_vals-amount);
                    update_value.put("StockOnHand",old_on_vals+amount);

                    db.update("Product",update_value,"_id=?",new String[]{Integer.toString(position)});

                    FragmentManager fragmentManager=getActivity().getSupportFragmentManager();
                    FragmentTransaction ft=fragmentManager.beginTransaction();
                    ft.replace(R.id.sublevel_frag,new ReceivingStocksFragment());
                    ft.commit();

                    cursor.close();
                    db.close();


                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast=Toast.makeText(getContext(),"Something went wrong during operation",Toast.LENGTH_SHORT);
                    toast.show();

                }


            }
        });

        SQLiteOpenHelper database= new ProductManagementDatabaseHelper(getContext());

        try {
            db_spinner=database.getReadableDatabase();
            cursor_spinner=db_spinner.query("Product",new String[]{"_id","Name"},null,
                    null,null,null,null);

            SimpleCursorAdapter spinneradapter= new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_list_item_1,cursor_spinner,new String[]{"Name"},
                    new int[]{android.R.id.text1,0}
                    );
            list.setAdapter(spinneradapter);

        } catch (Exception e) {
            e.printStackTrace();
            Toast toast=Toast.makeText(getContext(),"database not found",Toast.LENGTH_SHORT);
            toast.show();
        }

        return layout;
    }

    @Override
    public void onClick(View view) {

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        db_spinner.close();
        cursor_spinner.close();

    }
}