package com.example.bank2;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.models.User;

public class AccountActivity extends AppCompatActivity {
    private TextView tvWelcome;
    private Button btnMyCards, btnCreateCard, btnTransfer, btnHistory, btnVklad, btnSbp, btnAdmin, btnLogout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        tvWelcome = findViewById(R.id.tvWelcome);
        btnMyCards = findViewById(R.id.btnMyCards);
        btnCreateCard = findViewById(R.id.btnCreateCard);
        btnTransfer = findViewById(R.id.btnTransfer);
        btnHistory = findViewById(R.id.btnHistory);
        btnVklad = findViewById(R.id.btnVklad);
        btnSbp = findViewById(R.id.btnSbp);
        btnAdmin = findViewById(R.id.btnAdmin);
        btnLogout = findViewById(R.id.btnLogout);

        tvWelcome.setText("Добро пожаловать, " + User.getFio() + "!");

        // Настройка кнопок
        btnMyCards.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, MyCardsActivity.class);
            startActivity(intent);
        });

        btnCreateCard.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, CreateCardActivity.class);
            startActivity(intent);
        });

        btnTransfer.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, PerevodActivity.class);
            startActivity(intent);
        });

        btnHistory.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, HistoryActivity.class);
            startActivity(intent);
        });

        btnVklad.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, VkladActivity.class);
            startActivity(intent);
        });

        btnSbp.setOnClickListener(v -> {
            Intent intent = new Intent(AccountActivity.this, SbpConnectActivity.class);
            startActivity(intent);
        });

        // Показываем кнопку админ-панели только для админов
        if (User.getAdmin() == 1) {
            btnAdmin.setVisibility(android.view.View.VISIBLE);
            btnAdmin.setOnClickListener(v -> {
                Intent intent = new Intent(AccountActivity.this, AdminHistoryActivity.class);
                startActivity(intent);
            });
        }

        btnLogout.setOnClickListener(v -> {
            finish();
            Intent intent = new Intent(AccountActivity.this, LoginActivity.class);
            startActivity(intent);
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.menu_transfer) {
            startActivity(new Intent(this, PerevodActivity.class));
            return true;
        } else if (id == R.id.menu_my_cards) {
            startActivity(new Intent(this, MyCardsActivity.class));
            return true;
        } else if (id == R.id.menu_history) {
            startActivity(new Intent(this, HistoryActivity.class));
            return true;
        } else if (id == R.id.menu_vklad) {
            startActivity(new Intent(this, VkladActivity.class));
            return true;
        } else if (id == R.id.menu_sbp) {
            startActivity(new Intent(this, SbpConnectActivity.class));
            return true;
        } else if (id == R.id.menu_logout) {
            finish();
            startActivity(new Intent(this, LoginActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
