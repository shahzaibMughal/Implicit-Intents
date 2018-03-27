package com.example.shahzaib.implicitintent;

import android.Manifest;
import android.content.EntityIterator;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.AlarmClock;
import android.provider.ContactsContract;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    int CALL_PERMISSION_REQUEST_CODE = 0;

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        //check which permission is granted of denied
        if(requestCode == CALL_PERMISSION_REQUEST_CODE)
        {
            if(grantResults.length>0 && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            {
                // user granted the permission
                // check agrain for the permission, if it is granted or denied by some error like screenOverlay
                if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) == PackageManager.PERMISSION_GRANTED)
                {// granteeed permission is grated
                    Toast.makeText(this, "User Granted the permission :)", Toast.LENGTH_SHORT).show();
                    Toast.makeText(this, "Place phone call again", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(this, "permission is denied by some error :(", Toast.LENGTH_SHORT).show();
                }

            }
            else
            {
                // user denied the permission , request permission again
                ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISSION_REQUEST_CODE);
            }
        }
    }







    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);




    }


    public void onButtonClick(View view)
    {
        switch (view.getId())
        {
            case R.id.launchMap:
                launchMap();
                break;

            case R.id.openMarket:
                openMarket();
                break;

            case R.id.sendEmail:
                sendEmail();
                break;

            case  R.id.setAlaram:
                setAlaram();
                break;
            case  R.id.captureImage:
                startActivity(new Intent(this,captureImage.class));
                break;

            case R.id.insertContact:
                insertContact();
                break;

            case R.id.dialNumber:
                dialNumber();
                break;

            case R.id.placeCall:
                placeCall();
                break;

            case R.id.launchBrowser:
                launchBrowser();
                break;

            case R.id.composeSMS:
                composeSMS();
                break;
        }
    }

    private void composeSMS() {

        Intent intent = new Intent(Intent.ACTION_VIEW);

        intent.setType("vnd.android-dir/mms-sms");
        intent.putExtra("address","03056302013");
        intent.putExtra("sms_body","hello World!");



        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "No activity can perform this action", Toast.LENGTH_SHORT).show();
        }
    }


    private void launchBrowser() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("https://www.google.com"));
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

    private void placeCall() {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:03056302013"));

        // first check the permission then start the intentActivity
        if(ActivityCompat.checkSelfPermission(this,Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED )
        { // permission is not granted
            ActivityCompat.requestPermissions(this,new String[]{Manifest.permission.CALL_PHONE},CALL_PERMISSION_REQUEST_CODE);
        }
        else
        { // permission is granted
            if(intent.resolveActivity(getPackageManager())!=null)
            {
                startActivity(intent);
            }
        }


    }

    private void dialNumber() {
        Intent intent = new Intent(Intent.ACTION_DIAL);
        intent.setData(Uri.parse("tel:03056302013"));
        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

    private void insertContact() {
        Intent intent = new Intent(Intent.ACTION_INSERT);
        intent.setType(ContactsContract.Contacts.CONTENT_TYPE);
        intent.putExtra(ContactsContract.Intents.Insert.NAME, "shahzaib mughal");
        intent.putExtra(ContactsContract.Intents.Insert.EMAIL, "shahziab.mughal02013@gmail.com");
        intent.putExtra(ContactsContract.Intents.Insert.PHONE, "03056302013");
        intent.putExtra("finishActivityOnSaveCompleted", true); // when user finish, saving contact then this help to navigate back to our app

        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

    private void setAlaram() {
        Intent intent = new Intent(AlarmClock.ACTION_SET_ALARM);
        intent.putExtra(AlarmClock.EXTRA_MINUTES,30); // it ranges from 0-59
        intent.putExtra(AlarmClock.EXTRA_HOUR,10); // ranges 0-23
        intent.putExtra(AlarmClock.EXTRA_MESSAGE, "This is the message...");
//        intent.putExtra(AlarmClock.EXTRA_SKIP_UI,true);

        ArrayList<Integer> days = new ArrayList<>();
        days.add(Calendar.MONDAY);
        days.add(Calendar.FRIDAY);
        days.add(Calendar.SATURDAY);
        intent.putExtra(AlarmClock.EXTRA_DAYS, days);


        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
        else
        {
            Toast.makeText(this, "No activity can perform the action", Toast.LENGTH_SHORT).show();
        }

        /// hahahaha no neeed to & request the permission, i already waste my time
        /*if(checkAlaramPermission())
        {
            if(intent.resolveActivity(getPackageManager())!=null)
            {
                startActivity(intent);
            }
            else
            {
                Toast.makeText(this, "No activity can perform the action", Toast.LENGTH_SHORT).show();
            }
        }*/
    }

    private void sendEmail() {
        Intent intent = new Intent(Intent.ACTION_SEND);
        intent.setData(Uri.parse("mailto:"));
        String [] to = {"shahzaib.mughal02013@gmail.com","shahzaibzaheeer@gmail.com"};
        intent.putExtra(Intent.EXTRA_EMAIL,to); // pass array of string
        intent.putExtra(Intent.EXTRA_SUBJECT,"message Subject");
        intent.putExtra(Intent.EXTRA_TEXT,"message body");
        intent.setType("message/rfc822"); // MIME type compulsary for email

        Intent chooser = Intent.createChooser(intent,"Select Email App");
        startActivity(chooser);
    }

    private void openMarket() {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("market://details?id=com.facebook.katana"));

        if(intent.resolveActivity(getPackageManager())!=null)
        {
            startActivity(intent);
        }
    }

    private void launchMap()
    {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.setData(Uri.parse("geo:32.4464176,74.1298449"));

        Intent chooser = Intent.createChooser(intent,"Choose Map");
        startActivity(chooser);

    }
}
