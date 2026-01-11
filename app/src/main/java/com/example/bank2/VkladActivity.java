package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;
import com.example.bank2.models.Percents;
import com.example.bank2.models.User;
import com.example.bank2.models.Vklad;

public class VkladActivity extends AppCompatActivity {
    private TextView tvPercents, tvVkladInfo;
    private EditText etPercent, etMonths, etAmount;
    private Button btnCreateVklad, btnPopolnitVklad;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vklad);

        tvPercents = findViewById(R.id.tvPercents);
        tvVkladInfo = findViewById(R.id.tvVkladInfo);
        etPercent = findViewById(R.id.etPercent);
        etMonths = findViewById(R.id.etMonths);
        etAmount = findViewById(R.id.etAmount);
        btnCreateVklad = findViewById(R.id.btnCreateVklad);
        btnPopolnitVklad = findViewById(R.id.btnPopolnitVklad);

        dbHandler = new DataBaseHandler(this);

        new CheckVkladTask().execute();

        btnCreateVklad.setOnClickListener(v -> {
            String percent = etPercent.getText().toString().trim();
            String months = etMonths.getText().toString().trim();
            if (percent.isEmpty() || months.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }
            new CreateVkladTask().execute(percent, months);
        });

        btnPopolnitVklad.setOnClickListener(v -> {
            String amount = etAmount.getText().toString().trim();
            if (amount.isEmpty()) {
                Toast.makeText(this, "Введите сумму", Toast.LENGTH_SHORT).show();
                return;
            }
            new PopolnitVkladTask().execute(amount);
        });
    }

    private class CheckVkladTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return dbHandler.CheckVklad(User.getLogin());
        }

        @Override
        protected void onPostExecute(Boolean hasVklad) {
            tvPercents.setText(Percents.getPercents());
            if (hasVklad) {
                tvVkladInfo.setText("Вклад: " + Vklad.getNumber_vklad() + "\n" +
                        "Сумма: " + Vklad.getSum() + "\n" +
                        "Процент: " + Vklad.getPercent() + "%\n" +
                        "Дата окончания: " + Vklad.getEnd_date());
                btnPopolnitVklad.setEnabled(true);
                etAmount.setEnabled(true);
            } else {
                tvVkladInfo.setText("У вас нет вклада");
                btnPopolnitVklad.setEnabled(false);
                etAmount.setEnabled(false);
            }
        }
    }

    private class CreateVkladTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return dbHandler.CreateVklad(User.getLogin(), params[0], params[1]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(VkladActivity.this, "Вклад успешно открыт", Toast.LENGTH_SHORT).show();
                new CheckVkladTask().execute();
            } else {
                Toast.makeText(VkladActivity.this, "Ошибка при создании вклада", Toast.LENGTH_SHORT).show();
            }
        }
    }

    private class PopolnitVkladTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return dbHandler.PopolnitVklad(params[0], User.getLogin());
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result) {
                Toast.makeText(VkladActivity.this, "Вклад пополнен", Toast.LENGTH_SHORT).show();
                etAmount.setText("");
                new CheckVkladTask().execute();
            } else {
                Toast.makeText(VkladActivity.this, "Ошибка при пополнении вклада", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
