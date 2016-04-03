package com.dryv.onpoint;

/**
 * Created by anichindaniil on 30.03.16.
 *
 * Provides BT manipulation functions
 */

import android.app.Activity;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


public class BTUtils
{
    private static BluetoothAdapter mBluetoothAdapter;
    private static final short CONNECTION_RANGE = -45;
    private static final short VISIBILITY_RANGE = -95;
    private static  String LOG_TAG = "BESTAPPLICATIONEU";
    private Context context;
    private BroadcastReceiver mReceiver;
    BTUtils(Context c){
        context = c;
        // start looking for bluetooth devices
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        final String[] bt_code = new String[1];

        // Discover new devices
        // Create a BroadcastReceiver for ACTION_FOUND
        mReceiver = new BroadcastReceiver()
        {
            @Override
            public void onReceive(Context context, Intent intent)
            {
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction())){
                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
                    Log.d(LOG_TAG,device.getName());
                }
                /*// When discovery finds specified device
                if (BluetoothDevice.ACTION_FOUND.equals(intent.getAction()) && // Unneeded?
                        BluetoothDevice.EXTRA_NAME.equals(bt_name))
                {
                    // Get the "RSSI" to get the signal strength as integer
                    int rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE);

                    if (rssi >= CONNECTION_RANGE) {
                        // Connect to bt, get the key
                        bt_code[0] = "bt_code";     // Awful staff, isn't it?
                    } else if (rssi >= VISIBILITY_RANGE) {
                        new Utils().op_beep(getRSSILevel(rssi));
                        bt_code[0] = "bt_to_far";
                    }
                } else {
                    bt_code[0] = "bt_to_far";
                }*/
            }
        };

    }


    public static double getRSSILevel(int current_rssi) {
        // may be will do more complicated later
        return (double)(current_rssi - VISIBILITY_RANGE)/(CONNECTION_RANGE - VISIBILITY_RANGE);
    }
    public void stopDiscovery(){
        mBluetoothAdapter.cancelDiscovery();
        context.unregisterReceiver(mReceiver);
    }
    public void startDiscovery(){
        IntentFilter filter = new IntentFilter(BluetoothDevice.ACTION_FOUND);
        context.registerReceiver(mReceiver, filter);
        mBluetoothAdapter.startDiscovery();
    }
}
