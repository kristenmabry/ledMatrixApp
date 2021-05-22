package com.kristenmabry.ledmatrix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.kristenmabry.ledmatrix.bluetooth.BluetoothDevicesAdapter;
import com.kristenmabry.ledmatrix.R;
import com.kristenmabry.ledmatrix.bluetooth.BluetoothUtils;

public class ViewBluetoothDevices extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_bluetooth_devices);
        initToolbar();
        populateListView();
    }

    private void initToolbar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setTitle(getResources().getString(R.string.bluetooth_devices));
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewBluetoothDevices.this, ChoicesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void populateListView() {
        BluetoothDevice[] devices = BluetoothUtils.getAllDevices();
        if (devices.length > 0) {
            findViewById(R.id.no_devices).setVisibility(View.GONE);
            findViewById(R.id.list).setVisibility(View.VISIBLE);

            RecyclerView list = findViewById(R.id.list);
            list.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            list.setLayoutManager(layoutManager);

            BluetoothDevicesAdapter adapter = new BluetoothDevicesAdapter(this, devices);
            list.setAdapter(adapter);
        }
    }
}