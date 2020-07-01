package com.kristenmabry.ledmatrix;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

public class SendTextActivity extends AppCompatActivity {
    public static final String KEY_IS_NEW = "is_new";
    private LineEditorFragment line1;
    private LineEditorFragment line2;
    private TextView line1Output;
    private TextView line2Output;
    private boolean isNew;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(KEY_IS_NEW, true);

        line1 = (LineEditorFragment) getSupportFragmentManager().findFragmentById(R.id.line1);
        line2 = (LineEditorFragment) getSupportFragmentManager().findFragmentById(R.id.line2);
        line1Output = (TextView) findViewById(R.id.line1_output);
        line2Output = (TextView) findViewById(R.id.line2_output);
    }

    public void previewText(View v) {
        line1Output.setText(formatText(line1.getText(), line1.getHexColors()));
        line1Output.setBackgroundResource(android.R.color.black);

        line2Output.setText(formatText(line2.getText(), line2.getHexColors()));
        line2Output.setBackgroundResource(android.R.color.black);
    }

    private Spanned formatText(String text, String[] colors) {
        final String FONT_REPLACEMENT = "<font color='%1$s'>%2$s</font>";
        String lineText = "";
        text = String.format("%1$-5s", text);
        for (int i = 0; i < text.length(); ++i) {
            lineText += String.format(FONT_REPLACEMENT, colors[i], text.substring(i, i + 1));
        }
        return HtmlCompat.fromHtml(lineText, HtmlCompat.FROM_HTML_MODE_LEGACY);
    }

    public void openChoices(View v) {
        Intent intent = new Intent(SendTextActivity.this, ChoicesActivity.class);
        startActivity(intent);
    }

    public void saveLayout(View view) {
        Intent intent = new Intent(this, SaveLayoutActivity.class);
        MatrixTextLayout layout = new MatrixTextLayout(line1.getText(), line2.getText(), line1.getColors(), line2.getColors(), isNew);
        intent.putExtra(SaveLayoutActivity.KEY_MATRIX_LAYOUT, layout);
        startActivity(intent);
    }
}