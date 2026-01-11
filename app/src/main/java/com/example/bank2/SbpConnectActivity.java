package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;
import com.example.bank2.models.User;

public class SbpConnectActivity extends AppCompatActivity {
    private EditText etPhoneNumber;
    private Button btnConnect, btnDisconnect;
    private Switch swSbpStatus;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sbp_connect);

        etPhoneNumber = findViewById(R.id.etPhoneNumber);
        btnConnect = findViewById(R.id.btnConnect);
        btnDisconnect = findViewById(R.id.btnDisconnect);
        swSbpStatus = findViewById(R.id.swSbpStatus);

        dbHandler = new DataBaseHandler(this);

        etPhoneNumber.setText(User.getNumberPhone());

        btnConnect.setOnClickListener(v -> {
            String phone = etPhoneNumber.getText().toString().trim();
            if (phone.isEmpty()) {
                Toast.makeText(this, "Введите номер телефона", Toast.LENGTH_SHORT).show();
                return;
            }
            new ConnectSbpTask().execute(phone);
        });

        btnDisconnect.setOnClickListener(v -> new DisconnectSbpTask().execute());
    }

    private class ConnectSbpTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return dbHandler.updateNumberPhone(params[0]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(SbpConnectActivity.this, "СБП подключена", Toast.LENGTH_SHORT).show();
                swSbpStatus.setChecked(true);
            } else {
                Toast.makeText(SbpConnectActivity.this, "Ошибка при подключении СБП", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class DisconnectSbpTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return dbHandler.disconnectSBP();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(SbpConnectActivity.this, "СБП отключена", Toast.LENGTH_SHORT).show();
                swSbpStatus.setChecked(false);
            } else {
                Toast.makeText(SbpConnectActivity.this, "Ошибка при отключении СБП", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
