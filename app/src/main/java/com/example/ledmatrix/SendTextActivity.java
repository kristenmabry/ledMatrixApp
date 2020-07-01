package com.example.ledmatrix;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
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
        line1.init(getString(R.string.first_line), 1);
        line2 = (LineEditorComponent) findViewById(R.id.line2);
        line2.init(getString(R.string.second_line), 2);
        line1Output = (TextView) findViewById(R.id.line1_output);
        line2Output = (TextView) findViewById(R.id.line2_output);

        final Button cancelButton = (Button) findViewById(R.id.cancel_text);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(SendTextActivity.this, ChoicesActivity.class);
                startActivity(intent);
            }
        });

       previewText();

        LineEditorComponent.OnChangeListener listener = new LineEditorComponent.OnChangeListener() {
            public void onChange(int pickerNum, int lineNum) {
                Intent intent = new Intent(SendTextActivity.this, ColorPickerActivity.class);
                intent.putExtra(ColorPickerActivity.KEY_PICKER_NUM, pickerNum);
                intent.putExtra(ColorPickerActivity.KEY_LINE_NUM, lineNum);
                startActivityForResult(intent, ColorPickerActivity.KEY_PICKER_CODE);
            }
        };



        line1.setOnChangeListener(listener);
        line2.setOnChangeListener(listener);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ColorPickerActivity.KEY_PICKER_CODE && resultCode == RESULT_OK) {
            int[] values = data.getIntArrayExtra(ColorPickerActivity.KEY_PICKER_DATA);
            int picker = data.getIntExtra(ColorPickerActivity.KEY_PICKER_NUM, 0);
            int line = data.getIntExtra(ColorPickerActivity.KEY_LINE_NUM, 1);
            if (line == 1) {
                line1.setColor(picker, values);
            } else {
                line2.setColor(picker, values);
            }
        }
    }

    private void previewText() {
        final Button previewButton = (Button) findViewById(R.id.preview);
        previewButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                line1Output.setText(formatText(line1.getText(), line1.getHexColors()));
                line1Output.setBackgroundColor(getResources().getColor(android.R.color.black));

                line2Output.setText(formatText(line2.getText(), line2.getHexColors()));
                line2Output.setBackgroundColor(getResources().getColor(android.R.color.black));
            }
        });
    }

    private Spanned formatText(String text, String[] colors) {
        final String FONT_REPLACEMENT = "<font color='%1$s'>%2$s</font>";
        String lineText = "";
        for (int i = 0; i < 5; ++i) {
            lineText += String.format(FONT_REPLACEMENT, colors[i], text.substring(i, i + 1));
        }
        return Html.fromHtml(lineText);
    }
}