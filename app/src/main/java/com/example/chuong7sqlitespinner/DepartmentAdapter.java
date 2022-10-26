package com.example.chuong7sqlitespinner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.chuong7sqlitespinner.Department;

import java.util.ArrayList;

public class DepartmentAdapter extends RecyclerView.Adapter<DepartmentAdapter.DepartmentViewHolder> {
    ArrayList<Department> lstDepart;
    Context context;
    DepartmentCallback userCallback;
    // HÃ m khá»Ÿi táº¡o Ä‘á»ƒ tÆ°Æ¡ng tÃ¡c vá»›i list

    public DepartmentAdapter(ArrayList<Department> lstDepart) {
        this.lstDepart = lstDepart;
    }

    // hÃ m khá»Ÿi táº¡o cho tÆ°Æ¡ng tÃ¡c vá»›i item
    public void setCallback(DepartmentCallback callback) {
        this.userCallback = callback;
    }

    @NonNull
    @Override
    public DepartmentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);
        // Náº¡p layout cho View biá»ƒu diá»…n pháº§n tá»­ user
        View userView = inflater.inflate(R.layout.layoutitemtext, parent, false);

        DepartmentViewHolder viewHolder = new DepartmentViewHolder(userView);
        return viewHolder;

    }

    @Override
    public void onBindViewHolder(@NonNull DepartmentViewHolder holder, int position) {
        // láº¥y tá»«ng item cá»§a dá»¯ liá»‡u
        Department item = lstDepart.get(position);
        // gÃ¡n vÃ o item cá»§a view
        holder.tvName.setText(String.valueOf(position+1)+" - "+ item.getName());
        // bat su kien
        holder.ivDelete.setOnClickListener(view -> userCallback.onItemDeleteClicked(item, position));
        holder.ivEdit.setOnClickListener(view -> userCallback.onItemEditClicked(item, position));

    }

    @Override
    public int getItemCount() {
        return lstDepart.size();
    }

    class DepartmentViewHolder extends RecyclerView.ViewHolder {

        TextView tvName;
        ImageView ivEdit;
        ImageView ivDelete;
        public DepartmentViewHolder(@NonNull View itemView) {
            super(itemView);

            tvName = itemView.findViewById(R.id.tvName);
            ivEdit= itemView.findViewById(R.id.ivEdit);
            ivDelete= itemView.findViewById(R.id.ivDelete);
        }
    }
    // dÃ¹ng Ä‘á»ƒ thá»±c hiá»‡n thao tÃ¡c xoÃ¡, cáº­p nháº­t trÃªn dÃ²ng
    public interface DepartmentCallback{
        void onItemDeleteClicked(Department us, int position);
        void onItemEditClicked(Department us, int position);
    }
}