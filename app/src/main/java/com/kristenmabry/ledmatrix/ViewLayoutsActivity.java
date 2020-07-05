package com.kristenmabry.ledmatrix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;


public class ViewLayoutsActivity extends AppCompatActivity {
    private MatrixFileLayout[] savedLayouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_layouts);
        initToolbar();
        getSavedLayouts();
        populateListView();
    }

    private void initToolbar() {
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ViewLayoutsActivity.this, ChoicesActivity.class);
                startActivity(intent);
            }
        });
    }

    private void getSavedLayouts() {
        String[] jsonStrings = FileUtils.readFiles(this);
        if (jsonStrings == null) {
            Toast.makeText(this, getResources().getString(R.string.error_loading), Toast.LENGTH_SHORT).show();
            return;
        }
        Gson gson = new Gson();
        savedLayouts = new MatrixFileLayout[jsonStrings.length];
        for (int i = 0; i < jsonStrings.length; ++i) {
            savedLayouts[i] = gson.fromJson(jsonStrings[i], MatrixFileLayout.class);
        }
    }

    private void populateListView() {
        if (savedLayouts != null && savedLayouts.length != 0) {
            findViewById(R.id.no_layouts).setVisibility(View.GONE);
            findViewById(R.id.create_text).setVisibility(View.GONE);
            findViewById(R.id.create_custom).setVisibility(View.GONE);
            findViewById(R.id.list).setVisibility(View.VISIBLE);

            RecyclerView list = findViewById(R.id.list);
            list.setHasFixedSize(true);

            RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(this);
            list.setLayoutManager(layoutManager);

            savedLayouts = MatrixFileLayout.sortForDisplay(savedLayouts);
            RecyclerView.Adapter adapter = new LayoutListAdapter(savedLayouts);
            list.setAdapter(adapter);
        }
    }

    public void sendText(View view) {
        Intent intent = new Intent(this, SendTextActivity.class);
        intent.putExtra(SendTextActivity.KEY_IS_NEW, true);
        startActivity(intent);
    }
}