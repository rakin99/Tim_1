package com.example.mojprojekat.adapteri;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.example.mojprojekat.R;
import com.example.mojprojekat.model.Account;

import java.util.ArrayList;
import java.util.List;

public class SpinerAdapter extends ArrayAdapter {

    private Context context;
    private ArrayList<Account> accounts;
    LayoutInflater flater;

    public SpinerAdapter(@NonNull Context context, ArrayList<Account> accounts) {
        super(context,0, accounts);
        this.context=context;
        this.accounts= accounts;
    }

    public List<Account> getMessages() {
        return accounts;
    }

    @Override
    public int getCount() {
        return accounts.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public Object getItem(int position) {
        return accounts.get(position);
    }

    /*@Override
    public View getView(int position, View convertView, ViewGroup parent) {

        Account account =(Account) getItem(position);

        View rowview = flater.inflate(R.layout.activity_profile,null,true);

        TextView txtTitle = (TextView) rowview.findViewById(R.id.title);
        txtTitle.setText(rowItem.getTitle());

        ImageView imageView = (ImageView) rowview.findViewById(R.id.icon);
        imageView.setImageResource(rowItem.getImageId());

        return rowview;
    }*/

    @Override
    public View getDropDownView(int position, View convertView, ViewGroup parent) {
        if(convertView == null){
            convertView = flater.inflate(R.layout.accounts_list,parent, false);
        }
        Account account = accounts.get(position);
        TextView txtTitle = (TextView) convertView.findViewById(R.id.accountEmail);
        txtTitle.setText(account.getUsername()+"@"+account.getSmtpAddress());
        return convertView;
    }

}
