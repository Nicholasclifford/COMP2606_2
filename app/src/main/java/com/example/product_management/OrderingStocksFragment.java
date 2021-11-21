package com.example.product_management;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;


public class OrderingStocksFragment extends Fragment implements View.OnClickListener{



    public OrderingStocksFragment() {
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
        View layout=inflater.inflate(R.layout.fragment_ordering_stocks, container, false);
        Button button1=layout.findViewById(R.id.order_button);
        Spinner list=layout.findViewById(R.id.spinner_order);
        EditText stock=layout.findViewById(R.id.quantity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                            String product=String.valueOf(list.getSelectedItem());
                            int amount= Integer.parseInt(String.valueOf(stock.getText()));
                            // int a= Integer.parseInt(amount);

                            if(amount<0)
                            {
                                Toast toast=Toast.makeText(getContext(),"Please select a valid number",Toast.LENGTH_SHORT);
                                return;
                            }

                            SQLiteOpenHelper database= new ProductManagementDatabaseHelper(getContext());
                            SQLiteDatabase db=database.getReadableDatabase();
                            Cursor cursor=db.query("Product",new String[]{"_id","Name","StockInTransit"},
                                   "Name = ?", new String[]{},null,null,null);
               // String i="SELECT  _id,Name,StockInTransit FROM Product WHERE Name='"+product+"'";
                           // Cursor cursor=db.rawQuery(i,null);

                            Log.v("string test",product);
                DatabaseUtils.dumpCursor(cursor);
                            cursor.moveToFirst();
                            int oldvals=cursor.getInt(2);

                            ContentValues update_value=new ContentValues();
                            update_value.put("StockInTransit",oldvals+amount);

                            db.update("Product",update_value,"Name=?",new String[]{product});
                            cursor.close();
                            db.close();//This works but needs to use product variable in new String[]
            }
        });

        SQLiteOpenHelper database= new ProductManagementDatabaseHelper(getContext());

        try {
            SQLiteDatabase db=database.getReadableDatabase();
            Cursor cursor=db.query("Product",new String[]{"_id","Name"},null,
                    null,null,null,null);

            SimpleCursorAdapter spinneradapter= new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_list_item_1,cursor,new String[]{"Name"},
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
}