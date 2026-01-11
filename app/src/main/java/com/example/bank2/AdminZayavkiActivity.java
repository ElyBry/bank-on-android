package com.example.bank2;

import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;
import com.example.bank2.models.User;
import com.example.bank2.models.Zayavki;

import java.util.ArrayList;
import java.util.List;

public class AdminZayavkiActivity extends AppCompatActivity {
    private ListView lvZayavki;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin_zayavki);

        lvZayavki = findViewById(R.id.lvZayavki);
        dbHandler = new DataBaseHandler(this);

        new LoadZayavkiTask().execute();
    }

    private class LoadZayavkiTask extends AsyncTask<Void, Void, List<String>> {
        @Override
        protected List<String> doInBackground(Void... voids) {
            List<Zayavki> zayavkiList = dbHandler.LoadZayavki(User.getLogin());
            List<String> zayavkiStrings = new ArrayList<>();
            for (Zayavki zayavka : zayavkiList) {
                zayavkiStrings.add("Логин: " + zayavka.getLogin_user() + "\n" +
                        "Проблема: " + zayavka.getProblem() + "\n" +
                        "Текст: " + zayavka.getProblem_text() + "\n" +
                        "Дата: " + zayavka.getSend_date() + "\n" +
                        "Статус: " + zayavka.getIs_successful());
            }
            return zayavkiStrings;
        }

        @Override
        protected void onPostExecute(List<String> zayavki) {
            ArrayAdapter<String> adapter = new ArrayAdapter<>(AdminZayavkiActivity.this,
                    android.R.layout.simple_list_item_1, zayavki);
            lvZayavki.setAdapter(adapter);
        }
    }
}
