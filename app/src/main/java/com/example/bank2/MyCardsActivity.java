package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;
import com.example.bank2.models.Card;

import java.util.ArrayList;
import java.util.List;

public class MyCardsActivity extends AppCompatActivity {
    private ListView lvCards;
    private TextView tvBalance;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_cards);

        lvCards = findViewById(R.id.lvCards);
        tvBalance = findViewById(R.id.tvBalance);
        dbHandler = new DataBaseHandler(this);

        new LoadCardsTask().execute();
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
                        String balance = dbHandler.LoadCard(card.trim());
                        cardList.add("Карта: " + card.trim() + "\nБаланс: " + balance);
                    }
                }
            }
            return cardList;
        }

        @Override
        protected void onPostExecute(List<String> cards) {
            if (!cards.isEmpty()) {
                ArrayAdapter<String> adapter = new ArrayAdapter<>(MyCardsActivity.this,
                        android.R.layout.simple_list_item_1, cards);
                lvCards.setAdapter(adapter);
            }
        }
    }
}
