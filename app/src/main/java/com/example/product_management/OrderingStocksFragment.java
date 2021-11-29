package com.example.product_management;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.SimpleCursorAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentContainerView;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;


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


            OutputView databaseoutput = new OutputView();
            FragmentTransaction ft = getChildFragmentManager().beginTransaction();
            ft.add(R.id.databaseshow, databaseoutput);
            ft.addToBackStack(null);


        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View layout = inflater.inflate(R.layout.fragment_ordering_stocks, container, false);
        FragmentContainerView frag_tablet = (FragmentContainerView) layout.findViewById(R.id.tabletmainview);
        if(frag_tablet!=null)
        {
            FragmentContainerView data_show= (FragmentContainerView) layout.findViewById(R.id.databaseshow);
            data_show.setVisibility(View.GONE);
        }

        Button button1 = layout.findViewById(R.id.order_button);
        Spinner list = layout.findViewById(R.id.spinner_order);
        EditText stock = layout.findViewById(R.id.quantity);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {



               // String product = String.valueOf(list.getSelectedItem());
                int position = list.getSelectedItemPosition() + 1;
                int amount = Integer.parseInt(String.valueOf(stock.getText()));
                // int a= Integer.parseInt(amount);
                new update().execute(position,amount);


                if(frag_tablet!=null)
                {
                    
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    //ft.replace(R.id.sublevel_frag, new OrderingStocksFragment());
                    ft.replace(R.id.tabletdatabaseview,new OutputView());
                    ft.commit();

                    //ft.commit();
                }
                else{
                    Log.v("last","This has to be in phone view");
                    FragmentManager fragmentManager = getParentFragmentManager();
                    FragmentTransaction ft = fragmentManager.beginTransaction();
                    ft.replace(R.id.sublevel_frag, new OrderingStocksFragment());
                    ft.addToBackStack(null);
                    ft.commit();
                }


                //pre-execute

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

            //Toast toast = Toast.makeText(getContext(), "database not found", Toast.LENGTH_SHORT);
            //toast.show();
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

    private class update extends AsyncTask<Integer ,Integer,Boolean>
    {
        @Override
        protected Boolean doInBackground(Integer... integers) {

            int position=integers[0];
            int amount=integers[1];
            try {
                SQLiteOpenHelper database = new ProductManagementDatabaseHelper(getContext());
                SQLiteDatabase db = database.getReadableDatabase();
                Cursor cursor = db.query("Product", new String[]{"_id", "Name", "StockInTransit"},
                        "_id = ?", new String[]{Integer.toString(position)}, null, null, null);



                //Log.v("string test",product);
                //DatabaseUtils.dumpCursor(cursor);


                cursor.moveToFirst();
                int oldvals = cursor.getInt(2);

                ContentValues update_value = new ContentValues();
                update_value.put("StockInTransit", oldvals + amount);
                update_value.put("DIRTYBIT",true);

                db.update("Product", update_value, "_id=?", new String[]{Integer.toString(position)});
                //Toast toast=Toast.makeText(getContext(),"Database done",Toast.LENGTH_SHORT);
                //toast.show();


                cursor.close();
                db.close();
                return true;
            } catch (Exception e) {
                e.printStackTrace();

                return false;
            }

        }


        protected void onPostExecute(Boolean aBoolean) {
            //super.onPostExecute(aBoolean);

            if (!aBoolean)
            {
                Toast toast = Toast.makeText(getContext(), "database not found", Toast.LENGTH_SHORT);
                toast.show();
            }
        }
    }

}