package com.example.bank2;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.bank2.database.DataBaseHandler;
import com.example.bank2.models.User;

import java.sql.SQLException;

public class LoginActivity extends AppCompatActivity {
    private EditText etLogin, etPassword, etPassportSeries, etPassportNumber;
    private Button btnLogin, btnSignUp;
    private DataBaseHandler dbHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        etLogin = findViewById(R.id.etLogin);
        etPassword = findViewById(R.id.etPassword);
        etPassportSeries = findViewById(R.id.etPassportSeries);
        etPassportNumber = findViewById(R.id.etPassportNumber);
        btnLogin = findViewById(R.id.btnLogin);
        btnSignUp = findViewById(R.id.btnSignUp);

        dbHandler = new DataBaseHandler(this);

        btnLogin.setOnClickListener(v -> loginUser());
        btnSignUp.setOnClickListener(v -> {
            Intent intent = new Intent(LoginActivity.this, SignUpActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String login = etLogin.getText().toString().trim();
        String password = etPassword.getText().toString().trim();
        String passportSeries = etPassportSeries.getText().toString().trim();
        String passportNumber = etPassportNumber.getText().toString().trim();

        if (login.isEmpty()) {
            etLogin.setError("Введите логин");
            Toast.makeText(this, "Введите логин", Toast.LENGTH_SHORT).show();
            return;
        }
        if (password.isEmpty()) {
            etPassword.setError("Введите пароль");
            Toast.makeText(this, "Введите пароль", Toast.LENGTH_SHORT).show();
            return;
        }
        if (passportSeries.isEmpty() || passportNumber.isEmpty()) {
            Toast.makeText(this, "Введите паспортные данные", Toast.LENGTH_SHORT).show();
            return;
        }

        // Валидация паспорта
        if (passportSeries.length() != 4 || !passportSeries.matches("\\d+")) {
            etPassportSeries.setError("Серия паспорта должна содержать 4 цифры");
            Toast.makeText(this, "Серия паспорта должна содержать 4 цифры", Toast.LENGTH_SHORT).show();
            return;
        }

        if (passportNumber.length() != 6 || !passportNumber.matches("\\d+")) {
            etPassportNumber.setError("Номер паспорта должен содержать 6 цифр");
            Toast.makeText(this, "Номер паспорта должен содержать 6 цифр", Toast.LENGTH_SHORT).show();
            return;
        }

        String passport = passportSeries + " " + passportNumber;
        new LoginTask().execute(login, password, passport);
    }

    // Класс для хранения результата логина
    private static class LoginResult {
        String loginResult;
        int cardCount;
        Exception error;

        LoginResult(String loginResult, int cardCount, Exception error) {
            this.loginResult = loginResult;
            this.cardCount = cardCount;
            this.error = error;
        }
    }

    private class LoginTask extends AsyncTask<String, Void, LoginResult> {
        @Override
        protected LoginResult doInBackground(String... params) {
            String loginResult = dbHandler.logIn(params[0], params[1], params[2]);
            
            // Если логин успешен, проверяем карты в фоновом потоке
            if (!loginResult.equals("-1") && !loginResult.equals("-2")) {
                try {
                    int cardCount = dbHandler.checkCards(loginResult);
                    return new LoginResult(loginResult, cardCount, null);
                } catch (SQLException | ClassNotFoundException e) {
                    return new LoginResult(loginResult, -1, e);
                }
            }
            
            return new LoginResult(loginResult, -1, null);
        }

        @Override
        protected void onPostExecute(LoginResult result) {
            if (result.loginResult.equals("-2")) {
                Toast.makeText(LoginActivity.this, "Ошибка подключения к базе данных. Проверьте интернет-соединение.", Toast.LENGTH_LONG).show();
                return;
            }
            
            if (result.error != null) {
                Toast.makeText(LoginActivity.this, "Ошибка подключения к базе данных: " + result.error.getMessage(), Toast.LENGTH_LONG).show();
                return;
            }
            
            if (!result.loginResult.equals("-1")) {
                Toast.makeText(LoginActivity.this, "Вы успешно авторизовались!", Toast.LENGTH_SHORT).show();
                Intent intent;
                // Всегда открываем AccountActivity для всех пользователей
                // Админ может перейти в AdminHistoryActivity через меню
                if (result.cardCount > 0) {
                    intent = new Intent(LoginActivity.this, AccountActivity.class);
                } else {
                    // Если карт нет, сначала создаем карту
                    intent = new Intent(LoginActivity.this, CreateCardActivity.class);
                }
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(LoginActivity.this, "Неверный логин, пароль или паспорт", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
