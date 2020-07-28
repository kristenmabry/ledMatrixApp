package com.kristenmabry.ledmatrix.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;

import java.util.Set;

public class BluetoothUtils {

    public static BluetoothDevice[] getAllDevices() {
        BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        Set<BluetoothDevice> pairedDevices = bluetoothAdapter.getBondedDevices();
        return pairedDevices.toArray(new BluetoothDevice[pairedDevices.size()]);
    }
}
