package com.example.broadcastreceiver;

import android.content.ComponentName;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    private CustomReceiver mReceiver = new CustomReceiver();
    private ComponentName mReceiverComponentName;
    private PackageManager mPackageManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button broadcastButton = (Button) findViewById(R.id.broadcastButton);
        mReceiverComponentName = new ComponentName(this,CustomReceiver.class);
        mPackageManager = getPackageManager();

        LocalBroadcastManager.getInstance(this).registerReceiver(mReceiver,
                new IntentFilter(CustomReceiver.ACTION_CUSTOM_BROADCAST));
        broadcastButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendCustomBroadcast();
            }
        });

    }

    private void sendCustomBroadcast(){
        Intent broadcastIntent = new Intent(CustomReceiver.ACTION_CUSTOM_BROADCAST);

        LocalBroadcastManager.getInstance(this).sendBroadcast(broadcastIntent);
    }

    protected void onStart() {
        mPackageManager.setComponentEnabledSetting(mReceiverComponentName,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                PackageManager.DONT_KILL_APP);
        super.onStart();
    }

    protected void onStop() {
        mPackageManager.setComponentEnabledSetting(mReceiverComponentName,
                PackageManager.COMPONENT_ENABLED_STATE_DEFAULT,
                PackageManager.DONT_KILL_APP);
        super.onStop();
    }

    protected void onDestroy() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(mReceiver);
        super.onDestroy();
    }
}
