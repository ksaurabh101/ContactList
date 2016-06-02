package com.example.kumar.contactlist;

import android.content.Intent;
import android.content.res.Resources;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.ListFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.DataInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumar on 31-May-16.
 */
public class ContactList extends ListFragment implements View.OnClickListener {
    ListView listView;
    List<ContactDetails> list;
//    Button load;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.contact_list, container, false);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
//        load = (Button) getActivity().findViewById(R.id.load);
//        load.setOnClickListener(this);
        listView = getListView();
        list = new ArrayList<>();
        //get data...from Json file
        Resources resources = this.getResources();
        InputStream is = resources.openRawResource(R.raw.contact);
        DataInputStream dis = new DataInputStream(is);
        StringBuilder sb = new StringBuilder();
        String line = "";
        try {
            while ((line = dis.readLine()) != null) {
                sb.append(line + "\n");
            }
        } catch (IOException e) {
            Toast.makeText(getContext(), "Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        String result = sb.toString();
//        Toast.makeText(getContext(),result, Toast.LENGTH_LONG).show();

        try {
            list = ContactJsonParser.parseFeed(result);
//            list = parseFeed(result);
//            Toast.makeText(getContext(), "List size is One : " + list.size(), Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Toast.makeText(getContext(), "List Error " + e.getMessage(), Toast.LENGTH_LONG).show();
        }
        //get ListView of Json data
//        Mytask task =new Mytask();
//        task.execute(result);
        //Show result in list view
        ContactAdapter adapter=new ContactAdapter(getContext(),R.layout.row,list);
        listView.setAdapter(adapter);
//
//        String arr[] = {"Saurabh", "Kumar", "Maurya"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arr);
//        listView.setAdapter(adapter);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView text1 = (TextView) view.findViewById(R.id.name);
                TextView text2 = (TextView) view.findViewById(R.id.number);
//                Toast.makeText(getContext(), text1.getText(), Toast.LENGTH_LONG).show();
                Intent i=new Intent(getContext(),DetailsActivity.class);
                i.putExtra("Name",text1.getText());
                i.putExtra("Number",text2.getText());
                startActivity(i);
            }
        });
    }

//    public List<ContactDetails> parseFeed(String content) {
//        List<ContactDetails> contactList = new ArrayList<>();
//        try {
//            JSONObject obj = new JSONObject(content);
//            JSONArray contact = obj.getJSONArray("contact");
//            System.out.println("In try block ContactJsonParser");
//            Toast.makeText(getContext(), "In try block ContactJsonParser", Toast.LENGTH_LONG).show();
//            for (int i = 0; i < contact.length(); i++) {
//                JSONObject jobj = contact.getJSONObject(i);
//                ContactDetails contactDetails = new ContactDetails();
//                contactDetails.setName(jobj.getString("name"));
//                contactDetails.setNumber(jobj.getString("pno"));
//                System.out.println("User Details are : " + jobj.getString("name") + " : " + jobj.getString("pno"));
//                Toast.makeText(getContext(), "User Details are : " + jobj.getString("name") + " : " + jobj.getString("pno"), Toast.LENGTH_LONG).show();
//                contactList.add(contactDetails);
//            }
//            return contactList;
//        } catch (Exception e) {
//            System.out.println("Error : " + e.getMessage());
//            Toast.makeText(getContext(), "Error in ParseFeed : " + e.getMessage(), Toast.LENGTH_LONG).show();
//        }
//        return contactList;
//    }

    @Override
    public void onClick(View v) {
//        if (v.getId() == R.id.load) {
//            String arr[] = {"Saurabh", "Kumar", "Maurya"};
//            ArrayAdapter<String> adapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_list_item_1, arr);
//            listView.setAdapter(adapter);
//        }
    }

    private class Mytask extends AsyncTask<String, Void, Void> {
        @Override
        protected void onPreExecute() {
            Log.i("MSG", "List Is Executing");
        }

        @Override
        protected Void doInBackground(String... params) {
            list = ContactJsonParser.parseFeed(params[0]);
            return null;
        }

        @Override
        protected void onPostExecute(Void aVoid) {
//            ContactAdapter adapter=new ContactAdapter(getContext(),R.layout.row,list);
//            listView.setAdapter(adapter);
            Log.i("MSG", "List Is Done");
        }
    }
}
