package com.kristenmabry.ledmatrix;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;

import java.util.Arrays;


public class SaveLayoutActivity extends AppCompatActivity {
    public static final String KEY_MATRIX_LAYOUT = "matrix_layout";
    private MatrixTextLayout layout;
    private EditText nameInput;
    private EditText sortInput;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_save_layout);
        Intent intent = getIntent();
        layout = intent.getParcelableExtra(KEY_MATRIX_LAYOUT);

        nameInput = (EditText) findViewById(R.id.name);
        sortInput = (EditText) findViewById(R.id.sort_order);
        if (layout.getIsNew()) {
            nameInput.setText(layout.getLine1() + " " + layout.getLine2());
        } else {
            nameInput.setText(layout.getName());
            sortInput.setText(String.valueOf(layout.getSortOrder()));
        }
    }

    public void cancel(View view) {
        finish();
    }

    public void save(View view) {
        layout.setDetails(nameInput.getText().toString(), Integer.valueOf(sortInput.getText().toString()));
        String fileName = layout.getFileName();
        if (fileName == null) {
            String[] files = getFilesDir().list();
            fileName = layout.getName();
            int i = 1;
            while (Arrays.asList(files).contains(fileName + ".json") && layout.getIsNew()) {
                fileName = layout.getName() + "_" + i++;
            }
            fileName += ".json";
            layout.saveFile(fileName);
        }

        Gson gson = new Gson();
        String json = gson.toJson(new MatrixFileLayout(layout), MatrixFileLayout.class);
        if (FileUtils.writeToFile(this, fileName, json)) {
            Toast.makeText(this, getResources().getString(R.string.layout_saved, layout.getName()), Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, ViewLayoutsActivity.class);
            startActivity(intent);
        } else {
            Toast.makeText(this, getResources().getString(R.string.error_saving), Toast.LENGTH_SHORT).show();
        }
    }
}