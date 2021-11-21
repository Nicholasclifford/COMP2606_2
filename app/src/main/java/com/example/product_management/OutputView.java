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
            Cursor cursor= db.query("Product",new String[]{"_id","Name","StockOnHand","StockInTransit","ReorderQuantity","ReorderAmount"}
            ,null, null,null,null,null);

            DatabaseUtils.dumpCursor(cursor);
            ArrayList<String> productlistviewArrayList=new ArrayList<String>();
            cursor.moveToFirst();
            while(!cursor.isAfterLast())
            {
                String test = null;
                Productlistview f=new Productlistview(cursor.getString(1),Integer.toString(cursor.getInt(2)),
                        Integer.toString(cursor.getInt(3)),Integer.toString(cursor.getInt(4)),
                        Integer.toString(cursor.getInt(5)));
               test= f.getProductname();
               productlistviewArrayList.add(test);

               Log.v("testing",test);

               cursor.moveToNext();
            }
           // String retest=productlistviewArrayList.get(0).getProductname();
            //Log.v("before",retest);

            String[] u={"hammer","nails"};
            ArrayAdapter list_adapter=new ArrayAdapter(getContext(),android.R.layout.simple_list_item_1
            ,productlistviewArrayList);


         /*   SimpleCursorAdapter list_adapter=new SimpleCursorAdapter(getContext()
                    , android.R.layout.simple_list_item_1,
                    cursor,new String[]{"Name"}
                    ,new int[]{android.R.id.text1},0);*/

            productlistView.setAdapter(list_adapter);
           // cursor.close();
        } catch (Exception e) {
            e.printStackTrace();
            Toast toast=Toast.makeText(getContext(),"Database not found...",Toast.LENGTH_SHORT);
            toast.show();
        }


        return layout;
    }


}