package com.example.ledmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class SendTextActivity extends AppCompatActivity {
    private LineEditorComponent line1;
    private LineEditorComponent line2;
    private TextView line1Output;
    private TextView line2Output;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        line1 = (LineEditorComponent) findViewById(R.id.line1);
        line1.init(getString(R.string.first_line));
        line2 = (LineEditorComponent) findViewById(R.id.line2);
        line2.init(getString(R.string.second_line));
        line1Output = (TextView) findViewById(R.id.line1_output);
        line2Output = (TextView) findViewById(R.id.line2_output);

        final Button cancelButton = (Button) findViewById(R.id.cancel_text);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SendTextActivity.this, ChoicesActivity.class);
                startActivity(intent);
            }
        });

        final Button previewButton = (Button) findViewById(R.id.preview);
        previewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                line1Output.setText(line1.getText());
                line2Output.setText(line2.getText());
            }
        });
    }
}