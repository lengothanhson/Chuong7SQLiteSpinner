package com.example.chuong7sqlitespinner;

import android.content.Intent;
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

public class MainActivity extends AppCompatActivity implements UserAdapter.UserCallback {
    RecyclerView rvListCode;
    ArrayList<User> lstUser;
    UserAdapter userAdapter;
    FloatingActionButton fbAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        rvListCode = findViewById(R.id.rvList);
        fbAdd = findViewById(R.id.fbAdd);
        fbAdd.setOnClickListener(view -> addUserDialog());
        // láº¥y dá»¯ liá»‡u
        lstUser = UserDataQuery.getAll(this);
        userAdapter = new UserAdapter(lstUser);
        userAdapter.setCallback(this);
        // gan adapter
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvListCode.setAdapter(userAdapter);
        rvListCode.setLayoutManager(linearLayoutManager);
    }

    void addUserDialog() {
        // khá»Ÿi táº¡o dialog Ä‘á»ƒ thÃªm ngÆ°á»i dÃ¹ng.
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("ThÃªm má»›i");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edName);
        EditText edAvatar = (EditText) dialogView.findViewById(R.id.edAvatar);
        // load phÃ²ng ban
        Spinner snPart= dialogView.findViewById(R.id.snDepart);
        ArrayList<Department> lstDepart= DepartmentDataQuery.getAll(this);
        lstDepart.add(0,new Department(0,"Chá»n phÃ²ng ban"));
        ArrayAdapter<Department> departmentArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,lstDepart);
        departmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snPart.setAdapter(departmentArrayAdapter);
        //
        alertDialog.setPositiveButton("Äá»“ng Ã½", (dialog, which) -> {
            String name = edName.getText().toString();
            String avatar = edAvatar.getText().toString();
            Department itemde=(Department) snPart.getSelectedItem();
            if (itemde.id==0)
            {
                Toast.makeText(MainActivity.this, "Vui lÃ²ng chá»n phÃ²ng ban", Toast.LENGTH_LONG).show();

            } else
            if (name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Nháº­p dá»¯ liá»‡u khÃ´ng há»£p lá»‡", Toast.LENGTH_LONG).show();
            } else {
                User us = new User(0, name, avatar);
                us.departid= itemde.id;
                long id = UserDataQuery.insert(MainActivity.this, us);
                if (id > 0) {
                    Toast.makeText(MainActivity.this, "ThÃªm ngÆ°á»i dÃ¹ng thÃ nh cÃ´ng.", Toast.LENGTH_LONG).show();
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

    void updateUserDialog(User us) {
        // khá»Ÿi táº¡o dialog Ä‘á»ƒ cáº­p nháº­t ngÆ°á»i dÃ¹ng
        AlertDialog.Builder alertDialog = new AlertDialog.Builder(MainActivity.this);
        alertDialog.setTitle("Cáº­p nháº­t");
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_add_user, null);
        alertDialog.setView(dialogView);
        EditText edName = (EditText) dialogView.findViewById(R.id.edName);
        EditText edAvatar = (EditText) dialogView.findViewById(R.id.edAvatar);
        // load phÃ²ng ban
        Spinner snPart= dialogView.findViewById(R.id.snDepart);
        ArrayList<Department> lstDepart= DepartmentDataQuery.getAll(this);
        lstDepart.add(0,new Department(0,"Chá»n phÃ²ng ban"));
        ArrayAdapter<Department> departmentArrayAdapter= new ArrayAdapter<>(this, android.R.layout.simple_spinner_item,lstDepart);
        departmentArrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        snPart.setAdapter(departmentArrayAdapter);
        //

        // gÃ¡n dá»¯ liá»‡u
        edName.setText(us.getName());
        edAvatar.setText(us.getAvatar());
        snPart.setSelection(us.departid);
        //
        alertDialog.setPositiveButton("Äá»“ng Ã½", (dialog, which) -> {

            us.setName(edName.getText().toString());
            us.setAvatar(edAvatar.getText().toString());
            Department itemde=(Department) snPart.getSelectedItem();
            if (itemde.id==0)
            {
                Toast.makeText(MainActivity.this, "Vui lÃ²ng chá»n phÃ²ng ban", Toast.LENGTH_LONG).show();

            } else
            if (us.name.isEmpty()) {
                Toast.makeText(MainActivity.this, "Nháº­p dá»¯ liá»‡u khÃ´ng há»£p lá»‡", Toast.LENGTH_LONG).show();
            } else {
                us.departid= itemde.id;
                int id = UserDataQuery.update(MainActivity.this, us);
                if (id > 0) {
                    Toast.makeText(MainActivity.this, "Cáº­p nháº­t ngÆ°á»i dÃ¹ng thÃ nh cÃ´ng.", Toast.LENGTH_LONG).show();
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
        lstUser.clear();
        lstUser.addAll(UserDataQuery.getAll(MainActivity.this));
        userAdapter.notifyDataSetChanged();
    }

    @Override
    public void onItemDeleteClicked(User us, int position) {
        boolean rs = UserDataQuery.delete(MainActivity.this, us.id);
        if (rs) {
            Toast.makeText(this, "XoÃ¡ thÃ nh cÃ´ng", Toast.LENGTH_LONG).show();
            resetData();
        } else {
            Toast.makeText(this, "XoÃ¡ tháº¥t báº¡i", Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public void onItemEditClicked(User us, int position) {
        updateUserDialog(us);
    }
}