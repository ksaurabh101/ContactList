package com.example.kumar.contactlist;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumar on 31-May-16.
 */
public class ViewDetails extends Fragment {
    ListView listView;
    List<ContactDetails> list;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.view_deatils, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        listView = (ListView) getActivity().findViewById(R.id.detailView);
        list = new ArrayList<>();

        //get Details from Database
        try {
            DatabaseHandler handler = new DatabaseHandler(getContext());
            list = handler.getDetails();
            ViewAdapter adapter = new ViewAdapter(getContext(), R.layout.detail_row, list);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Database View Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            DatabaseHandler handler = new DatabaseHandler(getContext());
            list = handler.getDetails();
            ViewAdapter adapter = new ViewAdapter(getContext(), R.layout.detail_row, list);
            listView.setAdapter(adapter);
        } catch (Exception e) {
            Toast.makeText(getContext(), "Database View Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }
}
