package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;

import java.util.ArrayList;
import java.util.List;

public class PerevodActivity extends AppCompatActivity {
    private Spinner spTransferType, spCardFrom;
    private EditText etCardNumber, etAmount;
    private Button btnTransfer;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_perevod);

        spTransferType = findViewById(R.id.spTransferType);
        spCardFrom = findViewById(R.id.spCardFrom);
        etCardNumber = findViewById(R.id.etCardNumber);
        etAmount = findViewById(R.id.etAmount);
        btnTransfer = findViewById(R.id.btnTransfer);

        dbHandler = new DataBaseHandler(this);

        String[] transferTypes = {"По номеру карты", "Система быстрых платежей(По номеру)"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, transferTypes);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spTransferType.setAdapter(adapter);

        new LoadCardsTask().execute();

        btnTransfer.setOnClickListener(v -> {
            if (spCardFrom.getSelectedItem() == null) {
                Toast.makeText(this, "Выберите карту для перевода", Toast.LENGTH_SHORT).show();
                return;
            }
            String transferType = spTransferType.getSelectedItem().toString();
            String cardFrom = spCardFrom.getSelectedItem().toString();
            String cardTo = etCardNumber.getText().toString().trim();
            String amount = etAmount.getText().toString().trim();

            if (cardTo.isEmpty() || amount.isEmpty()) {
                Toast.makeText(this, "Заполните все поля", Toast.LENGTH_SHORT).show();
                return;
            }

            new AlertDialog.Builder(this)
                    .setTitle("Подтверждение")
                    .setMessage("Перевести " + amount + " руб с карты " + cardFrom + "?")
                    .setPositiveButton("Да", (dialog, which) -> {
                        new TransferTask().execute(transferType, cardTo, amount, cardFrom);
                    })
                    .setNegativeButton("Нет", null)
                    .show();
        });
    }

    private class LoadCardsTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            String cards = dbHandler.LoadCards();
            List<String> cardList = new ArrayList<>();
            if (!cards.isEmpty()) {
                String[] cardArray = cards.split(" ");
                for (String card : cardArray) {
                    if (!card.trim().isEmpty()) {
                        cardList.add(card.trim());
                    }
                }
            }
            return cardList;
        }

        @Override
        protected void onPostExecute(List<String> cards) {
            if (!cards.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(PerevodActivity.this,
                        android.R.layout.simple_spinner_item, cards);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCardFrom.setAdapter(adapter);
            }
        }
    }

    private class TransferTask extends AsyncTask<String, Void, Boolean> {
        @Override
        protected Boolean doInBackground(String... params) {
            return dbHandler.perevod(params[0], params[1], params[2], params[3]);
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == null) {
                Toast.makeText(PerevodActivity.this, "Ошибка подключения к базе данных. Проверьте интернет-соединение.", Toast.LENGTH_LONG).show();
                return;
            }
            if (result) {
                Toast.makeText(PerevodActivity.this, "Перевод выполнен успешно", Toast.LENGTH_SHORT).show();
                etCardNumber.setText("");
                etAmount.setText("");
            } else {
                Toast.makeText(PerevodActivity.this, "Ошибка при выполнении перевода", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
