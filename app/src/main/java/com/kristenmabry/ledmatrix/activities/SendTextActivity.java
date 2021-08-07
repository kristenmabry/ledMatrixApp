package com.kristenmabry.ledmatrix.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.text.HtmlCompat;

import android.bluetooth.BluetoothDevice;
import android.content.Intent;
import android.os.Bundle;
import android.text.Spanned;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.kristenmabry.ledmatrix.LineEditorFragment;
import com.kristenmabry.ledmatrix.bluetooth.BluetoothUtils;
import com.kristenmabry.ledmatrix.layouts.MatrixTextLayout;
import com.kristenmabry.ledmatrix.R;

import me.aflak.bluetooth.Bluetooth;
import me.aflak.bluetooth.interfaces.BluetoothCallback;
import me.aflak.bluetooth.interfaces.DeviceCallback;

public class SendTextActivity extends AppCompatActivity {
    public static final String KEY_IS_NEW = "is_new";
    private LineEditorFragment line1;
    private LineEditorFragment line2;
    private TextView line1Output;
    private TextView line2Output;
    private boolean isNew;
    private MatrixTextLayout prevLayout;
    private String btAddress = null;
    private Bluetooth bluetooth;

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
        }

        if (BluetoothUtils.isAddressValid(this)) {
            Button sendTextButton = (Button) findViewById(R.id.send_text);
            this.btAddress = BluetoothUtils.getSelectedAddress(this);
            bluetooth = new Bluetooth(this);
            bluetooth.setBluetoothCallback(bluetoothCallback);
            bluetooth.setDeviceCallback(new DeviceCallback() {
                @Override public void onDeviceConnected(BluetoothDevice device) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sendTextButton.setEnabled(true);
                        }
                    });
                }
                @Override public void onDeviceDisconnected(BluetoothDevice device, String message) {
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sendTextButton.setEnabled(false);
                        }
                    });
                }
                @Override public void onMessage(byte[] message) {}
                @Override public void onError(int errorCode) {}
                @Override public void onConnectError(BluetoothDevice device, String message) {}
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        bluetooth.onStart();
        if (this.btAddress != null) {
            bluetooth.connectToAddressWithPortTrick(this.btAddress);
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        bluetooth.disconnect();
        bluetooth.onStop();
    }

    public void previewText(View v) {
        line1Output.setText(formatText(line1.getText(), line1.getHexColors()));
        line1Output.setBackgroundResource(R.color.black);

        line2Output.setText(formatText(line2.getText(), line2.getHexColors()));
        line2Output.setBackgroundResource(R.color.black);
    }

    private Spanned formatText(String text, String[] colors) {
        final String FONT_REPLACEMENT = "<font color='%1$s'>%2$s</font>";
        StringBuilder lineText = new StringBuilder();
        text = String.format("%1$-5s", text);
        for (int i = 0; i < text.length(); ++i) {
            lineText.append(String.format(FONT_REPLACEMENT, colors[i], text.charAt(i)));
        }
        return HtmlCompat.fromHtml(lineText.toString(), HtmlCompat.FROM_HTML_MODE_LEGACY);
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

    public void sendLayout(View view) {
        byte[] line1Bytes = line1.encode();
        byte[] line2Bytes = line2.encode();
        byte[] finalString = new byte[21];
        finalString[0] = 'T';
        System.arraycopy(line1Bytes, 0, finalString, 1, 10);
        System.arraycopy(line2Bytes, 0, finalString, 11, 10);
        bluetooth.send(finalString);
        Toast.makeText(this, "Message sent.", Toast.LENGTH_SHORT).show();
    }

    private BluetoothCallback bluetoothCallback = new BluetoothCallback() {
        @Override public void onBluetoothTurningOn() {}
        @Override public void onBluetoothTurningOff() {}
        @Override public void onBluetoothOff() {}
        @Override public void onBluetoothOn() {}
        @Override public void onUserDeniedActivation() {}
    };
}