package com.example.ledmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.GridLayout;
import android.widget.LinearLayout;

public class ColorPickerActivity extends AppCompatActivity implements View.OnClickListener {
    final static public int KEY_PICKER_CODE = 1000;
    final static public String KEY_PICKER_DATA = "color_values";
    final static public String KEY_PICKER_NUM = "picker_num";
    final static public String KEY_LINE_NUM = "line_num";
    private int pickerNum;
    private int lineNum;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_color_picker);
        Intent intent = getIntent();
        pickerNum = intent.getIntExtra(KEY_PICKER_NUM, 0);
        lineNum = intent.getIntExtra(KEY_LINE_NUM, 1);
        GridLayout grid = (GridLayout) findViewById(R.id.grid);
        generateColors(grid);
    }

    @Override
    public void onClick(View v) {
        Intent returnIntent = new Intent();
        returnIntent.putExtra(KEY_LINE_NUM, lineNum);
        returnIntent.putExtra(KEY_PICKER_NUM, pickerNum);
        returnIntent.putExtra(KEY_PICKER_DATA, getColorValues(v.getId()));
        setResult(Activity.RESULT_OK, returnIntent);
        finish();
    }

    private View createSquare(int r, int g, int b) {
        View square = new View(ColorPickerActivity.this);
        int size = getResources().getDimensionPixelSize(R.dimen.color_picker_size);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(size, size);
        int margins = getResources().getDimensionPixelSize(R.dimen.color_picker_margin);
        params.setMargins(margins, margins, margins, margins);
        square.setLayoutParams(params);
        square.setBackgroundColor(Color.parseColor(intToHex(r, g, b)));
        square.setId(getColorId(r, g, b));
        square.setOnClickListener(this);
        return square;
    }

    private int getColorId(int r, int g, int b) {
        return (r * 100) + (g * 10) + b;
    }

    private int[] getColorValues(int id) {
        int r = id / 100;
        int g = (id % 100) / 10;
        int b = id % 10;
        int[] colors = { r, g, b };
        return colors;
    }

    static public String intToHex(int r, int g, int b) {
        String hexR = String.format("%02X", (int) (r * 255) / 7);
        String hexG = String.format("%02X", (int) (g * 255) / 7);
        String hexB = String.format("%02X", (int) (b * 255) / 7);
        return "#" + hexR + hexG + hexB;
    }

    private void generateColors(GridLayout grid) {
        int r;
        int g;
        int b;
        for (r = g = b = 7; b >= 0; --b, --g, --r) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (r = 1, b = g = 0; r < 8; ++r) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (r = 7; g < 8; ++g) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (g = 7; r >= 0; --r) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (r = 0; b < 8; ++b) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (b = 7; g >= 0; --g) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (g = 0; r < 8; ++r) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (r = 7; b >= 0; --b) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (b = 0; b < 8; ++b, ++g) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (b = 7, g = r = 0; g < 8; ++r, ++g) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (g = 7, r = b = 6; b > 0; --r, --b) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (g = 7; g > 0; --g) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
        for (b = 0; b < 8; ++b) {
            View square = createSquare(r, g, b);
            grid.addView(square);
        }
    }
}