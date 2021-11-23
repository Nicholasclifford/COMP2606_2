package com.example.product_management;

import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

//Use cursor to get data put data into one long string then use place in adapter
public class OutputView extends Fragment {


    public OutputView() {
        // Required empty public constructor
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View layout=inflater.inflate(R.layout.fragment_output_view, container, false);
        ListView productlistView=layout.findViewById(R.id.producttlist);

        SQLiteOpenHelper sqlopen=new ProductManagementDatabaseHelper(getContext());
        try {
            SQLiteDatabase db=sqlopen.getReadableDatabase();
            Cursor cursor= db.query("Product",new String[]{"_id","Name","StockOnHand","StockInTransit","Price","ReorderQuantity","ReorderAmount"}
            ,null, null,null,null,null);

            //DatabaseUtils.dumpCursor(cursor);
            ArrayList<String> productlistview= new ArrayList<>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                String test ;

                Productlistview d =new Productlistview(cursor.getString(1),Integer.toString(cursor.getInt(2)),
                        Integer.toString(cursor.getInt(3)),cursor.getDouble(4),Integer.toString(cursor.getInt(5)),
                        Integer.toString(cursor.getInt(6)));

                test=d.getProductname();

               productlistview.add(test);

               Log.v("testing",test);

               cursor.moveToNext();
            }

            ArrayAdapter list_adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1
            ,productlistview);

            productlistView.setAdapter(list_adapter);

        } catch (Exception e) {
            e.printStackTrace();
            Toast toast=Toast.makeText(getContext(),"Database not found...",Toast.LENGTH_SHORT);
            toast.show();
        }


        return layout;
    }


}