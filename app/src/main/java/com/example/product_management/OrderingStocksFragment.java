package com.example.product_management;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.DatabaseUtils;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
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


public class OrderingStocksFragment extends Fragment implements View.OnClickListener {

    private SQLiteDatabase db_spinner;
    private Cursor cursor_spinner;

    public OrderingStocksFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {

            FragmentManager frag=getParentFragmentManager();
            FragmentTransaction ft_tablet=frag.beginTransaction();
            Fragment tablet=frag.findFragmentById(R.id.tabletdatabaseview);

            OutputView databaseoutput = new OutputView();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            if (tablet == null) {
                ft.add(R.id.databaseshow, databaseoutput);
                ft.addToBackStack(null);

            }
            else
            {
                ft.add(R.id.tabletdatabaseview, databaseoutput);
            }
            ft.commit();


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_ordering_stocks, container, false);
        Button button1 = layout.findViewById(R.id.order_button);
        Spinner list = layout.findViewById(R.id.spinner_order);
        EditText stock = layout.findViewById(R.id.quantity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
  /*              {
                    Toast toast=Toast.makeText(getContext(),"Please enter a number",Toast.LENGTH_SHORT);
                    return;
                }*/
                String product = String.valueOf(list.getSelectedItem());
                int position = list.getSelectedItemPosition() + 1;
                int amount = Integer.parseInt(String.valueOf(stock.getText()));
                // int a= Integer.parseInt(amount);



                //pre-execute
                try {
                    SQLiteOpenHelper database = new ProductManagementDatabaseHelper(getContext());
                    SQLiteDatabase db = database.getReadableDatabase();
                    Cursor cursor = db.query("Product", new String[]{"_id", "Name", "StockInTransit"},
                            "_id = ?", new String[]{Integer.toString(position)}, null, null, null);



                    //Log.v("string test",product);
                    DatabaseUtils.dumpCursor(cursor);


                    cursor.moveToFirst();
                    int oldvals = cursor.getInt(2);

                    ContentValues update_value = new ContentValues();
                    update_value.put("StockInTransit", oldvals + amount);

                    db.update("Product", update_value, "_id=?", new String[]{Integer.toString(position)});
                    //Toast toast=Toast.makeText(getContext(),"Database done",Toast.LENGTH_SHORT);
                    //toast.show();

                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.sublevel_frag, new OrderingStocksFragment());
                    ft.commit();


                    cursor.close();
                    db.close();//This works but needs to use product variable in new String[]
                } catch (Exception e) {
                    e.printStackTrace();
                    Toast toast = Toast.makeText(getContext(), "Something went wrong during operation", Toast.LENGTH_SHORT);
                    toast.show();
                    return;
                }
                //let the database run in the background
            }
        });


//Spinner setup
        SQLiteOpenHelper database = new ProductManagementDatabaseHelper(getContext());
        try {
            db_spinner = database.getReadableDatabase();
            cursor_spinner = db_spinner.query("Product", new String[]{"_id", "Name"}, null,
                    null, null, null, null);

            SimpleCursorAdapter spinneradapter = new SimpleCursorAdapter(getContext(),
                    android.R.layout.simple_list_item_1, cursor_spinner, new String[]{"Name"},
                    new int[]{android.R.id.text1, 0}
            );
            list.setAdapter(spinneradapter);


        } catch (Exception e) {
            e.printStackTrace();

            Toast toast = Toast.makeText(getContext(), "database not found", Toast.LENGTH_SHORT);
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
        cursor_spinner.close();
        db_spinner.close();
    }

}