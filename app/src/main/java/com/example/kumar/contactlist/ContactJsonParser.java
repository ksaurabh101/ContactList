package com.example.kumar.contactlist;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kumar on 31-May-16.
 */
public class ContactJsonParser {
    public static List<ContactDetails> parseFeed(String content) {
        List<ContactDetails> contactList = new ArrayList<>();
        try {
            JSONObject obj = new JSONObject(content);
            JSONArray contact = obj.getJSONArray("contact");
            System.out.println("In try block ContactJsonParser");
            for (int i = 0; i < contact.length(); i++) {
                JSONObject jobj = contact.getJSONObject(i);
                ContactDetails contactDetails = new ContactDetails();
                contactDetails.setName(jobj.getString("name"));
                contactDetails.setNumber(jobj.getString("pno"));
                System.out.println("User Details are : " + jobj.getString("name") + " : " + jobj.getString("pno"));
                contactList.add(contactDetails);
            }
            return contactList;
        } catch (Exception e) {
            System.out.println("Error : " + e.getMessage());
        }
        return contactList;
    }
}
