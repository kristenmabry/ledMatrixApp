package com.kristenmabry.ledmatrix;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class ChoicesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_choices);
    }

    public void sendText(View view) {
        Intent intent = new Intent(ChoicesActivity.this, SendTextActivity.class);
        intent.putExtra(SendTextActivity.KEY_IS_NEW, true);
        startActivity(intent);
    }

    public void viewSaved(View view) {
        Intent intent = new Intent(ChoicesActivity.this, ViewLayoutsActivity.class);
        startActivity(intent);
    }
}