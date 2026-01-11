package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;

public class CreateCardActivity extends AppCompatActivity {
    private Button btnCreateCard;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_card);

        btnCreateCard = findViewById(R.id.btnCreateCard);
        dbHandler = new DataBaseHandler(this);

        btnCreateCard.setOnClickListener(v -> new CreateCardTask().execute());
    }

    private class CreateCardTask extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... voids) {
            return dbHandler.createCard();
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (result == null) {
                Toast.makeText(CreateCardActivity.this, "Ошибка подключения к базе данных. Проверьте интернет-соединение.", Toast.LENGTH_LONG).show();
                return;
            }
            if (result) {
                Toast.makeText(CreateCardActivity.this, "Карта успешно создана", Toast.LENGTH_SHORT).show();
                startActivity(new android.content.Intent(CreateCardActivity.this, AccountActivity.class));
                finish();
            } else {
                Toast.makeText(CreateCardActivity.this, "Ошибка при создании карты", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
