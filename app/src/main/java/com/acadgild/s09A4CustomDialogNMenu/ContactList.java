package com.acadgild.s09A4CustomDialogNMenu;

import android.app.Dialog;
import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputFilter;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

/*s9_a4_custom_dialog_n_menu
- Create Menu, Menu items & Dialog
- Create the Menu item called “ADD” and upon the click event on the menu-item,
- a pop-up should appear asking for the user to enter the Name, phone number and DOB.
- The popup must contain a Button to add the values into the ListView and close the pop-up after adding,
- a Button to add the values into the ListView and clear the placeholder for adding new values,
- and a Button to close the pop-up.*/

/*
create Adapter class (extend with BaseAdapter)
create constr.(Context, ListElements[ ]… ), initialize LayoutInflator obj
can create inner class ViewsHolder and declare View variables
in getView() inflate view and set View variables
can implement onItemClickListener method
*/

public class ContactList extends AppCompatActivity {

    Dialog dlgAdd;
    EditText etName, etNumber, etDob, etMob, etYob;
    Button btnSave, btnCancel;
    ListView listContactDetails;

    TextView tvName, tvPNumber, tvDOB;
    List<List<String>> allContactDetails;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listContactDetails = (ListView) findViewById(R.id.list_details);
        allContactDetails = new ArrayList<>();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_add, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        dlgAdd = new Dialog(ContactList.this);

        if (item.getItemId() == R.id.add_details)
            showDialogAdd();

        return super.onOptionsItemSelected(item);
    }

    private void showDialogAdd(){

        // customize dialog
        dlgAdd.setTitle(R.string.dlg_title);
        dlgAdd.setContentView(R.layout.dialog_adder);

        etName = (EditText) dlgAdd.findViewById(R.id.et_name);
        etNumber = (EditText) dlgAdd.findViewById(R.id.et_number);
        etDob = (EditText) dlgAdd.findViewById(R.id.et_dob);
        etMob = (EditText) dlgAdd.findViewById(R.id.et_mob);
        etYob = (EditText) dlgAdd.findViewById(R.id.et_yob);


        btnSave = (Button) dlgAdd.findViewById(R.id.btn_save);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                List<String> eachContactDetails = new ArrayList<String>();

                eachContactDetails.add(etName.getText().toString());
                eachContactDetails.add(etNumber.getText().toString());
                eachContactDetails.add(
                        String.valueOf(etDob.getText()) + "/"
                        + String.valueOf(etMob.getText()) + "/"
                        + String.valueOf(etYob.getText())
                );

                allContactDetails.add(eachContactDetails);


                listContactDetails.setAdapter(
                        new ContactListAdapter(
                                ContactList.this,
                                allContactDetails
                        )
                );

                dlgAdd.dismiss();
            }
        });

        btnCancel = (Button) dlgAdd.findViewById(R.id.btn_cancel);
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dlgAdd.dismiss();
            }
        });


        // show the customized dialog finally
        dlgAdd.show();
    }

    public class ContactListAdapter extends BaseAdapter{
        Context context;
        List<List<String>> allContactDetails;

        public ContactListAdapter(Context context, List<List<String>> allContactDetails) {
            this.context = context;
            this.allContactDetails = allContactDetails;
        }

        @Override
        public int getCount() {
            return allContactDetails.size();
        }

        @Override
        public Object getItem(int position) {
            return this.allContactDetails.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }



        @Override
        public View getView(int position, View view, ViewGroup parent) {
            view = getLayoutInflater().inflate(R.layout.list_elements, null);

            tvName = (TextView) view.findViewById(R.id.tv_name);
            tvName.setText(this.allContactDetails.get(position).get(0));

            tvPNumber = (TextView) view.findViewById(R.id.tv_number);
            tvPNumber.setText(this.allContactDetails.get(position).get(1));

            tvDOB = (TextView) view.findViewById(R.id.tv_dob);
            tvDOB.setText(this.allContactDetails.get(position).get(2));

            return view;
        }
    }


}
