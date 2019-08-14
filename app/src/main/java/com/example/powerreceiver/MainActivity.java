package com.example.powerreceiver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    public ComponentName mReceiverComponentName;
    public PackageManager mPackageManager;

    private CustomReceiver mReceiver = new CustomReceiver();

    //string for custom broadcast
    private static final String ACTION_CUSTOM_BROADCAST =
            "com.example.android.powerReceiver.ACTION_CUSTOM_BROADCAST";
    @Override
    protected void onStart(){

        mPackageManager.setComponentEnabledSetting
                (mReceiverComponentName, PackageManager.COMPONENT_ENABLED_STATE_ENABLED, PackageManager.DONT_KILL_APP);
        //registering custom broadcast
        LocalBroadcastManager.getInstance(this)
                .registerReceiver(mReceiver, new IntentFilter(ACTION_CUSTOM_BROADCAST));
        super.onStart();
    }
    @Override
    protected void onStop(){

        mPackageManager.setComponentEnabledSetting
                (mReceiverComponentName, PackageManager.COMPONENT_ENABLED_STATE_DISABLED, PackageManager.DONT_KILL_APP);
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        //I should unregister receiver since it's no longer needed
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mReceiverComponentName = new ComponentName(this, CustomReceiver.class);
        mPackageManager= getPackageManager();



    }

    public void SendCustomBroadcast(View view) {
        //creating custom broadcast for my app
        Intent customBroadcastIntent = new Intent(ACTION_CUSTOM_BROADCAST);  // new intent with custom action string
        //static registrations in manifest are not allowed with LocalBroadcastManager
        LocalBroadcastManager.getInstance(this).sendBroadcast(customBroadcastIntent);
    }
}
