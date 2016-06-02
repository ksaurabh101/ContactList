package com.example.kumar.contactlist;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.SmsManager;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by kumar on 01-Jun-16.
 */
public class DetailsActivity extends AppCompatActivity {

    TextView text1, text2;
    private Toolbar toolbar;
    Button bt, da;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Intent i = getIntent();
        final String name = i.getStringExtra("Name");
        final String number = i.getStringExtra("Number");
        text1 = (TextView) findViewById(R.id.viewName);
        text2 = (TextView) findViewById(R.id.viewNumber);
        text1.setText(name);
        text2.setText(number);

//        da = (Button) findViewById(R.id.insert);
//        da.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                GenerateOTP obj = new GenerateOTP();
//                final String otp = obj.generateOTP();
//                try {
//                    SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                    String time = sdf.format(new Date());
//                    DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
//                    handler.inserDetails(name, number, otp, time);
//                    Toast.makeText(getApplicationContext(), "Data is Inserted", Toast.LENGTH_LONG).show();
//                } catch (Exception e) {
//                    Toast.makeText(getApplicationContext(), "Database Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
//                }
//            }
//        });
        bt = (Button) findViewById(R.id.send);
        bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String phoneNo = number;
                String msg = "";
                GenerateOTP obj = new GenerateOTP();
                final String otp = obj.generateOTP();
                msg = "Hi. Your OTP is " + otp;

                try {

                    String SENT = "sent";
                    String DELIVERED = "delivered";

                    Intent sentIntent = new Intent(SENT);
                    /*Create Pending Intents*/
                    PendingIntent sentPI = PendingIntent.getBroadcast(
                            getApplicationContext(), 0, sentIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);

                    Intent deliveryIntent = new Intent(DELIVERED);

                    PendingIntent deliverPI = PendingIntent.getBroadcast(
                            getApplicationContext(), 0, deliveryIntent,
                            PendingIntent.FLAG_UPDATE_CURRENT);
                    /* Register for SMS send action */
                    registerReceiver(new BroadcastReceiver() {

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            String result = "";

                            switch (getResultCode()) {

                                case Activity.RESULT_OK:
                                    result = "Message Send";
                                    try {
                                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                                        String time = sdf.format(new Date());
                                        DatabaseHandler handler = new DatabaseHandler(getApplicationContext());
                                        handler.inserDetails(name, number, otp, time);
                                    } catch (Exception e) {
                                        Toast.makeText(getApplicationContext(), "Database Error : " + e.getMessage(), Toast.LENGTH_LONG).show();
                                    }
                                    break;
                                case SmsManager.RESULT_ERROR_GENERIC_FAILURE:
                                    result = "Transmission failed";
                                    break;
                                case SmsManager.RESULT_ERROR_RADIO_OFF:
                                    result = "Radio off";
                                    break;
                                case SmsManager.RESULT_ERROR_NULL_PDU:
                                    result = "No PDU defined";
                                    break;
                                case SmsManager.RESULT_ERROR_NO_SERVICE:
                                    result = "No service";
                                    break;
                            }

                            Toast.makeText(getApplicationContext(), result,
                                    Toast.LENGTH_LONG).show();
                        }

                    }, new IntentFilter(SENT));
     /* Register for Delivery event */
                    registerReceiver(new BroadcastReceiver() {

                        @Override
                        public void onReceive(Context context, Intent intent) {
                            Toast.makeText(getApplicationContext(), "Deliverd",
                                    Toast.LENGTH_LONG).show();
                        }

                    }, new IntentFilter(DELIVERED));

      /*Send SMS*/
                    SmsManager smsManager = SmsManager.getDefault();
                    smsManager.sendTextMessage(phoneNo, null, msg, sentPI,
                            deliverPI);
                } catch (Exception ex) {
                    Toast.makeText(getApplicationContext(),
                            ex.getMessage().toString(), Toast.LENGTH_LONG)
                            .show();
                    ex.printStackTrace();
                }
            }
        });
    }

    protected void sendSMS(String number, String body) {
        Log.i("Send SMS", "");
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);

        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        smsIntent.putExtra("address", "+91" + number);
        smsIntent.putExtra("sms_body", body);

        try {
            startActivity(smsIntent);
            finish();
            Log.i("Finished sending SMS...", "");
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(getApplicationContext(), "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }
}
