package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;
import com.example.bank2.models.CardHistory;

import java.util.ArrayList;
import java.util.List;

public class AdminHistoryActivity extends AppCompatActivity {
    private EditText etSearchCard;
    private ListView lvHistory;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_history);

        etSearchCard = findViewById(R.id.etSearchCard);
        lvHistory = findViewById(R.id.lvHistory);
        dbHandler = new DataBaseHandler(this);

        etSearchCard.setOnEditorActionListener((v, actionId, event) -> {
            String searchText = etSearchCard.getText().toString().trim();
            if (!searchText.isEmpty()) {
                new LoadAdminHistoryTask().execute(searchText);
            }
            return true;
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.admin_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_zayavki) {
            startActivity(new android.content.Intent(this, AdminZayavkiActivity.class));
            return true;
        } else if (id == R.id.menu_logout) {
            finish();
            startActivity(new android.content.Intent(this, LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class LoadAdminHistoryTask extends AsyncTask<String, Void, List<String>> {
        @Override
        protected List<String> doInBackground(String... params) {
            List<CardHistory> historyList = dbHandler.LoadHistoryAdmin(params[0]);
            List<String> historyStrings = new ArrayList<>();
            for (CardHistory history : historyList) {
                historyStrings.add(history.getOperation() + ": " + history.getSumma() + " руб\n" +
                        "От: " + history.getNumbercardotpr() + " (" + history.getFioOtpr() + ")\n" +
                        "К: " + history.getNumbercardpol() + " (" + history.getFioPol() + ")\n" +
                        "Дата: " + history.getDate());
            }
            return historyStrings;
        }

        @Override
        protected void onPostExecute(List<String> history) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminHistoryActivity.this,
                    android.R.layout.simple_list_item_1, history);
            lvHistory.setAdapter(adapter);
        }
    }
}
