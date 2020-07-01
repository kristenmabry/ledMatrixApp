package com.example.ledmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

public class SendTextActivity extends AppCompatActivity {
    private LineEditorFragment line1;
    private LineEditorFragment line2;
    private TextView line1Output;
    private TextView line2Output;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        line1 = (LineEditorFragment) getSupportFragmentManager().findFragmentById(R.id.line1);
        line2 = (LineEditorFragment) getSupportFragmentManager().findFragmentById(R.id.line2);
        line1Output = (TextView) findViewById(R.id.line1_output);
        line2Output = (TextView) findViewById(R.id.line2_output);
    }

    public void previewText(View v) {
        line1Output.setText(formatText(line1.getText(), line1.getHexColors()));
        line1Output.setBackgroundColor(getResources().getColor(android.R.color.black));

        line2Output.setText(formatText(line2.getText(), line2.getHexColors()));
        line2Output.setBackgroundColor(getResources().getColor(android.R.color.black));
    }

    private Spanned formatText(String text, String[] colors) {
        final String FONT_REPLACEMENT = "<font color='%1$s'>%2$s</font>";
        String lineText = "";
        text = String.format("%1$-5s", text);
        for (int i = 0; i < text.length(); ++i) {
            lineText += String.format(FONT_REPLACEMENT, colors[i], text.substring(i, i + 1));
        }
        return Html.fromHtml(lineText);
    }

    public void openChoices(View v) {
        Intent intent = new Intent(SendTextActivity.this, ChoicesActivity.class);
        startActivity(intent);
    }
}