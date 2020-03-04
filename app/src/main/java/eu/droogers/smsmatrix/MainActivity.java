package eu.droogers.smsmatrix;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.lifecycle.ViewModelProvider;

import eu.droogers.smsmatrix.viewModel.MainViewModel;

import static android.Manifest.permission.READ_CONTACTS;
import static android.Manifest.permission.READ_EXTERNAL_STORAGE;
import static android.Manifest.permission.READ_PHONE_STATE;
import static android.Manifest.permission.READ_SMS;
import static android.Manifest.permission.RECEIVE_SMS;
import static android.Manifest.permission.SEND_SMS;
import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {
    static Matrix mx;
    private EditText botUsername;
    private EditText botPassword;
    private EditText username;
    private EditText device;
    private EditText hsUrl;
    private EditText syncDelay;
    private EditText syncTimeout;
    private static final String[] PERMISSIONS_REQUIRED = new String[]{
            READ_SMS, SEND_SMS, RECEIVE_SMS, READ_PHONE_STATE, READ_CONTACTS, READ_EXTERNAL_STORAGE
    };
    private static final int PERMISSION_REQUEST_CODE = 200;
    private MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        viewModel = new ViewModelProvider(this).get(MainViewModel.class);

        botUsername = (EditText) findViewById(R.id.editText_botUsername);
        botPassword = (EditText) findViewById(R.id.editText_botpassword);
        username = (EditText) findViewById(R.id.editText_username);
        device = (EditText) findViewById(R.id.editText_device);
        hsUrl = (EditText) findViewById(R.id.editText_hsUrl);
        syncDelay = (EditText) findViewById(R.id.editText_syncDelay);
        syncTimeout = (EditText) findViewById(R.id.editText_syncTimeout);

        botUsername.setText(viewModel.getBotUsername());
        botPassword.setText(viewModel.getBotPassword());
        username.setText(viewModel.getUsername());
        device.setText(viewModel.getDevice());
        hsUrl.setText(viewModel.getHsUrl());
        syncDelay.setText(viewModel.getSyncDelay());
        syncTimeout.setText(viewModel.getSyncTimeout());

        Button saveButton = (Button) findViewById(R.id.button_save);
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!checkPermissions()) {
                    askPermissions();
                } else {
                    viewModel.setBotUsername(botUsername.getText().toString());
                    viewModel.setBotPassword(botPassword.getText().toString());
                    viewModel.setUsername(username.getText().toString());
                    viewModel.setDevice(device.getText().toString());
                    viewModel.setHsUrl(hsUrl.getText().toString());
                    viewModel.setSyncDelay(syncDelay.getText().toString());
                    viewModel.setSyncTimeout(syncTimeout.getText().toString());
                    viewModel.apply();

                    Log.e(TAG, "onClick: " + botUsername.getText().toString());
                    startService();
                }

            }
        });
        if (!checkPermissions()) {
            askPermissions();
        } else {
            startService();
        }
    }

    private boolean checkPermissions() {
        for (String permission : PERMISSIONS_REQUIRED) {
            int result = ContextCompat.checkSelfPermission(getApplicationContext(), permission);
            if (result != PackageManager.PERMISSION_GRANTED) {
                return false;
            }
            Log.i(TAG, "setOnClickListener - result result result" + result);
        }
        return true;
    }

    private void askPermissions() {
        ActivityCompat.requestPermissions(this, PERMISSIONS_REQUIRED, PERMISSION_REQUEST_CODE);
    }


    private void startService() {
        Intent intent = new Intent(this, MatrixService.class);
        startService(intent);
    }
}
