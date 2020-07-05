package com.kristenmabry.ledmatrix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.TextView;

import com.kristenmabry.ledmatrix.LineEditorFragment;
import com.kristenmabry.ledmatrix.classes.MatrixTextLayout;
import com.kristenmabry.ledmatrix.R;

public class SendTextActivity extends AppCompatActivity {
    public static final String KEY_IS_NEW = "is_new";
    private LineEditorFragment line1;
    private LineEditorFragment line2;
    private TextView line1Output;
    private TextView line2Output;
    private boolean isNew;
    private MatrixTextLayout prevLayout;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_text);

        Intent intent = getIntent();
        isNew = intent.getBooleanExtra(KEY_IS_NEW, true);

        line1 = (LineEditorFragment) getSupportFragmentManager().findFragmentById(R.id.line1);
        line2 = (LineEditorFragment) getSupportFragmentManager().findFragmentById(R.id.line2);
        line1Output = findViewById(R.id.line1_output);
        line2Output = findViewById(R.id.line2_output);

        if (!isNew) {
            prevLayout = intent.getParcelableExtra(SaveLayoutActivity.KEY_MATRIX_LAYOUT);
            line1.setPrevLayout(prevLayout.getLine1(), prevLayout.getColors1());
            line2.setPrevLayout(prevLayout.getLine2(), prevLayout.getColors2());
            this.previewText(null);
        }
    }

    public void previewText(View v) {
        line1Output.setText(formatText(line1.getText(), line1.getHexColors()));
        line1Output.setBackgroundResource(R.color.black);

        line2Output.setText(formatText(line2.getText(), line2.getHexColors()));
        line2Output.setBackgroundResource(R.color.black);
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

    public void cancel(View v) {
        finish();
    }

    public void saveLayout(View view) {
        Intent intent = new Intent(this, SaveLayoutActivity.class);
        if (isNew) {
            prevLayout = new MatrixTextLayout(line1.getText(), line2.getText(), line1.getColors(), line2.getColors(), isNew);
        } else {
            prevLayout.update(line1.getText(), line2.getText(), line1.getColors(), line2.getColors());
        }
        intent.putExtra(SaveLayoutActivity.KEY_MATRIX_LAYOUT, prevLayout);
        startActivity(intent);
    }
}