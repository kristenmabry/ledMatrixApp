package com.kristenmabry.ledmatrix;

import android.content.Context;
import android.content.Intent;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Bundle;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.kristenmabry.ledmatrix.activities.ColorPickerActivity;

import static android.app.Activity.RESULT_OK;

public class LineEditorFragment extends Fragment implements View.OnClickListener {
    private EditText input;
    private int[][] colors = { {7, 7, 7}, {7, 7, 7}, {7, 7, 7}, {7, 7, 7}, {7, 7, 7} };
    private String mTitle;
    private View[] pickers = new View[5];

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_line_editor, container, false);
    }

    @Override
    public void onInflate(@NonNull Context context, @NonNull AttributeSet attrs, @Nullable Bundle savedInstanceState) {
        super.onInflate(context, attrs, savedInstanceState);
        TypedArray a = context.obtainStyledAttributes(attrs,R.styleable.LineEditorFragment);
        CharSequence title = a.getText(R.styleable.LineEditorFragment_title);
        mTitle = title.toString();
        a.recycle();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        TextView titleView = view.findViewById(R.id.line_title);
        titleView.setText(mTitle);
        input = view.findViewById(R.id.line_text);

        pickers[0] = view.findViewById(R.id.color_picker1);
        pickers[1] = view.findViewById(R.id.color_picker2);
        pickers[2] = view.findViewById(R.id.color_picker3);
        pickers[3] = view.findViewById(R.id.color_picker4);
        pickers[4] = view.findViewById(R.id.color_picker5);
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

    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getContext(), ColorPickerActivity.class);
        switch (view.getId()) {
            case R.id.color_picker1:
                intent.putExtra(ColorPickerActivity.KEY_PICKER_NUM, 0);
                break;
            case R.id.color_picker2:
                intent.putExtra(ColorPickerActivity.KEY_PICKER_NUM, 1);
                break;
            case R.id.color_picker3:
                intent.putExtra(ColorPickerActivity.KEY_PICKER_NUM, 2);
                break;
            case R.id.color_picker4:
                intent.putExtra(ColorPickerActivity.KEY_PICKER_NUM, 3);
                break;
            case R.id.color_picker5:
                intent.putExtra(ColorPickerActivity.KEY_PICKER_NUM, 4);
                break;
        }
        startActivityForResult(intent, ColorPickerActivity.KEY_PICKER_CODE);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == ColorPickerActivity.KEY_PICKER_CODE && resultCode == RESULT_OK) {
            int[] values = data.getIntArrayExtra(ColorPickerActivity.KEY_PICKER_DATA);
            int picker = data.getIntExtra(ColorPickerActivity.KEY_PICKER_NUM, 0);
            setColor(picker, values);
        }
    }

    public int[][] getColors() {
        return colors;
    }

    public void setPrevLayout(String text, int[][] colorArr) {
        input.setText(text);
        for (int i = 0; i < 5; i++) {
            this.setColor(i, colorArr[i]);
        }
    }
}
