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
 * Created by kumar on 01-Jun-16.
 */
public class ViewAdapter extends ArrayAdapter<ContactDetails> {

    private Context context;
    private List<ContactDetails> list;

    public ViewAdapter(Context context, int resource, List<ContactDetails> details) {
        super(context, resource, details);
        list = details;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflator = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
        View view = inflator.inflate(R.layout.detail_row, parent, false);
        ContactDetails contact = list.get(position);
        TextView name = (TextView) view.findViewById(R.id.nameDetail);
        TextView number = (TextView) view.findViewById(R.id.numberDeatil);
        TextView otp = (TextView) view.findViewById(R.id.otp);
        TextView time = (TextView) view.findViewById(R.id.timeDetails);
        name.setText(contact.getName());
        number.setText(contact.getNumber());
        otp.setText(contact.getOtp());
        time.setText(contact.getTime());
        return view;
    }
}

