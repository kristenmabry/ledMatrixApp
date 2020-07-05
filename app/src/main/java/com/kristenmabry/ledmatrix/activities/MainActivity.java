package com.kristenmabry.ledmatrix.activities;


import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.kristenmabry.ledmatrix.R;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.start);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                boolean bluetoothConnected = true;
                if (bluetoothConnected) {
                    Intent intent = new Intent(MainActivity.this, ChoicesActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

}