package com.example.ledmatrix;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LineEditorComponent extends LinearLayout {
    private EditText input;

    public LineEditorComponent(Context context) {
        super(context);
    }

    public LineEditorComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(String title) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.component_line_editor, this);
        TextView titleView = (TextView) findViewById(R.id.line_title);
        titleView.setText(title);

        input = (EditText) findViewById(R.id.line_text);
    }

    public String getText() {
        return input.getText().toString();
    }
}
