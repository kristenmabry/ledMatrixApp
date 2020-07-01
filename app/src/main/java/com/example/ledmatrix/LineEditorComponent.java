package com.example.ledmatrix;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;

public class LineEditorComponent extends LinearLayout implements View.OnClickListener {
    private EditText input;
    private OnChangeListener mListener = null;
    private int[][] colors = { {7, 7, 7}, {7, 7, 7}, {7, 7, 7}, {7, 7, 7}, {7, 7, 7} };
    private int lineNum;
    private View[] pickers = new View[5];

    public LineEditorComponent(Context context) {
        super(context);
    }

    public LineEditorComponent(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public void init(String title, int line) {
        LayoutInflater inflater = LayoutInflater.from(getContext());
        inflater.inflate(R.layout.component_line_editor, this);
        TextView titleView = (TextView) findViewById(R.id.line_title);
        titleView.setText(title);
        lineNum = line;

        input = (EditText) findViewById(R.id.line_text);

        pickers[0] = (findViewById(R.id.color_picker1));
        pickers[1] = (findViewById(R.id.color_picker2));
        pickers[2] = (findViewById(R.id.color_picker3));
        pickers[3] = (findViewById(R.id.color_picker4));
        pickers[4] = (findViewById(R.id.color_picker5));
        for (int i = 0; i < 5; ++i) {
            pickers[i].setOnClickListener(this);
        }
    }

    public String getText() {
        return input.getText().toString();
    }

    public String[] getHexColors() {
        String[] hexColors = new String[5];
        for (int i = 0; i < 5; i++) {
            hexColors[i] = ColorPickerActivity.intToHex(colors[i][0], colors[i][1], colors[i][2]);
        }
        return hexColors;
    }

    public void setColor(int pickerNum, int[] values) {
        colors[pickerNum] = values;
        pickers[pickerNum].setBackgroundColor(Color.parseColor(ColorPickerActivity.intToHex(values[0], values[1], values[2])));
    }

    public void setOnChangeListener(OnChangeListener listener) {
        mListener = listener;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.color_picker1:
                mListener.onChange(0, lineNum);
                break;
            case R.id.color_picker2:
                mListener.onChange(1, lineNum);
                break;
            case R.id.color_picker3:
                mListener.onChange(2, lineNum);
                break;
            case R.id.color_picker4:
                mListener.onChange(3, lineNum);
                break;
            case R.id.color_picker5:
                mListener.onChange(4, lineNum);
                break;
        }
    }

    public interface OnChangeListener {
        public void onChange(int pickerNum, int lineNum);
    }
}
