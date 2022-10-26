package com.example.chuong7sqlitespinner;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;

public class DepartmentActivity extends AppCompatActivity implements DepartmentAdapter.DepartmentCallback {
    RecyclerView rvListCode;
    ArrayList<Department> lstDepart;
    DepartmentAdapter departAdapter;
    FloatingActionButton fbAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_department);
        rvListCode = findViewById(R.id.rvListDe);
        fbAdd = findViewById(R.id.fbAdd);
        fbAdd.setOnClickListener(view -> addUserDialog());
        // láº¥y dá»¯ liá»‡u
        lstDepart = DepartmentDataQuery.getAll(this);
        departAdapter= new DepartmentAdapter(lstDepart);
        departAdapter.setCallback(this);
        // gan adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListCode.setAdapter(departAdapter);
        rvListCode.setLayoutManager(linearLayoutManager);
    }

    void addUserDialog() {
        // khá»Ÿi táº¡o dialog Ä‘á»ƒ thÃªm ngÆ°á»i dÃ¹ng.
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DepartmentActivity.this);
        alertDialog.setTitle("ThÃªm má»›i");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_depart, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edNameDe);
        Spinner snType= dialogView.findViewById(R.id.snType);
        // load data for Spinner
        String []arrType=new String[]{"Khu 1","Khu 2","Khu 3"};
        ArrayAdapter<String> arrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, arrType);
        snType.setAdapter(arrayAdapter);
        //
        alertDialog.setPositiveButton("Äá»“ng Ã½", (dialog, which) -> {
            String name = edName.getText().toString();

            if (name.isEmpty()) {
                Toast.makeText(DepartmentActivity.this, "Nháº­p dá»¯ liá»‡u khÃ´ng há»£p lá»‡", Toast.LENGTH_LONG).show();
            } else {
                Department us = new Department(0, name);
                long id = DepartmentDataQuery.insert(DepartmentActivity.this, us);
                if (id > 0) {
                    Toast.makeText(DepartmentActivity.this, "ThÃªm phÃ²ng ban thÃ nh cÃ´ng.", Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("Huá»·", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

    void updateUserDialog(Department us) {
        // khá»Ÿi táº¡o dialog Ä‘á»ƒ cáº­p nháº­t ngÆ°á»i dÃ¹ng
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(DepartmentActivity.this);
        alertDialog.setTitle("Cáº­p nháº­t");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_depart, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edNameDe);

        // gÃ¡n dá»¯ liá»‡u
        edName.setText(us.getName());

        //
        alertDialog.setPositiveButton("Äá»“ng Ã½", (dialog, which) -> {

            us.setName(edName.getText().toString());

            if (us.name.isEmpty()) {
                Toast.makeText(DepartmentActivity.this, "Nháº­p dá»¯ liá»‡u khÃ´ng há»£p lá»‡", Toast.LENGTH_LONG).show();
            } else {

                int id = DepartmentDataQuery.update(DepartmentActivity.this, us);
                if (id > 0) {
                    Toast.makeText(DepartmentActivity.this, "Cáº­p nháº­t phÃ²ng ban thÃ nh cÃ´ng.", Toast.LENGTH_LONG).show();
                    resetData();
                    dialog.dismiss();
                }
            }
        });
        alertDialog.setNegativeButton("Há»§y", (dialog, which) -> {
            dialog.dismiss();
        });
        alertDialog.create();
        alertDialog.show();
    }

    void resetData() {
        lstDepart.clear();
        lstDepart.addAll(DepartmentDataQuery.getAll(DepartmentActivity.this));
        departAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemDeleteClicked(Department us, int position) {
        boolean rs = DepartmentDataQuery.delete(DepartmentActivity.this, us.id);
        if (rs) {
            Toast.makeText(this, "XoÃ¡ thÃ nh cÃ´ng", Toast.LENGTH_LONG).show();
            resetData();
        } else {
            Toast.makeText(this, "XoÃ¡ tháº¥t báº¡i", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemEditClicked(Department us, int position) {
        updateUserDialog(us);
    }
}