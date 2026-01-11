package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;
import com.example.bank2.models.CardHistory;

import java.util.ArrayList;
import java.util.List;

public class HistoryActivity extends AppCompatActivity {
    private ListView lvHistory;
    private Spinner spCard;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);

        lvHistory = findViewById(R.id.lvHistory);
        spCard = findViewById(R.id.spCard);
        dbHandler = new DataBaseHandler(this);

        new LoadCardsForHistoryTask().execute();
        
        spCard.setOnItemSelectedListener(new android.widget.AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(android.widget.AdapterView<?> parent, View view, int position, long id) {
                String selectedCard = (String) parent.getItemAtPosition(position);
                if (selectedCard != null) {
                    new LoadHistoryTask().execute(selectedCard);
                }
            }

            @Override
            public void onNothingSelected(android.widget.AdapterView<?> parent) {
            }
        });
    }

    private class LoadCardsForHistoryTask extends AsyncTask<Void, Void, List<String>> {
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
                ArrayAdapter<String> adapter = new ArrayAdapter<>(HistoryActivity.this,
                        android.R.layout.simple_spinner_item, cards);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                spCard.setAdapter(adapter);
            }
        }
    }

    private class LoadHistoryTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            List<CardHistory> historyList = dbHandler.LoadHistory(params[0]);
            List<String> historyStrings = new ArrayList<>();
            for (CardHistory history : historyList) {
                historyStrings.add(history.getOperation() + ": " + history.getSumma() + " руб\n" +
                        "От: " + history.getNumbercardotpr() + "\n" +
                        "К: " + history.getNumbercardpol() + "\n" +
                        "Дата: " + history.getDate());
            }
            return historyStrings;
        }

        @Override
        protected void onPostExecute(List<String> history) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(HistoryActivity.this,
                    android.R.layout.simple_list_item_1, history);
            lvHistory.setAdapter(adapter);
        }
    }
}
