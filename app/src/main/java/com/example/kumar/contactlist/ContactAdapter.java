package com.example.kumar.contactlist;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by kumar on 31-May-16.
 */
public class ContactAdapter extends ArrayAdapter<ContactDetails> {

    private  Context context;
    private  List<ContactDetails> list;
    public ContactAdapter(Context context, int resource, List<ContactDetails> details) {
        super(context, resource, details);
        list=details;
        this.context=context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator =(LayoutInflater)context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view =inflator.inflate(R.layout.row,parent,false);
        ContactDetails contact= list.get(position);
        TextView name=(TextView)view.findViewById(R.id.name);
        name.setText(contact.getName());
        TextView number=(TextView)view.findViewById(R.id.number);
        number.setText(contact.getNumber());
        return view;
    }
}
